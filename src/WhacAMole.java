import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame(" Mario: Whac-A-Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currentMoleTile;
    JButton currentPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score;

    // Create the constructuor
    public WhacAMole() {
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        // boardPanel.setBackground(Color.BLACK);
        frame.add(boardPanel);

        // plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image planting = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(planting.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image moleImage = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        score = 0;

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currentMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));

                    } else if (tile == currentPlantTile) {

                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                    }
                }

            });

        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove the mole from the current tile if it exists
                if (currentMoleTile != null) {
                    currentMoleTile.setIcon(null);
                    currentMoleTile = null;
                }
                // Randomly select a tile from the board
                int num = random.nextInt(9);
                JButton tile = board[num];

                // If the current tile is the same as the new tile, skip setting it
                if (currentPlantTile == tile)
                    return;

                // set the tile to mole

                currentMoleTile = tile;
                currentMoleTile.setIcon(moleIcon);
            }

        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPlantTile != null) {
                    currentPlantTile.setIcon(null);
                    currentPlantTile = null;
                }
                int num = random.nextInt(9);
                JButton tile = board[num];

                if (currentMoleTile == tile)
                    return;

                currentPlantTile = tile;
                currentPlantTile.setIcon(plantIcon);
            }

        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);

    }

}
