package com.github.lasoloz.gameproj.control.details;

/**
 * Observer interface - for rendering game state data.
 */
public interface Observer {
    void update(GameState state);
}
