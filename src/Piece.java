public abstract class Piece {
    private boolean red = false;
    private int attack;
    private int defense;
    private int movement;

    public Piece(boolean r, int at, int def, int mov)
    {
        red = r;
        attack = at;
        defense = def;
        movement = mov;
    }

    public boolean isRed()
    {
        return red;
    }

    public int getAttack(){
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "";
    }

    public abstract String getType();

}
