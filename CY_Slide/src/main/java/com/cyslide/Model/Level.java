public class Level{
private int number;
private int moveCounter;
private boolean completed;
private Record record;

private ArrayList<Tile> tiles;

public Level(){
        //TODO
        }
public void moveTile(int posX,int posY,String direction)throws MoveTileException{
        //the mouvement direction can be Left,Right,Up,Down
        //We verify if the neighbor is 0 (it doest exist, is empty)
        if(direction=="UP"){
        posY-=posY;
        //TODO
        }
        else if(direction=="DOWN"){
        posY+=posY;
        //TODO
        }

        else if(direction=="LEFT"){
        posX-=posX;
        //TODO
        }
        else if(direction=="RIGHT"){
        posX+=posX;
        //TODO
        }
        }

public boolean isCompleted(){
        //TODO
        }
public boolean isPlayable(){
        //Verify if level generated can be completed
        //TODO
        }

public void initLevelRNG(){
        //TODO

        }
        public void initLevelMove() {
    //Generate random level by movinf each tile, we are sure this level can be completed
        //TODO
        }
        }

public class MoveTileException extends Exception {
    public MoveTileException(String message) {
        super(message);
    }
}