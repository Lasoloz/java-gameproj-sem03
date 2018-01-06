package com.github.lasoloz.gameproj.blueprints;

public enum Direction {
    DIR_NORTH,
    DIR_EAST,
    DIR_WEST,
    DIR_SOUTH;

    public static Action mapMoveAction(Direction direction) {
        switch (direction) {
            case DIR_NORTH:
                return Action.ACT_MOVING_NORTH;
            case DIR_EAST:
                return Action.ACT_MOVING_EAST;
            case DIR_SOUTH:
                return Action.ACT_MOVING_SOUTH;
            case DIR_WEST:
                return Action.ACT_MOVING_WEST;
        }
        return Action.ACT_IDLE;
    }

    public static Action mapAttackAction(Direction direction) {
        switch (direction) {
            case DIR_NORTH:
                return Action.ACT_ATTACKING_NORTH;
            case DIR_EAST:
                return Action.ACT_ATTACKING_EAST;
            case DIR_SOUTH:
                return Action.ACT_ATTACKING_SOUTH;
            case DIR_WEST:
                return Action.ACT_ATTACKING_WEST;
        }

        return Action.ACT_IDLE;
    }

    public Action getMoveAction() {
        return mapMoveAction(this);
    }

    public Action getAttackAction() {
        return mapAttackAction(this);
    }
}
