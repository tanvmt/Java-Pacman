
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    private int lives = 3;

    private String namePlayer;

    private JLabel scoreLabel, showScore, livesLabel, move, pause, resume, levelLabel, showLevel;
    private Image arrowKeys, spaceKey, escKey, heart;
    MyFrame tmpMyFrame;
   
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
        // cl.show(getParent(), "Highscores");

        cl.show(getParent(), "Menu");  // Switch back to the menu in MainPanel
    }
    public void newMenu(){
        this.removeAll();
        tmpMyFrame = new MyFrame();
    }
    
    public void loadImage() {
        up = new ImageIcon("src\\Image\\up.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        down = new ImageIcon("src\\Image\\down.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        left = new ImageIcon("src\\Image\\left.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        right = new ImageIcon("src\\Image\\right.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        basic = new ImageIcon("src\\Image\\pacman.png").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);

        arrowKeys = new ImageIcon("src\\Image\\Arrow_keys.png").getImage();
        arrowKeys = arrowKeys.getScaledInstance(arrowKeys.getWidth(this) * 2, arrowKeys.getHeight(this) * 2,
                Image.SCALE_DEFAULT);

        escKey = new ImageIcon("src\\Image\\Esc_key.png").getImage();
        escKey = escKey.getScaledInstance(escKey.getWidth(this) * 2, escKey.getHeight(this) * 2,
                Image.SCALE_DEFAULT);

        spaceKey = new ImageIcon("src\\Image\\Space_key.png").getImage();
        spaceKey = spaceKey.getScaledInstance(spaceKey.getWidth(this) * 2, spaceKey.getHeight(this) * 2,
                Image.SCALE_DEFAULT);

        heart = new ImageIcon("src\\Image\\1_heart.png").getImage();
        heart = heart.getScaledInstance(heart.getWidth(this) * 3, heart.getHeight(this) * 3, Image.SCALE_DEFAULT);
    }
    
    public void drawBackground(Graphics2D g2D) {
        
        add(move);
        g2D.drawImage(arrowKeys, 75 + (150 - arrowKeys.getWidth(this)) / 2, move.getY() + 50, this);
        
        add(pause);
        g2D.drawImage(escKey, 75 + (150 - escKey.getWidth(this)) / 2, pause.getY() + 50, this);
        
        add(resume);
        g2D.drawImage(spaceKey, 75 + (150 - spaceKey.getWidth(this)) / 2, resume.getY() + 50,
                this);
        
        add(levelLabel);
        
        add(showLevel);
        
        add(scoreLabel);
        add(showScore);
        add(livesLabel);
        for (int i = 0; i < lives; i++) {
            g2D.drawImage(heart, MAX_X - 208 + i * heart.getWidth(this), livesLabel.getY() + 50, this);
        }
        
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
                showScore.setText(String.valueOf(score));
            }
            
            if(req_dx != 0 || req_dy !=0){
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacman.setdPacmanX(req_dx);
                    pacman.setdPacmanY(req_dy);
                }
                else{
                    pacman.setdPacmanX(0);
                    pacman.setdPacmanY(0);
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

        backButton = createButton("BACK", 0, 0, 150, 50, e -> {
            WriterScore();
            returnToMenu();});

        move = createLabel("MOVE", 75, 150);
        pause = createLabel("PAUSE", 75, move.getY() + move.getHeight() + 150);
        resume = createLabel("RESUME", 75, move.getY() + move.getHeight() + 300);

        levelLabel = createLabel("LEVEL", MAX_X - 220, 150);
        showLevel = createLabel("1", MAX_X - 220, 200);
        scoreLabel = createLabel("SCORE", MAX_X - 220, 300);
        showScore = createLabel(--score + "", MAX_X - 220, 350);
        livesLabel = createLabel("LIVES", MAX_X - 220, 450);

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
            if (pacman.getPacManX() > (ghost_x - 14) && pacman.getPacManX() < (ghost_x + 14)
                    && pacman.getPacManY() > (ghost_y - 14) && pacman.getPacManY() < (ghost_y + 14)
                    ) {
                this.lives -= 1;        
                timer.stop();
                checkLives();
                initPacman();
            }
        }

    }

    

    void setNamePlayer(){

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", Color.WHITE);
        // UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("Label.background", Color.WHITE);

        JLabel inputFrame = new JLabel("PLAYER'S NAME");
        inputFrame.setFont(new Font("Arial", Font.BOLD, 18));
        inputFrame.setForeground(Color.RED);  // Đổi màu chữ

        JTextField textNamePlayer = new JTextField(20);

        JPanel inputFrameBK = new JPanel();
        inputFrameBK.setBackground(Color.WHITE);
        inputFrameBK.add(inputFrame);
        inputFrameBK.add(textNamePlayer);
        inputFrameBK.setBorder(new LineBorder(Color.WHITE, 10)); 

        int result = JOptionPane.showConfirmDialog(null, inputFrameBK, 
            "INPUT NAME", JOptionPane.OK_CANCEL_OPTION);
        
            if (result == JOptionPane.OK_OPTION) {
                this.namePlayer = textNamePlayer.getText();
            } else {
                this.namePlayer = "";
            }
    }
    void setNamePlayer(String name){
        this.namePlayer = name; 
    }
    String getNamePlayer(){
        return this.namePlayer;
    }

    int getScore(){
        return this.score;
    }

    void checkLives(){
        if(lives == 0){
            announcement();
            System.out.println(namePlayer+ " " +score);
            WriterScore();
            returnToMenu();
            // newMenu();
        }
    }

    void announcement() {
        JLabel condition = new JLabel("You Lose");
        JLabel condition2 = new JLabel("Score: " + score);  // Hiển thị điểm số
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Đặt layout cho panel
        panel.add(condition);
        panel.add(condition2);
    
        JOptionPane.showMessageDialog(null, panel, "Announcement", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    void WriterScore(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Score.txt", true))) {  // Chế độ append
            writer.write(namePlayer + "," + score);  // Ghi tên và điểm số
            writer.newLine();  // Xuống dòng
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
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
        drawBackground(g2D);
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
            
            req_dx = 0;
            req_dy = -1;
            
            break;
        case KeyEvent.VK_DOWN:
            req_dx = 0;
            req_dy = 1;
            break;
        case KeyEvent.VK_ESCAPE:
            timer.stop();
            break;
        case KeyEvent.VK_SPACE:
            timer.start();
            break;
    }
}



    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
