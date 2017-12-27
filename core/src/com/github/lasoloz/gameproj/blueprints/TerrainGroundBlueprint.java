package com.github.lasoloz.gameproj.blueprints;

import com.github.lasoloz.gameproj.graphics.Drawable;

import static com.github.lasoloz.gameproj.blueprints.MoveDir.DIR_COUNT;

public class TerrainGroundBlueprint {
    private Drawable ground; // Top of the terrain element
    private Drawable wall;   // The wall of the terrain element

    private int[] boundModifiers;

    public TerrainGroundBlueprint(
            Drawable ground,
            Drawable wall,
            int[] boundModifiers
    ) throws BlueprintException {
        if (boundModifiers.length < DIR_COUNT) {
            throw new BlueprintException("Invalid direction count");
        }

        this.ground = ground;
        this.wall = wall;
        this.boundModifiers = new int[MoveDir.DIR_COUNT];

        for (int i = 0; i < MoveDir.DIR_COUNT; ++i) {
            this.boundModifiers[i] = boundModifiers[i];
        }
    }

    public Drawable getGround() {
        return ground;
    }

    public Drawable getWall() {
        return wall;
    }

    public int getBoundModifier(MoveDir dir) {
        return boundModifiers[dir.getIndexValue()];
    }
}
