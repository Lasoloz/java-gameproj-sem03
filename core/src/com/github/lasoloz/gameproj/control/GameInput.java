package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.lasoloz.gameproj.blueprints.Direction;
import com.github.lasoloz.gameproj.math.Vec2f;
import com.github.lasoloz.gameproj.math.Vec2i;

public class GameInput implements InputProcessor {
    private int mouseX = 0, mouseY = 0;
    private int lastScrollState = 0;
    private int scrollState = 0;
    private Vec2f relPoint = new Vec2f(0f, 0f);
    private Vec2f inputVector = new Vec2f(0f, 0f);
    private boolean gridState = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            inputVector.addToY( 3f);
            return true;
        } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            inputVector.addToX( 3f);
            return true;
        } else if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            inputVector.addToY(-3f);
            return true;
        } else if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            inputVector.addToX(-3f);
            return true;
        } else if (keycode == Input.Keys.G) {
            gridState = !gridState;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            inputVector.addToY(-3f);
            return true;
        } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            inputVector.addToX(-3f);
            return true;
        } else if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            inputVector.addToY( 3f);
            return true;
        } else if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            inputVector.addToX( 3f);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case Input.Buttons.LEFT:
                leftPressed = true;
                break;
            case Input.Buttons.RIGHT:
                rightPressed = true;
                break;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        lastScrollState = scrollState;
        scrollState += amount;
        return true;
    }


    public Vec2f getCameraFocus(
            Vec2f playerPos,
            Vec2i screenSize,
            int displayDiv
    ) {
        float pPosX = playerPos.getX();
        float pPosY = playerPos.getY();
        float deltaX = (mouseX - (float) screenSize.x / 2) / displayDiv;
        float deltaY = ((float) screenSize.y / 2 - mouseY) / displayDiv;
        if (displayDiv > 2) {
            float oPosX = pPosX + deltaX;
            float oPosY = pPosY + deltaY;

            // Set relative mouse coordinates:
            relPoint.setX(oPosX + deltaX / 2);
            relPoint.setY(oPosY + deltaY / 2);

            return new Vec2f(
                    (pPosX + oPosX) / 2f,
                    (pPosY + oPosY) / 2f
            );
        } else {
            relPoint.setX(pPosX + deltaX);
            relPoint.setY(pPosY + deltaY);
            return new Vec2f(
                    pPosX,
                    pPosY
            );
        }
    }

    public Vec2f getRelativeMouseCoord() {
        return relPoint;
    }

    public int getScrollState() {
        int state = scrollState - lastScrollState;
        lastScrollState = scrollState;
        return state;
    }

    public Vec2f getInputVector() {
        return inputVector;
    }

    public boolean getGridState() {
        return gridState;
    }


    public boolean isLeftPressed() {
        boolean leftPressed = this.leftPressed;
        this.leftPressed = false;
        return leftPressed;
    }

    public boolean isRightPressed() {
        boolean rightPressed = this.rightPressed;
        this.rightPressed = false;
        return rightPressed;
    }

    public Vec2i getMouseCoord() {
        return new Vec2i(mouseX, mouseY);
    }

    public Direction getDirectionFromCoord(Vec2i screenSize) {
        int xm = mouseX - screenSize.x / 2;
        int ym = mouseY - screenSize.y / 2;

        if (Math.abs(xm) < 28 && Math.abs(ym) < 16) {
            return Direction.DIR_NODIR;
        }

        float l01 = 8f/7f;
        float l23 = 2f/7f;
        float m0 =  l01;
        float m1 = -l01;
        float m2 = -l23;
        float m3 =  l23;

        boolean h0 = ym > m0 * xm;
        boolean h1 = ym > m1 * xm;
        boolean h2 = ym > m2 * xm;
        boolean h3 = ym > m3 * xm;


        if (h0) {
            if (h1) {
                return Direction.DIR_SOUTH;
            }

            if (h2) {
                return Direction.DIR_SOUTH_WEST;
            }

            if (h3) {
                return Direction.DIR_WEST;
            }

            return Direction.DIR_NORTH_WEST;
        }

        if (!h1) {
            return Direction.DIR_NORTH;
        }

        if (!h2) {
            return Direction.DIR_NORTH_EAST;
        }

        if (!h3) {
            return Direction.DIR_EAST;
        }

        return Direction.DIR_SOUTH_EAST;
    }
}
