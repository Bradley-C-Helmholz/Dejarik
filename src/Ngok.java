public class Ngok extends Piece{
    public Ngok(boolean red){
        super(red, 3, 8, 1);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Ngok";
    }

    public String getType(){
        return "Ngok";
    }
}
