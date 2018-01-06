package com.github.lasoloz.gameproj.blueprints;

public enum Action {
    ACT_IDLE(0),
    ACT_MOVING_NORTH(1),
    ACT_MOVING_EAST(1),
    ACT_MOVING_SOUTH(1),
    ACT_MOVING_WEST(1),
    ACT_ATTACKING_NORTH(2),
    ACT_ATTACKING_EAST(2),
    ACT_ATTACKING_SOUTH(2),
    ACT_ATTACKING_WEST(2);

    private int actionCode;

    Action(int actionCode) {
        this.actionCode = actionCode;
    }

    public boolean isMoveAction() {
        return actionCode == 1;
    }

    public boolean isAttackAction() {
        return actionCode == 2;
    }
}
