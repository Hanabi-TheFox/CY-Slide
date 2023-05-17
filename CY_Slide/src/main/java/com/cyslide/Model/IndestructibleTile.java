package com.cyslide.Model;
public class IndestructibleTile extends Tile {

    public IndestructibleTile(int posX,int posY) {
        super(posX,posY);
        setType(0); // indicates this tile is indestructible
    }


    @Override
    public void moved (int posX, int posY){
        // this id function for EmptyTile
    }
    @Override
    public void move(String direction, Tile [][] table){
        // this if function for NumberTile
    }
    @Override
    public boolean mouvementAvailable(String direction, Tile [][] table){
        // this is function for NumberTile
        return true;
    }
}