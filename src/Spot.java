import java.awt.*;

public class Spot {
    private Piece piece;
    private int x;
    private int y;
    private int[] whichSpace;
    private int[] attackSpace;

    public Spot(int x, int y, Piece piece)
    {
        setPiece(piece);
        setX(x);
        setY(y);
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece p)
    {
        piece = p;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setWhichSpace(int[] which){
        whichSpace = which;
    }

    public int[] getWhichSpace() {
        return whichSpace;
    }

    public void setAttackSpace(int[] whichAttack){
        attackSpace = whichAttack;
    }
    public int[] getAttackSpace(){
        return attackSpace;
    }

    @Override
    public String toString() {
        return piece.toString() + " at [" + x + ", " + y + "]\n";
    }
}
