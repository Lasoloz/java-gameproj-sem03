package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;

public class TerrainAssets {
    private TerrainSet terrainSet;
    private TerrainSet terrainSetAlt;

    public TerrainAssets(String pathToAssets) throws GraphicsException {
        String fullPathToAssets = "terrains/" + pathToAssets;
        FileHandle terrainDataFile = Gdx.files.internal(fullPathToAssets);

        if (!terrainDataFile.exists()) {
            throw new GraphicsException(
                    "TerrainAssets",
                    "Terrain asset on specified path does not exist!"
            );
        }

        BufferedReader br = terrainDataFile.reader(512);

        terrainSet = readInto(br);
        terrainSetAlt = readInto(br);
    }

    private TerrainSet readInto(BufferedReader br) throws GraphicsException {
        try {
            Drawable[] terrains = new Drawable[TerrainSet.TERRAIN_COUNT];
            for (int i = 0; i < TerrainSet.TERRAIN_COUNT; ++i) {
                String line = br.readLine();
                if (line == null) {
                    throw new GraphicsException(
                            "TerrainAssets",
                            "`.terrain` file has invalid number of lines!"
                    );
                }

                terrains[i] = new TextureWrapper("terrains/" + line);
            }

            return new TerrainSet(terrains);
        } catch (IOException ex) {
            throw new GraphicsException(
                    "TerrainAssets",
                    "Caught IOException: " + ex.getMessage()
            );
        }
    }


    public TerrainSet getTerrainSet() {
        return terrainSet;
    }

    public TerrainSet getTerrainSetAlt() {
        return terrainSetAlt;
    }
}
