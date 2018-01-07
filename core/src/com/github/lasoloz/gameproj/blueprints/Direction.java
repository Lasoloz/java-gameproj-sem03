package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.math.Vec2i;

public enum Direction {
    DIR_NODIR,
    DIR_NORTH,
    DIR_EAST,
    DIR_WEST,
    DIR_SOUTH,
    DIR_NORTH_EAST,
    DIR_SOUTH_EAST,
    DIR_SOUTH_WEST,
    DIR_NORTH_WEST;

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

    public static Vec2i mapMoveBaseVector(Direction direction) {
        switch (direction) {
            case DIR_NORTH:
                return new Vec2i(0, -1);
            case DIR_EAST:
                return new Vec2i(+1, 0);
            case DIR_SOUTH:
                return new Vec2i(0, +1);
            case DIR_WEST:
                return new Vec2i(-1, 0);
            case DIR_NORTH_EAST:
                return new Vec2i(+1 ,-1);
            case DIR_SOUTH_EAST:
                return new Vec2i(+1, +1);
            case DIR_SOUTH_WEST:
                return new Vec2i(-1, +1);
            case DIR_NORTH_WEST:
                return new Vec2i(-1, -1);
            default:
                return new Vec2i(0, 0);
        }
    }

    public Action getMoveAction() {
        return mapMoveAction(this);
    }

    public Action getAttackAction() {
        return mapAttackAction(this);
    }

    public Vec2i getMoveBaseVector() {
        return mapMoveBaseVector(this);
    }
}
