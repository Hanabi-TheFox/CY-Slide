Public Class Player {
    private String pseudo;
    private Hash<Record> listeRecords; // Pour assurer aucun record doublon
    private Hash<Level> listeResolvedLevels;
    private TaquinGame taquinGame;

    Player(String pseudo) throws PlayerPseudoException{
    if (this.verifyPseudo(pseudo).length() > 20) {
        throw new PlayerPseudoException("This pseudo bypasses 20 characters");
        }

    this.pseudo = pseudo;
    this.listeRecords = new HashSet<Record>();
    this.listeResolvedLevels = new HashSet<Record>();
        }

    public void addRecord(Record record) {
        this.listeRecords.add(record);
        //On ajoute dans le fichier csv le record
        // TODO
        }
        //Verify if pseudo already have been created previously on the csv file
        public boolean verifyPseudo(String pseudo) {
        //TODO
        }

        //Get Player records from csv file and insert in listeRecords
        public void updateListeRecords() {
        }
        //TODO
        }

public class PlayerPseudoException extends Exception {
    public PlayerPseudoException(String message) {
        super(message);
    }
}
