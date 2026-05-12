import javax.swing.*;
import java.awt.*;

public class Portrait extends JPanel {

    GetImage imager;
    boolean red;
    String pic;

    final String who;

    public Portrait(boolean r, String p){
        setBackground(Color.BLACK);
        imager = new GetImage();
        red = r;
        who = p;
        pic = "Neutral" + who;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.CYAN);
        if(red) g2.setPaint(Color.RED);
        g2.fillRect(26, 20, 270,270);

        g2.setPaint(Color.WHITE);
        g2.fillRect(36,30,250,250);

        g2.drawImage(imager.getImage("characters\\" + pic), 36,30,250,250, null);
    }
}
