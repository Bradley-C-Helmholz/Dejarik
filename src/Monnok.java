public class Monnok extends Piece{
    public Monnok(boolean red){
        super(red, 6, 5, 3);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Monnok";
    }

    public String getType(){
        return "Monnok";
    }
}