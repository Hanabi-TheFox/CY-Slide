package com.cyslide.Model;

/**
 * The NumberTile class represents a numbered tile in the CY Slide game.
 * It extends the Tile class and adds functionality to store and manipulate a number associated with the tile.
 */
public class NumberTile extends Tile {
    private int number; // Tile Number

    /**
     * Constructs a NumberTile object with the specified number, position on the X-axis, and position on the Y-axis.
     *
     * @param number The number associated with the tile.
     * @param posX   The X-axis position of the tile.
     * @param posY   The Y-axis position of the tile.
     */
    public NumberTile(int number, int posX, int posY) {
        super(posX, posY);
        setType(1);
        this.number = number;
    }

    /**
     * Moves the number tile in the specified direction on the game table.
     *
     * @param direction The direction in which to move the tile ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of RectangleWithLabel objects.
     */
    @Override
    public void move(String direction, RectangleWithLabel[][] table) {
        if (direction.equals("UP")) {
            SwapRectangles(getPosX(), getPosY(), getPosX() - 1, getPosY(), table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX() - 1][getPosY()].GetTile().moved(getPosX() - 1, getPosY());
        } else if (direction.equals("DOWN")) {
            SwapRectangles(getPosX(), getPosY(), getPosX() + 1, getPosY(), table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX() + 1][getPosY()].GetTile().moved(getPosX() + 1, getPosY());
        } else if (direction.equals("LEFT")) {
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY() - 1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY() - 1].GetTile().moved(getPosX(), getPosY() - 1);
        } else if (direction.equals("RIGHT")) {
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY() + 1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY() + 1].GetTile().moved(getPosX(), getPosY() + 1);
        }
    }

    /**
     * Checks if a movement in the specified direction is available for the number tile.
     *
     * @param x         The X-axis position of the tile.
     * @param y         The Y-axis position of the tile.
     * @param direction The direction in which to check the movement availability ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of RectangleWithLabel objects.
     * @return True if the movement is available, false otherwise.
     */
    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel[][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[x - 1][y].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[x + 1][y].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[x][y + 1].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[x][y - 1].GetTile().getType() != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps the positions of two RectangleWithLabel objects in the game table.
     *
     * @param i1  The X-axis position of the first rectangle.
     * @param j1  The Y-axis position of the first rectangle.
     * @param i2  The X-axis position of the second rectangle.
     * @param j2  The Y-axis position of the second rectangle.
     * @param tab The game table consisting of RectangleWithLabel objects.
     */
    public void SwapRectangles(int i1, int j1, int i2, int j2, RectangleWithLabel[][] tab) {
        RectangleWithLabel tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }

    /**
     * Sets the number associated with the tile.
     *
     * @param number The number to set.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns the number associated with the tile.
     *
     * @return The number associated with the tile.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Updates the position of the tile to the specified coordinates.
     *
     * @param posX The new X-axis position of the tile.
     * @param posY The new Y-axis position of the tile.
     */
    @Override
    public void moved(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }

    /**
     * Moves the number tile in the specified direction on the game table (alternative method).
     *
     * @param direction The direction in which to move the tile ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of Tile objects.
     */
    @Override
    public void move2(String direction, Tile[][] table) {
        if (direction.equals("UP")) {
            SwapTile(getPosX(), getPosY(), getPosX() - 1, getPosY(), table);
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            table[getPosX() - 1][getPosY()].moved(getPosX() - 1, getPosY());
        } else if (direction.equals("DOWN")) {
            SwapTile(getPosX(), getPosY(), getPosX() + 1, getPosY(), table);
            table[getPosX()][getPosY()].moved(getPosX(), getPosY());
            table[getPosX() + 1][getPosY()].moved(getPosX() + 1, getPosY());
        } else if (direction.equals("LEFT")) {
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
     * Checks if a movement in the specified direction is available for the number tile (alternative method).
     *
     * @param direction The direction in which to check the movement availability ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param table     The game table consisting of Tile objects.
     * @return True if the movement is available, false otherwise.
     */
    @Override
    public boolean mouvementAvailable2(String direction, Tile[][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[getPosX() - 1][getPosY()].getType() != -1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[getPosX() + 1][getPosY()].getType() != -1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[getPosX()][getPosY() + 1].getType() != -1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[getPosX()][getPosY() - 1].getType() != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps the positions of two Tile objects in the game table (alternative method).
     *
     * @param i1  The X-axis position of the first tile.
     * @param j1  The Y-axis position of the first tile.
     * @param i2  The X-axis position of the second tile.
     * @param j2  The Y-axis position of the second tile.
     * @param tab The game table consisting of Tile objects.
     */
    public void SwapTile(int i1, int j1, int i2, int j2, Tile[][] tab) {
        Tile tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }
}