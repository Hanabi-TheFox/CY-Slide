package com.cyslide.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Player class represents a player in the CY Slide game.
 * It stores the player's pseudo and the number of levels resolved by the player.
 * The player's data is stored and retrieved from a CSV file.
 */
public class Player {
    private String pseudo;
    private int levelResolved; // Indicates the number of the last level accomplished by the player

    /**
     * Constructs a Player object with the specified pseudo.
     * 
     * @param pseudo The pseudo of the player.
     * @throws PlayerPseudoException If the length of the pseudo is less than 3 characters or more than 20 characters.
     */
    public Player(String pseudo) throws PlayerPseudoException {
        this.pseudo = pseudo;

        if (this.pseudo.length() > 20) {
            throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
        }
        if (this.pseudo.length() < 3) {
            throw new PlayerPseudoException("Pseudo cannot have less than 3 characters");
        }

        // Read the player's data from the CSV file
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
        String line = "";
        boolean playerFound = false;
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                if (rowValues[0].equals(pseudo)) {
                    this.levelResolved = Integer.parseInt(rowValues[1]);
                    playerFound = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
        }

        // If player data not found, create a new entry in the CSV file
        if (!playerFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true))) {
                // Write the new data at the end of the file
                writer.write(pseudo + ";1");
                writer.newLine();
                System.out.println("Success in writing file");
                this.setLevelResolved(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in writing the file or creating the player object");
            }
        }
    }

    /**
     * Returns the pseudo of the player.
     * 
     * @return The pseudo of the player.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Returns the number of levels resolved by the player.
     * 
     * @return The number of levels resolved.
     */
    public int getLevelResolved() {
        return levelResolved;
    }

    /**
     * Sets the number of levels resolved by the player.
     * 
     * @param levelResolved The number of levels resolved.
     */
    public void setLevelResolved(int levelResolved) {
        this.levelResolved = levelResolved;
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
        String tempFile = "CY_Slide/src/main/java/com/cyslide/Data/PlayerTemp.csv";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(";");
                if (rowValues[0].equals(this.pseudo)) {
                    rowValues[1] = Integer.toString(this.levelResolved);
                }
                bw.write(String.join(";", rowValues));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading or writing file.");
        }
        File oldFile = new File(pathFile);
        if (oldFile.delete()) {
            File newFile = new File(pathFile);
            if (newFile.exists()) {
                newFile.delete();
            }
            if (new File(tempFile).renameTo(newFile)) {
                System.out.println("File updated successfully.");
            } else {
                System.out.println("Error updating file.");
            }
        } else {
            System.out.println("Error deleting file.");
        }
    }

    /**
     * Custom exception class for handling invalid player pseudos.
     */
    public class PlayerPseudoException extends Exception {
        public PlayerPseudoException(String message) {
            super(message);
        }
    }
}