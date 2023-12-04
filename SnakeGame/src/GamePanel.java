import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_HEIGHT = 600;
    static final int SCREEN_WIDTH = 600;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeBody = 6;
    int appleX;
    int appleY;
    int applesEaten;
    char direction = 'R';
    boolean runningMain;
    boolean running;
    Timer timer;
    Random random;

    JButton buttonPlay, menuButton, playAgainButton;


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(1,50,32));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        mainMenu();
    }
    public void mainMenu() {
        runningMain = true;
        this.setLayout(null);


        JLabel label = new JLabel("Snake Game");

        buttonPlay = new JButton();
        buttonPlay.setText("Play");
        buttonPlay.setFont(new Font("Ink Free", Font.BOLD, 45));

        // Calculate the middle of the screen
        int buttonWidth = 150;
        int buttonHeight = 50;

        int x = (SCREEN_WIDTH - buttonWidth) / 2;
        int y = (SCREEN_HEIGHT - buttonHeight) / 2;

        buttonPlay.setBounds(x, y, buttonWidth, buttonHeight);

        buttonPlay.setForeground(Color.WHITE);
        buttonPlay.setFocusable(false);
        buttonPlay.setContentAreaFilled(false);
        buttonPlay.setBorderPainted(false);
        this.add(buttonPlay);

        buttonPlay.addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent avt) {
                buttonPlay.setFont(new Font("Ink Free", Font.BOLD, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent avt) {
                buttonPlay.setFont(new Font("Ink Free", Font.BOLD, 40));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startGame();
                buttonPlay.setVisible(false);
            }
        });
    }

    public void startGame() {
        running = true;
        newApple();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeBody; i++) {
                if (i == 0) {
                    g.setColor(new Color(207, 159, 255));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(244, 167, 255));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("InK Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }

    }
    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() {
        for(int i = snakeBody; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            snakeBody++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {

        for(int i = snakeBody; i > 0; i--) {
            //if head hits body
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                runningMain = false;
                running = false;
            }

        }
        //if head hits left border
        if(x[0] < 0) {
            running = false;
            runningMain = false;
        }
        //if head hits right border
        if(x[0] > SCREEN_WIDTH) {
            runningMain = false;
            running = false;
        }
        //if head hits top border
        if(y[0] < 0) {
            running = false;
            runningMain = false;
        }
        //if head hits bottom border
        if(y[0] > SCREEN_HEIGHT) {
            runningMain = false;
            running = false;
        }
    }

    public void gameOver(Graphics g) {

        if(!runningMain && !running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("InK Free", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

            g.setColor(Color.WHITE);
            g.setFont(new Font("InK Free", Font.BOLD, 30));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) - 200, SCREEN_HEIGHT - 250);

            menuButton = new JButton();
            menuButton.setText("Go back to Main Menu");
            menuButton.setFont(new Font("Ink Free", Font.BOLD, 30));

            int buttonWidth = 150;
            int buttonHeight = 50;

            int x = (SCREEN_WIDTH - buttonWidth) / 2;
            int y = (SCREEN_HEIGHT - buttonHeight) / 2;

            menuButton.setBounds(x, y, buttonWidth, buttonHeight);

            menuButton.setForeground(Color.WHITE);
            menuButton.setFocusable(false);
            menuButton.setContentAreaFilled(false);
            menuButton.setBorderPainted(false);
            this.add(menuButton);



        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(running) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
            }
        }
    }
}