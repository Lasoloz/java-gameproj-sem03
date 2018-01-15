package com.github.lasoloz.gameproj.control;

import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2i;

/**
 * Class with static methods implementing some of the unit logic
 */
public class UnitLogic {
    /**
     * Function used to interact enemy with its surroundings
     * @param gameState Current game state object
     * @param pos Current position which has the enemy
     * @param newPos Selected position (where we want to move or attack)
     * @see GameState
     */
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


    /**
     * Player interaction on left click
     * @param gameState Current game state
     * @param pos Old position
     * @param newPos New position
     * @return true if stepped, false otherwise
     */
    public static boolean playerInteraction(
            GameState gameState,
            Vec2i pos,
            Vec2i newPos
    ) {
        GameMap map = gameState.getMap();

        if (!map.inMap(newPos)) {
            return false;
        }

        if (map.getData(newPos) != 0) {
            return false;
        }

        GameMapTile currentTile = map.getGameMapTile(pos);
        GameMapTile otherTile = map.getGameMapTile(newPos);
        Instance otherInstance = otherTile.getContent();

        if (otherInstance == null) {
            currentTile.moveContent(otherTile);
            gameState.getPlayerPos().set(newPos);
            return true;
        }

        switch (otherInstance.getBlueprint().getType()) {
            case LOOT:
                // Add loot value to score
                otherTile.removeContent();
                currentTile.moveContent(otherTile);
                gameState.getPlayerPos().set(newPos);
                otherTile.getContent().addLootValue(
                        otherInstance.getBlueprint().getValue()
                );
                return true;
            case ENEMY:
                // Damage enemy instance
                otherInstance.damage(
                        currentTile.getContent().
                                getBlueprint().getRandomDamage()
                );
                return true;
            case MISC:
                if (otherInstance.isExit()) {
                    gameState.win();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }


    /**
     * Function called in case of special interaction
     * @param gameState Current game state object
     * @param targetPos Target position
     * @return true if stunned, false otherwise
     */
    public static boolean playerSpecialInteraction(
            GameState gameState,
            Vec2i targetPos
    ) {
        GameMap map = gameState.getMap();
        if (!map.inMap(targetPos)) {
            return false;
        }

        Instance targetInstance = map.getInstance(targetPos.x, targetPos.y);

        if (targetInstance != null) {
            targetInstance.stun();
        }
        return true;
    }
}
