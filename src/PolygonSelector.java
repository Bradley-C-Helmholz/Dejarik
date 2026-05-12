import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;


public class PolygonSelector extends MouseAdapter
{
    PolygonPanel polygonPanel;
    StartGame game;
    Spot[] spacess = new Spot[25];
    Polygon[] all;
    int click = 0;
    int which;
    boolean over = false;
    int turn = 0;
    String current = "";
    boolean aiEnabled = false;
    boolean animationEnabled = false;
    DejarikAI ai;

    public PolygonSelector(PolygonPanel pp, StartGame g){
        this(pp, g, false, false);
    }

    public PolygonSelector(PolygonPanel pp, StartGame g, boolean vsAI){
        this(pp, g, vsAI, false);
    }

    public PolygonSelector(PolygonPanel pp, StartGame g, boolean vsAI, boolean anim){
        polygonPanel = pp;
        game = g;
        aiEnabled = vsAI;
        animationEnabled = anim;
        if (vsAI) ai = new DejarikAI(pp);
    }

    public void mousePressed(MouseEvent e){
        Point p = e.getPoint();
        polygonPanel.initializeValidSpotMoves();
        polygonPanel.initializeValidAttackMoves();
        initializeAllArrays();


        for(int i = 0; i < all.length; i++){
            if(all[i].contains(p) && !over){
                if(click == 0) {
                    resetColors();
                    System.out.println("click 0");
                    colorValidSpaces(i); // Shows valid moves

                    polygonPanel.colors[i] = Color.yellow; // Highlights selected space
                    polygonPanel.repaint();
                    displayStats(i);
                    which = i;
                    click++;
                    break;
                }
                if(click==1){
                    resetColors();
                    System.out.println("click 1");

                    compressedLogic(i);

                    which = i;
                    polygonPanel.repaint();
                    click--;

                    break;
                }
            }
        }

    }

    private void compressedLogic(int i){
        if(turn == 0){

            if (spacess[which].getPiece().getClass() != Void.class && spacess[which].getPiece().isRed()) {
                System.out.println("not void");
                current = spacess[which].getPiece().getType();
                fullMove(i);
            }
        }
        else if(turn == 1) {

            if (spacess[which].getPiece().getClass() != Void.class && spacess[which].getPiece().isRed()
                    && polygonPanel.spaces[which].getPiece().getType().equals(current)) {
                System.out.println("not void");
                fullMove(i);
            }
        }
        else if(turn == 2){
            game.leftBottom.setText("Blue Turn");
            game.rightBottom.setText("");
            if (spacess[which].getPiece().getClass() != Void.class && !spacess[which].getPiece().isRed()) {
                System.out.println("not void");
                current = spacess[which].getPiece().getType();
                fullMove(i);
            }
        }
        else if(turn == 3){

            if (spacess[which].getPiece().getClass() != Void.class && !spacess[which].getPiece().isRed()
                    && polygonPanel.spaces[which].getPiece().getType().equals(current)) {
                System.out.println("not void");
                fullMove(i);
                if(turn == 4) turn = 0;
            }
        }

        if (aiEnabled && !polygonPanel.showCombatAnim) {
            if (turn == 2) scheduleAIFirstAction();
            else if (turn == 3) scheduleAISecondAction();
        }
    }

    private void initializeAllArrays(){
        Polygon[] outerPolys = polygonPanel.outerRing;
        Polygon[] innerPolys = polygonPanel.innerRing;

        all = new Polygon[25];
        for(int i = 0; i < 12; i++){
            all[i] = outerPolys[i];
            all[i+12] = innerPolys[i];
        }
        all[24] = polygonPanel.center;

        for(int i = 0; i < 25; i++){
            spacess[i] = polygonPanel.spaces[i];
        }
    }

    private void colorValidSpaces(int i){

        if(spacess[i] != null){
            int[] whats = spacess[i].getWhichSpace();
            int[] whatAttack = spacess[i].getAttackSpace();

            for(int whatSpace : whats){
                if(spacess[i].getPiece().getClass() != Void.class){ // Makes sure only the spaces with pieces show valid options
                    if(polygonPanel.spaces[whatSpace].getPiece().isRed() != spacess[i].getPiece().isRed()){
                        //polygonPanel.colors[whatSpace] = Color.red; // Recognizes enemy piece
                    }
                    if(polygonPanel.spaces[whatSpace].getPiece().isRed() == spacess[i].getPiece().isRed()){
                        //polygonPanel.colors[whatSpace] = Color.cyan; // Recognizes enemy piece
                    }
                    if(polygonPanel.spaces[whatSpace].getPiece().getClass() == Void.class) {// Makes sure no occupied spaces are valid
                        polygonPanel.colors[whatSpace] = Color.green;
                    }
                    which = i;
                }
            }
            for(int at : whatAttack){
                if(spacess[i].getPiece().getType() != null) {
                    if (spacess[at].getPiece().getClass() != Void.class && spacess[i].getPiece().isRed() != spacess[at].getPiece().isRed()) {
                        polygonPanel.colors[at] = Color.orange;
                    }
                }
            }

        }
    }

