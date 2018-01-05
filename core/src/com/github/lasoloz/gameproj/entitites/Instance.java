package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.utils.ArrayMap;
import com.github.lasoloz.gameproj.blueprints.Blueprint;

public class Instance {
    private Blueprint blueprint;

//    private Property currentAction;


    public Instance(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }
}
