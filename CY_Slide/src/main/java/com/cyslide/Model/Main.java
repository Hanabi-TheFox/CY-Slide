package com.cyslide.Model;

import com.cyslide.Model.Player.PlayerPseudoException;

// ce main permet de tester nos fonction java
public class Main{
    public static void main(String[] args) throws PlayerPseudoException{
        Level level = new Level(1);
        Tile[][] table = level.getTable();
        System.out.println("taille tab" + table.length);
        for (int i=0; i < table.length; i++){
            for (int j=0; j < table.length; j++){
                System.out.println("case [" + i + "][" + j +"]");
                table[i][j].showTile();
            }
        }
        /* System.out.println("!! test move !!");
        pour changer la place d'une tuile, on appelle la fonction level.moveTile(posX, posY, direction) avec posX, posY les coordonnées et direction le sens ("UP, DOWN, RIGHT, LEFT")
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
        }*/

        
        
    }
}