package com.github.lasoloz.gameproj.blueprints;

/**
 * Enumeration defining possible instance types
 * MISC - Miscellaneous items on map (like torches, rocks, etc.)
 * LOOT - Lootable items, like gold, jewelries, etc.
 * ENEMY - Enemy items on the map (like giant scorpions, gnomes, etc.)
 * PLAYER - Player unit (should appear only once on map
 */
public enum InstanceType {
    // Static stuff:
    MISC, // Miscellaneous
    LOOT,

    // Enemy types:
    ENEMY,

    // Player type:
    PLAYER
}
