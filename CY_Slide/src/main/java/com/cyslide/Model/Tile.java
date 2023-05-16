public abstract class Tile{
    private int posX;
    private int posY;

    Tile(int posX,int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public abstract int getPosX() ;

    public abstract void setPosY(int posY);

    public abstract void setPosX(int posX);

    public abstract int getPosY();
}