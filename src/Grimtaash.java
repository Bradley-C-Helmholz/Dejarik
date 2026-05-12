public class Grimtaash extends Piece{
    public Grimtaash(boolean red){
        super(red, 8, 2, 2);
        // should be super(red, 8, 2, 2);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Grimtaash";
    }

    public String getType(){
        return "Grimtaash";
    }
}
