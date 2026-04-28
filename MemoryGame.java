import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Sets up all instance variables and calls upon methods to help game run 
 * Also sets up entire game frame, including all listeners and swing components 
 */
public class MemoryGame {
    private CardPanel[] cards = new CardPanel[12];
    private String[] type = {"circle", "square", "triangle", "circle", "triangle", "square", "square", "triangle", "circle", "circle", "square", "triangle"};
    private JLabel moveCounter;
    private JLabel matchCounter;
    private JButton resetButton;
    private int moves = 0;
    private int matches = 0;
    private int currentCard = -1; 

    public MemoryGame() {     
        // sets the frame of the game and the basic layout 
        JFrame frame = new JFrame("Memory Card Game");
        frame.setSize(700,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //  One Swing Component (JPanel) --> a panel to hold the number of moves and number of matched cards 
        JPanel labelPanel = new JPanel();
        moveCounter = new JLabel("Moves: 0");
        matchCounter = new JLabel("Matched 0/6");

        // designs the label panel and makes the game visually appealing
        labelPanel.setBackground(Color.DARK_GRAY);
        moveCounter.setForeground(Color.WHITE);
        matchCounter.setForeground(Color.WHITE);
        moveCounter.setFont(new Font("Arial", Font.BOLD, 16));
        matchCounter.setFont(new Font("Arial", Font.BOLD, 16));

        // One Swing Component (JButton) --> a reset button so users can play again
        resetButton = new JButton("Reset"); 
        resetButton.addActionListener(new ActionListener() {   
            @Override 
            public void actionPerformed(ActionEvent e) {
                shuffleCards(type);

                for (int i = 0; i < 12; i++) {
                    cards[i].setShape(type[i]);
                    cards[i].unflip();
                    cards[i].setEnabled(true);
                }
                moves = 0;
                matches = 0;
                currentCard = -1;
                moveCounter.setText("Moves: 0");
                matchCounter.setText("Matched 0/6"); 
            }
        });
        // styles the JButton to make it more visually appealing
        resetButton.setBackground(Color.GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBorderPainted(false);
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));

        // adds move counter, match counter and the reset button onto the label panel to make it more game-like
        labelPanel.add(moveCounter);
        labelPanel.add(matchCounter);
        labelPanel.add(resetButton);
        frame.add(labelPanel, BorderLayout.NORTH);

        // Another JPanel for the Cards (main area of user interaction)
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(0x35654d));
        cardPanel.setLayout(new GridLayout(3,4,30,20));

        // calls the shuffle function to change the order of the types of cards (shuffle function is down below)
        shuffleCards(type);

        // adds the cards onto the panel and calls the flipCard() function when an action is performed 
        for (int i = 0; i < 12; i++) {
            cards[i] = new CardPanel(type[i]);
            cards[i].setPreferredSize(new Dimension(60,60));

            int cardIndex = i;
            cards[i].addMouseListener(new MouseAdapter() { // whenever the user clicks the mouse, it will call upon the flipCard() method
                @Override   
                public void mouseClicked(MouseEvent e) {
                    flipCard(cardIndex);
                }
            });
                
            cardPanel.add(cards[i]);
        }

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /** 
     * THE MAIN LOGIC OF THE ENTIRE GAME
     * 
     * This method updates and tracks the moves and matches that the user finds. It also calls upon my paintComponent method to "flip" the 
     * card in the panel 
     */
    public void flipCard(int k) {
        if (currentCard == k) { return; } // ensures that you cannot click the same card twice
        else if (currentCard == -1 ) { // if it is still -1, that means that the current card is the FIRST card that was clicked
            currentCard = k;
            cards[k].flip();
        }
        else {  // runs if there is a card to compare; now we will begin checking for matches
            moves++;
            moveCounter.setText("Moves: " + moves);

            int first = currentCard;
            currentCard = -1; 
            cards[k].flip();

            if (type[first].equals(type[k])) {
                matches++;
                matchCounter.setText("Matched " + matches + "/6");
                cards[first].setEnabled(false);
                cards[k].setEnabled(false);

                if (matches == 6) { 
                    JOptionPane.showMessageDialog(null, "Congrats! You found all the matches in " + moves + " moves!");
                    System.exit(0);
                }
            }
            else { // runs if it is NOT a match
                cards[first].setEnabled(false);  // sets so the cards cannot be clicked again during the timer
                cards[k].setEnabled(false);
                Timer timer = new Timer(500, e -> {
                    cards[first].unflip();
                    cards[k].unflip();
                    cards[first].setEnabled(true);
                    cards[k].setEnabled(true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    /**
     * The Shuffle Function:
     * Randomizes the cards so the layout is always different each time a user plays or resets
     */
    public void shuffleCards(String[] type) {
        List<String> typeList = Arrays.asList(type);
        Collections.shuffle(typeList);
        typeList.toArray(type);
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}
    