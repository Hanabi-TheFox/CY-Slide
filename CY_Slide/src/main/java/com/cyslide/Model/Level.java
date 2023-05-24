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
 * @author @RDNATOS
 * @author @Ymasuu
 */
public class Level{
        private CySlideController controller;
        private int number;
        private int moveCounter;
        private boolean randomized;
        private boolean completed;
        private int record;
        private Tile[][] table;//table is resolved first
                        //And when Play button is clicked,
                        //the table changes its tiles randomly

        public Level(int number){
                this.controller = null;
                this.number = number;
                this.moveCounter = 0;
                this.randomized = false;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
        }

        public Level(int number, CySlideController controller){
                this.controller = controller;
                this.number = number;
                this.moveCounter = 0;
                this.randomized = false;
                this.completed = false;
                this.record = recoverRecord(number);
                this.table = recoverLvl(number);
        }

        public int getNumber(){
                return number;
        }
        public boolean getRandomized(){
                return randomized;
        }
        public int getMoveCounter(){
                return moveCounter;
        }
        public void setMoveCounter(int moveCounter){
                this.moveCounter = moveCounter;
        }
        public void setRandomized(boolean randomized){
                this.randomized = randomized;
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
                if (!table[x][y].GetTile().mouvementAvailable(x, y, direction, table) || table[x][y].GetTile().getType() == 0) {
                        System.out.println("We cannot move this tile.");
                        return false;
                }else{
                        table[x][y].GetTile().move(direction, table);
                        moveCounter++;
                        this.controller.handleMoveTileEvent();
                        return true;
                }
        }

        public boolean moveTile2(int x, int y, String direction){                        
                if (!table[x][y].mouvementAvailable2(direction, table) || table[x][y].getType() == 0) {
                        return false;
                }else{
                        table[x][y].move2(direction, table);
                        return true;
                }
        }
        
        /**
         * 
         * @param number
         * @return boolean
         * Returns true if Level is Completed by the player.
         * It compares the players's level with a solved one.
         * We verify 2 int matrix to see if they have the sae values
         */
        
         public boolean isCompleted(int[][] currentTable) {
                Level finalState = new Level(number); // We get a completed version of the level
            
                // We transform finalState into an int Matrix
                int[][] resolvedTable = this.LevelToIntMatrix(finalState);
            
                int size = currentTable.length;
            
                // Check if the matrices have the same values
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (currentTable[i][j] != resolvedTable[i][j]) {
                            // If any corresponding elements differ, the matrices are not the same
                            return false;
                        }
                    }
                }
            
                // Matrices have the same values
                setCompleted(true);
                return true;
            }
            
            
        
        
        /* public boolean isCompleted(int number, Tile[][] Rectangles){
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
        } */
        
        //Here we move tile by tile, so it's possible to complete the level
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

        //We get a level object and we transform its Tile[][] into int[][]
        public int[][] LevelToIntMatrix(Level level) {
                Tile[][] tileMatrix= level.getTable();
                int[][] matrix = new int[tileMatrix.length][tileMatrix.length];
                // Retrieve the necessary information from the Level object and populate the matrix
                for (int i = 0; i < tileMatrix.length; i++) {
                    for (int j = 0; j < tileMatrix.length; j++) {
                        Tile tile = tileMatrix[i][j];
                        if (tile instanceof NumberTile) {
                            NumberTile numberTile = (NumberTile) tile;
                            matrix[i][j] = numberTile.getNumber();
                        } else {
                            // Handle other tile types if necessary
                            matrix[i][j] = 0; // Or any default value you prefer
                        }
                    }
                }
            
                return matrix;
            }
            





        //Once the level is charged, we can move all tiles in
        // a random order
        //Here is entirely random, so we are not sure if the level
        //can be finished (BONUS)
        
        public void initLevelRNG(){
                //TODO
        }

        public boolean isPlayable(){
                //Verify if level generated can be completed
                return true;
        }
                
}
class MoveTileException extends Exception {
    public MoveTileException(String message) {
        super(message);
    }
}