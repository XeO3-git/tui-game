package render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TextCharacter;

public class WorldRenderConverter{
    public TextCharacter[][] fromString(){
        
    }
    public static TextCharacter[][] colorBasic(TextCharacter[][] world) {
        int height = world.length;
        int width = world[0].length;

        TextCharacter[][] result = new TextCharacter[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TextCharacter org = world[y][x];
                result[y][x] = getColorBasic(org); // replace with colored version
            }
        }

        return result;
    }
    private static TextCharacter getColorBasic(TextCharacter ch){
        switch ((char)ch.getCharacterString()) {
            case 'b' : 
                return TextCharacter.fromCharacter(█, ANSI.BLUE, ANSI.BLUE);
                break;
        
            default: return ch;
                break;
        }    
    }

    public static TextCharacter[][] scaleDungeon(char[][] tiles, int scale) {//nearest neighbor scaling? hope works
        int srcH = tiles.length;
        int srcW = tiles[0].length;

        int finH = srcH * scale;
        int finW = srcW * scale;

        TextCharacter[][] result = new TextCharacter[finH][finW];
        for (int y = 0; y < srcH; y++) {
            for (int x = 0; x < srcW; x++) {
                char org = tiles[y][x];
                for (int dy = 0; dy < scale; dy++) {
                    for (int dx = 0; dx < scale; dx++) {
                        result[y * scale + dy][x * scale + dx] = TextCharacter.fromCharacter(org)[0];
                    }
                }
            }
        }

        return result;
    }
}
