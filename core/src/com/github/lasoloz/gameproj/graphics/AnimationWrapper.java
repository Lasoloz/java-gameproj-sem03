package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationWrapper extends Drawable {
    private Animation<TextureRegion> animation;

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
