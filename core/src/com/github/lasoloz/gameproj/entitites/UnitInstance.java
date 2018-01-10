package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Blueprint;
import com.github.lasoloz.gameproj.blueprints.UnitBlueprint;

import java.sql.Time;
import java.util.Random;

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
}
