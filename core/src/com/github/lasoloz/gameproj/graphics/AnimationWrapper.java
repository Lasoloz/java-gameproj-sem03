package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation wrapper used create an animation.
 * Extends `Drawable`
 * @see Drawable
 */
public class AnimationWrapper extends Drawable {
    private Animation<TextureRegion> animation;

    /**
     * Constructor
     * @param atlas Atlas used for the drawable
     * @param atlasRegionName Region name for animation
     * @param frameDuration Duration of a frame (state time; see: libgdx docs)
     */
    public AnimationWrapper(
            TextureAtlas atlas,
            String atlasRegionName,
            float frameDuration
    ) {
        animation = new Animation<TextureRegion>(
                frameDuration,
                atlas.findRegions(atlasRegionName)
        );
    }

    /**
     * Construct a new AnimationWrapper
     * @param atlas Atlas used for drawables
     * @param atlasRegionName Region name in the atlas
     * @param frameDuration Frame duration in state time (see: libgdx docs)
     * @param originX X coordinate of origin of the drawable
     * @param originY Y coordinate of origin of the drawable
     */
    public AnimationWrapper(
            TextureAtlas atlas,
            String atlasRegionName,
            float frameDuration,
            int originX,
            int originY
    ) {
        super(originX, originY);
        animation = new Animation<TextureRegion>(
                frameDuration,
                atlas.findRegions(atlasRegionName)
        );
    }

    @Override
    protected void drawToCoords(
            SpriteBatch batch,
            int posX,
            int posY,
            float deltaTime
    ) {
        batch.draw(animation.getKeyFrame(deltaTime, true), posX, posY);
    }
}
