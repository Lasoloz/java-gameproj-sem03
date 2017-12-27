package com.github.lasoloz.gameproj.blueprints;

public enum MoveDir {
    DIR_NORTH(0), DIR_EAST(1), DIR_SOUTH(2), DIR_WEST(3);

    public final static int DIR_COUNT = 4;

    private int index;

    MoveDir(int dirIndex) {
        index = dirIndex;
    }
    public int getIndexValue() {
        return index;
    }
}
