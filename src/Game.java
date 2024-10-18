
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Game extends Window implements KeyListener {
    private JButton backButton;
    private Ghost[] ghost;
    private Level level;
    public Timer timer, frightenedGhostTimer;
    int[] dx, dy;
    private int ghost_x, ghost_y, ghost_dx, ghost_dy;
    private int count;
    
    private Pacman pacman;
    private int req_dx,req_dy;
    private int score = 0;
    private boolean inGame = true;
    private Image up, down, left, right, basic;
   
    Game() {

        setBackground(Color.BLACK);
        setLayout(null);
        loadImage();
        initVariables();
        initGhosts();
        initLevel();
        add(backButton);

        initPacman();
        System.out.println("11111111111111");
        setFocusable(true);
        addKeyListener(this);
        System.out.println("22222222222222");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                requestFocusInWindow();
            }
        });
    }
    
    private void returnToMenu() {
        timer.stop();  // Stop the game timer when returning to the menu
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "Menu");  // Switch back to the menu in MainPanel
    }
    
        public void loadImage(){
        up = new ImageIcon("src\\up.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        down = new ImageIcon("src\\down.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        left = new ImageIcon("src\\left.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        right = new ImageIcon("src\\right.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        basic = new ImageIcon("src\\pacman.png").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
    }
    
    
    void initPacman(){
        req_dx = 0;
        req_dy = 0;
        pacman = new Pacman((MAX_X - SCREEN_SIZE) / 2 + 5 + 7*BLOCK_SIZE, 5+4*BLOCK_SIZE,0,0);
    }
    
    void drawPacman(Graphics2D g2d){
        
        if (req_dx == -1) {
        	g2d.drawImage(left, pacman.getPacManX(), pacman.getPacManY(), this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, pacman.getPacManX(), pacman.getPacManY(), this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, pacman.getPacManX(), pacman.getPacManY() , this);
        } else if(req_dy == 1){
        	g2d.drawImage(down, pacman.getPacManX(), pacman.getPacManY() , this);
        }
        else{
            g2d.drawImage(basic, pacman.getPacManX(), pacman.getPacManY() , this);
        }
    }
    
    void movePacman(){
        int pos;
        short ch;
        int pacman_x = pacman.getPacManX();
        int pacman_y = pacman.getPacManY();
        int pacmand_x = pacman.getdPacmanX();
        int pacmand_y = pacman.getdPacmanY();
        int PACMAN_SPEED = pacman.getSpeed();
        
        if((pacman_x-4) % BLOCK_SIZE == 0 && (pacman_y-5) % BLOCK_SIZE ==0){
            pos = (pacman_x - 4 + N_BLOCKS * (pacman_y - 5) - (MAX_X - SCREEN_SIZE) / 2) / BLOCK_SIZE;
            ch = screenData[pos];
            
            if((ch&16) != 0){
                screenData[pos] = (short) (ch & 15);
                score++;
            }
            
            if(req_dx != 0 || req_dy !=0){
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacman.setdPacmanX(req_dx);
                    pacman.setdPacmanY(req_dy);
                }
            }
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacman.setdPacmanX(0);
                pacman.setdPacmanY(0);
            }
        }

          pacman.move();
        
    }
    

    void initVariables() {
        dx = new int[4];
        dy = new int[4];
        timer = new Timer(40, e -> this.updateGame());
        backButton = createButton("BACK", 0, 0,150,50, e -> returnToMenu());
    }

    void initGhosts() {// vị tris ghost
        ghost = new Ghost[4];
        ghost[0] = new Ghost((MAX_X - SCREEN_SIZE) / 2 + 5, 5, Color.RED, 4, 0, 0);
        ghost[1] = new Ghost((MAX_X - SCREEN_SIZE) / 2 + 5, 5 + BLOCK_SIZE * 30, Color.RED, 4, 0, 0);
        ghost[2] = new Ghost((MAX_X - SCREEN_SIZE) / 2 + 5 + BLOCK_SIZE * 30, 5, Color.RED, 4, 0, 0);
        ghost[3] = new Ghost((MAX_X - SCREEN_SIZE) / 2 + 5 + BLOCK_SIZE * 30, 5 + BLOCK_SIZE * 30, Color.RED, 4, 0, 0);
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
        for (int i = 0; i < 4; i++) {
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

    void resetGame() {
        score = 0;
        initVariables();
        initLevel();
        initGhosts();
        initPacman();

    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        level.drawMaze(g2D);

        drawPacman(g2D);
        drawGhosts(g2D);
    }

    public void updateGame() {
        movePacman();
        moveGhosts();
        repaint();
    }
    
    //controls
//    class TAdapter extends KeyAdapter {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//
//            int key = e.getKeyCode();
//            inGame = true;
//            if (inGame) {
//                System.out.println("333333333333333");
//                if (key == KeyEvent.VK_LEFT) {
//                    req_dx = -1;
//                    req_dy = 0;
//                } else if (key == KeyEvent.VK_RIGHT) {
//                    req_dx = 1;
//                    req_dy = 0;
//                } else if (key == KeyEvent.VK_UP) {
//                    req_dx = 0;
//                    req_dy = -1;
//                } else if (key == KeyEvent.VK_DOWN) {
//                    req_dx = 0;
//                    req_dy = 1;
//                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
//                    inGame = false;
//                } 
//            } else {
//                if (key == KeyEvent.VK_SPACE) {
//                    inGame = true;
//                }
//           }
//        }
//}


    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    System.out.println("Key Pressed: " + key); // In ra phím đã nhấn
    inGame = true;

    switch(key) {
        case KeyEvent.VK_LEFT:
            req_dx = -1;
            req_dy = 0;
            break;
        case KeyEvent.VK_RIGHT:
            req_dx = 1;
            req_dy = 0;
            break;
        case KeyEvent.VK_UP:
            System.out.println("upppppppppppp");
            req_dx = 0;
            req_dy = -1;
            System.out.println(req_dx +" va " + req_dy);
            break;
        case KeyEvent.VK_DOWN:
            req_dx = 0;
            req_dy = 1;
            break;
    }
}



    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
