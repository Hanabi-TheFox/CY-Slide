package com.cyslide.Model;
public class IndestructibleTile extends Tile {

    public IndestructibleTile(int posX,int posY) {
        super(posX,posY);
        setType(0); // indicates this tile is indestructible
    }


    @Override
    public void moved (int posX, int posY){
        // this if function for another type of tile.
    }
    @Override
    public void move(String direction, Tile [][] table){
        // this if function for another type of tile.
    }
    @Override
    public boolean mouvementAvailable(String direction, Tile [][] table){
        // this if function for another type of tile.
        return true;
    }
}