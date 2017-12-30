package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteWrapper implements Drawable {
    private Sprite sprite;

//    public SpriteWrapper(TextureAtlas atlas, String atlasRegionName) {
//        this.sprite = new Sprite(atlas.findRegion(atlasRegionName));
//    }

    public SpriteWrapper(TextureRegion region) {
        sprite = new Sprite(region);
    }

    @Override
    public void draw(SpriteBatch batch, float posX, float posY, float deltaTime) {
        batch.draw(sprite, posX, posY);
    }

    @Override
    public void draw(SpriteBatch batch, float posX, float posY, float width, float height, float deltaTime) {
        batch.draw(sprite, posX, posY, width, height);
    }
}
