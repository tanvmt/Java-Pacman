import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends Window implements ActionListener {
    private Ghost ghost;
    private Level level;
    public Timer timer, frightenedGhostTimer;
    int[] dx, dy;
    private int ghost_dx, ghost_dy;
    private int count;

    Game() {

        setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        initVariables();
        initGhosts();
        initLevel();
    }

    void initVariables() {
        dx = new int[] { -1, 1, 0, 0 };
        dy = new int[] { 0, 0, -1, 1 };
        timer = new Timer(40, e -> this.updateGame());
    }

    void initGhosts() {
        ghost = new Ghost(getMAX_X() / 4 + 50, getMAX_Y() / 4 + 50, Color.RED, 6, 1, 0);
    }

    void initLevel() {
        level = new Level();
    }

    void drawMaze(Graphics2D g2D) {
        g2D.setColor(Color.BLUE);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRect(getMAX_X() / 2 - BLOCK_SIZE * 3, getMAX_Y() / 2 - BLOCK_SIZE * 3, BLOCK_SIZE * 6, BLOCK_SIZE * 6);
    }

    void drawGhosts(Graphics2D g2D) {
        // g2D.setColor(ghost.getColor());
        // g2D.fillOval(ghost.getX(), ghost.getY(), BLOCK_SIZE, BLOCK_SIZE);
        g2D.drawImage(ghost.defaultIcon, ghost.getX(), ghost.getY(), this);
    }

    void moveGhosts() {
        int direction = 0; //0: left, 1: right, 2: up, 3:down;
        ghost.setDirection(dx[direction], dy[direction]);
        ghost.move();
        direction++;
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        drawMaze(g2D);

        // ghost.update();
        drawGhosts(g2D);
    }

    public void updateGame() {

        moveGhosts();

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
