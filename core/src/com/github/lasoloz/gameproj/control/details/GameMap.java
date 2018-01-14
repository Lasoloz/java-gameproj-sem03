package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.github.lasoloz.gameproj.blueprints.*;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.entitites.UnitInstance;
import com.github.lasoloz.gameproj.graphics.GraphicsException;
import com.github.lasoloz.gameproj.graphics.TerrainCollection;
import com.github.lasoloz.gameproj.math.Vec2i;
import com.github.lasoloz.gameproj.util.ResourceLoader;

import java.util.ArrayList;


/**
 * Class defining a map for the game
 * Private members:
 * map - Map data containing `GameMapTile`s
 * terrainCollection - Terrain sets used for the map (tiles used for drawing
 * map)
 * blueprintSet - Blueprint set used for instances in the map
 * originalPlayerPos - Starting position for the player
 * originalEnemyPositions - Original positions for the enemy instances
 * @see GameMapTile
 * @see BlueprintSet
 */
public class GameMap {
    private GameMapTile[][] map;
    private TerrainCollection terrainCollection;
    private BlueprintSet blueprintSet;
    private Vec2i originalPlayerPos;
    // Stupid, but should work:
    private ArrayList<Vec2i> originalEnemyPositions;

    GameMap() {
        map = null;
    }


    /**
     * Load map data from a map file (`json` format)
     * @param mapFileName Path to map file
     * @return Boolean value stating the success of the operation
     */
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

        // Get root. Catch serialization exceptions
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
            originalEnemyPositions = new ArrayList<Vec2i>();
            return parseUnits(root.get("units"));
        } catch (NullPointerException ex) {
            // Catch different exceptions and return them as `false`
            Gdx.app.error("GameMap", "Null element in map file!");
            return false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Catch different exceptions and return them as `false`
            Gdx.app.error("GameMap","Invalid map data (index out of bounds)!");
            return false;
        } catch (GraphicsException ex) {
            // Catch different exceptions and return them as `false`
            Gdx.app.error("GameMap", "Failed to load Terrain Collection!");
            return false;
        } catch (BlueprintException ex) {
            // Catch different exceptions and return them as `false`
            Gdx.app.error("GameMap", "Failed to load blueprint set!");
            return false;
        }
    }

    /**
     * Parse terrain data of the map
     * @param data Data sub-object in the json file
     */
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

    /**
     * Parse instance specification from the json file
     * @param units Instance list in the map file
     * @return Boolean value stating if operation was successful.
     */
    private boolean parseUnits(JsonValue units) {
        JsonValue unitIter = units.child;

        while (unitIter != null) {
            int type = unitIter.getInt("type");
            int posX = unitIter.getInt("x");
            int posY = unitIter.getInt("y");

            Blueprint currentBlueprint = blueprintSet.getBlueprint(type);
            if (currentBlueprint.getType() == InstanceType.PLAYER) {
                if (originalPlayerPos != null) {
                    Gdx.app.error(
                            "GameMap",
                            "Map cannot contain multiple player instances!"
                    );
                    return false;
                }

                originalPlayerPos = new Vec2i(posX, posY);
            } else if (currentBlueprint.getType() == InstanceType.ENEMY) {
                // Register enemy:
                originalEnemyPositions.add(new Vec2i(posX, posY));
            }

            map[posY][posX].setContent(createInstance(currentBlueprint));

            unitIter = unitIter.next;
        }

        Gdx.app.log("GameMap", "Unit list parsed!");
        if (originalPlayerPos == null) {
            Gdx.app.error(
                    "GameMap",
                    "Map must contain at least one player instance!"
            );
            return false;
        }

        return true;
    }

    /**
     * Create a new instance based on the blueprint
     * @param currentBlueprint Current selected blueprint for the unit
     * @return New instance specified by the map.
     */
    private Instance createInstance(Blueprint currentBlueprint) {
        switch (currentBlueprint.getType()) {
            case PLAYER:
            case ENEMY:
                // I have to do a stupid thing down here :/
                return new UnitInstance((UnitBlueprint) currentBlueprint);
            default:
                return new Instance(currentBlueprint);
        }
    }


    /**
     * Get width of the map (in tile counts)
     * @return Width of the map
     */
    public int getWidth() {
        return map[0].length;
    }

    /**
     * Get height of the map (in tile counts)
     * @return Height of the map
     */
    public int getHeight() {
        return map.length;
    }

    /**
     * Get map terrain data from coordinates
     * @param x x coordinate of selected tile
     * @param y y coordinate of selected tile
     * @return Terrain code of specified tile
     */
    public int getData(int x, int y) {
        return map[y][x].getTileCode();
    }

    /**
     * Get map terrain data from coordinates (using `Vec2i`)
     * @param pos Vector specifying map coordinates
     * @return Terrain code of specified tile
     * @see Vec2i
     */
    public int getData(Vec2i pos) {
        return map[pos.y][pos.x].getTileCode();
    }

    /**
     * Get original position of the player
     * @return Vector specifying player's original position
     */
    public Vec2i getOriginalPlayerPos() {
        return originalPlayerPos;
    }


    /**
     * Get instance standing on specific tile
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return Instance on the tile
     */
    public Instance getInstance(int x, int y) {
        return map[y][x].getContent();
    }

    /**
     * Get `GameMapTile` object of the specific coordinates (using `Vec2i`)
     * @param pos Vector specifying map coordinates
     * @return Map tile object.
     * @see GameMapTile
     */
    public GameMapTile getGameMapTile(Vec2i pos) {
        return map[pos.y][pos.x];
    }

    /**
     * Get `GameMapTile` object of the specific coordinates
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return Map tile object
     * @see GameMapTile
     */
    public GameMapTile getGameMapTile(int x, int y) {
        return map[y][x];
    }

    /**
     * Get terrain collection object of the current map
     * @return Terrain collection
     * @see TerrainCollection
     */
    public TerrainCollection getTerrainCollection() {
        return terrainCollection;
    }


    /**
     * Check if coordinates are inside map or not
     * @param pos Vector specifying map coordinates
     * @return Boolean stating if position is in map
     */
    public boolean inMap(Vec2i pos) {
        // Check if a position is in the map
        return !(pos.x < 0 ||
                pos.x >= map[0].length ||
                pos.y < 0 ||
                pos.y >= map.length
        );
    }


    /**
     * Get original enemy positions
     * @return Array list defining original positions of enemies
     */
    public ArrayList<Vec2i> getOriginalEnemyPositions() {
        return originalEnemyPositions;
    }
}
