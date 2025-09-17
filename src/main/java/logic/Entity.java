package logic;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;

public  class Entity {
    public int row = 0;
    public int column = 0;
    public TextColor colorFore;
    public TextColor colorBack;
    public SGR modifier;
    public String display;
    boolean isAlive = true;
    int maxHealth;
    int currentHealth;
    public Entity(int maxHealth, int row, int column, TextColor colorBack, TextColor colorFore, SGR modifier, String display){
        this.currentHealth = maxHealth;
        this.row = row;
        this.column = column;
        this.colorBack = colorBack;
        this.colorFore = colorFore;
        this.modifier = modifier;
        this.display = display;
    }
    public TextImage getRender(){
        BasicTextImage image = new BasicTextImage((int)display.lines().count(), display.length());
        TextGraphics graphics = image.newTextGraphics();
        graphics.setForegroundColor(colorFore);
        graphics.setBackgroundColor(colorBack);
        int row = 0;
        for(String line : display.split("\r?\n")) {
            graphics.putString(0, row++, line);
        }
        return image;
    }
    public void damage(int ammount){
        this.currentHealth -=1;
        if(currentHealth == 0) this.onDeath();
    }
    public void onDeath(){
        this.isAlive = false;
    }
    public synchronized TerminalPosition getPos(){
        return new TerminalPosition(column, row);
    }
    public synchronized int getMaxHp(){
        return maxHealth;
    }
    public synchronized int getHp(){
        return currentHealth;
    }

}
