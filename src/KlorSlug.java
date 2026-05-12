public class KlorSlug extends Piece{
    public KlorSlug(boolean red){
        super(red, 7, 3, 3);
    }

    @Override
    public String toString() {
        return super.toString() + "\nName: KlorSlug";
    }

    public String getType(){
        return "KlorSlug";
    }
}
