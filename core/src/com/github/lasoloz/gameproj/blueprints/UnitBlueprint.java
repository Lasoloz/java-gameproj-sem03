package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public abstract class UnitBlueprint extends Blueprint {
    private int stepsNeeded;
    private int meleeAttackMean;
    private int meleeAttackVar;
    private int rangedAttackMean;
    private int rangedAttackVar;
    private int protectionMean;
    private int protectionVar;
    private int maxHealth;
    private int range;


    UnitBlueprint(SpriteWrapper indexImage, Drawable idleAction) {
        super(indexImage, idleAction);
    }

    @Override
    public int getStepsNeeded() {
        return stepsNeeded;
    }

    public void setStepsNeeded(int stepsNeeded) {
        this.stepsNeeded = stepsNeeded;
    }


    @Override
    public int getMeleeAttackMean() {
        return meleeAttackMean;
    }

    public void setMeleeAttackMean(int meleeAttackMean) {
        this.meleeAttackMean = meleeAttackMean;
    }

    @Override
    public int getMeleeAttackVar() {
        return meleeAttackVar;
    }

    public void setMeleeAttackVar(int meleeAttackVar) {
        this.meleeAttackVar = meleeAttackVar;
    }

    @Override
    public int getRangedAttackMean() {
        return rangedAttackMean;
    }

    public void setRangedAttackMean(int rangedAttackMean) {
        this.rangedAttackMean = rangedAttackMean;
    }

    @Override
    public int getRangedAttackVar() {
        return rangedAttackVar;
    }

    public void setRangedAttackVar(int rangedAttackVar) {
        this.rangedAttackVar = rangedAttackVar;
    }

    @Override
    public int getProtectionMean() {
        return protectionMean;
    }

    public void setProtectionMean(int protectionMean) {
        this.protectionMean = protectionMean;
    }

    @Override
    public int getProtectionVar() {
        return protectionVar;
    }

    public void setProtectionVar(int protectionVar) {
        this.protectionVar = protectionVar;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }


    @Override
    public String getInfo() {
        return super.getInfo() + " - attack: " + meleeAttackMean + "(±" +
                meleeAttackVar + ") - protection: " + protectionMean + "(±" +
                protectionVar + ")";
    }


    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int getRange() {
        return range;
    }
}
