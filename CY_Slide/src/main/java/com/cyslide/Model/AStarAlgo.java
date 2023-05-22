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
        
        

        while (!openList.isEmpty()) {
            // Get the current state from the openList queue
            State current = openList.poll();
            currentTile = current.tiles;
        
            // Check if the current state is the final state
            if (isFinalState(currentTile, finalTile)) {
                System.out.println("Final state reached!");
                break;
            }
    
            // Add the current state to the closed set
            closedList.add(current.tiles);
    
            // Generate the neighbors of the current state
            List<Tile[][]> neighbors = generateNeighbors(currentTile, finalTile);
    
            if (neighbors.isEmpty()) {
                System.out.println("Error fuck");
            } else {
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
    }
    
          
        public void printState(Tile[][] state) {
        if (!displayedStates.contains(state)) {
            // Display the state tiles
            //System.out.println("Current:");
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
    
    
    private List<Tile[][]> generateNeighbors(Tile[][] currentTile, Tile[][] finalTile) {
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
            Tile[][] upState = copyState(currentTile);
            upState[emptyPosX][emptyPosY] = upState[emptyPosX - 1][emptyPosY];
            upState[emptyPosX - 1][emptyPosY] = new EmptyTile(emptyPosX - 1, emptyPosY);
            neighbors.add(upState);
        }
        
        if (emptyPosX < currentTile.length - 1) {
            Tile[][] downState = copyState(currentTile);
            downState[emptyPosX][emptyPosY] = downState[emptyPosX + 1][emptyPosY];
            downState[emptyPosX + 1][emptyPosY] = new EmptyTile(emptyPosX + 1, emptyPosY);
            neighbors.add(downState);
        }
        
        if (emptyPosY > 0) {
            Tile[][] leftState = copyState(currentTile);
            leftState[emptyPosX][emptyPosY] = leftState[emptyPosX][emptyPosY - 1];
            leftState[emptyPosX][emptyPosY - 1] = new EmptyTile(emptyPosX, emptyPosY - 1);
            neighbors.add(leftState);
        }
        
        if (emptyPosY < currentTile[0].length - 1) {
            Tile[][] rightState = copyState(currentTile);
            rightState[emptyPosX][emptyPosY] = rightState[emptyPosX][emptyPosY + 1];
            rightState[emptyPosX][emptyPosY + 1] = new EmptyTile(emptyPosX, emptyPosY + 1);
            neighbors.add(rightState);
        }
    
        // Calculate the Manhattan distance for each neighbor and store it in a list
        List<Integer> manhattanDistances = new ArrayList<>();
        for (Tile[][] neighbor : neighbors) {
            int manhattanDistance = calculeManhattanDistance(neighbor, finalTile);
            manhattanDistances.add(manhattanDistance);
        }
    
        // Find the index of the neighbor with the minimum Manhattan distance
        int minDistanceIndex = -1;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < manhattanDistances.size(); i++) {
            int distance = manhattanDistances.get(i);
            if (distance < minDistance && !isSameState(currentTile, neighbors.get(i))) {
                minDistance = distance;
                minDistanceIndex = i;
            }
        }
    
        // Choose the neighbor with the minimum Manhattan distance as the next state
        List<Tile[][]> nextStates = new ArrayList<>();
        if (minDistanceIndex != -1) {
            Tile[][] nextState = neighbors.get(minDistanceIndex);
            nextStates.add(nextState);
        }
    
        if (nextStates.isEmpty()) {
            System.out.println("Aucun voisin trouvé");
        }

        return nextStates;
    }
    
    private Tile[][] copyState(Tile[][] state) {
        Tile[][] copy = new Tile[state.length][];
        for (int i = 0; i < state.length; i++) {
            copy[i] = state[i].clone();
        }
        return copy;
    }
    /*private Tile[][] copyState(Tile[][] state) {
        Tile[][] copy = new Tile[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                Tile tile = state[i][j];
                if (tile instanceof NumberTile) {
                    NumberTile numberTile = (NumberTile) tile;
                    copy[i][j] = new NumberTile(numberTile.getNumber(), i, j);
                } else {
                    copy[i][j] = new EmptyTile(i, j);
                }
            }
        }
        return copy;
    }
    */
    
    public boolean isSameState(Tile[][] state1, Tile[][] state2) {
        if (state1.length != state2.length || state1[0].length != state2[0].length) {
            return false;
        }
        
        for (int i = 0; i < state1.length; i++) {
            for (int j = 0; j < state1[i].length; j++) {
                Tile tile1 = state1[i][j];
                Tile tile2 = state2[i][j];
    
                if (tile1 == null && tile2 == null) {
                    continue;
                }
                
                if (tile1 == null || tile2 == null) {
                    return false;
                }
    
                if (tile1.getType() != tile2.getType()) {
                    return false;
                }
                
                if (tile1.getType() == 1) {
                    NumberTile numberTile1 = (NumberTile) tile1;
                    NumberTile numberTile2 = (NumberTile) tile2;
                    if (numberTile1.getNumber() != numberTile2.getNumber()) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    
    
    public boolean isFinalState(Tile[][] currentTile, Tile[][] finalTile) {
        // Check if the tiles match the final state
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                if (currentTile[i][j].getType() != finalTile[i][j].getType()) {
                    return false;
                }
            }
        }
    
        // Check if the Manhattan distance is zero
        return calculeManhattanDistance(currentTile, finalTile) == 0;
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