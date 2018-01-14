package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.blueprints.Direction;

/**
 * Cursor set object used to wrap all of the important cursors of the game
 */
public class CursorSet implements Disposable {
    Cursor mainCursor;
    ArrayMap<Direction, Cursor> dirCursors;


    /**
     * Construct a new cursor set using the predefined path
     * @param cursorSetDirPath Path to the cursor images
     */
    public CursorSet(String cursorSetDirPath) {
        // Currently, this will be a static initialization of cursors:
        mainCursor = loadCursor(cursorSetDirPath + "cursor_2x.png", 0, 0);
        dirCursors = new ArrayMap<Direction, Cursor>();

        dirCursors.put(
                Direction.DIR_NORTH,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_0.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_NORTH_EAST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_1.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_EAST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_2.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_SOUTH_EAST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_3.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_SOUTH,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_4.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_SOUTH_WEST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_5.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_WEST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_6.png",
                        15,
                        15
                )
        );
        dirCursors.put(
                Direction.DIR_NORTH_WEST,
                loadCursor(
                        cursorSetDirPath + "cursor_movedir_2x_7.png",
                        15,
                        15
                )
        );

        // Add cursor for no direction:
        dirCursors.put(
                Direction.DIR_NODIR,
                mainCursor
        );
    }

    /**
     * Load one of the cursors
     * @param path Path to the cursor
     * @param xHotSpot Hot spot x coordinate on image
     * @param yHotspot Hot spot y coordinate on image
     * @return New Cursor
     * @see Cursor
     */
    private Cursor loadCursor(String path, int xHotSpot, int yHotspot) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));
        return Gdx.graphics.newCursor(pixmap, xHotSpot, yHotspot);
    }


    /**
     * Get main cursor of the set
     * @return Cursor object
     */
    public Cursor getMainCursor() {
        return mainCursor;
    }

    /**
     * Get direction cursor for one of the directions
     * @param direction Direction of action
     * @return Cursor associated to direction
     */
    public Cursor getDirCursor(Direction direction) {
        return dirCursors.get(direction);
    }

    @Override
    public void dispose() {
        mainCursor.dispose();

        ArrayMap.Values<Cursor> dirCursorIterator = dirCursors.values();
        while (dirCursorIterator.hasNext()) {
            dirCursorIterator.next().dispose();
        }
    }
}
