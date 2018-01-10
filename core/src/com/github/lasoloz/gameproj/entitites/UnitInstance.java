package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.UnitBlueprint;

public class UnitInstance extends Instance {
    private int health;

    public UnitInstance(UnitBlueprint unitBlueprint) {
        super(unitBlueprint);
        health = unitBlueprint.getMaxHealth();
    }


    @Override
    public String getInfo() {
        return blueprint.getInfo() + " - " + "HP: " + health + "/" +
                blueprint.getMaxHealth();
    }
}
