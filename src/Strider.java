public class Strider extends Piece{
    public Strider(boolean red){
        super(red, 2, 7, 3);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Strider";
    }

    public String getType(){
        return "Strider";
    }
}
