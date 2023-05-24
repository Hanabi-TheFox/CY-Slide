package com.cyslide.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import com.cyslide.CySlideController;

import javafx.beans.binding.NumberBinding;

/**
 * Class that represents a level of the game CY-Slide
 * a level is composed of a table of tiles and others informations related to the level
 */
public class Level{
        private CySlideController controller;
        private int number;
        private int moveCounter;
        private boolean randomized;
        private boolean completed;
        private int record;
        private Tile[][] table;

        /**
         * Constructor of Level
         * Initialize the attributes of the level
         * @param number the number of the level
         */
        public Level(int number){
                this.controller = null;
                this.number = number;
                this.moveCounter = 0;
                this.randomized = false;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
        }

        /**
         * Constructor of Level
         * Initialize the attributes of the level
         * @param number the number of the level
         * @param controller the controller of the game
         */
        public Level(int number, CySlideController controller){
                this.controller = controller;
                this.number = number;
                this.moveCounter = 0;
                this.randomized = false;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
        }

        /**
         * Getters for the number of the level
         * @return The number of the level
         */
        public int getNumber(){
                return number;
        }
        /**
         * Getters for the randomized boolean
         * @return true if the level is randomized, false otherwise
         */
        public boolean getRandomized(){
                return randomized;
        }
        /**
         * Setters for the randomized boolean
         * @param randomized
         */
        public void setRandomized(boolean randomized){
                this.randomized = randomized;
        }
        /**
         * Getters for the moveCounter
         * @return moveCounter the number of moves done by the player
         */
        public int getMoveCounter(){
                return moveCounter;
        }
        /**
         * Setters for the moveCounter
         * @param int
         */
        public void setMoveCounter(int moveCounter){
                this.moveCounter = moveCounter;
        }
        /**
         * Getters for the completed boolean
         * @return true if the level is completed, false otherwise
         */
        public boolean getCompleted(){
                return completed;
        }
        /**
         * Setters for the completed boolean
         * @param completed
         */
        public void setCompleted(boolean completed){
                this.completed = completed;
        }
        /**
         * Getters for the record
         * @return record the record of the level
         */
        public int getRecord(){
                return record;
        }
        /**
         * Setters for the record
         * @param record
         */
        public void setRecord(int record){
                this.record = record;
        }
        /**
         * Getters for the table
         * @return table the table of tiles of the level
         */
        public Tile[][] getTable(){
                return table;
        }
        /**
         * recover the record of the level in the file Record.csv
         * @param number the number of the level
         * @return record the record of the level
         */
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

        /**
         * recover the table of tiles of the level in the file LevelX.csv (X = number of the level)
         * @param number the number of the level
         * @return tab the table of tiles of the level
         */
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

        /**
         * Move a tile in the chosen direction
         * @param x the x coordinate of the tile
         * @param y the y coordinate of the tile
         * @param direction the direction of the movement
         * @param table the table of tiles of the level
         * @return true if the movement is possible, false otherwise
         */
        public boolean moveTile(int x, int y, String direction, RectangleWithLabel[][] table){                        
                if (!table[x][y].GetTile().mouvementAvailable(x, y, direction, table) || table[x][y].GetTile().getType() == 0) {
                        System.out.println("We cannot move this tile.");
                        return false;
                }else{
                        table[x][y].GetTile().move(direction, table);
                        // each time we move a tile, we increment the moveCounter
                        moveCounter++;
                        // each turn, an event is triggered in the controller
                        this.controller.handleMoveTileEvent();
                        return true;
                }
        }
        /**
         * Move a tile in the chosen direction
         * @param x the x coordinate of the tile
         * @param y the y coordinate of the tile
         * @param direction the direction of the movement
         * @return true if the movement is possible, false otherwise
         */
        public boolean moveTile2(int x, int y, String direction){                        
                if (!table[x][y].mouvementAvailable2(direction, table) || table[x][y].getType() == 0) {
                        return false;
                }else{
                        table[x][y].move2(direction, table);
                        return true;
                }
        }
        
        /**
         * Check if the level is completed
         * @param number the number of the level
         * @param Rectangles the table of tiles of the level
         * @return true if the level is completed, false otherwise
         */
        public boolean isCompleted(int number, Tile[][] Rectangles){
                Level finalState = new Level(number); //We get a completed version of the level
                int size = table.length;
                for (int i = 0; i < size; i++){
                        for (int j = 0; j < size; j++){
                                if(Rectangles[i][j].getType() != finalState.getTable()[i][j].getType()){
                                        return false;
                                }
                                if(table[i][j].getType() == 1){
                                        NumberTile nb1 = (NumberTile) Rectangles[i][j];
                                        NumberTile nb2 = (NumberTile) finalState.getTable()[i][j];
                                        if(nb1.getNumber() != nb2.getNumber()){
                                                return false;
                                        }
                                }
                        }
                }
                setCompleted(true);
                return true;
        }
        
        /**
         * Initialize the level by moving the tiles on random directions
         * with this method, we are sure that the level is solvable
         */
        public void initLevelMove() {
                int seed;
                if(number == 3 || number == 2 || number == 6){
                        seed = 1245;
                }else seed = 142;
                // we will create 500 elements of a random String List
                Random random = new Random(seed); // it's a seed that we have chosen after having seen the look of our levels
                String[] choice = {"UP", "DOWN", "RIGHT", "LEFT"};
                List<String> randomMoves = new ArrayList<>();
                for (int k = 0; k < 100; k++) {
                        int index = random.nextInt(4);
                        randomMoves.add(choice[index]);
                    }
                int size = table.length;
                for (int init = 0; init < 1000; init++){ // we want to do it 1000 times
                        for (String direction : randomMoves){   
                                for (int i = 0; i < size; i++){
                                        for (int j = 0; j < size; j++){
                                                if(table[i][j].getType() == -1){ // if it's an empty tile
                                                        if (moveTile2(i, j, direction)) {
                                                        // Movement is done
                                                        }
                                                }
                                        } 
                                }
                        }
                }
                System.out.println("Nous avons fini le mélange.");
        }

        /**
         * (Optional/Bonus part)
         * Initialize the level by setting the tiles in random positions
         * /!\This method can create a level that is not resolvable
         */
        public void initLevelRNG(){
                //TODO
        }

        /**
         * (Optional/Bonus part)
         * Check if the level is playable
         * @return Returns true if the level is playable, false otherwise
         * 
         */
        public boolean isPlayable(){
                //Verify if level generated can be completed
                return true;
        }
                
        /**
         * Save the record of the level in the file "record.txt" if it's a new record
         * @param number the number of the level
         * @param record the number of moves
         */
        public void saveRecord(int number, int record){
                //TODO: Implement this method
        }
}

/**
 * Exception that is thrown when we try to move a tile that cannot be moved
 */
class MoveTileException extends Exception {
        /**
         * Constructor of the exception
         * @param message the message of the exception
         */
        public MoveTileException(String message) {
                super(message);
        }
}
