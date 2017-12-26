package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.math.Vec2f;

public abstract class Unit extends Instance {
    public Unit(Vec2f pos) {
        super(pos);
    }

    public boolean isNeutral() {
        return true;
    }
}
