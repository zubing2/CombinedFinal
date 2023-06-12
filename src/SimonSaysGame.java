import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimonSaysGame extends JFrame {
    private List<Integer> sequence;
    private int currentStep;
    private int playerStep;
    private int score;
    private int speedInterval;

    private JButton[] buttons;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private JButton startButton;
    private JButton restartButton;
    private JButton menuButton;

    private Color[] buttonColors = {
            Color.red,
            Color.green,
            Color.blue,
            Color.yellow
    };

    private int[] delayTimes = {
            500,
            400,
            300,
            200,
            100,
            50
    };

    public SimonSaysGame() {
        setTitle("SimonSays Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2));
        buttons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setEnabled(false);
            buttons[i].setBackground(buttonColors[i]);
            buttons[i].addActionListener(new ButtonClickListener());
            centerPanel.add(buttons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        messageLabel = new JLabel("Click Start to Begin");
        southPanel.add(messageLabel);

        scoreLabel = new JLabel("Score: 0");
        southPanel.add(scoreLabel);

        startButton = new JButton("Start");
        startButton.addActionListener(new ButtonClickListener());
        southPanel.add(startButton);

        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ButtonClickListener());
        restartButton.setVisible(false); // Initially hidden
        southPanel.add(restartButton);

        menuButton = new JButton("Main Menu");
        menuButton.addActionListener(new ButtonClickListener());
        menuButton.setVisible(false); // Initially hidden
        southPanel.add(menuButton);

        add(southPanel, BorderLayout.SOUTH);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        sequence = new ArrayList<>();
        currentStep = 0;
        playerStep = 0;
        score = 0;
        speedInterval = 5; // Interval to speed up the sequences
        messageLabel.setText("Watch the sequence");
        updateScore();

        startButton.setVisible(false);
        restartButton.setVisible(false);
        menuButton.setVisible(false);

        generateNextSequence();
        playSequence();
    }

    private void generateNextSequence() {
        Random random = new Random();
        int nextButton = random.nextInt(4);
        sequence.add(nextButton);
    }

    private void playSequence() {
        Timer timer = new Timer(delayTimes[Math.min(score / speedInterval, delayTimes.length - 1)], new ActionListener() {
            private int currentIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                highlightButton(sequence.get(currentIndex));
                currentIndex++;

                if (currentIndex >= sequence.size()) {
                    ((Timer) e.getSource()).stop();
                    messageLabel.setText("Your Turn");
                    enableButtons();
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void highlightButton(int buttonIndex) {
        buttons[buttonIndex].setBackground(Color.white);

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons[buttonIndex].setBackground(buttonColors[buttonIndex]);
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void enableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void checkPlayerStep(int buttonIndex) {
        if (buttonIndex == sequence.get(playerStep)) {
            playerStep++;

            if (playerStep >= sequence.size()) {
                playerStep = 0;
                disableButtons();
                messageLabel.setText("Correct! Next sequence coming up...");
                generateNextSequence();
                updateScore();

                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playSequence();
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        } else {
            messageLabel.setText("Wrong! Game Over");
            disableButtons();
            showRestartMenuButtons();
        }
    }

    private void showRestartMenuButtons() {
        restartButton.setVisible(true);
        menuButton.setVisible(true);
    }

    private void updateScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                startButton.setEnabled(false);
                startGame();
            } else if (e.getSource() == restartButton) {
                restartGame();
            } else if (e.getSource() == menuButton) {
                returnToMainMenu();
            } else {
                JButton clickedButton = (JButton) e.getSource();
                for (int i = 0; i < 4; i++) {
                    if (clickedButton == buttons[i]) {
                        checkPlayerStep(i);
                        break;
                    }
                }
            }
        }
    }

    private void restartGame() {
        messageLabel.setText("Click Start to Begin");
        startButton.setEnabled(true);
        startButton.setVisible(true);
        restartButton.setVisible(false);
        menuButton.setVisible(false);
        score = 0;
        clearSequence();
    }

    private void returnToMainMenu() {
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

    private void clearSequence() {
        sequence.clear();
        currentStep = 0;
        playerStep = 0;
    }
}