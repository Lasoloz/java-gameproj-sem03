package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationWrapper implements Drawable {
    private Animation<TextureRegion> animation;
    private TextureAtlas atlas;

    public AnimationWrapper(Animation animation) {
        this.animation = animation;
    }

    public AnimationWrapper(String assetPath) {
        atlas = new TextureAtlas(assetPath);
        animation = new Animation(1/60f, atlas.getRegions());
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

    @Override
    public void dispose() {
        if (atlas != null) {
            atlas.dispose();
        }
    }
}
