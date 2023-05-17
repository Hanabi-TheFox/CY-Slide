package com.cyslide.Model;
public class EmptyTile extends Tile {

    public EmptyTile(int posX,int posY) {
        super(posX,posY);
        setType(-1); // indicates this tile is empty
    }

    @Override
    public void moved (int posX, int posY){
        // the tile has been moved so we change its coordinates
        setPosX(posX);
        setPosY(posY);
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