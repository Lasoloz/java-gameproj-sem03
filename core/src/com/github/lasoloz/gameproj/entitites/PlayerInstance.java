package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.blueprints.PlayerBlueprint;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.math.Vec2f;

public class PlayerInstance extends Instance {

    int step;

    public PlayerInstance(PlayerBlueprint blueprint) {
        super(blueprint);
        step = 0;
    }

    @Override
    public void setToIdle() {
        super.setToIdle();
        step = 0;
    }


    @Override
    public void addMoveAction(Direction direction) {
        currentAction = direction.getMoveAction();
        step = 0;
    }

    @Override
    public boolean moveFinished() {
        return step == blueprint.getStepsNeeded();
    }


    @Override
    public void update() {
        if (currentAction.isMoveAction()) {
            ++step;
        }
    }


    @Override
    public Vec2f getFinalPosition(Vec2f position) {
        float stepRatio = (float) step / (float) blueprint.getStepsNeeded();
        switch (currentAction) {
            case ACT_MOVING_NORTH:
                return new Vec2f(
                        position.x,
                        position.y + GameState.gridSize.y * stepRatio
                );
            case ACT_MOVING_EAST:
                return new Vec2f(
                        position.x + GameState.gridSize.x * stepRatio,
                        position.y
                );
            case ACT_MOVING_SOUTH:
                return new Vec2f(
                        position.x,
                        position.y - GameState.gridSize.y * stepRatio
                );
            case ACT_MOVING_WEST:
                return new Vec2f(
                        position.x - GameState.gridSize.x * stepRatio,
                        position.y
                );
        }
        return position.copy();
    }
}
