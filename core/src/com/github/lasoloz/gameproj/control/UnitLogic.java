package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UnitLogic {
    public static boolean interactWithNewPos(
            GameState gameState,
            Vec2i pos,
            Vec2i newPos
    ) {
        GameMap map = gameState.getMap();

        if (!map.inMap(newPos)) {
            return false;
        }

        // Check if new position has the correct terrain type:
        if (map.getData(newPos) != 0) {
            return false;
        }

        // Get instances:
        GameMapTile currentTile = map.getGameMapTile(pos);
        GameMapTile otherTile = map.getGameMapTile(newPos);
        Instance otherInstance = otherTile.getContent();

        if (otherInstance == null) {
            currentTile.moveContent(otherTile);
            if (otherTile.getContent().getBlueprint().getType() ==
                    InstanceType.PLAYER
                    ) {
                gameState.getPlayerPos().set(newPos);
            }
            return true;
        }

        // Check, current instance:
        switch (currentTile.getContent().getBlueprint().getType()) {
            case PLAYER:
                return playerInteraction(
                        currentTile,
                        otherTile,
                        gameState.getPlayerPos(),
                        newPos
                );
            case ENEMY:
                return enemyInteraction(currentTile, otherTile);
            default:
                return false;
        }
    }

    private static boolean playerInteraction(
            GameMapTile playerTile,
            GameMapTile targetTile,
            Vec2i playerPos,
            Vec2i targetPos
    ) {
        Instance targetInstance = targetTile.getContent();
        switch (targetInstance.getBlueprint().getType()) {
            case LOOT:
                // Add loot value to score
                targetTile.removeContent();
                playerTile.moveContent(targetTile);
                playerPos.set(targetPos);
                return true;
            case ENEMY:
                // Damage enemy instance
                targetInstance.damage(
                        playerTile.getContent().getBlueprint().getRandomDamage()
                );
                return true;
            default:
                return false;
        }
    }

    private static boolean enemyInteraction(
            GameMapTile enemyTile,
            GameMapTile targetTile
    ) {
        Instance targetInstance = targetTile.getContent();
        switch (targetInstance.getBlueprint().getType()) {
            case PLAYER:
                // Damage player:
                targetInstance.damage(
                        enemyTile.getContent().getBlueprint().getRandomDamage()
                );
                return true;
            default:
                return false;
        }
    }

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
}
