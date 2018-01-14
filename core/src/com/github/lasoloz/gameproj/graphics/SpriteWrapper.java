package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Class wrapping a single static image extending the `Drawable` base class
 * @see Drawable
 */
public class SpriteWrapper extends Drawable {
    private Sprite sprite;

    /**
     * Constructor
     * @param region Region of an atlas used for the sprite
     */
    public SpriteWrapper(TextureRegion region) {
        sprite = new Sprite(region);
    }

    /**
     * Constructor
     * @param region Region of an atlas used for the sprite
     * @param originX Origin on the X axis
     * @param originY Origin on the Y axis
     */
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
