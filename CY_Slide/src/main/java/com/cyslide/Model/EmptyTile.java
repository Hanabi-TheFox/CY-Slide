package com.cyslide.Model;

public class EmptyTile extends Tile {

    private static final int number = -1;// indicates this tile is empty

    EmptyTile(int posX,int posY) {
        super(posX,posY);
        setType(-1);
    }

    public static int getNumber() {
        return number;
    }

}