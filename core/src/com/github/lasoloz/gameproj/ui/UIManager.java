package com.github.lasoloz.gameproj.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.control.GameController;
import com.github.lasoloz.gameproj.control.details.GameState;
import com.github.lasoloz.gameproj.graphics.CursorSet;
import com.github.lasoloz.gameproj.math.Vec2i;

public class UIManager implements InputProcessor, Updatable, Disposable {
    private static int MENUITEM_WIDTH = 600;
    private static int MENUITEM_OFFSET = 110;
    private static int MENUITEM_HEIGHT = 100;
    private static int FONT_SIZE = 32;
    private static int MENUITEM_PADDING = 10;
    private static int TEXT_INPUT_WIDTH = 600;
    private static int TEXT_INPUT_HEIGHT = 42;
    private static int BUTTON_WIDTH = 200;
    private static int BUTTON_HEIGHT = 100;
    private static int BUTTON_HOR_OFFSET = 20;
    private static int BUTTON_VERT_OFFSET = 100;
    private static int BUTTON_PADDING = 8;


    // Menu manager items:
    private OrthographicCamera uiCamera;
    private ShapeRenderer uiRenderer;
    private SpriteBatch uiBatch;
    private Vec2i screenSize;
    private Vec2i mousePos;
    private boolean mouseWasClicked;

    private Texture mainBackground;
    private Texture loadBackground;
    private BitmapFont font;
    private boolean mainMenu;

    private String mapPath;
    private CursorSet cursorSet;

    // Game items:
    GameState gameState;
    GameController gameController;


    private static UIManager uiManager;

    private UIManager(Vec2i screenSize) {
        uiCamera = new OrthographicCamera();
        uiCamera.viewportWidth = screenSize.x;
        uiCamera.viewportHeight = screenSize.y;
        uiCamera.update();

        mainMenu = true;
        Gdx.input.setInputProcessor(this);
        uiRenderer = new ShapeRenderer();
        uiBatch = new SpriteBatch();
        this.screenSize = screenSize.copy();

        mousePos = new Vec2i(0, 0);
        mouseWasClicked = false;

        mainBackground = new Texture("other/mainBackground.png");
        loadBackground = new Texture("other/loadBackground.png");

        mapPath = "maps/dungeon0.map.json";


        // Set up cursor:
        cursorSet = new CursorSet("other/");
        Gdx.graphics.setCursor(cursorSet.getMainCursor());

        // Font:
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Courier Prime/Courier Prime.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        font = generator.generateFont(parameter);
        generator.dispose();
    }


