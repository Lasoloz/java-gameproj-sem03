package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public class EnemyBlueprint extends UnitBlueprint {
    public EnemyBlueprint(SpriteWrapper indexSprite, Drawable idleAnimation) {
        super(indexSprite, idleAnimation);
    }

    @Override
    public InstanceType getType() {
        return InstanceType.ENEMY;
    }
}
