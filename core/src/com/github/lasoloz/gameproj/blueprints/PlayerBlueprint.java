package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public class PlayerBlueprint extends Blueprint {

    public PlayerBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public UnitType getType() {
        return UnitType.PLAYER;
    }
}