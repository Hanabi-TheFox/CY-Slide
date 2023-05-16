package com.cyslide.Model;

public class IndestructibleTile extends Tile {

    private static final int number = 0;// indicates this tile is indestructible

    IndestructibleTile(int posX,int posY) {
        super(posX,posY);
        setType(0);
    }

    public static int getNumber() {
        return number;
    }

}