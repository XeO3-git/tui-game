package logic;
import java.io.IOException;
import java.util.ArrayList;
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
    private LinkedBlockingQueue<KeyStroke> queue;
    private Level(){}//private constructor making this class a singleton
    public static void startLevel(Screen screen){
        instance = new Level();
        instance.player = Player.getPlayer();
        instance.queue = new LinkedBlockingQueue<KeyStroke>();
        KeyListener keyListener = new KeyListener(screen, getKeyQueue());
        instance.keyListener = keyListener;
        keyListener.start();
    }
    public static synchronized Level getLevel(){
        return instance;
    }
    public static LinkedBlockingQueue<KeyStroke> getKeyQueue(){
        return getLevel().queue;
    }
    public static Player getPlayer(){
        return getLevel().player;
    }
    public static KeyListener GetKeyListener(){
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
