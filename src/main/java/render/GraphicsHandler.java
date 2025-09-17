package render;

import java.util.ArrayList;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.screen.Screen;

import logic.Entity;
import logic.Level;
import logic.Player;

public class GraphicsHandler extends Thread{
    private static GraphicsHandler instance;
    private Screen screen;
    private TextGraphics graphics;
    private GraphicsHandler(){//level object keeps a map object, an array of entities, and an inventory object.  
        
    }
    public static GraphicsHandler getGraphics (){
        return instance;
    }
    public static void startGraphics(Screen screen){
        instance = new GraphicsHandler();
        instance.start();
        instance.screen = screen;
        instance.graphics = screen.newTextGraphics();
    }
    public synchronized void updateWorld(TextImage toDraw) {//hud will be rendered, world will be rendered based on level obj
        //add entities
        Player player = Player.getPlayer();
        addEntityToWorld(toDraw, player);
        for (Entity entity : Level.getLevel().entities) {
            addEntityToWorld(toDraw, entity);
        }

        //render hud
        Hud.getHud().render.copyTo(toDraw);//TODO center this
        
        graphics.drawImage(TerminalPosition.TOP_LEFT_CORNER, toDraw);
    }
    private void addEntityToWorld(TextImage world, Entity entity){
        entity.getRender().copyTo(
            world,                             
            0, 0,  
            entity.getRender().getSize().getColumns(),
            entity.getRender().getSize().getRows(),
            entity.getPos().getColumn(),
            entity.getPos().getRow()
        );

    }
}
