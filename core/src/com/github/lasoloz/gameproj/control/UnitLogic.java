package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UnitLogic {
    public static void enemyInteraction(
            GameState gameState,
            Vec2i pos,
            Vec2i newPos
    ) {
        GameMap map = gameState.getMap();

        if (!map.inMap(newPos)) {
            gameState.addEnemyPosition(pos);
            return;
        }

        if (map.getData(newPos) != 0) {
            gameState.addEnemyPosition(pos);
            return;
        }

        GameMapTile currentTile = map.getGameMapTile(pos);
        GameMapTile otherTile = map.getGameMapTile(newPos);

        Instance otherInstance = otherTile.getContent();
        if (otherInstance == null) {
            currentTile.moveContent(otherTile);
            gameState.addEnemyPosition(newPos);
            return;
        }

        gameState.addEnemyPosition(pos);

        if (otherInstance.getBlueprint().getType() ==
                InstanceType.PLAYER
                ) {
            otherInstance.damage(
                    currentTile.getContent().getBlueprint().getRandomDamage()
            );
        }
    }

    public static boolean interactWithNewPos(
            GameState gameState,
            Vec2i pos,
            Vec2i newPos
    ) {
        GameMap map = gameState.getMap();

        if (!map.inMap(newPos)) {
            gameState.addEnemyPosition(pos);
            return false;
        }

        // Check if new position has the correct terrain type:
        if (map.getData(newPos) != 0) {
            gameState.addEnemyPosition(pos);
            return false;
        }

        // Get instances:
        GameMapTile currentTile = map.getGameMapTile(pos);
        GameMapTile otherTile = map.getGameMapTile(newPos);
        Instance otherInstance = otherTile.getContent();

        if (otherInstance == null) {
            currentTile.moveContent(otherTile);
            InstanceType type = otherTile.getContent().getBlueprint().getType();
            if (type == InstanceType.PLAYER) {
                gameState.getPlayerPos().set(newPos);
            } else if (type == InstanceType.ENEMY) {
                gameState.addEnemyPosition(newPos);
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
                return enemyInteraction(gameState, currentTile, otherTile);
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
            GameState gameState,
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



    public static void playerSpecialInteraction(
            GameState gameState,
            Vec2i targetPos
    ) {
        GameMap map = gameState.getMap();
        Instance targetInstance = map.getInstance(targetPos.x, targetPos.y);

        if (targetInstance != null) {
            targetInstance.stun();
        }
    }
}
