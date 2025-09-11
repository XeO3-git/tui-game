import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Tick {
    public static void onTick(){
        //System.out.println("tick");
        HandleResize(); //if I put the graphics in a new thread, should this be part of that thread?
        HandleInput();
        HandleGraphics();//should this be done on a new thread?
    }
    private static void HandleInput(){
       KeyStroke stroke = Level.getKeyQueue().poll();
       if(!(stroke == null)){
            if(stroke.getKeyType() == KeyType.Escape) Level.getLevel().stop();
            if(stroke.getKeyType() == KeyType.Character){
                if(stroke.getCharacter().charValue() == 'w'){
                    Player.getPlayer().line -=1;
                }
                if(stroke.getCharacter().charValue() == 'a'){
                    Player.getPlayer().column -=1;
                }
                if(stroke.getCharacter().charValue() == 's'){
                    Player.getPlayer().line +=1;
                }
                if(stroke.getCharacter().charValue() == 'd'){
                    Player.getPlayer().column +=1;
                }
            }
       }
    }
    public static void HandleGraphics(){
        TextGraphics graphics = Level.getTextGraphics();
        graphics.fill('*');
        Player player = Player.getPlayer();
        graphics.putString(player.column,player.line, player.display);
        
        try {
            Level.getScreen().refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void HandleResize(){
        TerminalSize nSize = Level.getScreen().doResizeIfNecessary();
        if(nSize != null){
            Screen screen = Level.getScreen();
            screen.clear();
        }
    }
}
