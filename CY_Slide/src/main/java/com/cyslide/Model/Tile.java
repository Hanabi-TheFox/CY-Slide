package com.cyslide.Model;

/**
 * The abstract class representing a tile in the game.
 */
public abstract class Tile implements Cloneable{
    private int posX;
    private int posY;
    private int type;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tile other = (Tile) obj;
        if (this.type == 1 && other.type == 1) {
            NumberTile thisNumberTile = (NumberTile) this;
            NumberTile otherNumberTile = (NumberTile) other;
            return thisNumberTile.equals(otherNumberTile);
        } else {
            return this.type == other.type;
        }
    }    

    @Override
    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (CloneNotSupportedException e) {
            // Gérer l'exception si la classe n'est pas clonable
            return null;
        }
    }

    /**
     * Moves the tile in the specified direction.
     *
     * @param direction The direction of the movement.
     * @param table     The game table containing the tiles.
     */
    public abstract void move(String direction, RectangleWithLabel [][] table);

    /**
     * Checks if the tile can be moved in the specified direction.
     *
     * @param x        The x-coordinate of the tile.
     * @param y        The y-coordinate of the tile.
     * @param direction The direction to check for movement.
     * @param table     The game table containing the tiles.
     * @return `true` if the movement is available, `false` otherwise.
     */
    public abstract boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel [][] table);

    /**
     * Updates the position of the tile after a successful move.
     *
     * @param posX The new x-coordinate of the tile.
     * @param posY The new y-coordinate of the tile.
     */
    public abstract void moved (int posX, int posY);

     /**
     * Moves the tile in the specified direction.
     *
     * @param direction The direction of the movement.
     * @param table     The game table containing the tiles.
     */
    public abstract void move2(String direction, Tile [][] table);

     /**
     * Checks if the tile can be moved in the specified direction.
     *
     * @param direction The direction to check for movement.
     * @param table     The game table containing the tiles.
     * @return `true` if the movement is available, `false` otherwise.
     */
    public abstract boolean mouvementAvailable2(String direction, Tile [][] table);

    /**
     * Swaps the positions of two tiles on the game table.
     *
     * @param i1   The x-coordinate of the first tile.
     * @param j1   The y-coordinate of the first tile.
     * @param i2   The x-coordinate of the second tile.
     * @param j2   The y-coordinate of the second tile.
     * @param tab  The game table containing the tiles.
     */
    public abstract void SwapTile(int i1, int j1,int i2, int j2, Tile[][] tab);

    /**
     * Constructs a tile with the specified position.
     *
     * @param posX The x-coordinate of the tile.
     * @param posY The y-coordinate of the tile.
     */
    public Tile(int posX,int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the type of the tile.
     *
     * @return The type of the tile.
     */
    public int getType(){
        return this.type;
    }

    /**
     * Sets the type of the tile.
     *
     * @param type The type of the tile.
     */
    public void setType(int type){
        this.type = type;;
    }

    /**
     * Gets the x-coordinate of the tile.
     *
     * @return The x-coordinate of the tile.
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Sets the y-coordinate of the tile.
     *
     * @param posY The y-coordinate of the tile.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Sets the x-coordinate of the tile.
     *
     * @param posX The x-coordinate of the tile.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Gets the y-coordinate of the tile.
     *
     * @return The y-coordinate of the tile.
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     * Displays information about the tile.
     */
    public void showTile(){
        if(type == 1){
            NumberTile nb = (NumberTile) this;
            System.out.println("nb = " + nb.getNumber());
        }
        if(type == -1){
            //EmptyTile empty = (EmptyTile) this;
            System.out.println("tile empty.");
        }
        if(type == 0){
            //IndestructibleTile ind = (IndestructibleTile) this;
            System.out.println("tile indestrutible.");
        }
    }
}