    public static UIManager getUIManager() {
        if (uiManager == null) {
            uiManager = new UIManager(
                    new Vec2i(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
            );
        }
        return uiManager;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void overrideControl() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean update() {
        if (mainMenu) {
            // Check for click event:
            if (mouseWasClicked) {
                mouseWasClicked = false;

                /*if (isSelected(-1)) {
                    System.out.println(
                            "Generating random dungeon is work in progress!"
                    );
                } else */if (isSelected(0)) {
                    mainMenu = false;
                } else if (isSelected(1)) {
                    System.out.println("Highscores  menu is work in progress!");
                }
            }
            renderMainMenu();
        } else {
            if (mouseWasClicked) {
                mouseWasClicked = false;

                if (isButtonSelected(0)) {
                    mainMenu = true;
                } else if (isButtonSelected(1)) {
                    gameState = new GameState(screenSize, 4);
                    if (gameState.loadMap(mapPath)) {
                        gameState.addCursorSet(cursorSet);
                        gameController = new GameController(gameState);
                        mainMenu = true;
                        return false;
                    } else {
                        /// TODO: Dialog window!
                        System.out.println("Failed to load specified map!");
                    }
                }
            }
            renderLoadMenu();
        }
        return true;
    }

    @Override
    public void resize(int width, int height) {
        screenSize.x = width;
        screenSize.y = height;
        uiCamera.viewportWidth = width;
        uiCamera.viewportHeight = height;
        uiCamera.update();
    }


    private void renderMainMenu() {
        uiBatch.begin();
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.draw(mainBackground, -917f, -462f);
        uiBatch.end();

        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = -1; i < 1/*2*/; ++i) {
            renderSelection(i);
        }
        uiRenderer.end();

        uiBatch.begin();
        renderMenuTexts();
        uiBatch.end();
    }

    private void renderLoadMenu() {
        uiBatch.begin();
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.draw(loadBackground, -650f, -400);
        uiBatch.end();

        uiRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        uiRenderer.setColor(.05f, .1f, .6f, .8f);
        uiRenderer.setProjectionMatrix(uiCamera.combined);
        // Render text input:
        uiRenderer.rect(
                -TEXT_INPUT_WIDTH / 2,
                TEXT_INPUT_HEIGHT,
                TEXT_INPUT_WIDTH,
                TEXT_INPUT_HEIGHT
        );

        // Render buttons:
        int leftX = -BUTTON_WIDTH - BUTTON_HOR_OFFSET / 2;
        int rightX = BUTTON_HOR_OFFSET / 2;
        int bottom = -BUTTON_VERT_OFFSET;

        // Ugly, but works
        if (isButtonSelected(1)) {
            uiRenderer.setColor(.05f, .2f, .8f, 1f);
        } else {
            uiRenderer.setColor(.05f, .1f, .6f, .8f);
        }
        uiRenderer.rect(
                leftX,
                bottom,
                BUTTON_WIDTH,
                BUTTON_HEIGHT
        );
        if (isButtonSelected(0)) {
            uiRenderer.setColor(.05f, .2f, .8f, 1f);
        } else {
            uiRenderer.setColor(.05f, .1f, .6f, .8f);
        }
        uiRenderer.rect(
                rightX,
                bottom,
                BUTTON_WIDTH,
                BUTTON_HEIGHT
        );
        uiRenderer.end();

        // Render text:
        uiBatch.begin();
        int fontY = bottom + BUTTON_HEIGHT / 2 + BUTTON_PADDING;
        font.draw(
                uiBatch,
                mapPath,
                -TEXT_INPUT_WIDTH / 2 + BUTTON_PADDING,
                TEXT_INPUT_HEIGHT + FONT_SIZE
        );
        font.draw(
                uiBatch,
                "Load",
                leftX + BUTTON_PADDING,
                fontY
        );
        font.draw(
                uiBatch,
                "Cancel",
                rightX + BUTTON_PADDING,
                fontY
        );
        uiBatch.end();
    }

    private void renderSelection(int selection) {
        uiRenderer.setProjectionMatrix(uiCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        if (isSelected(selection)) {
            uiRenderer.setColor(.05f, .2f, .8f, 1f);
        } else {
            uiRenderer.setColor(.05f, .1f, .6f, .8f);
        }
        uiRenderer.rect(
                -MENUITEM_WIDTH,
                selection * MENUITEM_OFFSET,
                MENUITEM_WIDTH,
                MENUITEM_HEIGHT
        );
    }

    private void renderMenuTexts() {
        int fontX = -MENUITEM_WIDTH + MENUITEM_PADDING;
        int yOffset = MENUITEM_HEIGHT / 2 + FONT_SIZE / 2 - MENUITEM_PADDING;
//        font.draw(
//                uiBatch,
//                "Create random dungeon (WIP)",
//                fontX,
//                MENUITEM_OFFSET + yOffset
//        );
        font.draw(
                uiBatch,
                "Load dungeon map",
                fontX,
                yOffset
        );
        font.draw(
                uiBatch,
                "Highscores (WIP)",
                fontX,
                yOffset - MENUITEM_OFFSET
        );
    }

    private boolean isSelected(int selection) {
        int left = -MENUITEM_WIDTH;
        int right = 0;
        int bottom = MENUITEM_OFFSET * selection;
        int top = bottom + MENUITEM_HEIGHT;

        int mouseX = mousePos.x - screenSize.x / 2;
        int mouseY = screenSize.y / 2 - mousePos.y;

        return mouseX > left && mouseX < right && mouseY > bottom &&
                mouseY < top;
    }

    private boolean isButtonSelected(int selection) {
        int left = -selection * BUTTON_WIDTH - BUTTON_HOR_OFFSET / 2;
        int bottom = -BUTTON_VERT_OFFSET;
        int top = bottom + BUTTON_HEIGHT;
        int right = left + BUTTON_WIDTH;

        int mouseX = mousePos.x - screenSize.x / 2;
        int mouseY = screenSize.y / 2 - mousePos.y;

        return mouseX > left && mouseX < right && mouseY > bottom &&
                mouseY < top;
    }



    // InputProcessor methods:
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACKSPACE && mapPath.length() > 0) {
            mapPath = mapPath.substring(0, mapPath.length() - 1);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if ((Character.isLetterOrDigit(character) ||
                character == '/' || character == '.'
        ) && mapPath.length() < 25) {
            mapPath += character;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mouseWasClicked = true;
        }
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
        mousePos.x = screenX;
        mousePos.y = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override public void dispose() {
        mainBackground.dispose();
        loadBackground.dispose();
    }
}
