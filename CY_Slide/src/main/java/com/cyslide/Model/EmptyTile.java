package com.cyslide.Model;

public abstract class EmptyTile extends Tile {

    private static final int number = -1;// indicates this tile is empty

    EmptyTile(int posX,int posY) {
        super(posX,posY);
        setType(-1); // indicates this tile is empty
    }

    public static int getNumber() {
        return number;
    }

    @Override
    public void moved (int posX, int posY){
        // the tile has been moved so we change its coordinates
        setPosX(posX);
        setPosY(posY);
    }

}