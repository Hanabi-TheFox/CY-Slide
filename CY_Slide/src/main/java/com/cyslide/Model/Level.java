package com.cyslide.Model;

import java.util.ArrayList;
public class Level{
        private int number;
        private int moveCounter;
        private boolean completed;
        private Record record;

        private ArrayList<Tile> tiles;
        private Tile[][] table;

        public Level(int number, Record record) throws PlayerPseudoException{
                this.number = number;
                this.moveCounter = 0;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
                //TODO
        }

        public Record recoverRecord(int number) throws PlayerPseudoException{
                //TODO (recover data from file)
                Player P = new Player("Ymasuu");
                return new Record(P, number, 0);
                //TODO
        }
        public Tile[][] recoverLvl(int number){
                //TODO (recover data from file)
                Tile [][] tab;
                tab = new Tile[3][3];
                return tab;
        }

        public void moveTile(int posX,int posY,String direction)throws MoveTileException{
                //the mouvement direction can be Left,Right,Up,Down
                //We verify if the neighbor is 0 (it doest exist, is empty)
                if(direction=="UP"){
                posY-=posY;
                //TODO
                }
                else if(direction=="DOWN"){
                posY+=posY;
                //TODO
                }

                else if(direction=="LEFT"){
                posX-=posX;
                //TODO
                }
                else if(direction=="RIGHT"){
                posX+=posX;
                //TODO
                }
        }
        public boolean isCompleted(){
                //TODO
                return false;
        }
        public boolean isPlayable(){
                //Verify if level generated can be completed
                //TODO
                return false;
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