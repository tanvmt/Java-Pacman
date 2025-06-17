
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//implements KeyListener
public class GameEngine extends Window {
    private GamePanel gamePanel;
    private JButton backButton;
    private Ghost[] ghost;
    private Level level;
    public Timer timer = new Timer(40, e -> this.updateGame());
    private int[] dx, dy;
    private int ghost_x, ghost_y, ghost_dx, ghost_dy;
    
    private Pacman pacman;
    private int req_dx,req_dy;
    private int score;
    private Image up, down, left, right, basic;
    private int lives;
    private int maxScore;
    private int levelCount = 1;

    private String namePlayer;

    private JLabel scoreLabel, showScore, livesLabel, move, pause, resume, levelLabel, showLevel;
    private Image arrowKeys, spaceKey, escKey, heart;
    MyFrame tmpMyFrame;
   
    GameEngine() {

        setBackground(Color.BLACK);
        setLayout(null);
        loadImage();
        initLevel();
        initVariables();

        add(backButton);

        setFocusable(true);

        addKeyListener(new TAdapter());
        gamePanel = new GamePanel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                requestFocusInWindow();
            }
        });

        add(gamePanel);
    }

    class GamePanel extends JPanel {
        GamePanel() {
            setBackground(Color.BLACK);
            setBounds((MAX_X - SCREEN_SIZE) / 2, (MAX_Y - SCREEN_SIZE - 22) / 2, SCREEN_SIZE, SCREEN_SIZE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g;
            level.drawMaze(g2D);
        
            drawPacman(g2D);
            drawGhosts(g2D);
        }
    }
    
    private void returnToMenu() {
        if (timer != null) {
            timer.stop();  // Stop the game timer when returning to the menu
        }
        
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "Menu");  // Switch back to the menu in MainPanel
    }
    
    public void loadImage() {
        up = new ImageIcon("src\\Image\\up.gif").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        down = new ImageIcon("src\\Image\\down.gif").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        left = new ImageIcon("src\\Image\\left.gif").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        right = new ImageIcon("src\\Image\\right.gif").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        basic = new ImageIcon("src\\Image\\pacman.png").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);

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
        g2D.drawImage(arrowKeys, move.getX() + (move.getWidth() - arrowKeys.getWidth(this)) / 2, move.getY() + 60, this);
        
        add(pause);
        g2D.drawImage(escKey, pause.getX() + (pause.getWidth() - escKey.getWidth(this)) / 2, pause.getY() + 60, this);
        
        add(resume);
        g2D.drawImage(spaceKey, resume.getX() + (resume.getWidth() - spaceKey.getWidth(this)) / 2, resume.getY() + 60,
                this);
        
        add(levelLabel);
        
        add(showLevel);
        
        add(scoreLabel);
        add(showScore);
        add(livesLabel);
        for (int i = 0; i < lives; i++) {
            g2D.drawImage(heart, livesLabel.getX() + (livesLabel.getWidth() - heart.getWidth(this) * 3) / 2 + i * heart.getWidth(this), livesLabel.getY() + 50, this);
        }
        
    }
    
    void initPacman(){
        req_dx = 0;
        req_dy = 0;
        pacman = new Pacman(level.getSpawnPacmanX(), level.getSpawnPacmanY(),0,0);
    }
    
    void drawPacman(Graphics2D g2d){
        
        if (req_dx == -1) {
        	g2d.drawImage(left, pacman.getPacManX() + 3, pacman.getPacManY() + 3, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, pacman.getPacManX() + 3, pacman.getPacManY() + 3, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, pacman.getPacManX() + 3, pacman.getPacManY() + 3, this);
        } else if(req_dy == 1){
        	g2d.drawImage(down, pacman.getPacManX() + 3, pacman.getPacManY() + 3, this);
        }
        else{
            g2d.drawImage(basic, pacman.getPacManX() + 3, pacman.getPacManY() + 3, this);
        }
    }
    
    void movePacman(){
        int pos;
        short ch;
        int pacman_x = pacman.getPacManX();
        int pacman_y = pacman.getPacManY();
        int pacmand_x = pacman.getdPacmanX();
        int pacmand_y = pacman.getdPacmanY();

        
        if((pacman_x) % BLOCK_SIZE == 0 && (pacman_y) % BLOCK_SIZE ==0){
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];
            
            if((ch&16) != 0){
                screenData[pos] = (short) (ch & 15);
                score++;
                showScore.setText(String.valueOf(score));
                if (score == maxScore) {
                    changeNextLevel();
                }
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
    
    void changeNextLevel() {
        if (levelCount < 3){
            showScore.setText(--score + "");
            showLevel.setText(++levelCount + "");
        }
        else {
            announcementYouWin();
            WriterScore();
            returnToMenu();
        }
        initLevel();      
        initGhosts();
        initPacman();
        gamePanel.setBounds((MAX_X - SCREEN_SIZE) / 2, (MAX_Y - SCREEN_SIZE - 22) / 2, SCREEN_SIZE, SCREEN_SIZE);
        gamePanel.repaint();
    }

    void initVariables() {
        dx = new int[4];
        dy = new int[4];


        backButton = createButton("BACK", 0, 0, 200, 50, e -> {
            setNamePlayer(null);
            score = 0;
            returnToMenu();});

        move = createLabel("MOVE", 100, 150, 200, 50, 30, Color.RED);
        pause = createLabel("PAUSE", 100, move.getY() + move.getHeight() + 150,200, 50, 30, Color.RED);
        resume = createLabel("RESUME", 100, move.getY() + move.getHeight() + 300, 200, 50, 30, Color.RED);

        levelLabel = createLabel("LEVEL", MAX_X - 300, 150, 200, 50, 30, Color.RED);
        showLevel = createLabel(levelCount + "", MAX_X - 300, levelLabel.getY() + 60, 200, 50, 30, Color.WHITE);
        
        scoreLabel = createLabel("SCORE", MAX_X - 300, move.getY() + move.getHeight() + 150, 200, 50, 30, Color.RED);
        showScore = createLabel(--score + "", MAX_X - 300, scoreLabel.getY() + 60, 200, 50, 30, Color.WHITE);
                
        livesLabel = createLabel("LIVES", MAX_X - 300, move.getY() + move.getHeight() + 300, 200, 50, 30, Color.RED);

    }

    void initGhosts() {
        ghost = new Ghost[level.getGhostQuantity()];
        int[] spawnGhostX = level.getSpawnGhostX();
        int[] spawnGhostY = level.getSpawnGhostY(); 
        for (int i = 0; i < ghost.length; i++) {
            ghost[i] = new Ghost(spawnGhostX[i], spawnGhostY[i], level.getGhostSpeed(), 0, 0,
                    level.getDetectionRadius());
        }
    }

    void initLevel() {
        level = new Level(levelCount);
        N_BLOCKS = level.N_BLOCKS;
        SCREEN_SIZE = level.SCREEN_SIZE;
        screenData = level.screenData;
        maxScore = level.getMaxScore();
        // showScore.setText("");
    }

    void drawGhosts(Graphics2D g2D) {
        for (int i = 0; i < level.getGhostQuantity(); i++) {
            g2D.drawImage(ghost[i].getDefaultIcon(), ghost[i].getX() + 3, ghost[i].getY() + 3, this);
        }
        
    }

    void moveGhosts() {
        for (int i = 0; i < level.getGhostQuantity(); i++) {
            ghost_dx = ghost[i].getDx();
            ghost_dy = ghost[i].getDy();
            ghost_x = ghost[i].getX();
            ghost_y = ghost[i].getY();
            int pos = 0;
            int count = 0;

            int distanceToPacman = (int) Math.sqrt(Math.pow(ghost_x - pacman.getPacManX(), 2) + Math.pow(ghost_y - pacman.getPacManY(), 2));

            if ((ghost_x) % BLOCK_SIZE == 0 && (ghost_y) % BLOCK_SIZE == 0) {
                pos = ghost_x / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y / BLOCK_SIZE);
                
                // Check left border
                if ((screenData[pos] & 1) == 0 && ghost_dx != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                // Check top border
                if ((screenData[pos] & 2) == 0 && ghost_dy != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                // Check right border
                if ((screenData[pos] & 4) == 0 && ghost_dx != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                // Check down border
                if ((screenData[pos] & 8) == 0 && ghost_dy != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                // Chasing Pacman upon detection
                if (distanceToPacman <= ghost[i].getDetectionRadius()) {
                    int minDistance = Integer.MAX_VALUE;
                    int bestDx = 0, bestDy = 0;
                    for (int j = 0; j < count; j++) {
                        int newGhostX = ghost_x + dx[j] * BLOCK_SIZE;
                        int newGhostY = ghost_y + dy[j] * BLOCK_SIZE;

                        int distance = (int) Math.sqrt(Math.pow(newGhostX - pacman.getPacManX(), 2)
                                + Math.pow(newGhostY - pacman.getPacManY(), 2));

                        if (distance < minDistance) {
                            minDistance = distance;
                            bestDx = dx[j];
                            bestDy = dy[j];
                        }
                    }
                    ghost[i].setDirection(bestDx, bestDy);
                } else if (count == 0) { // Avoid stuck
                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx = 0;
                        ghost_dy = 0;
                    } else {
                        ghost_dx = -ghost_dx;
                        ghost_dy = -ghost_dy;
                    }
                    ghost[i].setDirection(ghost_dx, ghost_dy);
                } else if(count>0){ // Random direction
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
            // Check collision with Pacman
            if (pacman.getPacManX() > (ghost_x - 18) && pacman.getPacManX() < (ghost_x + 18)
                    && pacman.getPacManY() > (ghost_y - 18) && pacman.getPacManY() < (ghost_y + 18)
                    ) {
                this.lives -= 1;        
                timer.stop();
                checkLives();
                initPacman();
                initGhosts();
            }
        }

    }

    void setNamePlayer() {
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", Color.WHITE);
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
        
        // Xử lý kết quả
        if (result == JOptionPane.OK_OPTION) {
            this.namePlayer = textNamePlayer.getText(); // Lưu tên người chơi nếu OK được nhấn
        } 
        else{
            returnToMenu();
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

    void announcementYouWin() {
        JLabel condition = new JLabel("You Win");
        JLabel condition2 = new JLabel("Score: " + score);  // Hiển thị điểm số
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Đặt layout cho panel
        panel.add(condition);
        panel.add(condition2);
    
        JOptionPane.showMessageDialog(null, panel, "Announcement", JOptionPane.INFORMATION_MESSAGE);
    }
      
    void WriterScore(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Score.txt", true))) {  // Chế độ append
            if(namePlayer!=null){
                writer.write(namePlayer + "," + score);  // Ghi tên và điểm số
                writer.newLine();  // Xuống dòng
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    void createGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        this.lives = 3;
        this.score = 0;
        this.levelCount = 1;

        showScore.setText(--score + "");
        showLevel.setText(levelCount + "");
        initLevel();
      
        initGhosts();
        initPacman();
        gamePanel.setBounds((MAX_X - SCREEN_SIZE) / 2, (MAX_Y - SCREEN_SIZE - 22) / 2, SCREEN_SIZE, SCREEN_SIZE);
    
        setNamePlayer(null); 
        setNamePlayer(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        drawBackground(g2D);     
    }

    public void updateGame() {
        movePacman();
        moveGhosts();
        repaint();
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            
            int key = e.getKeyCode();
        
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
    }   
}
