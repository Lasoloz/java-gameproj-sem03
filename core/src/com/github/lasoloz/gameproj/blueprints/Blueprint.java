package com.github.lasoloz.gameproj.blueprints;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

import java.util.Random;

public abstract class Blueprint {
    public static Random rnd = new Random(System.currentTimeMillis());

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
        if (actionDrawableMap.containsKey(action)) {
            return actionDrawableMap.get(action);
        } else {
            return actionDrawableMap.get(Action.ACT_IDLE);
        }
    }

    public abstract InstanceType getType();


    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return name;
    }

    public int getValue() {
        return 0;
    }

    public int getStepsNeeded() {
        return 0;
    }

    public int getMeleeAttackMean() {
        return 0;
    }

    public int getMeleeAttackVar() {
        return 0;
    }

    public int getRangedAttackMean() {
        return 0;
    }

    public int getRangedAttackVar() {
        return 0;
    }

    public int getProtectionMean() {
        return 0;
    }

    public int getProtectionVar() {
        return 0;
    }

    public int getMaxHealth() {
        return 0;
    }

    public int getRange() {
        return 0;
    }


    public int getRandomDamage() {
        return randomNormal(getMeleeAttackMean(), getMeleeAttackVar());
    }

    public int getRandomProtection() {
        return randomNormal(getProtectionMean(), getProtectionVar());
    }

    private int randomNormal(int mean, int var) {
        return mean - var + rnd.nextInt(2 * var + 1);
    }
}
