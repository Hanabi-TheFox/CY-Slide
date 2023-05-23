package com.cyslide.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author @RDNATOS @Hanabi-TheFox
 */
public class Player {
    private String pseudo;
    private int levelResolved; //indicates the number of the last level accomplished by the player

    public Player(String pseudo) throws PlayerPseudoException {
        this.pseudo = pseudo;

        if (this.pseudo.length() > 20) {
            throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
        }
        if (this.pseudo.length() < 3) {
            throw new PlayerPseudoException("Pseudo cannot have less than 3 characters");
        }
        // On ouvre le fichier CSV
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
        String line = "";
        boolean PlayerFound = false;
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            while ((line = br.readLine()) != null) {
                    String[] rowValues = line.split(";");
                    if (rowValues[0].equals(pseudo)) {
                        this.levelResolved = Integer.parseInt(rowValues[1]);
                        PlayerFound = true;
                        break;
                    }
                }
                System.out.println("File Found");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error reading file");
            }
        
        if (PlayerFound == false) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true))) {
                // Write the new data at the end of the file
                writer.write(pseudo + ";1");
                writer.newLine();
                System.out.println("Success in writing file");
                this.setLevelResolved(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in writing the file or/and creating player object");
            }
        }
    }
    public String getPseudo() {return pseudo;}
    public int getLevelResolved() {return levelResolved;}
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading or writing file.");
        }
    }

    public class PlayerPseudoException extends Exception {
        public PlayerPseudoException(String message) {
            super(message);
        }
    }
}
