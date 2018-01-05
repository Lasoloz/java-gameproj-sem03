package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public class MiscBlueprint extends Blueprint {
    public MiscBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public UnitTypes getType() {
        return UnitTypes.MISC;
    }
}
