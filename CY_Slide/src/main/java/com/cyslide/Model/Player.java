package com.cyslide.Model;

import java.util.HashSet;
public class Player {
    private String pseudo;
    private int levelResolved; //indicates the number of the last level accomplished by the player
    private TaquinGame taquinGame;

    public Player(String pseudo) throws PlayerPseudoException{
    if (this.pseudo.length() > 20) {
        throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
    }

    this.pseudo = pseudo;
    //this.listRecords = new HashSet<Record>();
    //this.listResolvedLevels = new HashSet<Level>();
    }




    class PlayerPseudoException extends Exception {
    public PlayerPseudoException(String message) {
        super(message);
    }
}}
