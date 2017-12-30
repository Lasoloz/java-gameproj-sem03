package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.util.ResourceLoader;

import java.io.BufferedReader;
import java.util.ArrayList;

public class TerrainCollection implements Disposable {
    private ArrayList<TerrainSet> terrainSets = new ArrayList<TerrainSet>();
    private TextureAtlas atlas;


    public TerrainCollection(String terrainFilePath) throws GraphicsException {
        FileHandle terrainFile =
                ResourceLoader.loadInternalOrLocalResource(terrainFilePath);
        if (terrainFile == null) {
            throw new GraphicsException(
                    "TerrainCollection",
                    "Terrain definition does not exist!"
            );
        }

        BufferedReader br = terrainFile.reader(256);
        try {
            processTerrainDefinition(br);
        } catch (Exception ex) {
            dispose();
            throw new GraphicsException(
                    "TerrainCollection",
                    ex.getMessage()
            );
        }
    }

    private void processTerrainDefinition(
            BufferedReader br
    ) throws GraphicsException {
        try {
            // Load up atlas file:
            String atlasFilePath = br.readLine();
            assert atlasFilePath != null : "null line for `atlasFilePath`";
            FileHandle atlasFileHandle =
                    ResourceLoader.loadInternalOrLocalResource(atlasFilePath);
            assert atlasFileHandle != null : "atlas file does not exist";
            atlas = new TextureAtlas(atlasFileHandle);

            // Read terrain set count:
            String terrainSetCountLine = br.readLine();
            assert terrainSetCountLine != null : "`terrainSetCountLine` is null";
            int terrainSetCount = Integer.parseInt(terrainSetCountLine);

            for (int i = 0; i < terrainSetCount; ++i) {
                // Read next terrain set prefix:
                String prefix = br.readLine();
                assert prefix != null : "`prefix` line `" + i + "` is null";
                // Create a terrain set:
                terrainSets.add(new TerrainSet(atlas, prefix));
            }
        } catch (Exception ex) {
            throw new GraphicsException(
                    "TerrainCollection>processTerrainDefinition",
                    ex.getMessage()
            );
        }
    }


    public TerrainSet getSet(int modifier) {
        return terrainSets.get(modifier % terrainSets.size());
    }


    @Override
    public void dispose() {
        if (atlas != null) {
            atlas.dispose();
            terrainSets.clear();
        }
    }
}
