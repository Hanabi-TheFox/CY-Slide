package com.cyslide.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.binding.NumberBinding;

/**
 * @author @RDNATOS
 * @author @Ymasuu
 */
public class Level{
        private int number;
        private int moveCounter;
        private boolean completed;
        private int record;
        private Tile[][] table;//table is resolved first
                        //And when Play button is clicked,
                        //the table changes its tiles randomly

        public Level(int number){
                this.number = number;
                this.moveCounter = 0;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
        }

        public int getNumber(){
                return number;
        }
        public int getMoveCounter(){
                return moveCounter;
        }
        public void setMoveCounter(int moveCounter){
                this.moveCounter = moveCounter;
        }
        public boolean getCompleted(){
                return completed;
        }
        public void setCompleted(boolean completed){
                this.completed = completed;
        }
        public int getRecord(){
                return record;
        }
        public void setRecord(int record){
                this.record = record;
        }
        public Tile[][] getTable(){
                return table;
        }

        public int recoverRecord(int number){
                String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Record.csv";
                String line = "";
                int record = -1; // if anyone succed the level, record = -1.
                try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
                        while ((line = br.readLine()) != null) {
                                String[] rowValues = line.split(";");
                                if (!rowValues[0].equals("Level")){
                                        int lvl = Integer.parseInt(rowValues[0]); // we search the line of our level
                                        if (lvl == number){
                                                record = Integer.parseInt(rowValues[1]);
                                        }
                                }
                        }
                System.out.println("File Found");
                } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error reading file");
                }
                return record;
        }

        public Tile[][] recoverLvl(int number) {
                Tile[][] tab = null; // Initialize tab outside the loop

                int numRow = 0;
                int numCol = 0;
        
                String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Level" + number + ".csv";
                String line = "";
        
                try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
                        while ((line = br.readLine()) != null) {
                                String[] rowValues = line.split(";");
                                numCol = rowValues.length; // Count the number of col
                                numRow++; // Count the number of Rows
                        }
                System.out.println("File Found");
                } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error reading file");
                }
                System.out.println("Ligne : " + numRow);
                System.out.println("Col : " + numCol);
                tab = new Tile[numRow][numCol];

                try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
                        int row = 0;
                        int counter = 1;
                        while ((line = br.readLine()) != null) {
                            String[] rowValues = line.split(";");
                            for (int col = 0; col < rowValues.length; col++) {
                                int value = Integer.parseInt(rowValues[col]);
                                if (value == 1){ // So it's a number tile
                                        tab[row][col] = new NumberTile(counter, row, col);
                                        counter++;
                                }else if(value == -1){ // it's an empty tile 
                                        tab[row][col] = new EmptyTile(row, col);
                                }else { // it's an indestructible tile
                                        tab[row][col] = new IndestructibleTile(row, col);
                                }
                            }
                            row++;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error reading file");
                }
                System.out.println("Level " + number);
                System.out.println("Tile Length: " + numRow);
                System.out.println("Tile Width: " + numCol);
            
                return tab;
            }

        
        public boolean moveTile(int x, int y, String direction, RectangleWithLabel[][] table){
                // pour changer la place d'une tuile, on appelle la fonction level.moveTile(posX, posY, direction) avec posX, posY les coordonnées et direction le sens ("UP, DOWN, RIGHT, LEFT")
                System.out.println("Test moveTile");
                        
                if (!table[x][y].GetTile().mouvementAvailable(x, y, direction, table) || table[x][y].GetTile().getType() == 0) {
                        System.out.println("We cannot move this tile.");
                        return false;
                }else{
                        table[x][y].GetTile().move(direction, table);
                        moveCounter++;
                        return true;
                }
        }
        
        public boolean isCompleted(int number){
                Level finalState = new Level(number);
                int size = table.length;
                for (int i = 0; i < size; i++){
                        for (int j = 0; j < size; j++){
                                if(table[i][j].getType() != finalState.getTable()[i][j].getType()){
                                        return false;
                                }
                                if(table[i][j].getType() == 1){
                                        NumberTile nb1 = (NumberTile) table[i][j];
                                        NumberTile nb2 = (NumberTile) finalState.getTable()[i][j];
                                        if(nb1.getNumber() != nb2.getNumber()){
                                                return false;
                                        }
                                }
                        }
                }
                return true;
        }
        public boolean isPlayable(){
                //Verify if level generated can be completed
                return true;
        }
        //Here we move tile by tile, so it's possible to complete the level
        public void initLevelMove() {
                //Generate random level by moving each tile, we are sure this level can be completed
                //TODO
                System.out.println("Level : " + this.number);
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
