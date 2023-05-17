package com.cyslide.Model;
public abstract class Tile{
    private int posX;
    private int posY;
    private int type;

    public abstract void move(String direction, Tile [][] table);
    public abstract boolean mouvementAvailable(String direction, Tile [][] table);
    public abstract void moved (int posX, int posY);

    Tile(int posX,int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public int getType(){
        return this.type;
    }
    public void setType(int type){
        this.type = type;;
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