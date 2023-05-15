public class NumberTile extends Tile{
    private int number;

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
}