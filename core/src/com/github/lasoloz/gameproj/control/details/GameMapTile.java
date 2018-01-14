package com.github.lasoloz.gameproj.control.details;

import com.github.lasoloz.gameproj.entitites.Instance;

/**
 * Game map tile defining a terrain type and instance standing over terrain
 * Private members:
 * tileCode - (currently integer, later will be enum, defining tile type)
 * content - Instance standing on tile.
 * seen - Boolean specifying if tile is seen by player
 * known - Boolean specifying if tile is known by player
 */
public class GameMapTile {
    private int tileCode;
    private Instance content;
    private boolean seen;
    private boolean known;

    /**
     * Constructor for map tile
     * @param tileCode Terrain tile code
     */
    GameMapTile(int tileCode) {
        /// TODO: Create enum type for tile code
        if (tileCode < -1 || tileCode > 1) {
            this.tileCode = 0;
        } else {
            this.tileCode = tileCode;
        }

        content = null;
    }

    /**
     * Get terrain tile code of current game map tile
     * @return Terrain tile code
     */
    public int getTileCode() {
        return tileCode;
    }

    /**
     * Get content of the tile
     * @return Instance standing on the tile
     * @see Instance
     */
    public Instance getContent() {
        return content;
    }

    /**
     * set the content of the tile
     * @param content New instance used for the tile
     */
    public void setContent(Instance content) {
        this.content = content;
    }

    /**
     * Remove content from the tile
     */
    public void removeContent() {
        this.content = null;
    }

    /**
     * Move content from this tile to the other
     * @param other Other tile, to which we move our content
     */
    public void moveContent(GameMapTile other) {
        other.setContent(this.content);
        removeContent();
    }


    /**
     * Get seen status
     * @return Boolean specifying if tile is seen
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Set seen status
     * @param seen Boolean specifying if tile is seen
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Get known status
     * @return Boolean specifying id tile is known by player
     */
    public boolean isKnown() {
        return known;
    }

    /**
     * Set known status
     * @param known Boolean specifying id tile is known by player
     */
    public void setKnown(boolean known) {
        this.known = known;
    }
}
