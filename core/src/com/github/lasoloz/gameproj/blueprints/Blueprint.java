package com.github.lasoloz.gameproj.blueprints;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

import java.util.Random;

/**
 * Blueprints define general properties of units, like animations, max
 * health points, or damage dealt.
 * Private members: static random generator for variance in action effects.
 * index image - this image will be used in the U.I.
 * name - generic name of all instances defined by the blueprint.
 * actionDrawableMap - an array map defining animation for different actions
 */
public abstract class Blueprint {
    static Random rnd = new Random(System.currentTimeMillis());

    private SpriteWrapper indexImage;
    private String name;

    private ArrayMap<Action, Drawable> actionDrawableMap;


    /**
     * Constructor for blueprints. It needs an index image and an idle
     * animation/image. Other information is set up in blueprint set reading
     * by setters.
     * @param indexImage index image of the unit type
     * @param idleAction idle action image/animation of the unit type
     */
    Blueprint(SpriteWrapper indexImage, Drawable idleAction) {
        this.indexImage = indexImage;
        actionDrawableMap = new ArrayMap<Action, Drawable>();
        actionDrawableMap.put(Action.ACT_IDLE, idleAction);
        name = "Unnamed Object";
    }

    /**
     * Assigns an image/animation to the specific action
     * @param action Specified action
     * @param drawable Assigned drawable
     */
    public void addActionDrawable(Action action, Drawable drawable) {
        if (!actionDrawableMap.containsKey(action)) {
            actionDrawableMap.removeKey(action);
        }
        actionDrawableMap.put(action, drawable);
    }

    /**
     * Get image/animation for the specific action
     * @param action Selected action
     * @return A drawable image.
     */
    public Drawable getActionImage(Action action) {
        /// TODO: Create a better data structure for this:
        if (actionDrawableMap.containsKey(action)) {
            return actionDrawableMap.get(action);
        } else {
            return actionDrawableMap.get(Action.ACT_IDLE);
        }
    }

    /**
     * Get type of the unit defined by the instance
     * @return Instance type (overridden in subclasses
     */
    public abstract InstanceType getType();


    /**
     * Set name of the unit defined by the blueprint
     * @param name New name for the unit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get generic information line for the unit (used in InfoRenderer)
     * @return Information message.
     * @see com.github.lasoloz.gameproj.control.InfoRenderer
     */
    public String getInfo() {
        return name;
    }

    /**
     * Get (loot) value
     * @return Numeric data representing value of the item.
     */
    public int getValue() {
        return 0;
    }

    /**
     * Get mean value for melee attack for the specific unit
     * @return Numeric value representing mean damage
     */
    public int getMeleeAttackMean() {
        return 0;
    }

    /**
     * Get variance in melee attack value for unit
     * @return Numeric value representing variance in damage
     */
    public int getMeleeAttackVar() {
        return 0;
    }

    /**
     * Get mean value for ranged attack damage
     * @return Numeric value representing mean ranged damage
     */
    public int getRangedAttackMean() {
        return 0;
    }

    /**
     * Get variance for ranged attack
     * @return Numeric value representing variance in damage
     */
    public int getRangedAttackVar() {
        return 0;
    }

    /**
     * Get mean protection value
     * @return Numeric value representing mean protection
     */
    public int getProtectionMean() {
        return 0;
    }

    /**
     * Get protection variance
     * @return Numeric value representing variance in protection
     */
    public int getProtectionVar() {
        return 0;
    }

    /**
     * Numeric value representing max health
     * @return Get max health points for specific unit
     */
    public int getMaxHealth() {
        return 0;
    }

    /**
     * Range of the unit
     * @return Numeric value representing how far does the specific unit see.
     */
    public int getRange() {
        return 0;
    }


    /**
     * Create a random damage value using mean and variance
     * @return Numeric value representing current damage output
     */
    public int getRandomDamage() {
        return randomUniform(getMeleeAttackMean(), getMeleeAttackVar());
    }

    /**
     * Create a random protection value using mean and variance
     * @return Numeric value representing current protection value
     */
    public int getRandomProtection() {
        return randomUniform(getProtectionMean(), getProtectionVar());
    }

    /**
     * Create a uniformly distributed random value
     * @param mean Mean value for the uniform distribution
     * @param var Distance towards the ends of the intervals
     * @return New random integer value in [mean - var, mean + var]
     */
    private int randomUniform(int mean, int var) {
        return mean - var + rnd.nextInt(2 * var + 1);
    }
}
