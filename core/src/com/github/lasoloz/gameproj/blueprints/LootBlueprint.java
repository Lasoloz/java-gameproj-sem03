package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public class LootBlueprint extends Blueprint {
    private int value;

    public LootBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " - value: " + value;
    }

    @Override
    public UnitType getType() {
        return UnitType.LOOT;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
