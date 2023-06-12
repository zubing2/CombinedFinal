import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {
    private JButton simonSaysButton;
    private JButton connectFourButton;
    private JButton ticTacToeButton;

    public MainMenu() {
        setLayout(new BorderLayout());
        setBackground(Color.ORANGE);

        JLabel titleLabel = new JLabel("Welcome to the Totally Real Casino");
        titleLabel.setFont(new Font("Prestige", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.ORANGE);
        add(titleLabel, BorderLayout.NORTH);


        JLabel messageLabel = new JLabel("Please select the game you'd like to play");
        messageLabel.setFont(new Font("Prestige", Font.PLAIN, 20));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBackground(Color.ORANGE);
        add(messageLabel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(Color.ORANGE);

        simonSaysButton = new JButton("Simon Says");
        simonSaysButton.addActionListener(this);
        simonSaysButton.setPreferredSize(new Dimension(500, 75));
        simonSaysButton.setForeground(Color.WHITE);
        simonSaysButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(simonSaysButton);


        connectFourButton = new JButton("Connect Four");
        connectFourButton.addActionListener(this);
        connectFourButton.setPreferredSize(new Dimension(500, 75));
        connectFourButton.setForeground(Color.WHITE);
        connectFourButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(connectFourButton);

        ticTacToeButton = new JButton("Tic Tac Toe");
        ticTacToeButton.addActionListener(this);
        ticTacToeButton.setPreferredSize(new Dimension(500, 75));
        ticTacToeButton.setForeground(Color.WHITE);
        ticTacToeButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(ticTacToeButton);


        add(buttonPanel, BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == simonSaysButton) {
            SwingUtilities.invokeLater(SimonSaysGame::new);
        } else if (e.getSource() == connectFourButton) {
            SwingUtilities.invokeLater(ConnectFourGame::new);
        } else if (e.getSource() == ticTacToeButton) {
            SwingUtilities.invokeLater(TicTacToeGame::new);
        }
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setVisible(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        MainMenu mainMenu = new MainMenu();
        frame.add(mainMenu);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}