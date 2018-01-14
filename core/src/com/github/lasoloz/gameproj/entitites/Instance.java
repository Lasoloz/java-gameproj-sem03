package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Blueprint;

/**
 * Base class for instances
 */
public class Instance {
    protected Blueprint blueprint;


    /**
     * Constructor of the instance
     * @param blueprint Instance's blueprint
     */
    public Instance(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    /**
     * Get blueprint of the instance
     * @return Instance's blueprint
     */
    public final Blueprint getBlueprint() {
        return blueprint;
    }


    /**
     * Get information about instance
     * @return String containing instance information
     */
    public String getInfo() {
        return blueprint.getInfo();
    }


    /**
     * Get current health status
     * @return Get health value
     */
    public int getHealth() {
        return 0;
    }

    /**
     * Damage this instance
     * @param damage Value of damage
     */
    public void damage(int damage) {}

    /**
     * Stun this item
     */
    public void stun() {}

    /**
     * Check if instance is stunned
     * @return Stun status
     */
    public boolean isStunned() {
        return  false;
    }
}
