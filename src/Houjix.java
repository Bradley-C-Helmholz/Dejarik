public class Houjix extends Piece{
    public Houjix(boolean red){
        super(red, 4, 4, 1);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Houjix";
    }

    public String getType(){
        return "Houjix";
    }
}
