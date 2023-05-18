package com.cyslide.Model;

import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Player {
    private String pseudo;
    private int levelResolved; //indicates the number of the last level accomplished by the player
    private TaquinGame taquinGame;

    public Player(String pseudo) throws PlayerPseudoException {
        this.pseudo = pseudo;

        if (this.pseudo.length() > 20) {
            throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
        }
    //this.listRecords = new HashSet<Record>();
    //this.listResolvedLevels = new HashSet<Level>();
    }
    public String getPseudo() {
        return pseudo;
    }

    public int recoverNbOfCompletedLvl(int levelResolved) throws PlayerPseudoException{
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;
            boolean isFirstLine = true; // To ignore the first line
    
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
    
                String[] rowValues = line.split(";");
                String pseudo = rowValues[0];
                levelResolved = Integer.parseInt(rowValues[1]);
                System.out.println("Level Completed for player " + pseudo + " : " + levelResolved);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return levelResolved;
    }
    




    class PlayerPseudoException extends Exception {
    public PlayerPseudoException(String message) {
        super(message);
    }
}}
