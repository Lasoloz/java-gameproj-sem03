package com.github.lasoloz.gameproj.graphics;

import java.util.ArrayList;

public class DirectionSpritesBuilder {
    private ArrayList<Drawable> dirSprites;

    private DirectionSpritesBuilder() {
        dirSprites = new ArrayList<Drawable>();
    }

    public static DirectionSpritesBuilder create() {
        return new DirectionSpritesBuilder();
    }

    public DirectionSpritesBuilder appendNext(Drawable sprite) {
        dirSprites.add(sprite);
        return this;
    }

    public DirectionSprites build() {
        return new DirectionSprites(dirSprites);
    }
}
