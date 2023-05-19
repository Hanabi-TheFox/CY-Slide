package com.cyslide.Model;
public abstract class Tile{
    private int posX;
    private int posY;
    private int type;

    public abstract void move(String direction, RectangleWithLabel [][] table);
    public abstract boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel [][] table);
    public abstract void moved (int posX, int posY);

    public abstract void move2(String direction, Tile [][] table);
    public abstract boolean mouvementAvailable2(String direction, Tile [][] table);

    public abstract void SwapTile(int i1, int j1,int i2, int j2, Tile[][] tab);

    public Tile(int posX,int posY) {
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

    public void showTile(){
        if(type == 1){
            NumberTile nb = (NumberTile) this;
            System.out.println("nb = " + nb.getNumber());
        }
        if(type == -1){
            //EmptyTile empty = (EmptyTile) this;
            System.out.println("tile empty.");
        }
        if(type == 0){
            //IndestructibleTile ind = (IndestructibleTile) this;
            System.out.println("tile indestrutible.");
        }
    }
}