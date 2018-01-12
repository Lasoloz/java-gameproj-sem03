package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.blueprints.InstanceType;
import com.github.lasoloz.gameproj.control.details.GameMap;
import com.github.lasoloz.gameproj.control.details.GameMapTile;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.control.details.Subject;
import com.github.lasoloz.gameproj.entitites.Instance;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

import java.util.ArrayList;
import java.util.Random;

public class GameController extends Subject {
    private Random rnd = new Random(TimeUtils.millis());


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
            if (updatePlayer()) {
                updateEnemies();
            }
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


    private boolean updatePlayer() {
        Vec2i playerPos = gameState.getPlayerPos();
        Instance player = gameState.getMap().getInstance(
                playerPos.x, playerPos.y
        );
        if (player.getHealth() <= 0) {
//            gameState.defeat();
        }

        // Alternative:
        boolean leftWasPressed = gameState.getInput().isLeftPressed();
        boolean rightWasPressed = gameState.getInput().isRightPressed();
        // Check move direction:
        Vec2i targetPos = gameState.getRelativeMouseGridPos();
        int dx = targetPos.x - playerPos.x;
        int dy = targetPos.y - playerPos.y;

        // To far...
        if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
            Gdx.graphics.setCursor(
                    gameState.getCursorSet().getMainCursor()
            );
            return false;
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
                return true;
            }
        } else if (rightWasPressed) {
            UnitLogic.playerSpecialInteraction(
                    gameState,
                    targetPos
            );
            gameState.step();
            return true;
        }

        return false;
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
        ArrayList<Vec2i> oldEnemyPositions = gameState.
                getCurrentEnemyPositions();

        gameState.resetEnemyPositions();

        GameMap map = gameState.getMap();
        for (Vec2i pos : oldEnemyPositions) {
            Instance current = map.getInstance(pos.x, pos.y);
            if (current != null &&
                    current.getBlueprint().getType() == InstanceType.ENEMY
                    ) {
                updateEnemy(current, pos.x, pos.y);
            }
        }
    }

    private void updateEnemy(Instance enemy, int x, int y) {
        Vec2i oldPos = new Vec2i(x, y);
        if (enemy.getHealth() <= 0) {
            gameState.getMap().getGameMapTile(x, y).removeContent();
            return;
        }

        if (enemy.isStunned()) {
            gameState.addEnemyPosition(oldPos);
            return;
        }

        Vec2i playerPos = gameState.getPlayerPos();

        int dx = playerPos.x - x;
        int dy = playerPos.y - y;
        int ndx = normalize(dx);
        int ndy = normalize(dy);
        if (Math.abs(dx) > Math.abs(dy)) {
            ndy = 0;
            if (rnd.nextInt(20) < 2) { // Sorry for the magic number :(
                ndx = 0;
            }
        } else {
            ndx = 0;
            if (rnd.nextInt(20) < 2) { // Same
                ndy = 0;
            }
        }

        Vec2i targetPos = new Vec2i(x + ndx, y + ndy);

//        System.out.println(
//                "Action from: " + oldPos + " to " + targetPos +
//                        "; dx" + dx + "; dy: " + dy);

        UnitLogic.enemyInteraction(gameState, oldPos, targetPos);
    }

    private static int normalize(int delta) {
        if (delta < 0) {
            return -1;
        } else if (delta > 0){
            return 1;
        } else {
            return 0;
        }
    }

}
