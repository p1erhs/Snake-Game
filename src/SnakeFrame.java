import javax.swing.*;

/**
 * Class that implements the basic frame of Snake game.
 */
public class SnakeFrame extends JFrame {
    /**
     * Constructor.
     */
    public SnakeFrame() {
        this.add(new SnakePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
