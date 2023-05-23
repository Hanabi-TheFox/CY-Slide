/*package com.cyslide.Model;

import java.util.*;

class Node implements Comparable<Node> {
    int[][] state;
    int gScore;
    int hScore;
    Node parent;

    public Node(int[][] state, int gScore, int hScore, Node parent) {
        this.state = state;
        this.gScore = gScore;
        this.hScore = hScore;
        this.parent = parent;
    }

    public int getFScore() {
        return gScore + hScore;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFScore(), other.getFScore());
    }
}

public class AStarAlgo {
    private static final int[][] GOAL_STATE = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    private static final int[][] MOVES = {
            {0, 1},  // Move blank tile right
            {0, -1}, // Move blank tile left
            {1, 0},  // Move blank tile down
            {-1, 0}  // Move blank tile up
    };

    public static int calculateMisplacedTiles(int[][] state) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != GOAL_STATE[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static List<int[][]> getNeighbours(int[][] state) {
        List<int[][]> neighbours = new ArrayList<>();
        int[] blankPosition = findBlankPosition(state);
        int blankRow = blankPosition[0];
        int blankCol = blankPosition[1];

        for (int[] move : MOVES) {
            int newRow = blankRow + move[0];
            int newCol = blankCol + move[1];

            if (isValidPosition(newRow, newCol)) {
                int[][] newState = cloneState(state);
                newState[blankRow][blankCol] = state[newRow][newCol];
                newState[newRow][newCol] = 0;
                neighbours.add(newState);
            }
        }

        return neighbours;
    }

    public static int[] findBlankPosition(int[][] state) {
        int[] position = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }

    public static int[][] cloneState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, 3);
        }
        return newState;
    }

    public static List<int[][]> astar(int[][] initial) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        int[][] initialState = cloneState(initial);
        int initialHScore = calculateMisplacedTiles(initialState);
        Node initialNode = new Node(initialState, 0, initialHScore, null);
        openSet.add(initialNode);
    
        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            int[][] currentState = currentNode.state;
    
            if (Arrays.deepEquals(currentState, GOAL_STATE)) {
                // Solution found
                List<int[][]> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(0, currentNode.state);
                    currentNode = currentNode.parent;
                }
                return path;
            }
    
            visited.add(currentNode);
    
            List<int[][]> neighbours = getNeighbours(currentState);
            for (int[][] neighbour : neighbours) {
                int gScore = currentNode.gScore + 1;
                int hScore = calculateMisplacedTiles(neighbour);
                Node neighbourNode = new Node(neighbour, gScore, hScore, currentNode);
    
                if (visited.contains(neighbourNode)) {
                    continue; // Skip already visited nodes
                }
    
                if (!openSet.contains(neighbourNode) || gScore < neighbourNode.gScore) {
                    openSet.remove(neighbourNode);
                    openSet.add(neighbourNode);
                }
            }
        }
    
        return null; // No solution found
    }
    
    public static void printState(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        // int[][] puzzle = {
        //         {1, 2, 3},
        //         {4, 0, 6},
        //         {7, 5, 8}
        // };

        int[][] puzzle = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
    };
    
        List<int[][]> solution = astar(puzzle);
    
        if (solution != null) {
            for (int[][] state : solution) {
                printState(state);
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}    
*/