package render;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.screen.Screen;

import logic.Entity;
import logic.Level;
import logic.Player;

public class GraphicsHandler{

    public static void updateWorld(Screen screen, TextImage toDraw) {//hud will be rendered, world will be rendered based on level obj
        //add entities
        Player player = Player.getPlayer();
        addEntityToWorld(toDraw, player);
        for (Entity entity : Level.getEntities()) {
            addEntityToWorld(toDraw, entity);
        }

        //render hud
        Hud.getHud().render.copyTo(toDraw);//TODO center this
        TextGraphics graphics = screen.newTextGraphics();
        TerminalPosition pos = new TerminalPosition(screen.getTerminalSize().getColumns()/2, screen.getTerminalSize().getRows()-1);
        graphics.drawImage(pos, toDraw);

        try {//refresh screen
              TerminalSize nSize = Level.getScreen().doResizeIfNecessary(); 
              if(nSize != null) screen.clear();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void addEntityToWorld(TextImage world, Entity entity){
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
