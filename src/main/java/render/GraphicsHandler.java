package render;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalPosition;
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
        graphics.drawImage(TerminalPosition.TOP_LEFT_CORNER, toDraw);

        try {//refresh screen
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
