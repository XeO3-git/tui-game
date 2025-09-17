package logic;
import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Tick {
    public static void onTick(){
        //System.out.println("tick");
        handleInput();
    }
    private static void handleInput(){
       KeyStroke stroke = Level.getKeyQueue().poll();
       if(!(stroke == null)){
            if(stroke.getKeyType() == KeyType.Escape) Level.getLevel().stop();
            if(stroke.getKeyType() == KeyType.Character){
                if(stroke.getCharacter().charValue() == 'w'){
                    Player.getPlayer().row -=1;
                }
                if(stroke.getCharacter().charValue() == 'a'){
                    Player.getPlayer().column -=1;
                }
                if(stroke.getCharacter().charValue() == 's'){
                    Player.getPlayer().row +=1;
                }
                if(stroke.getCharacter().charValue() == 'd'){
                    Player.getPlayer().column +=1;
                }
            }
       }
    }
}
