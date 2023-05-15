public class Player {
    private String pseudo;
    private Hash<Record> listRecords;
    private Hash<Level> listResolvedLevels;
    private TaquinGame taquinGame;

    Player(String pseudo) throws PlayerPseudoException{
    if (this.verifyPseudo(pseudo).length() > 20) {
        throw new PlayerPseudoException("This pseudo bypasses 20 characters");
        }

    this.pseudo = pseudo;
    this.listRecords = new HashSet<Record>();
    this.listResolvedLevels = new HashSet<Record>();
        }

    public void addRecord(Record record) {
        this.listRecords.add(record);
        //On ajoute dans le fichier csv le record
        // TODO
        }
    public void addResolvedLevel(Level level){
        this.listResolvedLevel.add(level);
        }
        //Verify if pseudo already have been created previously on the csv file
        public boolean verifyPseudo(String pseudo) {
        //TODO
        }

        //Get Player records from csv file and insert in listeRecords
        public void updateListRecords() {
        }
        //TODO
        }

public class PlayerPseudoException extends Exception {
    public PlayerPseudoException(String message) {
        super(message);
    }
}
