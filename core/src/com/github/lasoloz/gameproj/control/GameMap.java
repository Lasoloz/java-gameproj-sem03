package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.lasoloz.gameproj.blueprints.BlueprintException;
import com.github.lasoloz.gameproj.blueprints.TerrainGroundBlueprint;
import com.github.lasoloz.gameproj.graphics.AssetBundle;
import com.github.lasoloz.gameproj.graphics.Drawable;

import java.io.*;
import java.util.ArrayList;

public class GameMap {
//    private TerrainTile[][] tileMap;


//    public GameMap() {
//        tileMap = null;
//    }
//
//
//    public boolean loadMap(String path) {
//        try {
//            String filePath = "maps/" + path;
//            FileHandle mapFile = Gdx.files.internal(filePath);
//            if (!mapFile.exists()) {
//                Gdx.app.error(
//                        "GameMap",
//                        "Internal File does not exist (" + filePath + ")!"
//                );
//                return false;
//            }
//
//            ArrayList<TerrainGroundBlueprint> ttb =
//                    new ArrayList<TerrainGroundBlueprint>();
//
//            BufferedReader br = mapFile.reader(256);
//
//            String line = null;
//            boolean readTiles = true;
//            boolean readData = true;
//
//            while (true) {
//                Drawable tg = readTileTexture(br);
//                if (tg == null) {
//                    break;
//                }
//
//                Drawable tw = readTileTexture(br);
//                if (tw == null) {
//                    return false;
//                }
//
//                int[] boundModifiers = {0, 0, 0, 0};
//                ttb.add(new TerrainGroundBlueprint(tg, tw, boundModifiers));
//            }
//
//            while (true) {
//                break;
//            }
//            return true;
//        } catch (IOException ex) {
//            Gdx.app.error("GameMap:", "Failure: " + ex.getMessage());
//            return false;
//        } catch (BlueprintException ex) {
//            Gdx.app.error("GameMap:", "Failure: " + ex.getMessage());
//            return false;
//        }
//    }
//
//    private Drawable readTileTexture(
//            BufferedReader br
//    ) throws IOException {
//        String line = br.readLine();
//        if (line == null || line.equals("")) {
//            return null;
//        } else {
//            return AssetBundle.getAssetBundle().loadTexture(line);
//        }
//    }
}
