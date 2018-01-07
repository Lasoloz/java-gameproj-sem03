package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.Action;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UnitLogic {
    public static void updateUnits(GameState gameState) {
//        // Check if we are in a new step:
//        if (!gameState.readyForStep()) {
//            return;
//        }
//
//        // Check, if player movement is requested from last turn:
//        if (gameState.isWaitingForPlayer()) {
//            return;
//        }

//        // Then check, if somebody has the right to something (is in idle)
//        checkIfCanUpdate(gameState);
//
//        // Then update everything:
//        performUpdate(gameState);
//        gameState.step();
    }

//
//    private static void checkIfCanUpdate(GameState gameState) {
//        GameMap map = gameState.getMap();
//        int width = map.getWidth();
//        int height = map.getHeight();
//
//        // Check every other instance:
//        for (int y = 0; y < height; ++y) {
//            for (int x = 0; x < width; ++x) {
//                Instance current = map.getInstance(x, y);
//                if (
//                        current != null &&
//                                current.getCurrentAction() == Action.ACT_IDLE
//                        ) {
//                    current.doAction();
//                }
//            }
//        }
//
//        // Check player:
//        Vec2i playerPos = gameState.getPlayerPos();
//        if (map.getInstance(playerPos.x, playerPos.y)
//                .getCurrentAction() == Action.ACT_IDLE
//                ) {
//            gameState.setWaitingForPlayer(true);
//        }
//    }
//
//
//    private static void performUpdate(GameState gameState) {
//        GameMap map = gameState.getMap();
//        int width = map.getWidth();
//        int height = map.getHeight();
//
//        // Update every unit
//        for (int y = 0; y < height; ++y) {
//            for (int x = 0; x < width; ++x) {
//                Instance current = map.getInstance(x, y);
//                if (current != null) {
//                    current.update();
//                    if (current.moveFinished()) {
//                        map.getGameMapTile(x, y).moveContent(map.getGameMapTile(x - 1, y));
//                        current.setToIdle();
//                    }
//                }
//            }
//        }
//    }
//
//

    public static boolean movePlayer(
            GameState gameState,
            Vec2i playerPos,
            Direction moveDirection
    ) {
        return moveUnit(gameState, playerPos, moveDirection);
    }

    public static boolean moveUnit(
            GameState gameState,
            Vec2i unitPos,
            Direction moveDirection
    ) {
        GameMap map = gameState.getMap();
        Vec2i newPos = unitPos.add(moveDirection.getMoveBaseVector());

        if (!map.inMap(newPos)) {
            return false;
        }

        // Check if new position has the correct terrain type:
        if (map.getData(newPos) != 0) {
            return false;
        }

        // Check if new position is occupied:
        if (map.getGameMapTile(newPos).isOccupied()) {
            return false;
        }

        // If everything is alright, then we can move our unit:
        map.getGameMapTile(unitPos).moveContent(map.getGameMapTile(newPos));
        unitPos.set(newPos);
        return true;
    }
//    public static boolean addMoveAction(
//            GameState gameState,
//            Vec2i instancePos,
//            Direction dir
//    ) {
//        GameMap map = gameState.getMap();
//        Vec2i moveBaseVec = dir.getMoveBaseVector();
//        Vec2i newPos = instancePos.add(moveBaseVec);
//        // Check if new position is out of bounds
//        if (newPos.x < 0 ||
//                newPos.x >= map.getWidth() ||
//                newPos.y < 0 ||
//                newPos.y >= map.getHeight()
//                ) {
//            return false;
//        }
//
//        // Check if new position may contain instances
//        if (map.getData(newPos.x, newPos.y) != 0) {
//            return false;
//        }
//
//        // Check occupancy:
//        GameMapTile tile = map.getGameMapTile(newPos.x, newPos.y);
//        if (tile.isOccupied()) {
//            return false;
//        }
//
//        // Start move action:
//        tile.markOccupied();
//        map.getInstance(instancePos.x, instancePos.y).addMoveAction(dir);
//        return true;
//    }
}
