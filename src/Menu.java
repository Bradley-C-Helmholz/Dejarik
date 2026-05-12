import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {

    JPanel bottom = new JPanel(new GridLayout(5,1));
    JPanel bLabels = new JPanel(new GridLayout(1,2));
    JPanel bBox = new JPanel(new GridLayout(1,2));
    JPanel middle = new JPanel(new GridLayout(1,2));

    Portrait redChoice;
    Portrait blueChoice;

    JComboBox pickRed = new JComboBox();
    JComboBox pickBlue = new JComboBox();

    JLabel redL = new JLabel("Choose Red:", JLabel.CENTER);
    JLabel blueL = new JLabel("Choose Blue:", JLabel.CENTER);

    JButton start = new JButton("Start");
    JCheckBox vsAICheckbox = new JCheckBox("Play vs AI");
    JCheckBox animCheckbox = new JCheckBox("Combat Animation");
    //JButton rules = new JButton("Rules");

    public static void main(String[] args)
    {
        new Menu();
    }

    public Menu(){
        GetImage imager = new GetImage();
        setBackground(Color.WHITE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(middle, "Center");
        blueChoice = new Portrait(false, "Luke");
        middle.add(blueChoice);
        redChoice = new Portrait(true, "Luke");
        middle.add(redChoice);

        add(bottom, "South");
        bottom.add(bLabels);
        bottom.add(bBox);
        //bottom.add(rules);
        bottom.add(vsAICheckbox);
        bottom.add(animCheckbox);
        bottom.add(start);

        bLabels.add(blueL);
        bLabels.add(redL);

        bBox.add(pickBlue);
        pickBlue.addItem("Luke");
        pickBlue.addItem("C3PO");
        pickBlue.addItem("Chewbacca");
        bBox.add(pickRed);
        pickRed.addItem("Luke");
        pickRed.addItem("C3PO");
        pickRed.addItem("Chewbacca");
        //rules.addActionListener(this);
        start.addActionListener(this);
        pickRed.addActionListener(this);
        pickBlue.addActionListener(this);
        vsAICheckbox.addActionListener(this);

        setTitle("Dejarik Menu");
        setIconImage(imager.getImage("DejarikTable"));
        setBounds(100,100,650,450);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent a){
        if(a.getSource() == start){
            boolean playVsAI = vsAICheckbox.isSelected();
            int r = pickRed.getSelectedIndex();
            int b = pickBlue.getSelectedIndex();
            String rName = getName(r);
            String bName = getName(b);
            dispose();
            new StartGame(rName, bName, playVsAI, animCheckbox.isSelected());
        }
        if(a.getSource() == vsAICheckbox){
            boolean aiSelected = vsAICheckbox.isSelected();
            pickBlue.setEnabled(!aiSelected);
            blueL.setText(aiSelected ? "AI Player" : "Choose Blue:");
        }
        if(a.getSource() == pickRed){
            int r = pickRed.getSelectedIndex();
            String rName = getName(r);
            redChoice.pic = "Neutral" + rName;
            redChoice.repaint();
        }
        if(a.getSource() == pickBlue){
            int b = pickBlue.getSelectedIndex();
            String bName = getName(b);
            blueChoice.pic = "Neutral" + bName;
            blueChoice.repaint();
        }
    }

    private String getName(int num){
        switch(num){
            case 0: return "Luke";
            case 1: return "C3PO";
            case 2: return "Chewbacca";
        }
        return null;
    }

}
