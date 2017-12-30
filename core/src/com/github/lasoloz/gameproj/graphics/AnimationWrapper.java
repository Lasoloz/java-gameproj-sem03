package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationWrapper implements Drawable {
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

    @Override
    public void draw(SpriteBatch batch, float posX, float posY, float deltaTime) {
        batch.draw(animation.getKeyFrame(deltaTime, true), posX, posY);
    }

    @Override
    public void draw(
            SpriteBatch batch,
            float posX,
            float posY,
            float width,
            float height,
            float deltaTime
    ) {
        batch.draw(
                animation.getKeyFrame(deltaTime, true),
                posX,
                posY,
                width,
                height
        );
    }
}
