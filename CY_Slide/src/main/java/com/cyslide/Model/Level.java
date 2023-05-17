package com.cyslide.Model;
import java.util.ArrayList;
public class Level{
        private int number;
        private int moveCounter;
        private boolean completed;
        private Record record;
        private ArrayList<Tile> tiles;
        private Tile[][] table;

        public void moveTile(int posX,int posY,String direction)throws MoveTileException{
                //the mouvement direction can be Left,Right,Up,Down
                //We verify if the neighbor is 0 (it doest exist, is empty)
                if (!table[posX][posY].mouvementAvailable(direction, table)) {
                        throw new MoveTileException("we cannot move this tile");
                }else{
                        table[posX][posY].move(direction, table);
                }
        }
        public boolean isCompleted(){
                //TODO
                return false;
        }
        public boolean isPlayable(){
                //Verify if level generated can be completed
                return true;
        }
        //Here we move tile by tile, so it's possible to complete the level
        public void initLevelMove() {
                //Generate random level by moving each tile, we are sure this level can be completed
                //TODO
        }
        //Once the level is charged, we can move all tiles in
        // a random order
        //Here is entirely random, so we are not sure if the level
        //can be finished (BONUS)
        
        public void initLevelRNG(){
                //TODO
        }
                
}
class MoveTileException extends Exception {
    public MoveTileException(String message) {
        super(message);
    }
}
