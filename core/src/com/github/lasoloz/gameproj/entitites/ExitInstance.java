package com.github.lasoloz.gameproj.entitites;

import com.github.lasoloz.gameproj.blueprints.Blueprint;

public class ExitInstance extends Instance {
    public ExitInstance(Blueprint bp) {
        super(bp);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
