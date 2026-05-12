import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartGame extends JFrame implements ActionListener
{

    JPanel leftPanel = new JPanel(new GridLayout(2,1));
    JPanel rightPanel = new JPanel(new GridLayout(2, 1));
    JTextArea moveLog = new JTextArea("Move Log:\n\n", 1, 21);
    JTextArea creatureStats = new JTextArea("\nSpot [null]",1, 17);
    JButton restart = new JButton ("Restart");
    JButton menu = new JButton("Back to Menu");
    JScrollPane logScroll = new JScrollPane(moveLog);
    JPanel bottomPanel = new JPanel(new GridLayout(1,4));
    JLabel leftBottom = new JLabel("", JLabel.CENTER);
    JLabel rightBottom = new JLabel("Red Turn", JLabel.CENTER);
    GetImage imager;

    Portrait redPlayer;
    Portrait bluePlayer;

    Font font = new Font("Matura MT Script Capitals", Font.PLAIN, 19);
    Font font2 = new Font("Matura MT Script Capitals", Font.PLAIN, 27);
    private String redCharacter = "";
    private String blueCharacter = "";
    private boolean vsAI = false;
    private boolean animEnabled = false;


    public static void main(String[] args)
    {
        new StartGame("Luke", "Chewbacca");
    }

    public StartGame(String r, String b){
        this(r, b, false);
    }

    public StartGame(String r, String b, boolean ai){
        this(r, b, ai, false);
    }

    public StartGame(String r, String b, boolean ai, boolean anim){
        redCharacter = r;
        blueCharacter = b;
        vsAI = ai;
        animEnabled = anim;

        imager = new GetImage();
        PolygonPanel polygonPanel = new PolygonPanel();
        PolygonSelector selector = new PolygonSelector(polygonPanel, this, vsAI, animEnabled);
        polygonPanel.addMouseListener(selector);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(polygonPanel, "Center");

        creatureStats.setBackground(Color.BLACK);
        creatureStats.setForeground(Color.ORANGE);
        creatureStats.setEditable(false);
        creatureStats.setFont(font2);
        add(leftPanel,"West");
        leftPanel.add(creatureStats);
        bluePlayer = new Portrait(false, blueCharacter);
        leftPanel.add(bluePlayer); // Character portrait for BLUE

        moveLog.setBackground(Color.BLACK);
        moveLog.setForeground(Color.ORANGE);
        moveLog.setEditable(false);
        moveLog.setLineWrap(true);
        moveLog.setFont(font);
        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(rightPanel, "East");
        redPlayer = new Portrait(true, redCharacter);
        rightPanel.add(redPlayer); // Character portrait for RED
        rightPanel.add(logScroll);

        restart.setFont(font);
        menu.setFont(font);
        leftBottom.setFont(font);
        leftBottom.setForeground(Color.CYAN);
        rightBottom.setFont(font);
        rightBottom.setForeground(Color.RED);

        bottomPanel.add(leftBottom);
        bottomPanel.add(menu);
        bottomPanel.add(restart);
        bottomPanel.add(rightBottom);
        bottomPanel.setBackground(Color.BLACK);

        add(bottomPanel, "South");
        restart.addActionListener(this);
        menu.addActionListener(this);

        setTitle("Dejarik");
        setIconImage(imager.getImage("DejarikTable"));
        setBounds(100,100,500,73);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent a){
        if(a.getSource() == restart){
            dispose();
            new StartGame(redCharacter, blueCharacter, vsAI, animEnabled);
        }
        if(a.getSource() == menu){
            dispose();
            new Menu();
        }
    }


}
