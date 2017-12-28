package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.InputProcessor;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameInput implements InputProcessor {
    private int mouseX, mouseY;
    private int lastScrollState = 0;
    private int scrollState = 0;
    private Vec2f relPoint = new Vec2f(0f, 0f);

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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

    /**
     * Returns the points relative to the player's position
     * @return
     */
    public Vec2f getRelativeMouseCoord() {
        return relPoint;
    }

    public int getScrollState() {
        int state = scrollState - lastScrollState;
        lastScrollState = scrollState;
        return state;
    }
}
