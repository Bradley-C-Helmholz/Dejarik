public class Ghhk extends Piece{
    public Ghhk(boolean red){
        super(red, 4, 3, 2);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Ghhk";
    }

    public String getType(){
        return "Ghhk";
    }
}
