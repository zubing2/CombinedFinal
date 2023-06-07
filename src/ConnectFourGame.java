import javax.swing.*;
import java.awt.*;

public class ConnectFourGame extends JFrame {

    private ConnectFourBoard board;

    public ConnectFourGame() {
        setTitle("Connect Four");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 700);
        setLayout(new BorderLayout());

        board = new ConnectFourBoard(this);
        add(board, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConnectFourGame::new);
    }

    public void endGame(boolean isDraw, String winner) {
        String message;
        if (isDraw) {
            message = "It's a tie!";
        } else {
            message = winner + " wins!";
        }
        JOptionPane.showMessageDialog(this, message);
        board.resetBoard();
        System.exit(0);
    }
}