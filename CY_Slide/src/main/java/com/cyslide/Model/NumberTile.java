package com.cyslide.Model;

public class NumberTile extends Tile{
    private int number; //Tile Number
    private boolean blocked; //True if tile canot be played

    NumberTile(int number, int posX, int posY) {
        super(posX, posY);
        setType(1);
        this.number = number;
    }

        public void move(String direction, Tile [][] table) {
            //TODO
            if (mouvementAvailable(direction, table) == true) {
                //TODO
            }
        }
        public boolean mouvementAvailable(String direction, Tile [][] table) {        
            if(direction == "UP"){
                if (table[getPosX()-1][getPosY()].getType() != -1 || getPosX() == 0){
                    return false;
                }
            }else if (direction == "DOWN"){
                if (table[getPosX()+1][getPosY()].getType() != -1 || table.length == getPosX()){
                    return false;
                }
            }else if (direction == "RIGHT"){
                if (table[getPosX()][getPosY()+1].getType() != -1 || table[getPosX()].length == 0){
                    return false;
                }
            }else if (direction == "LEFT"){
                if (table[getPosX()][getPosY()-1].getType() != -1 || table[getPosX()].length == getPosY()){
                    return false;
                }
            }
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