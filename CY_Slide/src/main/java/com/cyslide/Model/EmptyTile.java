package com.cyslide.Model;

import javafx.scene.shape.Rectangle;

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
    public void move(String direction, RectangleWithLabel [][] table){
        if(direction=="UP"){
            // we go to the top of the matrix so we must reduce the position of the first tab which is table[]
           // We change our table
           SwapRectangles(getPosX(), getPosY(), getPosX()-1, getPosY(), table);
           // Now we change coordinates of the number tile
           table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
           // Now we set coordinate of the empty tile
           setPosX(getPosX()-1);
       }else if(direction=="DOWN"){
            SwapRectangles(getPosX(), getPosY(), getPosX()+1, getPosY(), table);
           table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
           setPosX(getPosX()+1);
       }else if(direction=="LEFT"){ // we go to the left of the matrix so we must reduce the position of the second tab which is table[][]
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY()-1, table);
           table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
           setPosY(getPosY()-1);
       }else if(direction=="RIGHT"){
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY()+1, table);
           table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
           setPosY(getPosY()+1);
       }
    }
    public void SwapRectangles(int i1, int j1,int i2, int j2, RectangleWithLabel[][] tab){
        RectangleWithLabel tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }
    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel[][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[x-1][y].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[x+1][y].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[x][y+1].GetTile().getType() != 1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[x][y-1].GetTile().getType() != 1) {
                return false;
            }
        }
        return true;
    }

}