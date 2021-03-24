import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Class that implements the logic of the game and develops it.
 */
public class SnakePanel extends JPanel implements ActionListener{
    static final int WIDTH = 800;
    static final int HEIGHT = 650;
    static final int UNIT = 25;
    static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT * UNIT);
    static final int DELAY = 125;
    int bodyparts = 6; //default size of snake.
    int score = 0;
    int appleW;
    int appleH;
    Random random;
    char dir = 'R'; // default direction is right as 'R'.
    boolean running;
    Timer timer;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    /**
     * Constructor.
     */
    public SnakePanel() {

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        random = new Random();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (dir != 'R') {
                            dir = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (dir != 'L') {
                            dir = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (dir != 'D') {
                            dir = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (dir != 'U') {
                            dir = 'D';
                        }
                        break;
                }
                moveSnake();
            }
        });
        start();

    }

    /**
     * Just starting game.
     */
    public void start(){
        apple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //doing the necassary checks while running.
        if(running) {
            moveSnake();
            ateApple();
            snakeAttack();
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    /**
     * Drawing rects that make up snake body parts of snake.
     * @param g
     */
    public void drawGame(Graphics g) {
        if (running) {
            g.setColor(Color.red); //giving colour for apple
            g.fillOval(appleW, appleH, UNIT, UNIT);
            for (int i = 0; i < bodyparts; i++) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT, UNIT);
            }
            g.setColor(Color.blue);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: "+score, (WIDTH- metrics1.stringWidth("Score: "+score))/2, g.getFont().getSize());
        }
        else
            lose(g);
    }

    /**
     * This method creates apple.
     */
    public void apple() {
        appleW = random.nextInt(WIDTH / UNIT) * UNIT;
        appleH = random.nextInt(HEIGHT / UNIT) * UNIT;
    }

    public void moveSnake() {
        for(int i= bodyparts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(dir) {
            case 'U':
                y[0] = y[0] - UNIT;
                break;
            case 'D':
                y[0] = y[0] + UNIT;
                break;
            case 'L':
                x[0] = x[0] - UNIT;
                break;
            case 'R':
                x[0] = x[0] + UNIT;
                break;
        }
    }

    /**
     * Method that checks if snake go ahead to its body or gets over limits of screen.
     */
    public void snakeAttack(){
        for(int i=bodyparts;i>0;i--) {
            if((x[0]==x[i] && y[0]==y[i]))
                running = false;
        }
        if((x[0]<0))
            running = false;
        if(x[0]>WIDTH)
            running = false;
        if(y[0]<0)
            running = false;
        if(y[0]>HEIGHT)
            running = false;
        if(!running)
            timer.stop();
    }

    /**
     * This method checks if snake ate apple and increases bodyparts and score.
     */
    public void ateApple() {
        if(x[0]==appleW && y[0]==appleH) {
            bodyparts++;
            score++;
            apple();
        }
    }

    /**
     * This method exists to be called when player loses.
     * @param g
     */
    public void lose(Graphics g) {
        g.setColor(Color.blue);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics num1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+score, (WIDTH- num1.stringWidth("Score: "+score))/2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics num2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - num2.stringWidth("Game Over"))/2, HEIGHT/2);

    }
}
