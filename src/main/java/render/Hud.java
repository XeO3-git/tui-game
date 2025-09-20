package render;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;

import logic.Player;

public class Hud {
    public static Hud instance;
    public TextImage render;
    private Hud(){}
    public static Hud getHud(){//returns an hud object and updates displayed values
        if(instance == null){
            instance = new Hud();
            instance.render = new BasicTextImage(36, 1);
            //hp
            String hp = Integer.toString(Player.getPlayer().getHp());
            for(int i =0; i<hp.length(); i++){
                instance.render.setCharacterAt(i, 0, TextCharacter.fromCharacter(hp.charAt(i), TextColor.ANSI.RED, TextColor.ANSI.RED_BRIGHT, SGR.BOLD)[0]);
            }
            for(int i =0; i<10-hp.length(); i++){
                instance.render.setCharacterAt(i+hp.length(), 0, TextCharacter.fromCharacter('█', TextColor.ANSI.RED_BRIGHT, TextColor.ANSI.RED_BRIGHT, SGR.BOLD)[0]);
            }
           //TODO repeat with mana and stamina
        }
        return instance;
    }

}
