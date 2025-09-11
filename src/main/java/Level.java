import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
public class Level {
    public boolean running;
    private TextGraphics graphics;
    private Screen screen;
    private Terminal terminal;
    private static Level instance;
    private LinkedBlockingQueue<KeyStroke> queue;
    private KeyListener keyListener;
    private Level(){}//private constructor making this class a singleton
    public static synchronized Level getLevel(){
        if(instance == null){
            try {
                Terminal terminal = new DefaultTerminalFactory().createTerminal();
                Screen screen = new TerminalScreen(terminal);
                instance = new Level();
                instance.screen = screen;
                instance.terminal = terminal;
                instance.graphics = screen.newTextGraphics();
                screen.startScreen();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> { //shutdown hook to avoid resource leaks
                    try {
                        System.out.println("exiting");
                        if (screen != null) {
                            if (instance.keyListener != null) instance.keyListener.interrupt();
                            if (screen != null) {
                                screen.stopScreen();
                                screen.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            } catch (IOException e) {
                e.printStackTrace();
            }
            instance.queue = new LinkedBlockingQueue<KeyStroke>();
            KeyListener keyListener = new KeyListener(instance.screen, instance.queue);
            instance.keyListener = keyListener;
            keyListener.start();
        }
        return instance;
    }
    public static TextGraphics getTextGraphics(){
        return getLevel().graphics;
    }
    public static Screen getScreen(){
        return getLevel().screen;
    }
    public static Terminal getTerminal(){
        return getLevel().terminal;
    }
    public static LinkedBlockingQueue<KeyStroke> getKeyQueue(){
        return getLevel().queue;
    }
    public static KeyListener getKeyListener(){
        return getLevel().keyListener;
    }
    public void stop(){
        System.out.println("exiting");
        stopMainLoop();
        /* //TODO for later when I create a save method
         * if(Player.canSave){
         * player.save()
         * }
         */
        if (instance.keyListener != null) instance.keyListener.interrupt();
         try {
            if (screen != null) {
                screen.stopScreen();
                screen.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startMainLoop(){
        final int TICKS_PER_SECOND = 20;
        final long NANO_PER_TICK = 1_000_000_000L / TICKS_PER_SECOND;
        running = true;
        long lastTime = System.nanoTime();
        long accumulated = 0;

        while (running) {
            long now = System.nanoTime();
            long delta = now - lastTime;
            lastTime = now;
            accumulated += delta;

            while (accumulated >= NANO_PER_TICK) {
                Tick.onTick();  // logic update
                accumulated -= NANO_PER_TICK;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    private void stopMainLoop(){
        running = false;
    }
}
