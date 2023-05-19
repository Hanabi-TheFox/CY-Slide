package com.cyslide.Model;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class AStarAlgo {

    public class State{
        Tile[][] tiles;
        int g; // represents the cumulative cost of the path from the initial state to the current state. This is the actual distance traveled to the current state.
        int f; // represents an estimate of the total cost of the path from the initial state through the current state to the final state.

        public State(Tile[][] tiles, int g, int f){
            this.tiles = tiles;
            this.g = g;
            this.f = f;
        }
    }

    HashSet<Tile[][]> displayedStates = new HashSet<>();

    public void aStar(Tile[][] currentTile, Tile[][] finalTile){
        // list of states to be explored. It contains the states that still need to be evaluated.
        PriorityQueue<State> openList = new PriorityQueue<>(Comparator.comparingInt(s -> s.f));
        
        // list of states that have already been explored. It contains the states that have already been evaluated.
        HashSet<Tile[][]> closedList = new HashSet<>();

        // Add the initial state to the open set with g = 0 and f = heuristic
        int initialF = calculeManhattanDistance(currentTile, finalTile);
        System.out.println("Dist Manhattan: " + initialF);
        State initialState = new State(currentTile, 0, initialF);
        openList.add(initialState);

        // Convert the openList to a temporary list for displaying its contents
        List<State> tempList = new ArrayList<>(openList);

        // Display the content of the openList
        System.out.println("Contenu de l'openList:");
        for (State state : tempList) {
            System.out.println("Tiles:");
            printState(state.tiles);
            System.out.println("g: " + state.g);
            System.out.println("f: " + state.f);
            System.out.println("----------------------");
        }

        while (!openList.isEmpty() && !isFinalState(currentTile, finalTile)) {

            // Récupérer l'état actuel de la file d'attente openList
            State current = openList.poll();
            currentTile = current.tiles;

            //printState(current.tiles);

            // Check if the actual state is the final state
            if (isFinalState(currentTile, finalTile)) {
                System.out.println("Final state reached!");
                break;
            }

            // Adjustement of the actual state into the closed state
            closedList.add(current.tiles);
            
            // Generate state neighbors
            List<Tile[][]> neighbors = generateNeighbors(current.tiles);

            for (Tile[][] neighbor : neighbors) {
                // Calculate costs for the neighbor state
                int neighborG = current.g + 1;
                int neighborF = neighborG + calculeManhattanDistance(neighbor, finalTile);

                // Check if the neighbor state is already in the closed state
                if (closedList.contains(neighbor)) {
                    continue;
                }

                // Check if the neighbor state is already in the open state with a better cost
                boolean isOpenBetter = false;
                for (State openState : openList) {
                    if (isSameState(openState.tiles, neighbor) && neighborG < openState.g) {
                        isOpenBetter = true;
                        break;
                    }
                }

                if (isOpenBetter) {
                    continue;
                }

                // Add the neighbor to the open state
                State neighborState = new State(neighbor, neighborG, neighborF);
                openList.add(neighborState);

                printState(neighbor);
            }
        
        }
    }

    public void printState(Tile[][] state) {
        if (!displayedStates.contains(state)) {
            // Display the state tiles
            System.out.println("Current:");
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state[i].length; j++) {
                    if (state[i][j].getType() == 1) {
                        NumberTile numberTile = (NumberTile) state[i][j];
                        System.out.print(numberTile.getNumber() + " ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            System.out.println();
            displayedStates.add(state); // Add the state to the set of displayed states
        }
    }
    
    
    public List<Tile[][]> generateNeighbors(Tile[][] currentTile) {
        List<Tile[][]> neighbors = new ArrayList<>();
    
        // Find the empty tile
        int emptyRow = -1;
        int emptyCol = -1;
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                if (currentTile[i][j] instanceof EmptyTile) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
            if (emptyRow != -1 && emptyCol != -1) {
                break;
            }
        }
    
        // Generate neighboring states by moving adjacent tiles to the empty tile
        // Example: Move upwards
        if (emptyRow > 0) {
            Tile[][] neighbor = cloneTileArray(currentTile);
            neighbor[emptyRow][emptyCol] = currentTile[emptyRow - 1][emptyCol];
            neighbor[emptyRow - 1][emptyCol] = new EmptyTile(emptyRow - 1, emptyCol); // Create a new EmptyTile at the moved position
            neighbors.add(neighbor);
        }
    
        // Generate other neighboring states (move downwards, left, right, etc.)
    
        return neighbors;
    }
    
    
    // Utility method to clone a tile array
    private Tile[][] cloneTileArray(Tile[][] tiles) {
        Tile[][] clone = new Tile[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            clone[i] = tiles[i].clone();
        }
        return clone;
    }
    

    public boolean isSameState(Tile[][] state1, Tile[][] state2) {
        if (state1.length != state2.length || state1[0].length != state2[0].length) {
            return false;
        }
        for (int i = 0; i < state1.length; i++) {
            for (int j = 0; j < state1[i].length; j++) {
                Tile tile1 = state1[i][j];
                Tile tile2 = state2[i][j];

                if (!tile1.equals(tile2)) {
                    return false;
                }
            }
        }
        return true;
    }
    

    public boolean isFinalState(Tile[][] currentState, Tile[][] finalState) {
        // Check if the two states have the same length
        if (currentState.length != finalState.length || currentState[0].length != finalState[0].length) {
            return false;
        }

        // Compare the tiles of the two states to check their equality
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                Tile currentTile = currentState[i][j];
                Tile finalTile = finalState[i][j];
    
                // Check if the tiles are different
                if (!currentTile.equals(finalTile)) {
                    return false;
                }
            }
        }    
        return true;
    }
    
    

    public int calculeManhattanDistance(Tile[][] currentTile, Tile[][] finalTile) {
        int distance = 0;
    
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                // Recover the current tile at the position i, j
                Tile currentTileValue = currentTile[i][j];
                if (currentTileValue != null && currentTileValue instanceof NumberTile) { // Ignore empty tiles and other types of tiles
                    int targetRow = -1;
                    int targetCol = -1;
    
                    // Find the final position of the tile in finalTile
                    for (int k = 0; k < finalTile.length; k++) {
                        for (int l = 0; l < finalTile[k].length; l++) {
                            // If the corresponding tile is found and is an instance of NumberTile, save the coordinates
                            if (finalTile[k][l] != null && finalTile[k][l] instanceof NumberTile && ((NumberTile) finalTile[k][l]).getNumber() == ((NumberTile) currentTileValue).getNumber()) {
                                targetRow = k;
                                targetCol = l;
                                break;
                            }
                        }
                        // Optimize the search
                        if (targetRow != -1 && targetCol != -1) {
                            break;
                        }
                    }
    
                    // Calculate the Manhattan distance
                    if (targetRow != -1 && targetCol != -1) {
                        distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                    }
                }
            }
        }
    
        return distance;
    }
    
    
    
}