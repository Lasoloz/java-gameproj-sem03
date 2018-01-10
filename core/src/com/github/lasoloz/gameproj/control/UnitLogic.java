package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.Action;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UnitLogic {
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
