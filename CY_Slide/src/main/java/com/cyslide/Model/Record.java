package com.cyslide.Model;

//Java Class for saving all the player records. This file has the record of
//all the players that played the game

public class Record{
    private Player player;
    private int level;
    private int moves;

    Record(Player player, int level, int moves){
        this.player = player;
        this.level = level;
        this.moves = moves;
    }
}