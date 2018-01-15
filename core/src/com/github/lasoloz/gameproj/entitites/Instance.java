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


    /**
     * Get loot value
     * @return Numerical value of root
     */
    public int getLoot() {
        return 0;
    }

    /**
     * Add loot to overall loot value
     * @param loot Value to be added to sum of loot values
     */
    public void addLootValue(int loot) {}


    /**
     * Check if instance is the exit
     * @return Boolean value stating if instance is exit
     */
    public boolean isExit() {
        return false;
    }
}
