package com.github.lasoloz.gameproj.entitites;

public enum Property {
    PR_HEALTH(true),

    PR_MOVE_FREQUENCY(false),
    PR_MELEE_DAMAGE(false),
    PR_ARROW_DAMAGE(false),
    PR_MAGIC_DAMAGE(false),
    PR_MELEE_PROTECTION(false),
    PR_ARROW_PROTECTION(false),
    PR_MAGIC_PROTECTION(false),

    /// TODO: Actions should be moved to a different class!
    PR_IDLE(false),
    PR_MOVING_NORTH(false),
    PR_MOVING_SOUTH(false),
    PR_MOVING_WEST(false),
    PR_MOVING_EAST(false),
    PR_ATTACKING_NORTH(false),
    PR_ATTACKING_SOUTH(false),
    PR_ATTACKING_WEST(false),
    PR_ATTACKING_EAST(false);

    boolean instanceSpecific;

    Property(boolean instanceSpecific) {
        this.instanceSpecific = instanceSpecific;
    }

    public boolean isInstanceSpecific() {
        return instanceSpecific;
    }
}
