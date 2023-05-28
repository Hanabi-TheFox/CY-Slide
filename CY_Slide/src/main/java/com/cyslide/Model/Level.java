package com.cyslide.Model;

import com.cyslide.CySlideController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * The Level class represents a game level in CySlide.
 * It contains information about the level number, move counter, randomization status, completion status, record, and the game table.
 * The class provides methods for retrieving and modifying these properties, as well as recovering level data from files, moving tiles, and checking level completion.
 */
public class Level implements Cloneable{
    private CySlideController controller;
    private int number;
    private int moveCounter;
    private boolean randomized;
    private boolean completed;
    private int record;
    private Tile[][] table;

    /**
     * Constructs a new Level object with the specified level number.
     *
     * @param number The level number.
     */
    public Level(int number) {
        this.controller = null;
        this.number = number;
        this.moveCounter = 0;
        this.randomized = false;
        this.completed = false;
        this.record = recoverRecord(number);
        this.table = recoverLvl(number);
    }

    /**
     * Constructs a new Level object with the specified level number and controller.
     *
     * @param number     The level number.
     * @param controller The CySlideController object associated with the level.
     */
    public Level(int number, CySlideController controller) {
        this.controller = controller;
        this.number = number;
        this.moveCounter = 0;
        this.randomized = false;
        this.completed = false;
        this.record = recoverRecord(number);
        this.table = recoverLvl(number);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Level other = (Level) obj;
        return Arrays.deepEquals(table, other.table);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(table);
    }

