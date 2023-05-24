package com.cyslide.Model;

/**
 * The IndestructibleTile class represents an indestructible tile in the game.
 * It extends the Tile class and inherits its properties and methods.
 */
public class IndestructibleTile extends Tile {

    /**
     * Constructs a new IndestructibleTile object with the specified position.
     *
     * @param posX The X-axis position of the tile.
     * @param posY The Y-axis position of the tile.
     */
    public IndestructibleTile(int posX, int posY) {
        super(posX, posY);
        setType(0); // indicates this tile is indestructible
    }

    /**
     * This method is overridden to do nothing for this type of tile.
     *
     * @param i1  The X-axis position of the first tile.
     * @param j1  The Y-axis position of the first tile.
     * @param i2  The X-axis position of the second tile.
     * @param j2  The Y-axis position of the second tile.
     * @param tab The game table consisting of Tile objects.
     */
    @Override
    public void SwapTile(int i1, int j1, int i2, int j2, Tile[][] tab) {
        // nothing for this type of tile.
    }

    /**
     * This method is overridden to do nothing for this type of tile.
     *
     * @param posX The new X-axis position of the tile.
     * @param posY The new Y-axis position of the tile.
     */
    @Override
    public void moved(int posX, int posY) {
        // this if function for another type of tile.
    }

    /**
     * This method is overridden to do nothing for this type of tile.
     *
     * @param direction The direction in which to move the tile ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of RectangleWithLabel objects.
     */
    @Override
    public void move(String direction, RectangleWithLabel[][] table) {
        // this if function for another type of tile.
    }

    /**
     * This method is overridden to always return true for this type of tile.
     *
     * @param x         The X-axis position of the tile.
     * @param y         The Y-axis position of the tile.
     * @param direction The direction in which to check the movement availability ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of RectangleWithLabel objects.
     * @return Always returns true.
     */
    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel[][] table) {
        // this if function for another type of tile.
        return true;
    }

    /**
     * This method is overridden to do nothing for this type of tile.
     *
     * @param direction The direction in which to move the tile ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of Tile objects.
     */
    @Override
    public void move2(String direction, Tile[][] table) {
        // this if function for another type of tile.
    }

    /**
     * This method is overridden to always return true for this type of tile.
     *
     * @param direction The direction in which to check the movement availability ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of Tile objects.
     * @return Always returns true.
     */
    @Override
    public boolean mouvementAvailable2(String direction, Tile[][] table) {
        // this if function for another type of tile.
        return true;
    }
}