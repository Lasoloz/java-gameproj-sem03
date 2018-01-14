package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

/**
 * Blueprint extending unit blueprint, defining Player's unit
 * (controllable unit)
 */
public class PlayerBlueprint extends UnitBlueprint {

    PlayerBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public InstanceType getType() {
        return InstanceType.PLAYER;
    }
}
