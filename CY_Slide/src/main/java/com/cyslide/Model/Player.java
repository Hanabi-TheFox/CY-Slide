package com.cyslide.Model;

import java.util.HashSet;
public class Player {
    private String pseudo;
    private HashSet<Record> listRecords;
    private HashSet<Level> listResolvedLevels;
    private TaquinGame taquinGame;

    Player(String pseudo) throws PlayerPseudoException{
    if (this.verifyPseudo(pseudo) == false) {
        throw new PlayerPseudoException("Pseudo cannot have more than 20 characters");
        }

    this.pseudo = pseudo;
    this.listRecords = new HashSet<Record>();
    this.listResolvedLevels = new HashSet<Level>();
        }

    public void addRecord(Record record) {
        this.listRecords.add(record);
        //On ajoute dans le fichier csv le record
        // TODO
        }
    public void addResolvedLevel(Level level){
        this.listResolvedLevels.add(level);
        }
        //Verify if pseudo bypasses 20 characters
        public boolean verifyPseudo(String pseudo) {
        //TODO
            return false;
        }

        //Get Player records from csv file and insert in listeRecords
        public void updateListRecords() {
        }
        //TODO
        }

class PlayerPseudoException extends Exception {
    public PlayerPseudoException(String message) {
        super(message);
    }
}
