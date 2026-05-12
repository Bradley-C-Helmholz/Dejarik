public class Void extends Piece{
    public Void(boolean red){
        super(red, 0, 0, 0);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Void";
    }

    public String getType(){
        return null;
    }
}