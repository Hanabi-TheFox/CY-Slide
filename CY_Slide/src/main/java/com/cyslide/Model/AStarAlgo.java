package com.cyslide.Model;

import java.util.*;

class Node implements Comparable<Node> {
    private Level state;
    private int gScore;
    private int hScore;
    private int fScore;
    private Node parent;

    public Node(Level state, int gScore, int hScore, Node parent) {
        this.state = state;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
        this.parent = parent;
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
        return compareTileArrays(state.getTable(), other.state.getTable());
    }
    
    private boolean compareTileArrays(Tile[][] array1, Tile[][] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1.length; j++){
                if (!array1[i][j].equals(array2[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
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
                if (currentTile[i][j] instanceof NumberTile && finalTile[i][j] instanceof EmptyTile){
                    count++;
                }
            }
        }
        return count+1; // for the empty tile in our currentTile
    }
    public static Tile[][] moveTile2(int x, int y, String direction, Tile[][] table) {
        if (!table[x][y].mouvementAvailable2(direction, table) || table[x][y].getType() == 0) {
            return table;
        } else {
            table[x][y].move2(direction, table);
            //System.out.println("Print table : ");
            //AStarAlgo.printState2(table);
            return table;
        }
    }
    // public static boolean sameTab(Tile[][] tab1, Tile[][] tab2) {
    //     if (tab1.length != tab2.length) {
    //         return false; // dimension different
    //     }
    
    //     for (int i = 0; i < tab1.length; i++) {
    //         for (int j = 0; j < tab1.length; j++) {
    //             if (tab1[i][j].getType() != tab2[i][j].getType()) {
    //                 return false; // type different so array different
    //             }else if(tab1[i][j].getType() == 1 && tab2[i][j].getType() == 1){
    //                 NumberTile nb1 = (NumberTile) tab1[i][j];
    //                 NumberTile nb2 = (NumberTile) tab2[i][j];
    //                 if(nb1.getNumber() != nb2.getNumber()){
    //                     return false; // number different so array different 
    //                 }
    //             }
    //         }
    //     }
    
    //     return true; // same array
    // }

    private static List<Level> generateNeighbors(Level currentLevel, Set<Node> closedList) {
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
        // Créer une copie du niveau courant pour chaque voisin
        for (String direction : directions) {
            Level lvlNeighbor = currentLevel.clone();
            if(lvlNeighbor.moveTile2(emptyRow, emptyCol, direction)){
                Node neighborNode = new Node(lvlNeighbor, 0, 0, null);
                if (!closedList.contains(neighborNode)) {
                    // Ajouter le voisin à la liste des voisins à explorer
                    neighbors.add(lvlNeighbor);
                }
            }
        }   
        // System.out.println("Affichage des voisins :");
        //         for (Level neighbor : neighbors) {
        //             printState(neighbor);
        //         }   
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
            Level currentState = currentNode.getState();
        
            // System.out.println("Pendant la boucle");
            // printState(currentState);
    
            if (currentState.isCompleted2(currentState.getNumber(), currentState.getTable())) {
                // Solution found
                List<Level> path = new ArrayList<>();
                int i = 0;
                while (currentNode != null) {
                    path.add(i, currentNode.getState());
                    i++;
                    currentNode = currentNode.getParent();
                }
                return path;
            }

            closedList.add(currentNode);
    
            List<Level> neighbors = generateNeighbors(currentState, closedList);
            for (Level neighbor : neighbors) {
                int gScore = currentNode.getGScore() + 1;
                int hScore = calculeMisplacedTileCount(neighbor);

                Node neighborNode = new Node(neighbor, gScore, hScore, currentNode);
                if (!closedList.contains(neighborNode)){
                    openList.add(neighborNode);
                    System.out.println("Neighbor added to open list:");
                    printState(neighborNode.getState());
                }
            }
        }
        return null; // No solution found
    }
    
    public static void printState(Level state) {
        System.out.println("--------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.getTable()[i][j] instanceof EmptyTile) {
                    System.out.print("0 ");
                } else if (state.getTable()[i][j] instanceof NumberTile) {
                    NumberTile numberTile = (NumberTile) state.getTable()[i][j];
                    System.out.print(numberTile.getNumber() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------");
    }

    public static void printState2(Tile[][] ste) {
        System.out.println("--------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ste[i][j] instanceof EmptyTile) {
                    System.out.print("0 ");
                } else if (ste[i][j] instanceof NumberTile) {
                    NumberTile numberTile = (NumberTile) ste[i][j];
                    System.out.print(numberTile.getNumber() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("--------------");
    }
    

    public static void main(String[] args) {
        Level level = new Level(3);
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
