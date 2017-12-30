package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureWrapper implements Drawable {
    private Texture texture;

    public TextureWrapper(String assetPath) {
        texture = new Texture(assetPath);
    }
    public TextureWrapper(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(
            SpriteBatch batch,
            float posX,
            float posY,
            float deltaTime
    ) {
        batch.draw(texture, posX, posY);
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
        batch.draw(texture, posX, posY, width, height);
    }

//    @Override
//    public void dispose() {
//        texture.dispose();
//    }
}
