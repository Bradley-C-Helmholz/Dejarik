import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RulesScreen extends JFrame {

    public RulesScreen() {
        setTitle("Dejarik - Rules");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20));

        // Title
        JLabel title = new JLabel("HOW TO PLAY DEJARIK", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(200, 160, 50));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Rules content
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));
        rulesPanel.setBackground(new Color(20, 20, 20));
        rulesPanel.setBorder(new EmptyBorder(10, 40, 20, 40));

        String[][] sections = {
            {"The Board",
                "Dejarik is played on a circular board with 25 spaces:\n" +
                "  •  12 spaces in the outer ring\n" +
                "  •  12 spaces in the inner ring\n" +
                "  •  1 center space\n" +
                "Pieces can move between rings and around each ring depending on their movement stat."},
            {"Setup",
                "Each player is randomly assigned 4 creatures from a pool of 8.\n" +
                "Red pieces start on the right side of the board.\n" +
                "Blue pieces start on the left side of the board."},
            {"Taking a Turn",
                "Each turn has two phases, in order:\n" +
                "  1.  Move — Select one of your pieces and move it to a highlighted green space.\n" +
                "  2.  Attack — Select one of your pieces adjacent to an enemy and attack it.\n" +
                "Both phases are mandatory if a valid move or attack is available."},
            {"Movement",
                "When you select a piece, valid move destinations are highlighted in green.\n" +
                "How far a piece can move depends on its Movement stat.\n" +
                "Pieces cannot move through or onto spaces occupied by other pieces."},
            {"Combat",
                "When you attack, both pieces roll dice based on their stats:\n" +
                "  •  Attacker rolls: random(Attack) + 1\n" +
                "  •  Defender rolls: random(Defense) + 1\n" +
                "If the attacker’s roll is higher, the defender is destroyed.\n" +
                "If the rolls are tied or the defender wins, the attacker is destroyed."},
            {"Winning",
                "Eliminate all of your opponent’s pieces to win the game.\n" +
                "Your character portrait will react to the outcome of each battle!"}
        };

        for (String[] section : sections) {
            rulesPanel.add(makeSectionHeader(section[0]));
            rulesPanel.add(makeSectionBody(section[1]));
            rulesPanel.add(Box.createVerticalStrut(12));
        }

        JScrollPane scrollPane = new JScrollPane(rulesPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(20, 20, 20));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Close button
        JButton close = new JButton("Close");
        close.setFont(new Font("SansSerif", Font.BOLD, 14));
        close.setBackground(new Color(200, 160, 50));
        close.setForeground(Color.BLACK);
        close.setFocusPainted(false);
        close.setBorder(new EmptyBorder(8, 24, 8, 24));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 20, 20));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 16, 0));
        buttonPanel.add(close);
        add(buttonPanel, BorderLayout.SOUTH);

        setBounds(150, 100, 600, 520);
        setResizable(false);
        setVisible(true);
    }

    private JLabel makeSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD, 17));
        label.setForeground(new Color(200, 160, 50));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(4, 0, 2, 0));
        return label;
    }

    private JTextArea makeSectionBody(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setForeground(new Color(210, 210, 210));
        area.setBackground(new Color(20, 20, 20));
        area.setEditable(false);
        area.setFocusable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        return area;
    }
}