    private void moveToVoid(int i){
        if(spacess[i].getPiece().getClass() == Void.class){
            int[] whichSpaces = spacess[which].getWhichSpace();
            for(int element : whichSpaces){
                if(element == i){
                    System.out.println("found match");
                    Piece previousSP = spacess[which].getPiece();
                    polygonPanel.spaces[i].setPiece(previousSP);
                    polygonPanel.spaces[which].setPiece(new Void(true));

                    game.moveLog.append(polygonPanel.spaces[i].getPiece().getType()+ " moved from: [" + which +"] to ["+ i + "]\n\n");
                    turn++;
                    if(turn == 2){
                        game.bluePlayer.pic = "Neutral" + game.bluePlayer.who;
                        game.redPlayer.pic = "Neutral" + game.redPlayer.who;
                        game.bluePlayer.repaint();
                        game.redPlayer.repaint();

                        game.leftBottom.setText("Blue Turn");
                        game.rightBottom.setText("");
                    }
                    else if(turn == 4){
                        game.redPlayer.pic = "Neutral" + game.redPlayer.who;
                        game.bluePlayer.pic = "Neutral" + game.bluePlayer.who;
                        game.bluePlayer.repaint();
                        game.redPlayer.repaint();

                        game.leftBottom.setText("");
                        game.rightBottom.setText("Red Turn");
                    }

                    sound("clicky.wav");
                    break;
                }
            }
        }
    }

    private void killPiece(int i){
        if(spacess[i].getPiece().getClass() != Void.class){
            if(polygonPanel.spaces[which].getPiece().isRed() != spacess[i].getPiece().isRed()) {
                int[] whichSpaces = spacess[which].getWhichSpace();
                for (int element : whichSpaces) {
                    if (element == i) {
                        System.out.println("found enemy");
                        Piece previousSP = spacess[which].getPiece();

                        game.moveLog.append(polygonPanel.spaces[which].getPiece().getType()+ " moved from: " + which +" to "+ i + "\n");
                        game.moveLog.append(polygonPanel.spaces[which].getPiece().getType()+ " killed " +
                                polygonPanel.spaces[i].getPiece().getType() + " at " + i + "!\n\n");

                        polygonPanel.spaces[i].setPiece(previousSP);
                        polygonPanel.spaces[which].setPiece(new Void(true));

                        if(turn == 2){
                            game.leftBottom.setText("Blue Turn");
                            game.rightBottom.setText("");
                        }
                        else if(turn == 4){
                            game.leftBottom.setText("");
                            game.rightBottom.setText("Red Turn");
                        }

                        sound("kill.wav");
                        System.out.println("enemy destroyed");
                        break;
                    }
                }
            }
        }
    }

