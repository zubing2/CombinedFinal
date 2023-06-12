import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourGame extends JFrame {
    private final int ROWS = 6;
    private final int COLS = 7;
    private final int BUTTON_SIZE = 80;

    private JButton[][] boardButtons;
    private int currentPlayer;

    public ConnectFourGame() {
        setTitle("Connect Four");
        setSize(COLS * BUTTON_SIZE, (ROWS + 1) * BUTTON_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        currentPlayer = 1;

        // Create the game board
        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        boardButtons = new JButton[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                button.setEnabled(false); // Disable buttons initially
                button.addActionListener(new ButtonListener(col));
                boardPanel.add(button);
                boardButtons[row][col] = button;
            }
        }

        // Create the column buttons
        JPanel columnPanel = new JPanel(new GridLayout(1, COLS));
        for (int col = 0; col < COLS; col++) {
            JButton button = new JButton("Drop");
            button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            button.addActionListener(new ButtonListener(col));
            columnPanel.add(button);
        }

        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(columnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void enableColumnButtons(boolean enable) {
        Component[] components = getContentPane().getComponents();
        JPanel columnPanel = null;
        for (Component component : components) {
            if (component instanceof JPanel && ((JPanel) component).getLayout() instanceof GridLayout) {
                columnPanel = (JPanel) component;
                break;
            }
        }
        if (columnPanel != null) {
            Component[] buttons = columnPanel.getComponents();
            for (Component button : buttons) {
                if (button instanceof JButton) {
                    button.setEnabled(enable);
                }
            }
        }
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardButtons[row][col].setText("");
                boardButtons[row][col].setEnabled(false);
                boardButtons[row][col].setBackground(null);
            }
        }
        enableColumnButtons(true);
        currentPlayer = 1; // Winner goes first
    }

    private class ButtonListener implements ActionListener {
        private int column;

        public ButtonListener(int col) {
            column = col;
        }

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            // Find the lowest empty row in the selected column
            int row;
            for (row = ROWS - 1; row >= 0; row--) {
                if (boardButtons[row][column].getText().isEmpty()) {
                    break;
                }
            }

            // If the column is full, do nothing
            if (row < 0) {
                return;
            }

            // Update the button text and color
            boardButtons[row][column].setText(currentPlayer == 1 ? "1" : "2");
            boardButtons[row][column].setBackground(currentPlayer == 1 ? Color.RED : Color.YELLOW);

            // Disable the board button
            boardButtons[row][column].setEnabled(false);

            // Check for a win
            if (checkWin(row, column)) {
                String message = "Player " + currentPlayer + " wins!";
                int choice = JOptionPane.showOptionDialog(null, message, "Game Over", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Restart", "Main Menu"}, null);
                if (choice == 0) {
                    resetGame();
                } else {
                    dispose();
                    JFrame frame = new JFrame("Main Menu");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(500, 500);

                    MainMenu mainMenu = new MainMenu();
                    frame.add(mainMenu);

                    // Center the frame on the screen
                    frame.setLocationRelativeTo(null);

                    frame.setVisible(true);
                }
            } else {
                // Switch to the other player
                currentPlayer = currentPlayer == 1 ? 2 : 1;
            }
        }

        private boolean checkWin(int row, int col) {
            String target = currentPlayer == 1 ? "1" : "2";

            // Check horizontal
            int count = 0;
            for (int c = 0; c < COLS; c++) {
                if (boardButtons[row][c].getText().equals(target)) {
                    count++;
                    if (count == 4)
                        return true;
                } else {
                    count = 0;
                }
            }

            // Check vertical
            count = 0;
            for (int r = 0; r < ROWS; r++) {
                if (boardButtons[r][col].getText().equals(target)) {
                    count++;
                    if (count == 4)
                        return true;
                } else {
                    count = 0;
                }
            }

            // Check diagonal (down-right)
            count = 0;
            int startRow = row - Math.min(row, col);
            int startCol = col - Math.min(row, col);
            for (int i = 0; i < Math.min(ROWS - startRow, COLS - startCol); i++) {
                if (boardButtons[startRow + i][startCol + i].getText().equals(target)) {
                    count++;
                    if (count == 4)
                        return true;
                } else {
                    count = 0;
                }
            }

            // Check diagonal (up-right)
            count = 0;
            startRow = row + Math.min(ROWS - 1 - row, col);
            startCol = col - Math.min(ROWS - 1 - row, col);
            for (int i = 0; i < Math.min(startRow + 1, COLS - startCol); i++) {
                if (boardButtons[startRow - i][startCol + i].getText().equals(target)) {
                    count++;
                    if (count == 4)
                        return true;
                } else {
                    count = 0;
                }
            }

            return false;
        }
    }
}