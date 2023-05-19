package com.cyslide.Model;

import com.cyslide.Model.Player.PlayerPseudoException;

// ce main permet de tester nos fonction java
public class Main{
    public static void main(String[] args) throws PlayerPseudoException{
        /*Level level = new Level(1);
        Tile[][] table = level.getTable();
        System.out.println("taille tab" + table.length);
        for (int i=0; i < table.length; i++){
            for (int j=0; j < table.length; j++){
                System.out.println("case [" + i + "][" + j +"]");
                table[i][j].showTile();
            }
        }

        System.out.println("record du niveau " + level.getNumber() + " est : " + level.getRecord());
        */
        /*
        try {

        System.out.println("!! test move !!");
        // pour changer la place d'une tuile, on appelle la fonction level.moveTile(posX, posY, direction) avec posX, posY les coordonnées et direction le sens ("UP, DOWN, RIGHT, LEFT")
        level.moveTile(3, 4, "DOWN");
        table[3][4].showTile();
        System.out.println("X : " + table[3][4].getPosX());
        System.out.println("X : " + table[4][4].getPosX());
        table[4][4].showTile();
        level.moveTile(3, 4, "UP");
        table[2][4].showTile();
        table[3][4].showTile();
        table[1][1].showTile();
        level.moveTile(1, 1, "UP");
        table[1][1].showTile();*/


        
        

        /*try {

            Player player = new Player("Admin");    
            player.recoverNbOfCompletedLvl();
        
        } catch (PlayerPseudoException e) {
            System.err.println("Erreur lors de la création du joueur : " + e.getMessage());

        }
        */
        int rows = 3;
        int cols = 3;
        //Level level = new Level(2);
        /*
        Tile[][] finalState = level.getTable();
        System.out.println("Final state :");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (finalState[i][j].getType() == 1) {
                    NumberTile nb = (NumberTile) finalState[i][j];
                    System.out.print(nb.getNumber() + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(); // Ajout d'un saut de ligne après chaque ligne de tuiles
        }
        */

        Tile[][] finalState = new Tile[rows][cols];
        finalState[0][0] = new NumberTile(1,0,0);
        finalState[0][1] = new NumberTile(2,0,1);
        finalState[0][2] = new NumberTile(3,0,2);
        finalState[1][0] = new NumberTile(4,1,0);
        finalState[1][1] = new NumberTile(5,1,1);
        finalState[1][2] = new NumberTile(6,1,2);
        finalState[2][0] = new NumberTile(7,2,0);
        finalState[2][1] = new NumberTile(8,2,1);
        finalState[2][2] = new EmptyTile(2,2);

        System.out.println("Final state :");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (finalState[i][j].getType() == 1) {
                    NumberTile nb = (NumberTile) finalState[i][j];
                    System.out.print(nb.getNumber() + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }

        

        Tile[][] initialState = new Tile[rows][cols];
        initialState[0][0] = new NumberTile(8,0,0);
        initialState[0][1] = new NumberTile(1,0,1);
        initialState[0][2] = new NumberTile(3,0,2);
        initialState[1][0] = new NumberTile(4,1,0);
        initialState[1][1] = new EmptyTile(2,2);
        initialState[1][2] = new NumberTile(2,1,2);
        initialState[2][0] = new NumberTile(7,2,0);
        initialState[2][1] = new NumberTile(6,2,1);
        initialState[2][2] = new NumberTile(5,2,2);

        System.out.println("Initial state :");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (initialState[i][j].getType() == 1) {
                    NumberTile nb = (NumberTile) initialState[i][j];
                    System.out.print(nb.getNumber() + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        AStarAlgo aStarAlgo = new AStarAlgo();
        aStarAlgo.aStar(initialState, finalState);
         
    }
}