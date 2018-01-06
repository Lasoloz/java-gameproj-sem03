package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.Action;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UnitLogic {
    public static void updateUnits(GameState gameState) {
        // Check, if player movement is requested from last turn:
        if (gameState.isWaitingForPlayer()) {
            return;
        }

        // Then check, if somebody has the right to something (is in idle)
        checkIfCanUpdate(gameState);

        // Then update everything:
        performUpdate(gameState);
    }


    private static void checkIfCanUpdate(GameState gameState) {
        GameMap map = gameState.getMap();
        int width = map.getWidth();
        int height = map.getHeight();

        // Check every other instance:
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Instance current = map.getInstance(x, y);
                if (
                        current != null &&
                                current.getCurrentAction() == Action.ACT_IDLE
                        ) {
                    current.doAction();
                }
            }
        }

        // Check player:
        Vec2i playerPos = gameState.getPlayerPos();
        if (map.getInstance(playerPos.x, playerPos.y)
                .getCurrentAction() == Action.ACT_IDLE
                ) {
            gameState.setWaitingForPlayer(true);
        }
    }


    private static void performUpdate(GameState gameState) {
        GameMap map = gameState.getMap();
        int width = map.getWidth();
        int height = map.getHeight();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Instance current = map.getInstance(x, y);
                if (current != null) {
                    current.update();
                }
            }
        }
    }
}
