package com.cyslide.Model;

import com.cyslide.CySlideController;
import javafx.beans.binding.NumberBinding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * The Level class represents a game level in CySlide.
 * It contains information about the level number, move counter, randomization status, completion status, record, and the game table.
 * The class provides methods for retrieving and modifying these properties, as well as recovering level data from files, moving tiles, and checking level completion.
 */
public class Level {
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
            System.out.println("File Found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
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
            System.out.println("Error reading file");
        }
        System.out.println("Level " + number);
        System.out.println("Tile Length: " + numRow);
        System.out.println("Tile Width: " + numCol);

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
     * Moves a tile in the specified direction on the game table.
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
        Level finalState = new Level(number);
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
     * Initializes the level by moving the tiles in a random order.
     */
    public void initLevelMove() {
        int seed;
        if (number == 3 || number == 2 || number == 6) {
            seed = 1245;
        } else seed = 142;

        Random random = new Random(seed);
        String[] choice = {"UP", "DOWN", "RIGHT", "LEFT"};
        List<String> randomMoves = new ArrayList<>();
        for (int k = 0; k < 100; k++) {
            int index = random.nextInt(4);
            randomMoves.add(choice[index]);
        }
        int size = table.length;
        for (int init = 0; init < 1000; init++) {
            for (String direction : randomMoves) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (table[i][j].getType() == -1) {
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
     * Converts the level object's tile matrix into an integer matrix.
     *
     * @param level The level object.
     * @return The converted integer matrix.
     */
    public int[][] LevelToIntMatrix(Level level) {
        Tile[][] tileMatrix = level.getTable();
        int[][] matrix = new int[tileMatrix.length][tileMatrix.length];

        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix.length; j++) {
                matrix[i][j] = tileMatrix[i][j].getType();
            }
        }

        return matrix;
    }
}
