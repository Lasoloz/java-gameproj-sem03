package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteWrapper extends Drawable {
    private Sprite sprite;

//    public SpriteWrapper(TextureAtlas atlas, String atlasRegionName) {
//        this.sprite = new Sprite(atlas.findRegion(atlasRegionName));
//    }

    public SpriteWrapper(TextureRegion region) {
        sprite = new Sprite(region);
    }

    public SpriteWrapper(TextureRegion region, int originX, int originY) {
        super(originX, originY);
        sprite = new Sprite(region);
    }

    @Override
    protected void drawToCoords(
            SpriteBatch batch,
            int posX,
            int posY,
            float deltaTime
    ) {
        batch.draw(sprite, posX, posY);
    }
}
