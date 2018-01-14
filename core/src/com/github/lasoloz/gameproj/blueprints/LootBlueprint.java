package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

/**
 * Loot blueprint extending blueprint, defining lootable items.
 * private members: value - value of the loot object
 */
public class LootBlueprint extends Blueprint {
    private int value;

    LootBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " - value: " + value;
    }

    @Override
    public InstanceType getType() {
        return InstanceType.LOOT;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Set value of the loot
     * @param value Numeric data representing value of the loot
     */
    public void setValue(int value) {
        this.value = value;
    }
}
