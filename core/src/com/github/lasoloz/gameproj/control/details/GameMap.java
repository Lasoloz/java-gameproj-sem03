package com.github.lasoloz.gameproj.control.details;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;

public class GameMap {
    private int[][] map;

    public GameMap() {
        map = null;
    }

    public boolean isLoaded() {
        return map != null;
    }


    public boolean loadMap(String mapFileName) {
        String fullMapFileName = "maps/" + mapFileName;
        FileHandle mapFile = Gdx.files.internal(fullMapFileName);

        if (!mapFile.exists()) {
            Gdx.app.error("GameMap", "Map file does not exist!");
            return false;
        }

        BufferedReader br = mapFile.reader(256);

        try {
            String dataType = br.readLine();

            if (dataType == null) {
                Gdx.app.error(
                        "GameMap",
                        "Data type line must contain a string specification!"
                );
                return false;
            }

            String mapSize = br.readLine();
            if (mapSize == null) {
                Gdx.app.error(
                        "GameMap",
                        "Map size line must have content!"
                );
            }
            String[] nums = mapSize.split(" +");
            int height = Integer.parseInt(nums[0]);
            int width = Integer.parseInt(nums[1]);

            if (dataType.equals("num")) {
                return parseNumericData(br, width, height);
            } else {
                return parseBinaryData(br, width, height);
            }

        } catch (IOException ex) {
            Gdx.app.error("GameMap", "IOException occoured!");
            map = null;
            return false;
//        } catch (IndexOutOfBoundsException ex) {
//            Gdx.app.error("GameMap", "IndexOutOfBoundsException occoured!");
//            System.err.println(ex.toString());
//            map = null;
//            return false;
        } catch (NumberFormatException ex) {
            Gdx.app.error("GameMap", "NumberFormatException occoured!");
            map = null;
            return false;
        }
    }


    private boolean parseNumericData(
            BufferedReader br,
            int width,
            int height
    ) throws IOException {
        int countX = 0;
        int countY = 0;
        map = new int [height][width];
        while (countX < width || countY < height) {
            String currentData = br.readLine();
            if (currentData == null) {
                return false;
            }
            String[] tiles = currentData.split(" +");

            for (int i = 0; i < tiles.length; ++i) {
                try {
                    map[countY][countX] = Integer.parseInt(tiles[i]);
                    ++countX;
                    if (countX == width) {
                        countX = 0;
                        ++countY;
                        if (countY == height) {
                            return true;
                        }
                    }
                } catch (NumberFormatException ex) {
                    Gdx.app.error("GameMap", "Failed to read map tile!");
                }
            }
        }

        return true;
    }


    private boolean parseBinaryData(BufferedReader br, int width, int height) {
        /// TODO: implement this!
        return false;
    }


    public int getWidth() {
        return map[0].length;
    }

    public int getHeight() {
        return map.length;
    }

    public int getData(int x, int y) {
        return map[y][x];
    }
}