    @Override
    public Level clone() {
        try {
            Level clonedLevel = (Level) super.clone();
            // Perform a deep copy of the table table
            clonedLevel.table = new Tile[table.length][table[0].length];
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    clonedLevel.table[i][j] = table[i][j].clone();
                }
            }
            return clonedLevel;
        } catch (CloneNotSupportedException e) {
            // Handle the exception if the class is not clonable
            return null;
        }
    }

    /**
     * Returns the level number.
     *
     * @return The level number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the randomization status of the level.
     *
     * @return The randomization status.
     */
    public boolean getRandomized() {
        return randomized;
    }

    /**
     * Returns the move counter of the level.
     *
     * @return The move counter.
     */
    public int getMoveCounter() {
        return moveCounter;
    }

    /**
     * Sets the move counter of the level.
     *
     * @param moveCounter The move counter to set.
     */
    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    /**
     * Sets the randomization status of the level.
     *
     * @param randomized The randomization status to set.
     */
    public void setRandomized(boolean randomized) {
        this.randomized = randomized;
    }

    /**
     * Returns the completion status of the level.
     *
     * @return The completion status.
     */
    public boolean getCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the level.
     *
     * @param completed The completion status to set.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Returns the record of the level.
     *
     * @return The record of the level.
     */
    public int getRecord() {
        return record;
    }

    /**
     * Sets the record of the level.
     *
     * @param record The record to set.
     */
    public void setRecord(int record) {
        this.record = record;
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Record.csv";
        String tempFile = "CY_Slide/src/main/java/com/cyslide/Data/RecordTemp.csv";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                if (rowValues[0].equals(Integer.toString(this.number))) {
                    rowValues[1] = Integer.toString(this.record);
                }
                bw.write(String.join(";", rowValues));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading or writing file.");
        }
        File oldFile = new File(pathFile);
        if (oldFile.delete()) {
            File newFile = new File(pathFile);
            if (newFile.exists()) {
                newFile.delete();
            }
            if (new File(tempFile).renameTo(newFile)) {
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Error updating file.");
            }
        } else {
            System.out.println("Error deleting file.");
        }
    }

    /**
     * Returns the game table of the level.
     *
     * @return The game table.
     */
    public Tile[][] getTable() {
        return table;
    }

    /**
     * Sets the table of the level.
     *
     * @param table The table to set.
     */
    public void setTable(Tile[][] table) {
        this.table = table;
    }

    /**
     * Recovers the record for the specified level from a file.
     *
     * @param number The level number.
     * @return The recovered record value.
     */
    public int recoverRecord(int number) {
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Record.csv";
        String line = "";
        int record = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                if (!rowValues[0].equals("Level")) {
                    int lvl = Integer.parseInt(rowValues[0]);
                    if (lvl == number) {
                        record = Integer.parseInt(rowValues[1]);
                    }
                }
            }
            //System.out.println("File Found");
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Error reading file");
        }
        return record;
    }

    /**
     * Recovers the level data from a file and constructs the game table.
     *
     * @param number The level number.
     * @return The constructed game table.
     */
    public Tile[][] recoverLvl(int number) {
        Tile[][] tab = null;

        int numRow = 0;
        int numCol = 0;

        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Level" + number + ".csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                numCol = rowValues.length;
                numRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tab = new Tile[numRow][numCol];

        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            int row = 0;
            int counter = 1;
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                for (int col = 0; col < rowValues.length; col++) {
                    int value = Integer.parseInt(rowValues[col]);
                    if (value == 1) {
                        tab[row][col] = new NumberTile(counter, row, col);
                        counter++;
                    } else if (value == -1) {
                        tab[row][col] = new EmptyTile(row, col);
                    } else {
                        tab[row][col] = new IndestructibleTile(row, col);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    /**
     * Moves a tile in the specified direction on the game table.
     *
     * @param x      The x-coordinate of the tile.
     * @param y      The y-coordinate of the tile.
     * @param direction The direction to move the tile.
     * @param table  The game table containing the tiles.
     * @return True if the tile was successfully moved, false otherwise.
     */
    public boolean moveTile(int x, int y, String direction, RectangleWithLabel[][] table) {
        if (!table[x][y].GetTile().mouvementAvailable(x, y, direction, table) || table[x][y].GetTile().getType() == 0) {
            System.out.println("We cannot move this tile.");
            return false;
        } else {
            table[x][y].GetTile().move(direction, table);
            moveCounter++;
            this.controller.handleMoveTileEvent();
            return true;
        }
    }

    /**
     * Moves a tile in the specified direction on the game table. This is the function for automatic resolution 
     *
     * @param x         The x-coordinate of the tile.
     * @param y         The y-coordinate of the tile.
     * @param direction The direction to move the tile.
     * @return True if the tile was successfully moved, false otherwise.
     */
    public boolean moveTile2(int x, int y, String direction) {
        if (!table[x][y].mouvementAvailable2(direction, table) || table[x][y].getType() == 0) {
            return false;
        } else {
            table[x][y].move2(direction, table);
            //System.out.println("Print table : ");
            //AStarAlgo.printState2(table);
            return true;
        }
    }

    /**
     * Checks if the level is completed by comparing the current table with a solved one.
     *
     * @param currentTable The current game table.
     * @return True if the level is completed, false otherwise.
     */
    public boolean isCompleted(int[][] currentTable) {
        //We get a Level Object with the solved Matrix
        Level finalState = new Level(number);
        //Conversion of Level Matrix to Int Matrix
        int[][] resolvedTable = this.LevelToIntMatrix(finalState);
        int size = currentTable.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (currentTable[i][j] != resolvedTable[i][j]) {
                    return false;
                }
            }
        }

        setCompleted(true);
        return true;
    }
    
    /**
     * Checks if the level is completed by comparing the current table with a solved one.
     * This is the function for automatic resolution
     * @param number The level number.
     * @param tab The current game table.
     * @return True if the level is completed, false otherwise.
     */
    public boolean isCompleted2(int number, Tile[][] tab){
        Level finalState = new Level(number); //We get a completed version of the level
        int size = table.length;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if(tab[i][j].getType() != finalState.getTable()[i][j].getType()){
                    return false;
                }
                if(table[i][j].getType() == 1){
                NumberTile nb1 = (NumberTile) tab[i][j];
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
     * Initializes the level by moving the tiles in a random order.
     */
    public void initLevelMove() {
        // these comments show how to use a seed system
        /*int seed;
        if (number == 3 || number == 2 || number == 6) {
            seed = 1245;
        } else seed = 142;

        Random random = new Random();*/
        String[] choice = {"UP", "DOWN", "RIGHT", "LEFT"};
        /*List<String> randomMoves = new ArrayList<>();
        for (int k = 0; k < 100; k++) {
            int index = random.nextInt(4);
            randomMoves.add(choice[index]);
        }*/
        while(isInitialPos(this)){
            
            List<Point> emptyPositions = findEmptyPositions();
            Random random = new Random();
            int randomEmpty = random.nextInt(emptyPositions.size());
            Point emptyPoint = emptyPositions.get(randomEmpty);
            int randomDirection = random.nextInt(4);
            String direction = choice[randomDirection];
            moveTile2(emptyPoint.x, emptyPoint.y, direction);
        }
    }

    /**
     * Initializes the level by moving the tiles in a random order.
     * This is the function for automatic resolution
     * @param Level The level to initialize
     * @return True if the level is initialized, false otherwise.
     */
    public boolean isInitialPos(Level level){
        Level initialLevel = new Level(level.getNumber());
        Tile[][] initialTab = initialLevel.getTable();
        Tile[][] tab = level.getTable();
        for(int i = 0; i < tab.length; i++){
            for(int j = 0; j < tab.length; j++){
                if(tab[i][j].getType() == initialTab[i][j].getType()){
                    if(tab[i][j].getType() == 1){
                        NumberTile nb1 = (NumberTile) tab[i][j];
                        NumberTile nb2 = (NumberTile) initialTab[i][j];
                        if(nb1.getNumber() == nb2.getNumber()){
                            // we do not want tiles to be in their initial position
                            return true;
                        }
                    }else if(tab[i][j].getType() == -1){
                        // we do not want tiles to be in their initial position
                        return true; 
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds the empty positions on the game table.
     *
     * @return A list of empty positions.
     */
    private List<Point> findEmptyPositions() {
        List<Point> emptyPositions = new ArrayList<>();
    
        for (int i = 0; i < getTable().length; i++) {
            for (int j = 0; j < getTable()[i].length; j++) {
                if (getTable()[i][j].getType() == -1) {
                    emptyPositions.add(new Point(i, j));
                }
            }
        }
        return emptyPositions;
    }


    /**
     * Converts a Level object into an int[][] matrix.
     *
     * @param level The level to convert.
     * @return The int[][] matrix.
     */
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
                    matrix[i][j] = tile.getType(); // Or any default value you prefer
                }
            }
        }
        return matrix;
    }

    /**
     * read a matrix and convert it on the table of the level
     *
     * @param table The int[][] matrix.
     */
    public void MatrixToLevel(int[][] table2) {
        Tile[][] table = new Tile[table2.length][table2.length];
        for (int i = 0; i < table2.length; i++) {
            for (int j = 0; j < table2.length; j++) {
                if (table2[i][j] == -1) {
                    table[i][j] = new EmptyTile(i,j);
                } else if (table2[i][j] == 0) {
                    table[i][j] = new IndestructibleTile(i,j);
                } else {
                    table[i][j] = new NumberTile(table2[i][j],i,j);
                } 
            }
        }
        setTable(table);
    }       
}

/**
 * A class to represent a point on the game table.
 */
class Point{
    int x;
    int y;

    /**
     * Constructor of the class.
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}