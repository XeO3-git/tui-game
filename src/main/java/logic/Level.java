package logic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import render.GraphicsHandler;

public class Level {
    public boolean running;
    private Player player;
    public ArrayList<Entity> entities;
    private static Level instance;
    private KeyListener keyListener;
    private Screen screen;
    private LinkedBlockingQueue<KeyStroke> queue;
    private Level(){}//private constructor making this class a singleton
    public static void startLevel(Screen screen){
        try {
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = new Level();
        instance.screen = screen;
        instance.player = Player.getPlayer();
        instance.queue = new LinkedBlockingQueue<KeyStroke>();
        KeyListener keyListener = new KeyListener(screen, getKeyQueue());
        instance.keyListener = keyListener;
        keyListener.start();
    }
    private static Level getLevel(){
        return instance;
    }
    public static List<Entity> getEntities(){
        return getLevel().entities;
    }
    public static LinkedBlockingQueue<KeyStroke> getKeyQueue(){
        return getLevel().queue;
    }
    public static Screen getScreen(){
        return Level.getLevel().screen;
    }
    public static Player getPlayer(){
        return getLevel().player;
    }
    public static KeyListener GetKeyListener(){
        return getLevel().keyListener;
    }
    public static void stop(){
        System.out.println("exiting");
        Runtime.getRuntime().exit(0);
        getLevel().stopMainLoop();
        /* //TODO for later when I create a save method
         * if(Player.canSave){
         * player.save()
         * }
         */
    }
    public static void startMainLoop(){
        final int TICKS_PER_SECOND = 20;
        final long NANO_PER_TICK = 1_000_000_000L / TICKS_PER_SECOND;
        getLevel().running = true;
        long lastTime = System.nanoTime();
        long accumulated = 0;

        while (getLevel().running) {
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
