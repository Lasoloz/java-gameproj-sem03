package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.github.lasoloz.gameproj.blueprints.BlueprintException;
import com.github.lasoloz.gameproj.blueprints.BlueprintSet;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.graphics.GraphicsException;
import com.github.lasoloz.gameproj.graphics.TerrainCollection;
import com.github.lasoloz.gameproj.util.ResourceLoader;

public class GameMap {
    private GameMapTile[][] map;
    private TerrainCollection terrainCollection;
    private BlueprintSet blueprintSet;

    public GameMap() {
        map = null;
    }

    public boolean isLoaded() {
        return map != null;
    }


    public boolean loadMap(String mapFileName) {
        Gdx.app.log(
                "GameMap",
                "Attempting to load game map `" +
                        mapFileName +
                        "`..."
        );
        FileHandle mapFile = ResourceLoader.loadInternalOrLocalResource(
                mapFileName
        );

        if (mapFile == null) {
            Gdx.app.error("GameMap", "Map file does not exist!");
            return false;
        }

        JsonValue root;
        try {
            root = new JsonReader().parse(mapFile);
        } catch (SerializationException ex) {
            Gdx.app.error("GameMap", "Failed to parse JSON!");
            return false;
        }

        try {
            int width = root.getInt("width");
            int height = root.getInt("height");

            // Parse map data:
            map = new GameMapTile[height][width];
            parseData(root.get("data"));

            // Parse terrain set:
            String terrainPath = root.getString("terrain");
            terrainCollection = new TerrainCollection(terrainPath);
            Gdx.app.log("GameMap", "Terrain data parsed!");

            // Parse blueprint set:
            String blueprintSetPath = root.getString("blueprint_set");
            blueprintSet = new BlueprintSet(blueprintSetPath);
            Gdx.app.log("GameMap", "Blueprint data parsed!");

            // Parse units:
            parseUnits(root.get("units"));

            return true;
        } catch (NullPointerException ex) {
            Gdx.app.error("GameMap", "Null element in map file!");
            return false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            Gdx.app.error("GameMap","Invalid map data (index out of bounds)!");
            return false;
        } catch (GraphicsException ex) {
            Gdx.app.error("GameMap", "Failed to load Terrain Collection!");
            return false;
        } catch (BlueprintException ex) {
            Gdx.app.error("GameMap", "Failed to load blueprint set!");
            return false;
        }
    }

    private void parseData(JsonValue data) {
        int row = 0;

        while (row < map.length) {
            JsonValue rowIter = data.get(row);
            int col = 0;

            while (col < map[0].length) {
                JsonValue colIter = rowIter.get(col);
                map[row][col] = new GameMapTile(colIter.asInt());
                ++col;
            }

            ++row;
        }

        Gdx.app.log("GameMap", "Map data parsed!");
    }

    private void parseUnits(JsonValue units) {
        JsonValue unitIter = units.child;

        while (unitIter != null) {
            int type = unitIter.getInt("type");
            int posX = unitIter.getInt("x");
            int posY = unitIter.getInt("y");

            map[posY][posX].addContent(
                    new Instance(blueprintSet.getBlueprint(type))
            );

            unitIter = unitIter.next;
        }

        Gdx.app.log("GameMap", "Unit list parsed!");
    }


    public int getWidth() {
        return map[0].length;
    }

    public int getHeight() {
        return map.length;
    }

    public int getData(int x, int y) {
        return map[y][x].getTileCode();
    }


    public Instance getInstance(int x, int y) {
        return map[y][x].getContent();
    }

    public GameMapTile getGameMapTile(int x, int y) {
        return map[y][x];
    }

    public TerrainCollection getTerrainCollection() {
        return terrainCollection;
    }
}
