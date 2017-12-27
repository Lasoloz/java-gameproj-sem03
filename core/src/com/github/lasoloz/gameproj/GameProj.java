package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.lasoloz.gameproj.control.GameInput;
import com.github.lasoloz.gameproj.entitites.TerrainGround;
import com.github.lasoloz.gameproj.graphics.*;
import com.github.lasoloz.gameproj.entitites.TerrainWall;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameProj extends ApplicationAdapter { 
    SpriteBatch batch;
//    Texture img;
//    private BitmapFont font;
    Drawable testWallImg;
    Drawable testGroundImg;
    Drawable testVoidImg;
    Drawable testVoidNImg;
    TerrainGround ground0, ground1;
    TerrainWall wall0, wall1;

    OrthographicCamera camera;
    GameInput input = new GameInput();
    Vec2f playerPos = new Vec2f(0f, 0f);
    Vec2f screenSize;

//    TerrainSet ts;
    TerrainAssets terrainAssets;

    @Override
    public void create () {
        // load asset bundle placeholder:
        if (!AssetBundle.getAssetBundle().initPlaceHolder("placeholder.png")) {
            System.exit(1);
        }

        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");
//        testWallImg = new TextureWrapper("terrain/proto/proto-wall.png");
//        testGroundImg = new TextureWrapper("terrain/proto/proto-ground.png");
//        testWallImg = new TextureWrapper("terrains/dirt/dirt-wall01.png");
//        testGroundImg = new TextureWrapper("terrains/dirt/dirt01.png");
//        testVoidImg = new TextureWrapper("terrains/dirt/dirt-void01.png");
//        testVoidNImg = new TextureWrapper("terrains/dirt/dirt-void-n01.png");
//        ts = new TerrainSet(
//                testGroundImg,
//                testVoidImg,
//                testVoidNImg,
//                testWallImg
//        );


//        int[] modifiers = new int[4];
//        for (int i = 0; i < 4; ++i) { modifiers[i] = 0; }
//        try {
//            TerrainGroundBlueprint terrainGroundBlueprint = new TerrainGroundBlueprint(
//                    testGroundImg, testWallImg, modifiers
//            );
//
//            ground0 = new TerrainGround(new Vec2f(0f, 0f), testGroundImg);
//            wall0 = new TerrainWall(new Vec2f(0f, 16f), testWallImg);
//            ground1 = new TerrainGround(new Vec2f(28f, 0f), testGroundImg);
//            wall1 = new TerrainWall(new Vec2f(56f, 16f), testWallImg);
////            instance0.addContent(instance1);
//        } catch (BlueprintException ex) {
//            System.err.println(ex.getMessage());
//        }


        // Create camera:
        int displayW = Gdx.graphics.getWidth();
        int displayH = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(displayW/4, displayH/4);
        camera.translate(25, 25);
        camera.update();

        screenSize = new Vec2f(displayW, displayH);

        try {
            terrainAssets = new TerrainAssets("dirt.terrain");
        } catch (GraphicsException ex) {
            Gdx.app.error("GameProj", ex.getMessage());
        }

        Gdx.input.setInputProcessor(input);
//        font = new BitmapFont();
//        font.setColor(Color.BLUE);
    }

    @Override
    public void render () {
        camera.update();
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        font.draw(batch, "Hello World!", 200, 200);
//        batch.draw(img, 0, 0);
//        ground0.draw(batch, 0f);
//        wall0.draw(batch, 0f);
//        ground1.draw(batch, 0f);
//        wall1.draw(batch, 0f);
        TerrainSet ts = terrainAssets.getTerrainSetAlt();
        ts.drawGround(batch, new Vec2f(0f, 0f), 0);
        ts.drawVoid(batch, new Vec2f(28f, 0f), 0);
        ts.drawVoidN(batch, new Vec2f(28f, 16f), 0);
        ts.drawWall(batch, new Vec2f(0f, 16f), 0);
        batch.end();

        checkBtns();
        updateCam();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 4;
        camera.viewportHeight = height / 4;

        screenSize.setX(width);
        screenSize.setY(height);
    }

    public void checkBtns() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerPos.addToX(-3f);
//            camera.position.x -= 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerPos.addToX( 3f);
//            camera.position.x += 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            camera.position.y += 3;
            playerPos.addToY( 3f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            camera.position.y -= 3;
            playerPos.addToY(-3f);
        }
    }

    public void updateCam() {
        Vec2f f = input.getCameraFocus(playerPos, screenSize);
        camera.position.x = f.getX();
        camera.position.y = f.getY();
    }

    @Override
    public void dispose () {
        batch.dispose();
//        testWallImg.dispose();
//        testGroundImg.dispose();
//        img.dispose();
//        font.dispose();
    }
}
