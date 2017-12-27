package com.github.lasoloz.gameproj.control;

import com.badlogic.gdx.InputProcessor;
import com.github.lasoloz.gameproj.math.Vec2f;

public class GameInput implements InputProcessor {
    int mouseX, mouseY;
    Vec2f relPoint = new Vec2f(0f, 0f);

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
        return false;
    }


    public Vec2f getCameraFocus(
            Vec2f playerPos,
            Vec2f screenSize
    ) {
        float pPosX = playerPos.getX();
        float pPosY = playerPos.getY();
        float deltaX = (mouseX - screenSize.getX() / 2) / 4;
        float deltaY = (screenSize.getY() / 2 - mouseY) / 4;
        float oPosX = pPosX + deltaX;
        float oPosY = pPosY + deltaY;

        // Set relative mouse coordinates:
        relPoint.setX(oPosX);
        relPoint.setY(oPosY);

        return new Vec2f(
                (pPosX + oPosX) / 2f,
                (pPosY + oPosY) / 2f
        );
    }

    /**
     * Returns the points relative to the player's position
     * @return
     */
    public Vec2f getRelativeMouseCoord() {
        return relPoint;
    }
}
