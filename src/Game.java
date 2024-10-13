import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends Window implements ActionListener {
    private Ghost[] ghost;
    private Level level;
    public Timer timer, frightenedGhostTimer;
    int[] dx, dy;
    private int ghost_x, ghost_y, ghost_dx, ghost_dy;
    private int count;

    Game() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        initVariables();
        initGhosts();
        initLevel();
    }

    void initVariables() {
        dx = new int[4];
        dy = new int[4];
        timer = new Timer(40, e -> this.updateGame());
    }

    void initGhosts() {
        ghost = new Ghost[4];
        ghost[0] = new Ghost((MAX_X - SCREEN_SIZE) / 2 + 5, 5, Color.RED, 4, 0, 0);
        ghost[1] = new Ghost((MAX_X-SCREEN_SIZE)/2+5, 5+BLOCK_SIZE*30, Color.RED, 4, 0, 0);
        ghost[2] = new Ghost((MAX_X-SCREEN_SIZE)/2+5 + BLOCK_SIZE*30, 5, Color.RED, 4, 0, 0);
        ghost[3] = new Ghost((MAX_X-SCREEN_SIZE)/2+5 + BLOCK_SIZE*30, 5+BLOCK_SIZE*30, Color.RED, 4, 0, 0);
    }

    void initLevel() {
        level = new Level();
        screenData = level.screenData;
    }

    void drawGhosts(Graphics2D g2D) {
        for (int i = 0; i < 4; i++) {
            g2D.drawImage(ghost[i].defaultIcon, ghost[i].getX(), ghost[i].getY(), this);
        }
        
    }

    void moveGhosts() {
        // int direction = 1; //0: left, 1: right, 2: up, 3:down;
        // ghost.setDirection(dx[direction], dy[direction]);
        // Random rand = 
        // int direction = Math.random()
        for (int i = 0; i < 4;i++){
            ghost_dx = ghost[i].getDx();
            ghost_dy = ghost[i].getDy();
            ghost_x = ghost[i].getX();
            ghost_y = ghost[i].getY();
            int pos = 0;
            int count = 0;
            
            if ((ghost_x - 4) % BLOCK_SIZE == 0 && (ghost_y - 5) % BLOCK_SIZE == 0) {
                pos = (ghost_x - 4 + N_BLOCKS * (ghost_y - 5) - (MAX_X - SCREEN_SIZE) / 2) / BLOCK_SIZE;

                if ((screenData[pos] & 1) == 0 && ghost_dx != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {
                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx = 0;
                        ghost_dy = 0;
                    } else {
                        ghost_dx = -ghost_dx;
                        ghost_dy = -ghost_dy;
                    }
                } else {
                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx = dx[count];
                    ghost_dy = dy[count];
                    ghost[i].setDirection(ghost_dx, ghost_dy);

                }

            }
            ghost[i].move();
        }
        

    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        level.drawMaze(g2D);

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
