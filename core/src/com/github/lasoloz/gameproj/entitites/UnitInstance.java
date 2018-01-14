package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.UnitBlueprint;

/**
 * Unit instance extending Instance base class having additional members for
 * additional stats
 */
public class UnitInstance extends Instance {
    private int health;
    private int stunned;

    /**
     * Construct new instance from unit blueprint
     * @param unitBlueprint Unit blueprint of unit instance
     */
    public UnitInstance(UnitBlueprint unitBlueprint) {
        super(unitBlueprint);
        health = unitBlueprint.getMaxHealth();
        stunned = 0;
    }


    @Override
    public String getInfo() {
        return blueprint.getInfo() + " - " + "HP: " + health + "/" +
                blueprint.getMaxHealth() +
                (stunned > 0 ? ", stunned" : "");
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void damage(int damage) {
        int protection = blueprint.getRandomProtection();
        int deltaDamage = Math.max(0, damage - protection);
        health -= deltaDamage;
    }

    @Override
    public void stun() {
        stunned = 3; // Sorry for the magic number :( I'll write a constant
        // definition in the future
    }

    @Override
    public boolean isStunned() {
        if (stunned > 0) {
            --stunned;
            return true;
        }
        return false;
    }
}
