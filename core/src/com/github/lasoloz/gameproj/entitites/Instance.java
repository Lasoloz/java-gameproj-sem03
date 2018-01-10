package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Action;
import com.github.lasoloz.gameproj.blueprints.Blueprint;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.math.Vec2f;

public class Instance {
    protected Blueprint blueprint;

    protected Action currentAction;


    public Instance(Blueprint blueprint) {
        this.blueprint = blueprint;
        currentAction = Action.ACT_IDLE;
    }

    public final Blueprint getBlueprint() {
        return blueprint;
    }

    public final Action getCurrentAction() {
        return currentAction;
    }

    public void setToIdle() {
        currentAction = Action.ACT_IDLE;
    }


    public void addMoveAction(Direction direction) {
        this.currentAction = Action.ACT_IDLE;
    }

    public boolean moveFinished() {
        return false;
    }

    // Enemy players will have simple artificial intelligence
    public void doAction() {}


    public void update() {}


    public Vec2f getFinalPosition(Vec2f position) {
        return position.copy();
    }


    public String getInfo() {
        return blueprint.getInfo();
    }
}
