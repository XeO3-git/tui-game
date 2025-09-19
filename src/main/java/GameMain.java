

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import logic.Level;
import render.GraphicsHandler;

public class GameMain {
    public static void main(String[] args) {
        try {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        Level.startLevel(screen);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> { //shutdown hook to avoid resource leaks
                System.out.println("exiting");
                if (screen != null) {
                    Level.stop();
                    try {
                        screen.stopScreen();
                        screen.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                }
        }));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }  
}
