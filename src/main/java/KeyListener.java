import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class KeyListener extends Thread{
    private final Screen screen;
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
