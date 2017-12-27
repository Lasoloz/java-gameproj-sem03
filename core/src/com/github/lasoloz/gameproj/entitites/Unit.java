package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.UnitBlueprint;
import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Unit extends Instance {
    private UnitBlueprint blueprint;

    public Unit(Vec2f pos) {
        super(pos);
    }

    public boolean isNeutral() {
        return true;
    }


    public void update() {}

    public void setMovement() {
        //
    }
}
