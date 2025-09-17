package logic;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class KeyListener extends Thread{//screen needs to be accessed from this, yet this also needs to be accessed by the logic code
    private final Screen screen;//maybe create the screen in the main method and then start the keylistner from there? and add keylistner as a param in Level.getlevel
    private final LinkedBlockingQueue<KeyStroke> queue;
    public KeyListener(Screen screen, LinkedBlockingQueue<KeyStroke> queue){
        this.screen = screen;
        this.queue = queue;
    }
    public void run() {
          try {
            while (!Thread.currentThread().isInterrupted()) {
                var key = screen.readInput(); 
                if (key != null) {
                    queue.offer(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
