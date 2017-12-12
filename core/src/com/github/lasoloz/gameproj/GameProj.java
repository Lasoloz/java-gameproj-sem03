package com.github.lasoloz.gameproj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameProj extends ApplicationAdapter { 
    SpriteBatch batch;
//    Texture img;
    private BitmapFont font;

    @Override
    public void create () {
        batch = new SpriteBatch();
//        img = new Texture("badlogic.jpg");
        font = new BitmapFont();
        font.setColor(Color.BLUE);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Hello World!", 200, 200);
//        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
//        img.dispose();
        font.dispose();
    }
}
