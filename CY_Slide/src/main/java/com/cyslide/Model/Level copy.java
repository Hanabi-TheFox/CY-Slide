package com.cyslide.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
                this.table = recoverLvl();
                //TODO
        }

        public Record recoverRecord(int number) throws PlayerPseudoException{
                //TODO (recover data from file)
                Player P = new Player("Ymasuu");
                return new Record(P, number, 0);
        }
        
        public Tile[][] recoverLvl() {
                String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Level1.csv";
                String line = "";
            
                int numRow = 0;
                int numCol = 0;
            
                try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                                continue; // Ignore empty line
                        }
                        String[] rowValues = line.split(";");
            
                        numCol = rowValues.length;
                        numRow++;
            
                        System.out.println(line);
                    }
                    System.out.println("File Found");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error reading file");
                }
            
                Tile[][] tab = new Tile[numRow][numCol];

                // Print elements of tab
                for (int i = 0; i < numRow; i++) {
                        for (int j = 0; j < numCol; j++) {
                                Tile tile = tab[i][j];
                                System.out.println("Tile at position (" + i + ", " + j + "): " + tile);
                        }
                }
                return tab;
            }
            
            


        public void moveTile(int posX,int posY,String direction)throws MoveTileException{
                //the mouvement direction can be Left,Right,Up,Down
                //We verify if the neighbor is 0 (it doest exist, is empty)
                /*if (this.verifyPseudo(pseudo) == false) {
                        throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
                }*/


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