package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.blueprints.BlueprintException;
import com.github.lasoloz.gameproj.blueprints.TerrainTileBlueprint;
import com.github.lasoloz.gameproj.entitites.TerrainTile;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.TextureWrapper;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameProj extends ApplicationAdapter { 
    SpriteBatch batch;
//    Texture img;
//    private BitmapFont font;
    Drawable testWallImg;
    Drawable testGroundImg;
    TerrainTile instance0;
    TerrainTile instance1;

    OrthographicCamera camera;

    @Override
    public void create () {
        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");
//        testWallImg = new TextureWrapper("terrain/proto/proto-wall.png");
//        testGroundImg = new TextureWrapper("terrain/proto/proto-ground.png");
        testWallImg = new TextureWrapper("terrain/dirt/dirt-wall01.png");
        testGroundImg = new TextureWrapper("terrain/dirt/dirt01-.png");
        int[] modifiers = new int[4];
        for (int i = 0; i < 4; ++i) { modifiers[i] = 0; }
        try {
            TerrainTileBlueprint terrainTileBlueprint = new TerrainTileBlueprint(
                    testGroundImg, testWallImg, modifiers
            );

            instance0 = new TerrainTile(new Vec2f(5f, 15f), terrainTileBlueprint, 1);
            instance1 = new TerrainTile(new Vec2f(5, 15f), terrainTileBlueprint, 2);
            instance0.addContent(instance1);
        } catch (BlueprintException ex) {
            System.err.println(ex.getMessage());
        }


        // Create camera:
        int displayW = Gdx.graphics.getWidth();
        int displayH = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(displayW/8, displayH/8);
        camera.translate(25, 25);
        camera.update();
//        font = new BitmapFont();
//        font.setColor(Color.BLUE);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        font.draw(batch, "Hello World!", 200, 200);
//        batch.draw(img, 0, 0);
        instance0.draw(batch, 0, 0f);
//        instance1.draw(batch, 0, 0f);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        testWallImg.dispose();
        testGroundImg.dispose();
//        img.dispose();
//        font.dispose();
    }
}
