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
                if (this.mouvementAvailable(posX,posY,direction, table) == false) {
                        throw new MoveTileException("we cannot move this tile");
                }else{
                        Tile tmp; //Tile for swap
                        //table[posX][posY].moveTile(direction, table);
                        
                        switch (direction) {
                                case "UP":
                                    System.out.println("Moving Up");
                                    tmp = table[posX - 1][posY]; //tmp is the emptyTile
                                    table[posX - 1][posY] = table[posX][posY]; // The emptyTile now has the tile that was moved
                                    table[posX][posY] = tmp; //the tile we moved now is empty
                                    break;
                                case "DOWN":
                                    System.out.println("Moving Down");
                                    tmp = table[posX + 1][posY]; //tmp is the emptyTile
                                    table[posX + 1][posY] = table[posX][posY]; // The emptyTile now has the tile that was moved
                                    table[posX][posY] = tmp; //the tile we moved now is empty
                                    break;
                                case "LEFT":
                                    System.out.println("Moving Left");
                                    tmp = table[posX][posY-1]; //tmp is the emptyTile
                                    table[posX][posY-1] = table[posX][posY]; // The emptyTile now has the tile that was moved
                                    table[posX][posY] = tmp; //the tile we moved now is empty
                                    break;
                                case "RIGHT":
                                    System.out.println("Moving Right");
                                    tmp = table[posX][posY+1]; //tmp is the emptyTile
                                    table[posX][posY+1] = table[posX][posY]; // The emptyTile now has the tile that was moved
                                    table[posX][posY] = tmp; //the tile we moved now is empty
                                    break;
                                default:
                                    // if directions is not (Up,down,left or right)
                                    System.out.println("Invalid Direction");
                            }
    
                }
                                
                /*7 */
        }

        //This method verifies if the mouvement can be made
        public boolean mouvementAvailable(int posX, int posY, String direction, Tile[][] table) {
                if (direction.equals("UP") && posX - 1 >= 0 && posX - 1 < table.length && posY >= 0 && posY < table[posX - 1].length) {
                    if (table[posX - 1][posY].getNumber() == -1) {
                        return true;
                    }
                } else if (direction.equals("DOWN") && posX + 1 >= 0 && posX + 1 < table.length && posY >= 0 && posY < table[posX + 1].length) {
                    if (table[posX + 1][posY].getNumber() == -1) {
                        return true;
                    }
                } else if (direction.equals("LEFT") && posX >= 0 && posY - 1 < table.length && posY - 1 >= 0 && posY - 1 < table[posX].length) {
                    if (table[posX][posY - 1].getNumber() == -1) {
                        return true;
                    }
                } else if (direction.equals("RIGHT") && posX >= 0 && posX < table.length && posY + 1 >= 0 && posY + 1 < table[posX].length) {
                    if (table[posX][posY + 1].getNumber() == -1) {
                        return true;
                    }
                }
            
                return false;
            }
            
            public boolean isCompleted() {
                // TODO
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