public class NumberTile extends Tile{
    private int number; //Tile Number
    private boolean blocked; //True if tile canot be played

    NumberTile(int number, int posX, int posY) {
        super(posX,posY);
        this.number = number;

        public void move(String direction) {
            //TODO
            if (mouvementAvailable(direction) == True) {
                //TODO
            }
        }
        public boolean mouvementAvailable(String direction) {

        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBlocked() {
        this.blocked = True;
    }
    public void setUnblocked(){
        this.blocked = False;
    }
}