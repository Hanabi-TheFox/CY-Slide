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
    public static boolean sameTab(Tile[][] tab1, Tile[][] tab2) {
        if (tab1.length != tab2.length) {
            return false; // dimension different
        }
    
        for (int i = 0; i < tab1.length; i++) {
            for (int j = 0; j < tab1.length; j++) {
                if (tab1[i][j].getType() != tab2[i][j].getType()) {
                    return false; // type different so array different
                }else if(tab1[i][j].getType() == 1 && tab2[i][j].getType() == 1){
                    NumberTile nb1 = (NumberTile) tab1[i][j];
                    NumberTile nb2 = (NumberTile) tab2[i][j];
                    if(nb1.getNumber() != nb2.getNumber()){
                        return false; // number different so array different 
                    }
                }
            }
        }
    
        return true; // same array
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
        // Créer une copie du niveau courant pour chaque voisin
        for (String direction : directions) {
            Level lvlNeighbor = currentLevel.clone();
            int newRow = emptyRow;
            int newCol = emptyCol;
            System.out.println("Direction : " + direction);
            System.out.println("PremierTest");
            printState(lvlNeighbor);
            boolean leveltest = lvlNeighbor.moveTile2(newRow, newCol, direction);
            System.out.println("newRow : " + leveltest);
            if(leveltest){

                // if our neighbor is not the solution
                if (!lvlNeighbor.isCompleted2(lvlNeighbor.getNumber(), lvlNeighbor.getTable())) {
                    // We add our neighbor to the list
                    neighbors.add(lvlNeighbor);
                }else{
                    // We found our solution, so we add the new node to our closed list
                    // Ajouter à la list fermé ??
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
    
            List<Level> neighbours = generateNeighbors(currentState);
            Node minF = new Node(null, 0, 150, null);
            for (Level neighbour : neighbours) {
                int gScore = currentNode.getGScore() + 1;
                int hScore = calculeMisplacedTileCount(neighbour);

                Node neighbourNode = new Node(neighbour, gScore, hScore, currentNode);
                System.out.println("HScore: " + hScore);
                System.out.println("FScore: " + neighbourNode.getFScore());
                if (neighbourNode.getFScore() < minF.getFScore()){ // we find our node with a minimum F
                    minF = neighbourNode;
                }
            }
            openList.add(minF);
            // we add the parent of our minimum to our closed list
            closedList.add(minF.getParent());
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