    private void attackPiece(int i){
        if(spacess[i].getPiece().getClass() != Void.class && spacess[which].getPiece().getClass() != Void.class){
            if (spacess[which].getPiece().isRed() != spacess[i].getPiece().isRed()){
                int[] attackSpaces = spacess[which].getAttackSpace();
                for(int element : attackSpaces){
                    if(element == i){
                        System.out.println("found enemy");

                        Piece attacker = polygonPanel.spaces[which].getPiece();
                        Piece defender = polygonPanel.spaces[i].getPiece();

                        int atkRoll = (int)(Math.random() * attacker.getAttack()) + 1;
                        int defRoll = (int)(Math.random() * defender.getDefense()) + 1;
                        boolean win = atkRoll > defRoll;

                        game.moveLog.append(attacker.getType() + " is fighting " + defender.getType()
                                + "\nAttacker: " + atkRoll + "\nDefender: " + defRoll + "\n");
                        updatePortraits(win, attacker.isRed());

                        final int targetSpot = i;
                        final boolean wasAnimated = animationEnabled;
                        Runnable applyResult = () -> {
                            if (win) {
                                game.moveLog.append(attacker.getType() + " killed " +
                                        defender.getType() + " at [" + targetSpot + "]!\n\n");
                                polygonPanel.spaces[targetSpot].setPiece(new Void(true));
                                System.out.println("enemy destroyed");
                            } else {
                                game.moveLog.append(attacker.getType() + " failed to kill " +
                                        defender.getType() + " at [" + targetSpot + "]!\n\n");
                                System.out.println("failed to destroy enemy");
                            }
                            turn++;
                            if (turn == 2) {
                                game.leftBottom.setText("Blue Turn");
                                game.rightBottom.setText("");
                            } else if (turn == 4) {
                                game.leftBottom.setText("");
                                game.rightBottom.setText("Red Turn");
                                turn = 0;
                            }
                            sound("kill.wav");
                            checkWin();
                            polygonPanel.repaint();
                            if (wasAnimated && aiEnabled && !over) {
                                if (turn == 2) scheduleAIFirstAction();
                                else if (turn == 3) scheduleAISecondAction();
                            }
                        };

                        if (animationEnabled) {
                            polygonPanel.startCombatAnimation(which, i, atkRoll, defRoll, win,
                                    displayName(attacker.getType()), displayName(defender.getType()),
                                    attacker.isRed(), applyResult);
                        } else {
                            applyResult.run();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void updatePortraits(boolean win, boolean redAttacked) {
        if (win) {
            if (redAttacked) {
                game.bluePlayer.pic = "Shocked" + game.bluePlayer.who;
                game.redPlayer.pic  = "Happy"   + game.redPlayer.who;
            } else {
                game.redPlayer.pic  = "Shocked" + game.redPlayer.who;
                game.bluePlayer.pic = "Happy"   + game.bluePlayer.who;
            }
        }
        game.redPlayer.repaint();
        game.bluePlayer.repaint();
    }

    private String displayName(String type) {
        if (type == null) return "";
        switch (type) {
            case "KlorSlug":  return "K'lor'slug";
            case "Savrip":    return "Manetellian Savrip";
            case "Grimtaash": return "Grimtaash the Molator";
            case "Strider":   return "Kintan Strider";
            case "Ngok":      return "N'gok";
            case "Ghhk":      return "Ghhhk";
            default:          return type;
        }
    }

    private void fullMove(int i){
        //killPiece(i);
        moveToVoid(i);
        attackPiece(i);
    }

    private void checkWin(){
        int numRed = 0;
        int numBlue = 0;

        for(Spot i : spacess){
            if(i.getPiece().getType() != null) {
                if (i.getPiece().isRed()) {
                    numRed++;
                } else numBlue++;
            }
        }
        if(numRed == 0 || numBlue == 0){
            over = true;
            if(numRed == 0){
                System.out.println("blue won");
                polygonPanel.whoWon = "Blue Won!";
                game.leftBottom.setText("Blue Won!");
                game.rightBottom.setText("Red Lost!");
                game.redPlayer.pic = "Sad" + game.redPlayer.who;
            }
            else{
                System.out.println("red won");
                polygonPanel.whoWon = " Red Won!";
                game.leftBottom.setText("Blue Lost!");
                game.rightBottom.setText("Red Won!");
                game.bluePlayer.pic = "Sad" + game.bluePlayer.who;

            }
        }
    }

    private void displayStats(int i){
        String name = spacess[i].getPiece().getType();
        int attack = spacess[i].getPiece().getAttack();
        int defense = spacess[i].getPiece().getDefense();
        int move = spacess[i].getPiece().getMovement();

        if(name != null) {
            if(name.equals("KlorSlug")) name = "K'lor'slug";
            if(name.equals("Savrip")) name = "Manetellian " + name;
            if(name.equals("Grimtaash")) name = name + " the Molator";
            if(name.equals("Strider")) name = "Kintan " + name;
            if(name.equals("Ngok")) name = "N'gok";
            if(name.equals("Ghhk")) name = "Ghhhk";

            game.creatureStats.setText("\nSpot [" + i + "]\n\n" + name + "\n=============\nAttack: " + attack +
            "\nDefense: " + defense + "\nMovement: " + move + "\n=============");
        }
        else{
            game.creatureStats.setText("\nSpot [" + i + "]");
        }
    }

    private void resetColors(){
        for(int i = 0; i < 12; i++){
            if(i % 2 == 0){
                polygonPanel.colors[i] = Color.BLACK;
                polygonPanel.colors[i+12] = Color.WHITE;
            }
            else{
                polygonPanel.colors[i] = Color.WHITE;
                polygonPanel.colors[i+12] = Color.BLACK;
            }
        }
        polygonPanel.colors[24] = Color.WHITE;
    }

    public void sound(String file){
        try
        {
            new PlaySound(file);
        }
        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void scheduleAIFirstAction() {
        Timer timer = new Timer(600, e -> {
            if (over) return;
            polygonPanel.initializeValidSpotMoves();
            polygonPanel.initializeValidAttackMoves();
            initializeAllArrays();
            DejarikAI.AIMove move = ai.chooseFirstAction(polygonPanel.spaces);
            if (move == null) {
                forceIncrementTurn();
                forceIncrementTurn();
            } else {
                which = move.fromSpot;
                current = polygonPanel.spaces[which].getPiece().getType();
                resetColors();
                compressedLogic(move.toSpot);
                polygonPanel.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void scheduleAISecondAction() {
        Timer timer = new Timer(600, e -> {
            if (over) return;
            polygonPanel.initializeValidSpotMoves();
            polygonPanel.initializeValidAttackMoves();
            initializeAllArrays();
            DejarikAI.AIMove move = ai.chooseSecondAction(polygonPanel.spaces, current);
            if (move == null) {
                forceIncrementTurn();
            } else {
                which = move.fromSpot;
                resetColors();
                compressedLogic(move.toSpot);
                polygonPanel.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void forceIncrementTurn() {
        turn++;
        if (turn == 4) turn = 0;
        game.moveLog.append("[AI passes — no valid action]\n\n");
        game.leftBottom.setText("");
        game.rightBottom.setText("Red Turn");
        polygonPanel.repaint();
    }

}
