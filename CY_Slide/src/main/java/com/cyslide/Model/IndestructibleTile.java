package com.cyslide.Model;
public class IndestructibleTile extends Tile {

    public IndestructibleTile(int posX,int posY) {
        super(posX,posY);
        setType(0); // indicates this tile is indestructible
    }

    @Override
    public void SwapTile(int i1, int j1,int i2, int j2, Tile[][] tab){
        // nothing for this type of tile.
    }

    @Override
    public void moved (int posX, int posY){
        // this if function for another type of tile.
    }
    @Override
    public void move(String direction, RectangleWithLabel [][] table){
        // this if function for another type of tile.
    }
    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel [][] table){
        // this if function for another type of tile.
        return true;
    }
    @Override
    public void move2(String direction, Tile [][] table){
        // this if function for another type of tile.
    }
    @Override
    public boolean mouvementAvailable2(String direction, Tile [][] table){
        // this if function for another type of tile.
        return true;
    }
}