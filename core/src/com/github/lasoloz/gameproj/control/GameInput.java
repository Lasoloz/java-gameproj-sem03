package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameInput implements InputProcessor {
    private int mouseX, mouseY;
    private int lastScrollState = 0;
    private int scrollState = 0;
    private Vec2f relPoint = new Vec2f(0f, 0f);
    private Vec2f inputVector = new Vec2f(0f, 0f);
    private boolean gridState = false;

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
        return false;
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
            Vec2f screenSize,
            int displayDiv
    ) {
        float pPosX = playerPos.getX();
        float pPosY = playerPos.getY();
        float deltaX = (mouseX - screenSize.getX() / 2) / displayDiv;
        float deltaY = (screenSize.getY() / 2 - mouseY) / displayDiv;
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
}
