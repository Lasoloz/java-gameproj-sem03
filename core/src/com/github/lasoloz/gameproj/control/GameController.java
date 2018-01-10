package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.entitites.PlayerInstance;
import com.github.lasoloz.gameproj.graphics.TerrainSet;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

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

        updateUnits();

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


    private void updateUnits() {
//        if (gameState.readyForStep()) {
//            // Check direction:
//            GameInput gameInput = gameState.getInput();
//            Direction moveDirection = gameInput.getDirectionFromCoord(
//                    gameState.getScreenSize()
//            );
//
//            Gdx.graphics.setCursor(gameState.getCursorSet().getDirCursor(
//                    moveDirection)
//            );
//
//            // Check, if player did something (mouse click)
//            if (gameInput.isLeftPressed()) {
//
//                Vec2i playerPos = gameState.getPlayerPos();
//                if (UnitLogic.movePlayer(
//                        gameState, playerPos, moveDirection
//                )) {
//                    gameState.step();
//                    Gdx.graphics.setCursor(
//                            gameState.getCursorSet().getMainCursor()
//                    );
//                }
//            }
//        }

        // Alternative:
        boolean leftWasPressed = gameState.getInput().isLeftPressed();
        if (gameState.readyForStep()) {
            // Check move direction:
            Vec2i targetPos = gameState.getRelativeMouseGridPos();
            if (
                    targetPos.x < 0 ||
                            targetPos.y < 0 ||
                            targetPos.x >= gameState.getMap().getWidth() ||
                            targetPos.y >= gameState.getMap().getHeight()
                    ) {
                leftWasPressed = false; // Don't let player move
            }
            Vec2i playerPos = gameState.getPlayerPos();
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
            Direction moveDir;
            if (dx == 0) {
                if (dy == -1) {
                    moveDir = Direction.DIR_NORTH;
                } else if (dy == 1) {
                    moveDir = Direction.DIR_SOUTH;
                } else {
                    moveDir = Direction.DIR_NODIR;
                }
            } else if (dx == -1) {
                if (dy == -1) {
                    moveDir = Direction.DIR_NORTH_WEST;
                } else if (dy == 1) {
                    moveDir = Direction.DIR_SOUTH_WEST;
                } else {
                    moveDir = Direction.DIR_WEST;
                }
            } else {
                if (dy == -1) {
                    moveDir = Direction.DIR_NORTH_EAST;
                } else if (dy == 1) {
                    moveDir = Direction.DIR_SOUTH_EAST;
                } else {
                    moveDir = Direction.DIR_EAST;
                }
            }

            // Set the move cursor needed
            Gdx.graphics.setCursor(
                    gameState.getCursorSet().getDirCursor(moveDir)
            );

            // Check if player did something (mouse click)
            if (leftWasPressed) {
                if (UnitLogic.movePlayer(
                        gameState, playerPos, moveDir
                )) {
                    gameState.step();
                    Gdx.graphics.setCursor(
                            gameState.getCursorSet().getMainCursor()
                    );
                    updatePlayerVisibility();
                }
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

}
