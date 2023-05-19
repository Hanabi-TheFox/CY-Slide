package com.cyslide.Model;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class AStarAlgo {

    public class State{
        Tile[][] tiles;
        int g; // represents the cumulative cost of the path from the initial state to the current state. It is the actual distance traveled to reach the current state.
        int f; // represents an estimate of the total cost of the path from the initial state, through the current state, to the final state.

        public State(Tile[][] tiles, int g, int f){
            this.tiles = tiles;
            this.g = g;
            this.f = f;
        }
    }

    HashSet<Tile[][]> displayedStates = new HashSet<>();

    public void aStar(Tile[][] currentTile, Tile[][] finalTile) {
        // List of states to explore. It contains the states that still need to be evaluated.
        PriorityQueue<State> openList = new PriorityQueue<>(Comparator.comparingInt(s -> s.f));
    
        // List of states already explored. It contains the states that have already been evaluated.
        HashSet<Tile[][]> closedList = new HashSet<>();
    
        // Add the initial state to the open set with g = 0 and f = heuristic
        int initialF = calculeManhattanDistance(currentTile, finalTile);
        System.out.println("Manhattan Distance: " + initialF);
        State initialState = new State(currentTile, 0, initialF);
        openList.add(initialState);
    
        // Convert openList to a temporary list to display its content
        List<State> tempList = new ArrayList<>(openList);
    
        // Display the content of openList
        System.out.println("Content of openList:");
        for (State state : tempList) {
            System.out.println("Tiles:");
            printState(state.tiles);
            System.out.println("g: " + state.g);
            System.out.println("f: " + state.f);
            System.out.println("----------------------");
        }
    
        while (!openList.isEmpty() && !isFinalState(currentTile, finalTile)) {
    
            // Get the current state from the openList queue
            State current = openList.poll();
            currentTile = current.tiles;
    
            //printState(current.tiles);
    
            // Check if the current state is the final state
            if (isFinalState(currentTile, finalTile)) {
                System.out.println("Final state reached!");
                break;
            }
    
            // Add the current state to the closed set
            closedList.add(current.tiles);
    
            // Generate the neighbors of the current state
            List<Tile[][]> neighbors = generateNeighbors(current.tiles);
    
            if (neighbors.isEmpty()) {
                System.out.println("Error fuck");
            }
    
            for (Tile[][] neighbor : neighbors) {
                // Calculate the costs for the neighbor state
                int neighborG = current.g + 1;
                int neighborH = calculeManhattanDistance(neighbor, finalTile);
                int neighborF = neighborG + neighborH;
    
                // Check if the neighbor state is already in the closed set
                if (closedList.contains(neighbor)) {
                    continue;
                }
    
                // Check if the neighbor state is already in the open set with a better cost
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
    
                // Add the neighbor to the open set
                State neighborState = new State(neighbor, neighborG, neighborF);
                openList.add(neighborState);
    
                printState(neighborState.tiles);
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
        int emptyPosX = -1;
        int emptyPosY = -1;
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                if (currentTile[i][j].getType() == -1) {
                    emptyPosX = i;
                    emptyPosY = j;
                    break;
                }
            }
            if (emptyPosX != -1 && emptyPosY != -1) {
                break;
            }
        }
        System.out.println("Empty Tile Position: (" + emptyPosX + ", " + emptyPosY + ")");
    
        // Generate neighbors by moving the tiles adjacent to the empty tile
        if (emptyPosX > 0) {
            NumberTile upNeighbor = (NumberTile) currentTile[emptyPosX - 1][emptyPosY];
            if (upNeighbor.mouvementAvailable("DOWN", currentTile)) {
                Tile[][] upState = copyState(currentTile);
                upNeighbor.move("DOWN", upState);
                neighbors.add(upState);
            }
        }
    
        if (emptyPosX < currentTile.length - 1) {
            NumberTile downNeighbor = (NumberTile) currentTile[emptyPosX + 1][emptyPosY];
            if (downNeighbor.mouvementAvailable("UP", currentTile)) {
                Tile[][] downState = copyState(currentTile);
                downNeighbor.move("UP", downState);
                neighbors.add(downState);
            }
        }
    
        if (emptyPosY > 0) {
            NumberTile leftNeighbor = (NumberTile) currentTile[emptyPosX][emptyPosY - 1];
            if (leftNeighbor.mouvementAvailable("RIGHT", currentTile)) {
                Tile[][] leftState = copyState(currentTile);
                leftNeighbor.move("RIGHT", leftState);
                neighbors.add(leftState);
            }
        }
    
        if (emptyPosY < currentTile[emptyPosX].length - 1) {
            NumberTile rightNeighbor = (NumberTile) currentTile[emptyPosX][emptyPosY + 1];
            if (rightNeighbor.mouvementAvailable("LEFT", currentTile)) {
                Tile[][] rightState = copyState(currentTile);
                rightNeighbor.move("LEFT", rightState);
                neighbors.add(rightState);
            }
        }
        if(neighbors.isEmpty()){
            System.out.println("Aucun voisin trouvé");
        }
        return neighbors;
    }
    
    private Tile[][] copyState(Tile[][] state) {
        Tile[][] copy = new Tile[state.length][];
        for (int i = 0; i < state.length; i++) {
            copy[i] = state[i].clone();
        }
        return copy;
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