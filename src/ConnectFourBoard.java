import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourBoard extends JPanel implements ActionListener {
    private ConnectFourPiece[][] pieces;
    private int[][] board;
    private boolean playerRed;
    private boolean gameEnded;
    private ConnectFourGame game;

    public ConnectFourBoard(ConnectFourGame game) {
        setLayout(new GridLayout(6, 7));

        pieces = new ConnectFourPiece[6][7];
        board = new int[6][7];
        playerRed = true;
        gameEnded = false;
        this.game = game;

        initializePieces();
    }

    private void initializePieces() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                pieces[i][j] = new ConnectFourPiece();
                pieces[i][j].addActionListener(this);
                add(pieces[i][j]);
            }
        }
    }

    public void resetBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                pieces[i][j].reset();
                board[i][j] = 0;
            }
        }
        playerRed = true;
        gameEnded = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) {
            return;
        }

        ConnectFourPiece piece = (ConnectFourPiece) e.getSource();
        int row = getPieceRow(piece);
        int col = getPieceColumn(piece);

        if (board[row][col] == 0) {
            int dropRow = dropPiece(col);
            pieces[dropRow][col].setPlayerRed(playerRed);

            if (checkForWin(dropRow, col)) {
                String winner = playerRed ? "Red" : "Yellow";
                game.endGame(false, winner);
                gameEnded = true;
            } else if (isBoardFull()) {
                game.endGame(true, "");
                gameEnded = true;
            } else {
                playerRed = !playerRed;
            }
        }
    }

    private int getPieceRow(ConnectFourPiece piece) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (pieces[i][j] == piece) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getPieceColumn(ConnectFourPiece piece) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (pieces[i][j] == piece) {
                    return j;
                }
            }
        }
        return -1;
    }

    private int dropPiece(int col) {
        int row = 5;

        while (row >= 0 && board[row][col] != 0) {
            row--;
        }

        if (row >= 0) {
            board[row][col] = playerRed ? 1 : 2;
        }

        return row;
    }

    private boolean checkForWin(int row, int col) {
        int player = playerRed ? 1 : 2;

        // Check horizontal
        int count = 0;
        for (int c = Math.max(0, col - 3); c <= Math.min(6, col + 3); c++) {
            if (board[row][c] == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check vertical
        count = 0;
        for (int r = Math.max(0, row - 3); r <= Math.min(5, row + 3); r++) {
            if (board[r][col] == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal
        count = 0;
        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        for (int i = 0; i < 6; i++) {
            int r = startRow + i;
            int c = startCol + i;
            if (r < 0 || r >= 6 || c < 0 || c >= 7) {
                break;
            }
            if (board[r][c] == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        count = 0;
        startRow = row + Math.min(5 - row, col);
        startCol = col - Math.min(5 - row, col);
        for (int i = 0; i < 6; i++) {
            int r = startRow - i;
            int c = startCol + i;
            if (r < 0 || r >= 6 || c < 0 || c >= 7) {
                break;
            }
            if (board[r][c] == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}