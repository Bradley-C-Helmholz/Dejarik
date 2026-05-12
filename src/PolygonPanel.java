import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class PolygonPanel extends JPanel
{
    Polygon[] outerRing;
    Polygon[] innerRing;
    Polygon center;
    Color[] colors;
    Spot[] spaces;
    GetImage imager;
    Font font = new Font("Copperplate Gothic Bold", Font.PLAIN, 30);
    String whoWon = "";

    boolean showCombatAnim = false;
    int animFrom, animTo;
    int animAtkRoll, animDefRoll;
    boolean animWin;
    int animDisplayAtk, animDisplayDef;
    String animPhase = "";
    Runnable animCallback;
    Timer animTimer;
    String animAtkName, animDefName;
    boolean animAtkIsRed;

    public PolygonPanel(){
        setBackground(Color.white);
        imager = new GetImage();

        spaces = new Spot[25];
        for(int i = 0; i < 25; i++){
            spaces[i] = new Spot(1,1, new Void(true));
        }

        firstSpots();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //g2.drawImage(imager.getImage("Dejarik_Shield"), 115,0,830,830,null);

        if(outerRing == null){
            initBoard();
            setXYCoor();
            initializeValidSpotMoves();
        }

        for(int i = 0; i < outerRing.length; i++){
            g2.draw(outerRing[i]);
            g2.setPaint(colors[i]);
            g2.fill(outerRing[i]);
            g2.setPaint(Color.BLACK);
        }
        for(int i = 0; i < innerRing.length; i++){
            g2.draw(innerRing[i]);
            g2.setPaint(colors[i+12]);
            g2.fill(innerRing[i]);
            g2.setPaint(Color.BLACK);
        }
        g2.draw(center);
        g2.setPaint(colors[24]);
        g2.fill(center);
        g2.setPaint(Color.BLACK);

        for(Spot s : spaces){ // Paints images of bases and creatures
            if(s.getPiece().getClass() != Void.class){

                if(s.getPiece().isRed()){
                    g2.drawImage(imager.getImage("Base2_redt"), s.getX()-30, s.getY()+10, null);
                }
                else {
                    g2.drawImage(imager.getImage("Base2_bluet"), s.getX() - 30, s.getY() + 10, null);
                }
                BufferedImage creature = imager.getImage(s.getPiece().getType());
                if (s.getPiece().getType().equals("Monnok")){
                    g2.drawImage(creature, s.getX()-20, s.getY()-65, null);
                }
                else if(s.getPiece().getType().equals("Houjix")){
                    g2.drawImage(creature, s.getX()-28, s.getY()-28, null);
                }
                else {
                    g2.drawImage(creature, s.getX() - 30, s.getY() - 50, null);
                }
            }
        }

        if(!whoWon.equals("")) {
            g2.setPaint(Color.darkGray);
            g2.fillRect(0, getHeight() / 2 - 20, getWidth(), 40);
            g2.setPaint(Color.ORANGE);
            g2.setFont(font);
            g2.drawString(whoWon, getWidth() / 2 - 90, getHeight() / 2 + 10);
        }

        if (showCombatAnim) drawCombatAnimation(g2);

    }

    private void setXYCoor(){
        for(int i = 0; i < 12; i++){
            int[] coors = inputPolar(220,i*30+15);
            spaces[i].setX(getWidth()/2+coors[0]);
            spaces[i].setY(getHeight()/2+coors[1]);
        }
        for(int i = 12; i < 24; i++){
            int[] coors = inputPolar(120,i*30+15);
            spaces[i].setX(getWidth()/2+coors[0]);
            spaces[i].setY(getHeight()/2+coors[1]);
        }
        spaces[24].setX(getWidth()/2);
        spaces[24].setY(getHeight()/2);

    }

    private void initBoard(){
        outerRing = new Polygon[12];
        innerRing = new Polygon[12];
        center = new Polygon();
        colors = new Color[25];

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        for(int i = 0; i < 12; i++){
            Polygon side = new Polygon();
            int[] coor = inputPolar(65,i * 30);
            side.addPoint(x + coor[0], y + coor[1]);
            center.addPoint(x + coor[0], y + coor[1]);
            //coor = inputPolar(65, i * 30 + 15); //
            //side.addPoint(x + coor[0], y + coor[1]); //
            //center.addPoint(x + coor[0], y + coor[1]); //
            coor = inputPolar(65, i * 30 + 30);
            side.addPoint(x + coor[0], y + coor[1]);
            center.addPoint(x + coor[0], y + coor[1]);
            coor = inputPolar(170, i * 30 + 30);
            side.addPoint(x + coor[0], y + coor[1]);
            //coor = inputPolar(170, i * 30 + 15); //
            //side.addPoint(x + coor[0], y + coor[1]); //
            coor = inputPolar(170, i * 30);
            side.addPoint(x + coor[0], y + coor[1]);

            innerRing[i] = side;
        }

        for(int i = 0; i < 12; i++){
            Polygon side = new Polygon();
            int[] coor = inputPolar(170,i * 30);
            side.addPoint(x + coor[0], y + coor[1]);
            //coor = inputPolar(170, i * 30 + 15); //
            //side.addPoint(x + coor[0], y + coor[1]); //
            coor = inputPolar(170, i * 30 + 30);
            side.addPoint(x + coor[0], y + coor[1]);
            coor = inputPolar(270, i * 30 + 30);
            side.addPoint(x + coor[0], y + coor[1]);
            //coor = inputPolar(270, i * 30 + 15); //
            //side.addPoint(x + coor[0], y + coor[1]); //
            coor = inputPolar(270, i * 30);
            side.addPoint(x + coor[0], y + coor[1]);

            outerRing[i] = side;
        }

        for(int i = 0; i < 12; i++){
            if(i % 2 == 0){
                colors[i] = Color.BLACK;
                colors[i+12] = Color.WHITE;
            }
            else{
                colors[i] = Color.WHITE;
                colors[i+12] = Color.BLACK;
            }
        }
        colors[24] = Color.WHITE;

    }

    public int[] inputPolar(double x, double y){
        int[] answer = new int[2];

        double[] coordinates = {x,y};
        coordinates = GFG.ConvertToCartesian(coordinates);

        answer[0] = (int) coordinates[0];
        answer[1] = (int) coordinates[1];

        return answer;
    }

    public void initializeValidSpotMoves(){

        for(int i = 0; i < 25; i++){
            int[] listValid;
            int move = spaces[i].getPiece().getMovement();
            if(move == 1){ // Beginning of "if movement is 1"
                if(i <= 11){
                    listValid = new int[3];
                    listValid[0] = i+1;
                    listValid[1] = i-1;
                    listValid[2] = i+12;
                    if(i==0) listValid[1] = 11;
                    if(i==11) listValid[0] = 0;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i >= 12 && i <= 23){
                    listValid = new int[4];
                    listValid[0] = i+1;
                    listValid[1] = i-1;
                    listValid[2] = 24;
                    listValid[3] = i-12;
                    if(i==12) listValid[1] = 23;
                    if(i==23) listValid[0] = 12;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i==24){
                    listValid = new int[12];
                    for(int j = 12; j <=23; j++){
                        listValid[j-12] = j;
                    }
                    spaces[i].setWhichSpace(listValid);
                }
            } // End of "if movement is 1"
            else if(move == 2){ // Beginning of "if movement is 2"
                if(i <= 11){
                    listValid = new int[3];
                    listValid[0] = i+2;
                    listValid[1] = i-2;
                    listValid[2] = 24;
                    if(i==0) listValid[1] = 10;
                    if(i==11) listValid[0] = 1;
                    if(i==1) listValid[1] = 11;
                    if(i==10) listValid[0] = 0;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i >= 12 && i <= 23){
                    listValid = new int[3];
                    listValid[0] = i+2;
                    if(i+2 > 23) listValid[0] = i-10;
                    listValid[1] = i-2;
                    if(i-2 < 12) listValid[1] = i+10;
                    listValid[2] = i+6;
                    if(i+6 > 23) listValid[2] = i-6;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i==24){
                    listValid = new int[12];
                    for(int j = 0; j <=11; j++){
                        listValid[j] = j;
                    }
                    spaces[i].setWhichSpace(listValid);
                }
            } // End of "if movement is 2"
            else if(move == 3){ // Beginning of "if movement is 3"
                if(i <= 11){
                    listValid = new int[3];
                    listValid[0] = i+3;
                    if(i+3 > 11) listValid[0] = i-9;
                    listValid[1] = i-3;
                    if(i-3 < 0) listValid[1] = i+9;
                    listValid[2] = i+18;
                    if(i+18 > 23) listValid[2] = i+6;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i >= 12 && i <= 23){
                    listValid = new int[3];
                    listValid[0] = i+3;
                    if(i+3 > 23) listValid[0] = i-9;
                    listValid[1] = i-3;
                    if(i-3 < 12) listValid[1] = i+9;
                    listValid[2] = i-6;
                    if(i-6 > 11) listValid[2] = i-18;

                    spaces[i].setWhichSpace(listValid);
                }
                if(i == 24){
                    spaces[i].setWhichSpace(new int[0]);
                }
            } // End of "if movement is 3"
            else{
                spaces[i].setWhichSpace(new int[0]);
            }
        }

    }

    public void initializeValidAttackMoves(){
        for(int i = 0; i < 25; i++){
            int[] listValid;

            if(i <= 11){
                listValid = new int[3];
                listValid[0] = i+1;
                listValid[1] = i-1;
                listValid[2] = i+12;
                if(i==0) listValid[1] = 11;
                if(i==11) listValid[0] = 0;

                spaces[i].setAttackSpace(listValid);
            }
            if(i >= 12 && i <= 23){
                listValid = new int[4];
                listValid[0] = i+1;
                listValid[1] = i-1;
                listValid[2] = 24;
                listValid[3] = i-12;
                if(i==12) listValid[1] = 23;
                if(i==23) listValid[0] = 12;

                spaces[i].setAttackSpace(listValid);
            }
            if(i==24){
                listValid = new int[12];
                for(int j = 12; j <=23; j++){
                    listValid[j-12] = j;
                }
                spaces[i].setAttackSpace(listValid);
            }
        }
    }

    public void firstSpots(){

        int rand;
        Piece[] blueToChoose = {new KlorSlug(false), new Savrip(false), new Houjix(false), new Ngok(false),
                new Monnok(false), new Grimtaash(false), new Strider(false), new Ghhk(false)};

        Piece[] redToChoose = {new KlorSlug(true), new Savrip(true), new Houjix(true), new Ngok(true),
                new Monnok(true), new Grimtaash(true), new Strider(true), new Ghhk(true)};

        String[] namesAlready = {"", "", "", "", "", "", "", ""};
        int len = namesAlready.length;
        System.out.println(len);

        for(int i = 1; i <= 4; i++) {

            rand = (int) (Math.random() * (len));
            String bChoice = blueToChoose[rand].getType();
            String rChoice = redToChoose[rand].getType();

            while(namedAlready(bChoice, namesAlready)){
                rand = (int) (Math.random() * (len));
                bChoice = blueToChoose[rand].getType();
            }
            spaces[i] = new Spot(1, 1, blueToChoose[rand]);
            namesAlready[i] = bChoice;

            while(namedAlready(rChoice, namesAlready)){
                rand = (int) (Math.random() * (len));
                rChoice = redToChoose[rand].getType();
            }
            spaces[i + 6] = new Spot(1, 1, redToChoose[rand]);
            namesAlready[len - i] = blueToChoose[rand].getType();

        }

    }

    private boolean namedAlready(String name, String[] list){
        for(String n : list){
            if(n.equals(name)){
                return true;
            }
        }
        return false;
    }

    public void startCombatAnimation(int from, int to, int atkRoll, int defRoll,
                                     boolean win, String atkName, String defName,
                                     boolean atkIsRed, Runnable callback) {
        animFrom = from; animTo = to;
        animAtkRoll = atkRoll; animDefRoll = defRoll;
        animWin = win;
        animAtkName = atkName; animDefName = defName;
        animAtkIsRed = atkIsRed;
        animCallback = callback;
        animDisplayAtk = 0; animDisplayDef = 0;
        animPhase = "roll_atk";
        showCombatAnim = true;
        animTimer = new Timer(100, e -> advanceAnimation());
        animTimer.start();
    }

    private void advanceAnimation() {
        switch (animPhase) {
            case "roll_atk":
                animDisplayAtk++;
                if (animDisplayAtk >= animAtkRoll) animPhase = "roll_def";
                repaint();
                break;
            case "roll_def":
                animDisplayDef++;
                if (animDisplayDef >= animDefRoll) {
                    animTimer.stop();
                    animPhase = "result";
                    repaint();
                    Timer resultTimer = new Timer(1250, e -> {
                        showCombatAnim = false;
                        ((Timer) e.getSource()).stop();
                        animCallback.run();
                    });
                    resultTimer.setRepeats(false);
                    resultTimer.start();
                } else {
                    repaint();
                }
                break;
        }
    }

    private void drawCombatAnimation(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        int bw = 460, bh = 220;
        int bx = getWidth() / 2 - bw / 2;
        int by = getHeight() / 2 - bh / 2;

        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(bx, by, bw, bh);
        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(bx, by, bw, bh);

        FontMetrics fm;

        // Title
        g2.setFont(font);
        g2.setColor(Color.ORANGE);
        String title = "COMBAT!";
        fm = g2.getFontMetrics();
        g2.drawString(title, getWidth() / 2 - fm.stringWidth(title) / 2, by + 40);

        // Names + VS
        Font nameFont = font.deriveFont(16f);
        g2.setFont(nameFont);
        fm = g2.getFontMetrics();
        Color atkColor = animAtkIsRed ? Color.RED : Color.CYAN;
        Color defColor = animAtkIsRed ? Color.CYAN : Color.RED;
        g2.setColor(atkColor);
        g2.drawString(animAtkName, bx + 20, by + 80);
        g2.setColor(defColor);
        g2.drawString(animDefName, bx + bw - 20 - fm.stringWidth(animDefName), by + 80);
        g2.setColor(Color.WHITE);
        g2.drawString("VS", getWidth() / 2 - fm.stringWidth("VS") / 2, by + 80);

        // Rolls
        Font rollFont = font.deriveFont(Font.BOLD, 40f);
        g2.setFont(rollFont);
        fm = g2.getFontMetrics();

        String atkStr = (animPhase.equals("roll_atk") && animDisplayAtk < animAtkRoll)
                ? String.valueOf(animDisplayAtk + 1) : String.valueOf(animAtkRoll);
        String defStr = animPhase.equals("roll_atk") ? "?"
                : ((animPhase.equals("roll_def") && animDisplayDef < animDefRoll)
                    ? String.valueOf(animDisplayDef + 1) : String.valueOf(animDefRoll));

        g2.setColor(atkColor);
        g2.drawString("[" + atkStr + "]", bx + 40, by + 160);
        g2.setColor(defColor);
        String defRollDisplay = "[" + defStr + "]";
        g2.drawString(defRollDisplay, bx + bw - 40 - fm.stringWidth(defRollDisplay), by + 160);

        // Result
        if (animPhase.equals("result")) {
            Font resultFont = font.deriveFont(Font.BOLD, 22f);
            g2.setFont(resultFont);
            fm = g2.getFontMetrics();
            String result = animWin ? "*** ATTACKER WINS! ***" : "*** DEFENDER HOLDS! ***";
            g2.setColor(animWin ? Color.RED : Color.GREEN);
            g2.drawString(result, getWidth() / 2 - fm.stringWidth(result) / 2, by + 205);
        }
    }


}
