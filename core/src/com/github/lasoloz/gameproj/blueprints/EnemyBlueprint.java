package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

/**
 * Blueprint defining enemies, extending `UnitBlueprint`
 * @see UnitBlueprint
 */
public class EnemyBlueprint extends UnitBlueprint {
    EnemyBlueprint(SpriteWrapper indexSprite, Drawable idleAnimation) {
        super(indexSprite, idleAnimation);
    }

    @Override
    public InstanceType getType() {
        return InstanceType.ENEMY;
    }
}
