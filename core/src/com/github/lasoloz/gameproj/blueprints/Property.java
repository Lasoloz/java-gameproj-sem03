package com.github.lasoloz.gameproj.blueprints;

public enum Property {
    PR_HEALTH(true),

    PR_MOVE_FREQUENCY(false),
    PR_MELEE_DAMAGE(false),
    PR_ARROW_DAMAGE(false),
    PR_MAGIC_DAMAGE(false),
    PR_MELEE_PROTECTION(false),
    PR_ARROW_PROTECTION(false),
    PR_MAGIC_PROTECTION(false),

    PR_VALUE(false);

    boolean instanceSpecific;

    Property(boolean instanceSpecific) {
        this.instanceSpecific = instanceSpecific;
    }

    public boolean isInstanceSpecific() {
        return instanceSpecific;
    }
}
