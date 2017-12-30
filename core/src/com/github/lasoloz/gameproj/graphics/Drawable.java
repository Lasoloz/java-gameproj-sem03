package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Drawable {
    void draw(SpriteBatch batch, float posX, float posY, float deltaTime);

    void draw(
            SpriteBatch batch,
            float posX,
            float posY,
            float width,
            float height,
            float deltaTime
    );
}
