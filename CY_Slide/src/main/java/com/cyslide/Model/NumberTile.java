package com.cyslide.Model;

public class NumberTile extends Tile{
    private int number; //Tile Number
    private boolean blocked; //True if tile canot be played

    public NumberTile(int number, int posX, int posY) {
        super(posX, posY);
        setType(1);
        this.number = number;
    }

        public void move(String direction, Tile [][] table) {
            if(direction=="UP"){
                setPosX(getPosX()-1); // we go to the top of the matrix so we must reduce the position of the first tab which is table[]
                // Now we change our table
                SwapTile(getPosX(), getPosY(), getPosX()-1, getPosY(), table);
            }else if(direction=="DOWN"){
                setPosX(getPosX()+1);
                SwapTile(getPosX(), getPosY(), getPosX()+1, getPosY(), table);
            }else if(direction=="LEFT"){ // we go to the left of the matrix so we must reduce the position of the second tab which is table[][]
                setPosY(getPosY()-1);
                SwapTile(getPosX(), getPosY(), getPosX(), getPosY()-1, table);
            }else if(direction=="RIGHT"){
                setPosY(getPosY()+1);
                SwapTile(getPosX(), getPosY(), getPosX(), getPosY()+1, table);
            }
        }
        public boolean mouvementAvailable(String direction, Tile [][] table) {   // attention si probleme rajouter les "this".getPosX()
            if(direction == "UP"){
                if (table[getPosX()-1][getPosY()].getType() != -1 || getPosX() == 0){
                    return false;
                }
            }else if (direction == "DOWN"){
                if (table[getPosX()+1][getPosY()].getType() != -1 || getPosX() == table.length){
                    return false;
                }
            }else if (direction == "RIGHT"){
                if (table[getPosX()][getPosY()+1].getType() != -1 || getPosY() == 0){
                    return false;
                }
            }else if (direction == "LEFT"){
                if (table[getPosX()][getPosY()-1].getType() != -1 || getPosY() == table[getPosX()].length){
                    return false;
                }
            }
            return true;
        }

    public void SwapTile(int i1, int j1,int i2, int j2, Tile[][] tab){
        Tile tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
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