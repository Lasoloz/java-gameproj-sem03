package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;

public abstract class UnitBlueprint extends Blueprint {
    protected int stepsNeeded;
    protected int meleeAttackMean;
    protected int meleeAttackVar;
    protected int rangedAttackMean;
    protected int rangedAttackVar;
    protected int protectionMean;
    protected int protectionVar;
    protected int maxHealth;


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
}
