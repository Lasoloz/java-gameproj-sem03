package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;


/**
 * Blueprint defining movable units (which interact with enemies or player).
 * Extends `Blueprint`
 * Private members:
 * meleeAttackMean - Mean melee attack damage
 * meleeAttackVar - Variance in melee attack damage
 * rangedAttackMean - Mean ranged attack damage
 * rangedAttackVar - Variance in ranged attack damage
 * protectionMean - Mean protection value
 * protectionVar - Variance in protection value
 * maxHealth - Max health points for unit
 * range - Range of vision for the selected unit
 * @see Blueprint
 */
public abstract class UnitBlueprint extends Blueprint {
    private int meleeAttackMean;
    private int meleeAttackVar;
    private int rangedAttackMean;
    private int rangedAttackVar;
    private int protectionMean;
    private int protectionVar;
    private int maxHealth;
    private int range;


    /**
     * Constructor calling superclass constructor
     * @param indexImage Index sprite for unit
     * @param idleAction Idle sprite/animation for new unit
     */
    UnitBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }


    @Override
    public int getMeleeAttackMean() {
        return meleeAttackMean;
    }

    /**
     * Set value for melee attack mean damage
     * @param meleeAttackMean New melee attack mean
     */
    public void setMeleeAttackMean(int meleeAttackMean) {
        this.meleeAttackMean = meleeAttackMean;
    }

    @Override
    public int getMeleeAttackVar() {
        return meleeAttackVar;
    }

    /**
     * Set value for melee attack damage variance
     * @param meleeAttackVar New melee attack variance
     */
    public void setMeleeAttackVar(int meleeAttackVar) {
        this.meleeAttackVar = meleeAttackVar;
    }

    @Override
    public int getRangedAttackMean() {
        return rangedAttackMean;
    }

    /**
     * Set ranged attack damage mean
     * @param rangedAttackMean New ranged attack mean
     */
    public void setRangedAttackMean(int rangedAttackMean) {
        this.rangedAttackMean = rangedAttackMean;
    }

    @Override
    public int getRangedAttackVar() {
        return rangedAttackVar;
    }

    /**
     * Set ranged attack damage variance
     * @param rangedAttackVar New ranged attack variance
     */
    public void setRangedAttackVar(int rangedAttackVar) {
        this.rangedAttackVar = rangedAttackVar;
    }

    @Override
    public int getProtectionMean() {
        return protectionMean;
    }

    /**
     * Set protection mean value
     * @param protectionMean New mean value for protection
     */
    public void setProtectionMean(int protectionMean) {
        this.protectionMean = protectionMean;
    }

    @Override
    public int getProtectionVar() {
        return protectionVar;
    }

    /**
     * Set variance for protection
     * @param protectionVar New variance for protection
     */
    public void setProtectionVar(int protectionVar) {
        this.protectionVar = protectionVar;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Set max health points for a unit
     * @param maxHealth New value for max health points
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }


    @Override
    public String getInfo() {
        return super.getInfo() + " - attack: " + meleeAttackMean + "(±" +
                meleeAttackVar + ") - protection: " + protectionMean + "(±" +
                protectionVar + ")";
    }


    /**
     * Set range of the unit
     * @param range New range value for the unit
     */
    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getRange() {
        return range;
    }
}
