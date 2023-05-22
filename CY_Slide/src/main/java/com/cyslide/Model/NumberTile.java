package com.cyslide.Model;

//import org.w3c.dom.css.Rect;

public class NumberTile extends Tile{
    private int number; //Tile Number

    public NumberTile(int number, int posX, int posY) {
        super(posX, posY);
        setType(1);
        this.number = number;
    }

    @Override
    public void move(String direction, RectangleWithLabel [][] table) {
        if(direction=="UP"){
                // we go to the top of the matrix so we must reduce the position of the first tab which is table[]
            // We change our table
            SwapRectangles(getPosX(), getPosY(), getPosX()-1, getPosY(), table);
            // Now we change coordinates of the empty tile
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            // Now we set coordinate of the number tile
            table[getPosX()-1][getPosY()].GetTile().moved(getPosX()-1, getPosY());
        }else if(direction=="DOWN"){
            SwapRectangles(getPosX(), getPosY(), getPosX()+1, getPosY(), table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()+1][getPosY()].GetTile().moved(getPosX()+1, getPosY());
        }else if(direction=="LEFT"){ // we go to the left of the matrix so we must reduce the position of the second tab which is table[][]
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY()-1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY()-1].GetTile().moved(getPosX(), getPosY()-1);
        }else if(direction=="RIGHT"){
            SwapRectangles(getPosX(), getPosY(), getPosX(), getPosY()+1, table);
            table[getPosX()][getPosY()].GetTile().moved(getPosX(), getPosY());
            table[getPosX()][getPosY()+1].GetTile().moved(getPosX(), getPosY()+1);
        }
    }

    @Override
    public boolean mouvementAvailable(int x, int y, String direction, RectangleWithLabel [][] table) {
        if (direction.equals("UP")) {
            if (getPosX() == 0 || table[x-1][y].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            if (getPosX() == table.length - 1 || table[x+1][y].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            if (getPosY() == table[getPosX()].length - 1 || table[x][y+1].GetTile().getType() != -1) {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            if (getPosY() == 0 || table[x][y-1].GetTile().getType() != -1) {
                return false;
            }
        }
        return true;
    }
    public void SwapRectangles(int i1, int j1,int i2, int j2, RectangleWithLabel[][] tab){
        RectangleWithLabel tmp = tab[i1][j1];
        tab[i1][j1] = tab[i2][j2];
        tab[i2][j2] = tmp;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    @Override
    public void moved(int posX, int posY) {
        // the tile has been moved so we change its coordinates
        setPosX(posX);
        setPosY(posY);
    }

    @Override
        public void move2(String direction, Tile [][] table) {
            if(direction=="UP"){
                 // we go to the top of the matrix so we must reduce the position of the first tab which is table[x][] 
                // We change our table
                SwapTile(getPosX(), getPosY(), getPosX()-1, getPosY(), table);
                // Now we change coordinates of the empty tile
                table[getPosX()][getPosY()].moved(getPosX(), getPosY());
                // Now we set coordinate of the number tile
                table[getPosX()-1][getPosY()].moved(getPosX()-1, getPosY());
            }else if(direction=="DOWN"){
                SwapTile(getPosX(), getPosY(), getPosX()+1, getPosY(), table);
                table[getPosX()][getPosY()].moved(getPosX(), getPosY());
                table[getPosX()+1][getPosY()].moved(getPosX()+1, getPosY());
            }else if(direction=="LEFT"){ // we go to the left of the matrix so we must reduce the position of the second tab which is table[][y]
                SwapTile(getPosX(), getPosY(), getPosX(), getPosY()-1, table);
                table[getPosX()][getPosY()].moved(getPosX(), getPosY());
                table[getPosX()][getPosY()-1].moved(getPosX(), getPosY()-1);
            }else if(direction=="RIGHT"){
                SwapTile(getPosX(), getPosY(), getPosX(), getPosY()+1, table);
                table[getPosX()][getPosY()].moved(getPosX(), getPosY());
                table[getPosX()][getPosY()+1].moved(getPosX(), getPosY()+1);
            }
        }

        @Override
        public boolean mouvementAvailable2(String direction, Tile [][] table) {
            if (direction.equals("UP")) {
                if (getPosX() == 0 || table[getPosX()-1][getPosY()].getType() != -1) {
                    return false;
                }
            } else if (direction.equals("DOWN")) {
                if (getPosX() == table.length - 1 || table[getPosX()+1][getPosY()].getType() != -1) {
                    return false;
                }
            } else if (direction.equals("RIGHT")) {
                if (getPosY() == table[getPosX()].length - 1 || table[getPosX()][getPosY()+1].getType() != -1) {
                    return false;
                }
            } else if (direction.equals("LEFT")) {
                if (getPosY() == 0 || table[getPosX()][getPosY()-1].getType() != -1) {
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
}