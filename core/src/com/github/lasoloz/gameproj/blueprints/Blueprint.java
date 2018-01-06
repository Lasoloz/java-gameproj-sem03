package com.github.lasoloz.gameproj.blueprints;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Blueprint {
    private SpriteWrapper indexImage;
    private String name;

    private ArrayMap<Action, Drawable> actionDrawableMap;


    public Blueprint(SpriteWrapper indexImage, Drawable idleAction) {
        this.indexImage = indexImage;
        actionDrawableMap = new ArrayMap<Action, Drawable>();
        actionDrawableMap.put(Action.ACT_IDLE, idleAction);
        name = "Unnamed Object";
    }

    public void addActionDrawable(Action action, Drawable drawable) {
        if (!actionDrawableMap.containsKey(action)) {
            actionDrawableMap.removeKey(action);
        }
        actionDrawableMap.put(action, drawable);
    }

    public Drawable getActionImage(Action action) {
        /// TODO: Create a better data structure for this:
        if (!actionDrawableMap.containsKey(action)) {
            return actionDrawableMap.get(action);
        } else {
            return actionDrawableMap.get(Action.ACT_IDLE);
        }
    }

    public abstract UnitType getType();


    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return name;
    }

    public int getValue() {
        return 0;
    }

    public Vec2f moveSpeed() {
        return new Vec2f(0f, 0f);
    }

    public int meleeAttackMean() {
        return 0;
    }

    public int meleeAttackVar() {
        return 0;
    }

    public int rangedAttackMean() {
        return 0;
    }

    public int rangedAttackVar() {
        return 0;
    }

    public int protectionMean() {
        return 0;
    }

    public int protectionVar() {
        return 0;
    }
}