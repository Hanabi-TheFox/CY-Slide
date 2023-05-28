package com.cyslide.Model;

import java.util.*;

class Node implements Comparable<Node> {
    private Level state;
    private int gScore;
    private int hScore;
    private int fScore;
    private Node parent;

    public Node(Level state, int gScore, int hScore) {
        this.state = state;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
        this.parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        // we want to compare tile arrays in state attributes
        return compareTileArrays(state.getTable(), other.state.getTable());
    }
    
    private boolean compareTileArrays(Tile[][] array1, Tile[][] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        return Arrays.deepEquals(array1, array2);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state.getTable());
    }

    
    public Level getState() {
        return state;
    }

    public int getGScore() {
        return gScore;
    }

    public int getHScore() {
        return hScore;
    }

    public int getFScore() {
        return fScore;
    }


    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFScore(), other.getFScore());
    }
}



/**
 * Algorithm Class alowing to solve automatically 
 * every level on the app
 * @author @ClemZi02
 * @author @Ymasuu
 */
public class AStarAlgo {  
    public static int calculeMisplacedTileCount(Level level) {
        Level finalLevel = new Level(level.getNumber());
        Tile[][] finalTile = finalLevel.getTable();
        Tile[][] currentTile = level.getTable();
        int count = 0;
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                // we want to count the number of tiles that are misplaced in the table 
                if (currentTile[i][j] instanceof NumberTile && finalTile[i][j] instanceof NumberTile) {
                    NumberTile currentNumberTile = (NumberTile) currentTile[i][j];
                    NumberTile finalNumberTile = (NumberTile) finalTile[i][j];
                    if (currentNumberTile.getNumber() != finalNumberTile.getNumber()) {
                        count++;
                    }
                }
                if (currentTile[i][j] instanceof NumberTile && finalTile[i][j] instanceof EmptyTile){
                    count++;
                }
            }
        }
        return count+1; // for the empty tile in our currentTile
    }

    /**
     * Generate neighbors by swapping adjacent cells with empty tiles
     * @param currentLevel the currentLevel we are on
     * @param closedList the list of closed nodes
     * @return a list of Level
     */
    private static List<Level> generateNeighbors(Level currentLevel, Set<Node> closedList) {
        // Find coordinates for empty tiles
        int[][] emptyPositions = new int[2][2]; // Table to store the positions of the empty tiles
        int emptyCount = 0; // Empty tiles counter
        for (int i = 0; i < currentLevel.getTable().length; i++) {
            for (int j = 0; j < currentLevel.getTable()[i].length; j++) {
                if (currentLevel.getTable()[i][j].getType() == -1) {
                    emptyPositions[emptyCount][0] = i; // coordinates x
                    emptyPositions[emptyCount][1] = j; // coordinates y
                    emptyCount++;
                    if (emptyCount >= 2) {
                        break; // If we find 2 empty tiles, we stop the loop
                    }
                }
            }
            if (emptyCount >= 2) {
                break; // If we find 2 empty tiles, we stop the loop
            }
        }
    
        // Generate neighbors by swapping adjacent cells with empty tiles
        List<Level> neighbors = new ArrayList<>();
        String[] directions = {"UP", "DOWN", "RIGHT", "LEFT"};
        for (int i = 0; i < emptyCount; i++) {
            int emptyRow = emptyPositions[i][0];
            int emptyCol = emptyPositions[i][1];
    
            for (String direction : directions) {
                Level lvlNeighbor = currentLevel.clone();
                if (lvlNeighbor.moveTile2(emptyRow, emptyCol, direction)) {
                    Node neighborNode = new Node(lvlNeighbor, 0, 0);
                    if (!closedList.contains(neighborNode)) {
                        neighbors.add(lvlNeighbor);
                    }
                }
            }
        }
    
        return neighbors;
    }
    
    /**
     * A* algorithm
     * @param level the level we want to solve
     * @return the list of iterations to solve the level (each Level is a step)
     */
    public static List<Level> astar(Level level) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
    
        Level initialState = level;
        int initialHScore = calculeMisplacedTileCount(initialState);
        Node initialNode = new Node(initialState, 0, initialHScore);
        openList.add(initialNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            Level currentState = currentNode.getState();
        
            if (currentState.isCompleted2(currentState.getNumber(), currentState.getTable())) {
                // Solution found
                System.out.println("Solution found !");
                List<Level> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(currentNode.getState());
                    currentNode = currentNode.getParent();
                }
                return path;
            }

            closedList.add(currentNode);

            List<Level> neighbors = generateNeighbors(currentState, closedList);
            for (Level neighbor : neighbors) {
                int gScore = currentNode.getGScore() + 1;
                int hScore = calculeMisplacedTileCount(neighbor);

                Node neighborNode = new Node(neighbor, gScore, hScore);
                if (!closedList.contains(neighborNode)){
                    neighborNode.setParent(currentNode);
                    openList.add(neighborNode);
                }
            }
        }
        return null; // No solution found
    }
    
    /**
     * Print the state of the level
     * @param state the level we want to print
     */
    public static void printState(Level state) {
        System.out.println("--------------");
        for (int i = 0; i < state.getTable().length; i++){
            for (int j = 0; j < state.getTable().length; j++){
                if (state.getTable()[i][j] instanceof EmptyTile){
                    System.out.print("0 ");
                } else if (state.getTable()[i][j] instanceof NumberTile){
                    NumberTile numberTile = (NumberTile) state.getTable()[i][j];
                    System.out.print(numberTile.getNumber() + " ");
                }else System.out.print("X ");
            }
            System.out.println();
        }
        System.out.println("--------------");
    }
}    
