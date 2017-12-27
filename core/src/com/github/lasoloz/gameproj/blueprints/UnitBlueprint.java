package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.math.Vec2f;

public class UnitBlueprint {
    private boolean moves;
    private boolean interacts;

    private int moveFrequency;


    public UnitBlueprint(boolean moves, boolean interacts) {
        this.moves = moves;
        this.interacts = interacts;
        moveFrequency = 1;
    }

    public boolean canMove() {
        return moves;
    }

    public boolean canInteract() {
        return interacts;
    }
}
