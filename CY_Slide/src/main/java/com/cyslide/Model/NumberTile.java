package com.cyslide.Model;

public class NumberTile extends Tile{
    private int number; //Tile Number
    private boolean blocked; //True if tile canot be played

    NumberTile(int number, int posX, int posY) {
        super(posX, posY);
        this.number = number;
    }

        public void move(String direction) {
            //TODO
            if (mouvementAvailable(direction) == true) {
                //TODO
            }
        }
        public boolean mouvementAvailable(String direction) {
            return true;
        }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setBlocked() {
        this.blocked = true;
    }
    public void setUnblocked(){
        this.blocked = false;
    }
}