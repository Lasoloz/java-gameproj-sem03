package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.blueprints.PlayerBlueprint;

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
}
