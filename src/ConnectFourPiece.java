import javax.swing.*;
import java.awt.*;

public class ConnectFourPiece extends JButton {
    private boolean isRed;

    public ConnectFourPiece() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setPreferredSize(new Dimension(80, 80));
    }

    public void setPlayerRed(boolean isRed) {
        this.isRed = isRed;
        setBackground(isRed ? Color.RED : Color.YELLOW);
    }

    public void reset() {
        isRed = false;
        setBackground(Color.WHITE);
        setEnabled(true);
    }
}