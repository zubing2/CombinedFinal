import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGame extends JFrame {
    private JButton[][] board;
    private boolean player1Turn;
    private boolean gameOver;
    private boolean againstAI;

    public TicTacToeGame(boolean againstAI) {
        this.againstAI = againstAI;
        board = new JButton[3][3];
        player1Turn = true;
        gameOver = false;

        JPanel panel = new JPanel(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(100, 100));
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                button.addActionListener(new ButtonClickListener(row, col));
                board[row][col] = button;
                panel.add(button);
            }
        }

        add(panel);
        setTitle("Tic Tac Toe");
        setSize(320, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (!gameOver && button.getText().isEmpty()) {
                if (player1Turn) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }
                button.setEnabled(false);
                checkGameOver();
                if (!gameOver && againstAI) {
                    makeAIMove();
                }
            }
        }
    }

    private void checkGameOver() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].getText().isEmpty() &&
                    board[i][0].getText().equals(board[i][1].getText()) &&
                    board[i][0].getText().equals(board[i][2].getText())) {
                gameOver = true;
                highlightWinnerCells(i, 0, i, 1, i, 2);
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!board[0][i].getText().isEmpty() &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[0][i].getText().equals(board[2][i].getText())) {
                gameOver = true;
                highlightWinnerCells(0, i, 1, i, 2, i);
                break;
            }
        }

        if (!board[0][0].getText().isEmpty() &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[0][0].getText().equals(board[2][2].getText())) {
            gameOver = true;
            highlightWinnerCells(0, 0, 1, 1, 2, 2);
        }

        if (!board[0][2].getText().isEmpty() &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[0][2].getText().equals(board[2][0].getText())) {
            gameOver = true;
            highlightWinnerCells(0, 2, 1, 1, 2, 0);
        }

        if (gameOver) {
            String winner = player1Turn ? "Player 1" : (againstAI ? "AI" : "Player 2");
            JOptionPane.showMessageDialog(this, winner + " wins!");
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void highlightWinnerCells(int row1, int col1, int row2, int col2, int row3, int col3) {
        board[row1][col1].setBackground(Color.GREEN);
        board[row2][col2].setBackground(Color.GREEN);
        board[row3][col3].setBackground(Color.GREEN);
    }

    private void resetGame() {
        gameOver = false;
        player1Turn = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText("");
                board[row][col].setEnabled(true);
                board[row][col].setBackground(null);
            }
        }
    }

    private void makeAIMove() {
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!board[row][col].getText().isEmpty());
        board[row][col].setText("O");
        board[row][col].setEnabled(false);
        checkGameOver();
    }
}