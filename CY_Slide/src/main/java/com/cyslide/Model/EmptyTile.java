package com.cyslide.Model;

import javafx.scene.shape.Rectangle;

/**
 * Represents an empty tile in the CySlide game.
 * EmptyTile extends the Tile class and represents a tile with no number on it.
 */
public class EmptyTile extends Tile {

    /**
     * Constructs an empty tile object with the specified position.
     *
     * @param posX The x-coordinate of the tile.
     * @param posY The y-coordinate of the tile.
     */
    public EmptyTile(int posX, int posY) {
        super(posX, posY);
        setType(-1); // Indicates this tile is empty
    }

    /**
     * Updates the position of the empty tile.
     *
     * @param posX The new x-coordinate of the tile.
     * @param posY The new y-coordinate of the tile.
     */
    @Override
    public void moved(int posX, int posY) {
        // The tile has been moved, so we change its coordinates
        setPosX(posX);
        setPosY(posY);
    }

    /**
     * Moves the empty tile in the specified direction on the game table.
     *
     * @param direction The direction to move the tile.
     * @param table     The game table represented by a two-dimensional array of RectangleWithLabel objects.
     */
    @Override
    public void move(String direction, RectangleWithLabel[][] table) {
        if (direction.equals("UP")) {
            // We go to the top of the matrix, so we must reduce the position of the first tab which is table[]
            // We change our table
            swapRectangles(getPosX(), getPosY(), getPosX() - 1, getPosY(), table);
            // Now we change coordinates of the empty tile
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            // Now we set coordinates of the number tile
            table[getPosX() - 1][getPosY()].GetTile().moved(getPosX() - 1, getPosY());
        } else if (direction.equals("DOWN")) {
            swapRectangles(getPosX(), getPosY(), getPosX() + 1, getPosY(), table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX() + 1][getPosY()].GetTile().moved(getPosX() + 1, getPosY());
        } else if (direction.equals("LEFT")) {
            // We go to the left of the matrix, so we must reduce the position of the second tab which is table[][]
            swapRectangles(getPosX(), getPosY(), getPosX(), getPosY() - 1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY() - 1].GetTile().moved(getPosX(), getPosY() - 1);
        } else if (direction.equals("RIGHT")) {
            swapRectangles(getPosX(), getPosY(), getPosX(), getPosY() + 1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY() + 1].GetTile().moved(getPosX(), getPosY() + 1);
        }
    }

    /**
     * Swaps two RectangleWithLabel objects in the table array.
     *
     * @param i1   The x-coordinate of the first tile.
     * @param j1   The y-coordinate of the first tile.
     * @param i2   The x-coordinate of the second tile.
     * @param j2   The y-coordinate of the second tile.
     * @param tab  The two-dimensional array representing the game table.
     */
    public void swapRectangles(int i1, int j1, int i2, int j2, RectangleWithLabel[][] tab) {
        RectangleWithLabel tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }

    /**
     * Checks if a movement is available for the empty tile in the specified direction on the game table.
     *
     * @param x         The x-coordinate of the empty tile.
     * @param y         The y-coordinate of the empty tile.
     * @param direction The direction to check for movement.
     * @param table     The game table represented by a two-dimensional array of RectangleWithLabel objects.
     * @return True if a movement is available, false otherwise.
     */
    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel[][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[x - 1][y].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[x + 1][y].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[x][y + 1].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[x][y - 1].GetTile().getType() != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the empty tile in the specified direction on the game table represented by a two-dimensional array of Tile objects.
     *
     * @param direction The direction to move the tile.
     * @param table     The game table represented by a two-dimensional array of Tile objects.
     */
    @Override
    public void move2(String direction, Tile[][] table) {
        if (direction.equals("UP")) {
            // We go to the top of the matrix, so we must reduce the position of the first tab which is table[]
            // We change our table
            SwapTile(getPosX(), getPosY(), getPosX() - 1, getPosY(), table);
            // Now we change coordinates of the empty tile
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            // Now we set coordinates of the number tile
            table[getPosX() - 1][getPosY()].moved(getPosX() - 1, getPosY());
        } else if (direction.equals("DOWN")) {
            SwapTile(getPosX(), getPosY(), getPosX() + 1, getPosY(), table);
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            table[getPosX() + 1][getPosY()].moved(getPosX() + 1, getPosY());
        } else if (direction.equals("LEFT")) {
            // We go to the left of the matrix, so we must reduce the position of the second tab which is table[][]
            SwapTile(getPosX(), getPosY(), getPosX(), getPosY() - 1, table);
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            table[getPosX()][getPosY() - 1].moved(getPosX(), getPosY() - 1);
        } else if (direction.equals("RIGHT")) {
            SwapTile(getPosX(), getPosY(), getPosX(), getPosY() + 1, table);
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            table[getPosX()][getPosY() + 1].moved(getPosX(), getPosY() + 1);
        }
    }

    /**
     * Swaps two Tile objects in the table array.
     *
     * @param i1   The x-coordinate of the first tile.
     * @param j1   The y-coordinate of the first tile.
     * @param i2   The x-coordinate of the second tile.
     * @param j2   The y-coordinate of the second tile.
     * @param tab  The two-dimensional array representing the game table.
     */
    @Override
    public void SwapTile(int i1, int j1, int i2, int j2, Tile[][] tab) {
        Tile tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }

    /**
     * Checks if a movement is available for the empty tile in the specified direction on the game table represented by a two-dimensional array of Tile objects.
     *
     * @param direction The direction to check for movement.
     * @param table     The game table represented by a two-dimensional array of Tile objects.
     * @return True if a movement is available, false otherwise.
     */
    @Override
    public boolean mouvementAvailable2(String direction, Tile[][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[getPosX() - 1][getPosY()].getType() != 1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[getPosX() + 1][getPosY()].getType() != 1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[getPosX()][getPosY() + 1].getType() != 1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[getPosX()][getPosY() - 1].getType() != 1) {
                return false;
            }
        }
        return true;
    }

}
