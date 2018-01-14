package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

/**
 * Blueprint type extending `Blueprint`, defining miscellaneous objects
 */
public class MiscBlueprint extends Blueprint {
    MiscBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public InstanceType getType() {
        return InstanceType.MISC;
    }
}
