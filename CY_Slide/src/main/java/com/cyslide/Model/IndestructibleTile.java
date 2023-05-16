package com.cyslide.Model;

public class IndestructibleTile extends Tile {


    IndestructibleTile(int posX,int posY) {
        super(posX,posY);
        setType(0); // indicates this tile is indestructible
    }
}