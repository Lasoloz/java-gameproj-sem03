package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Blueprint;

public class Instance {
    protected Blueprint blueprint;


    public Instance(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public final Blueprint getBlueprint() {
        return blueprint;
    }


    public String getInfo() {
        return blueprint.getInfo();
    }


    public int getHealth() {
        return 0;
    }

    public void damage(int damage) {}
}
