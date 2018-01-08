package com.github.lasoloz.gameproj.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.github.lasoloz.gameproj.blueprints.Direction;

public class CursorSet implements Disposable {
    Cursor mainCursor;
    ArrayMap<Direction, Cursor> dirCursors;


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

    private Cursor loadCursor(String path, int xHotSpot, int yHotspot) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));
        return Gdx.graphics.newCursor(pixmap, xHotSpot, yHotspot);
    }


    public Cursor getMainCursor() {
        return mainCursor;
    }

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
