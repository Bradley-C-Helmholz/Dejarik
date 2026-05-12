public class Savrip extends Piece{
    public Savrip(boolean red){
        super(red, 6, 6, 2);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: Mantellian Savrip";
    }

    public String getType(){
        return "Savrip";
    }
}