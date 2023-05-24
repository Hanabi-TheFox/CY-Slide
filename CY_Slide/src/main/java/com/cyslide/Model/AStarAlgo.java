package com.cyslide.Model;

import java.util.*;

class Node implements Comparable<Node> {
    Level state;
    int gScore;
    int hScore;
    Node parent;

    public Node(Level state, int gScore, int hScore, Node parent) {
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
    public static int calculeMisplacedTileCount(Level level) {
        Level finalLevel = new Level(level.getNumber());
        Tile[][] finalTile = finalLevel.getTable();
        Tile[][] currentTile = level.getTable();
        int count = 0;
        for (int i = 0; i < currentTile.length; i++) {
            for (int j = 0; j < currentTile[i].length; j++) {
                if (currentTile[i][j] instanceof NumberTile && finalTile[i][j] instanceof NumberTile) {
                    NumberTile currentNumberTile = (NumberTile) currentTile[i][j];
                    NumberTile finalNumberTile = (NumberTile) finalTile[i][j];
                    if (currentNumberTile.getNumber() != finalNumberTile.getNumber()) {
                        count++;
                    }
                }
            }
        }
        return count + 1; // Add 1 for empty tile  
    }
    
    private static List<Level> generateNeighbors(Level currentLevel) {
        // Trouver les coordonnées de la case vide
        int emptyRow = -1;
        int emptyCol = -1;
        for (int i = 0; i < currentLevel.getTable().length; i++) {
            for (int j = 0; j < currentLevel.getTable()[i].length; j++) {
                if (currentLevel.getTable()[i][j].getType() == -1) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }
    
        // Générer les voisins en permutant les cases adjacentes avec la case vide
        List<Level> neighbors = new ArrayList<>();
        String[] directions = {"UP", "DOWN", "RIGHT", "LEFT"};
        for (String direction : directions) {
            int newRow = emptyRow;
            int newCol = emptyCol;
            System.out.println("Direction : " + direction);
            // Créer une copie du niveau courant pour chaque voisin
            Level lvlNeighbor = currentLevel;
            System.out.println("PremierTest");
            printState(lvlNeighbor);
            System.out.println("newRow : " + lvlNeighbor.moveTile2(newRow, newCol, direction));
            if (lvlNeighbor.moveTile2(newRow, newCol, direction)) {
                lvlNeighbor.getTable()[newRow][newCol].move2(direction,lvlNeighbor.getTable());
                printState(lvlNeighbor);

                // Vérifier si le voisin est déjà dans un état final
                System.out.println("newRow : " + lvlNeighbor.isCompleted(lvlNeighbor.getNumber(), lvlNeighbor.getTable()));

                if (!lvlNeighbor.isCompleted(lvlNeighbor.getNumber(), lvlNeighbor.getTable())) {
                    // Mouvement effectué, ajouter le voisin à la liste
                    neighbors.add(lvlNeighbor);

                }
                
            }
        }   
        System.out.println("Affichage des voisins :");
                for (Level neighbor : neighbors) {
                    printState(neighbor);
                }   
        return neighbors;
    }

    public static List<Level> astar(Level level) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();
    
        Level initialState = level;
        int initialHScore = calculeMisplacedTileCount(initialState);
        //System.out.println(initialHScore);
        Node initialNode = new Node(initialState, 0, initialHScore, null);
        openList.add(initialNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            Level currentState = currentNode.state;
        
            // System.out.println("Pendant la boucle");
            // printState(currentState);
    
            if (currentState.isCompleted(currentState.getNumber(), currentState.getTable())) {
                // Solution found
                List<Level> path = new ArrayList<>();
                int i = 0;
                while (currentNode != null) {
                    path.add(i, currentNode.state);
                    i++;
                    currentNode = currentNode.parent;
                }
                return path;
            }

            closedList.add(currentNode);
    
            List<Level> neighbours = generateNeighbors(currentState);
            for (Level neighbour : neighbours) {
                int gScore = currentNode.gScore + 1;
                int hScore = calculeMisplacedTileCount(neighbour);
                System.out.println("hScore: " + hScore);
                Node neighbourNode = new Node(neighbour, gScore, hScore, currentNode);
    
                if (closedList.contains(neighbourNode)) { //   A REVOIR 
                    continue; // Skip already closedList nodes
                } else {
                    openList.remove(neighbourNode);
                    closedList.add(neighbourNode);
                }
            }
        }
        return null; // No solution found
    }
    
    public static void printState(Level ste) {
        System.out.println("--------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ste.getTable()[i][j] instanceof EmptyTile) {
                    System.out.print("0 ");
                } else if (ste.getTable()[i][j] instanceof NumberTile) {
                    NumberTile numberTile = (NumberTile) ste.getTable()[i][j];
                    System.out.print(numberTile.getNumber() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------");
    }
    

    public static void main(String[] args) {
        Level level = new Level(2);
        level.initLevelMove();

        System.out.println("Initial State :");
        printState(level);

        List<Level> solution = astar(level);
        
        if (solution != null) {
            for (Level state : solution) {
                printState(level);
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}    
