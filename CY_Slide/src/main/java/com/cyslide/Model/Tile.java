package com.cyslide.Model;

public abstract class Tile{
    private int posX;
    private int posY;
    private int number; //if -1, empty tile
                        //if -2, indestructible tile
                        //if >=0, tile is a normal tile


    public Tile(int posX,int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }
}