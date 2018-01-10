package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.lasoloz.gameproj.blueprints.Blueprint;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

import javax.management.InstanceNotFoundException;

public class GameController extends Subject {
    public GameController(GameState gameState) {
        super(gameState);
        Gdx.input.setInputProcessor(gameState.getInput());

        updatePlayerVisibility();
    }


    @Override
    public void update() {
        gameState.updateTime();
        gameState.moveCameraTowardsPlayer();

        if (gameState.readyForStep()) {
            updatePlayer();
            updateEnemies();
        }

        int scrollState = gameState.getInput().getScrollState();
        if (scrollState < 0) {
            gameState.incrementDisplayDiv();
        } else if (scrollState > 0) {
            gameState.decrementDisplayDiv();
        }
        updateCamera();
        super.update();
    }


    public void resize(int width, int height) {
        int displayDiv = gameState.getDisplayDiv();
        OrthographicCamera camera = gameState.getCamera();
        camera.viewportWidth = width / displayDiv;
        camera.viewportHeight = height / displayDiv;

        OrthographicCamera uiCamera = gameState.getUiCamera();
        uiCamera.viewportWidth = width;
        uiCamera.viewportHeight = height;

        Vec2i screenSize = gameState.getScreenSize();
        screenSize.x = width;
        screenSize.y = height;
    }



    private void updateCamera() {
        // Get field renderer camera, and update based on player position:
        GameInput input = gameState.getInput();
        Vec2f f = input.getCameraFocus(
                gameState.getCameraPos(),
                gameState.getScreenSize(),
                gameState.getDisplayDiv()
        );
        OrthographicCamera camera = gameState.getCamera();
        camera.position.x = f.getX();
        camera.position.y = f.getY();
        camera.update();

        // UI renderer camera:
        OrthographicCamera uiCamera = gameState.getUiCamera();
        Vec2i screenSize = gameState.getScreenSize();
        uiCamera.position.x = screenSize.x / 2;
        uiCamera.position.y = screenSize.y / 2;
        uiCamera.update();
    }


    private void updatePlayer() {
        Vec2i playerPos = gameState.getPlayerPos();
        Instance player = gameState.getMap().getInstance(
                playerPos.x, playerPos.y
        );
        if (player.getHealth() <= 0) {
//            gameState.defeat();
        }

        // Alternative:
        boolean leftWasPressed = gameState.getInput().isLeftPressed();
        // Check move direction:
        Vec2i targetPos = gameState.getRelativeMouseGridPos();
        int dx = targetPos.x - playerPos.x;
        int dy = targetPos.y - playerPos.y;

        // To far...
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
            Gdx.graphics.setCursor(
                    gameState.getCursorSet().getMainCursor()
            );
            return;
        }


        // Close to the player, let's calculate direction:
        Direction targetDir;
        if (dx == 0) {
            if (dy == -1) {
                targetDir = Direction.DIR_NORTH;
            } else if (dy == 1) {
                targetDir = Direction.DIR_SOUTH;
            } else {
                targetDir = Direction.DIR_NODIR;
            }
        } else if (dx == -1) {
            if (dy == -1) {
                targetDir = Direction.DIR_NORTH_WEST;
            } else if (dy == 1) {
                targetDir = Direction.DIR_SOUTH_WEST;
            } else {
                targetDir = Direction.DIR_WEST;
            }
        } else {
            if (dy == -1) {
                targetDir = Direction.DIR_NORTH_EAST;
            } else if (dy == 1) {
                targetDir = Direction.DIR_SOUTH_EAST;
            } else {
                targetDir = Direction.DIR_EAST;
            }
        }

        // Set the move cursor needed
        Gdx.graphics.setCursor(
                gameState.getCursorSet().getDirCursor(targetDir)
        );

        // Check if player did something (mouse click)
        if (leftWasPressed) {
            if (UnitLogic.interactWithNewPos(
                    gameState,
                    playerPos,
                    targetPos
            )) {
                gameState.step();
                Gdx.graphics.setCursor(
                        gameState.getCursorSet().getMainCursor()
                );
                updatePlayerVisibility();
            }
        }
    }


    private void updatePlayerVisibility() {
        Vec2i playerPos = gameState.getPlayerPos();
        GameMap map = gameState.getMap();
        Instance player = map.getInstance(playerPos.x, playerPos.y);
        int range = player.getBlueprint().getRange();

        // Reset the seen status for every cell:
        resetSeen();

        // Get visibility boundaries:
        int vLeft = Math.max(0, playerPos.x - range);
        int vTop = Math.max(0, playerPos.y - range);
        int vRight = Math.min(map.getWidth() - 1, playerPos.x + range);
        int vBottom = Math.min(map.getHeight() - 1, playerPos.y + range);

        // Simple, next time count walls!!!
        for (int y = vTop; y <= vBottom; ++y) {
            for (int x = vLeft; x <= vRight; ++x) {
                GameMapTile tile = map.getGameMapTile(x, y);
                tile.setKnown(true);
                tile.setSeen(true);
            }
        }
    }

    private void resetSeen() {
        // Reset seen status to false:
        GameMap map = gameState.getMap();
        for (int y = 0; y < map.getHeight(); ++y) {
            for (int x = 0; x < map.getWidth(); ++x) {
                map.getGameMapTile(x, y).setSeen(false);
            }
        }
    }



    private void updateEnemies() {
        GameMap map = gameState.getMap();
        for (int y = 0; y < map.getHeight(); ++y) {
            for (int x = 0; x < map.getWidth(); ++x) {
                Instance current = map.getInstance(x, y);
                if (current != null &&
                        current.getBlueprint().getType() == InstanceType.ENEMY
                        ) {
                    updateEnemy(current, x, y);
                }
            }
        }
    }

    private void updateEnemy(Instance enemy, int x, int y) {
        if (enemy.getHealth() <= 0) {
            gameState.getMap().getGameMapTile(x, y).removeContent();
        }
    }

}
