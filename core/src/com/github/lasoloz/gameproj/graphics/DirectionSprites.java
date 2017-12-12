package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class DirectionSprites {
    private List<Drawable> dirSprites;

    public DirectionSprites(List<Drawable> dirSprites) {
        this.dirSprites = dirSprites;
    }



    public void draw(
            SpriteBatch batch,
            float posX,
            float posY,
            float deltaTime,
            int index
    ) {
        int i = index % dirSprites.size();
        dirSprites.get(i).draw(batch, posX, posY, deltaTime);
    }


    public void draw(
            SpriteBatch batch,
            float posX,
            float posY,
            float width,
            float height,
            float deltaTime,
            int index
    ) {
        int i = index % dirSprites.size();
        dirSprites.get(i).draw(batch, posX, posY, width, height, deltaTime);
    }
}
