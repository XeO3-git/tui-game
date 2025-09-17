package logic;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextImage;

public class Player extends Entity{
    public int maxMana = 10;
    public int currentMana;
    public int maxStamina = 10;
    public int currentStamina;
    public int maxHealth;
    public static Player instance;
    private Player(){
        //these values should be stored in a file. use gson lib to store these in a json file and load them when running getplayer, save when exiting game.
        //TODO these are dummy values. CHANGE THEM
        super(1, 0, 0, TextColor.ANSI.BLACK, TextColor.ANSI.WHITE, SGR.BOLD, "\\o/");
        currentMana = maxMana;
        currentStamina = maxStamina;
    }
    public static synchronized Player getPlayer(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }
        public synchronized int getMaxStamina(){
        return maxStamina;
    }
    public synchronized int getStaminam(){
        return currentStamina;
    }
        public synchronized int getMaxMana(){
        return maxMana;
    }
    public synchronized int getHp(){
        return maxMana;
    }
}
