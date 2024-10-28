import java.awt.*;

class Level extends Window{

    private Dimension d;


    private short levelData[];
    private int ghostQuantity;
    private int[] spawnGhostX, spawnGhostY;
    private int ghostSpeed;
    private int detectionRadius;

    private int spawnPacmanX, spawnPacmanY;

    private int maxScore;

    public int getGhostQuantity() {
        return ghostQuantity;
    }

    public int[] getSpawnGhostX() {
        return spawnGhostX;
    }

    public int[] getSpawnGhostY() {
        return spawnGhostY;
    }

    public int getGhostSpeed() {
        return ghostSpeed;
    }

    public int getDetectionRadius() {
        return detectionRadius;
    }

    public int getSpawnPacmanX() {
        return spawnPacmanX;
    }

    public int getSpawnPacmanY() {
        return spawnPacmanY;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void createLevelHard(){
        N_BLOCKS = 31;
        SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

        ghostQuantity = 4;
        spawnGhostX = new int[] { 5, 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1), 5 + BLOCK_SIZE * (N_BLOCKS - 1)};
        spawnGhostY = new int[] { 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1), 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1)};
        ghostSpeed = 4;
        detectionRadius = 100;

        spawnPacmanX = 5 + BLOCK_SIZE * 13;
        spawnPacmanY = 5 + BLOCK_SIZE * 14;

        maxScore = 1006;
        // maxScore = 20;

        levelData = new short[] { //0 = BLUE, 1 = LEFT BORDER, 2 = TOP BORDER, 4 = RIGHT BORDER, 8 = BOTTOM BORDER, 16 = DOTS
            19, 26, 26, 26, 18, 26, 26, 26, 26, 18, 18, 18, 18, 18, 18, 26, 18, 18, 18, 18, 18, 18, 26, 26, 26, 26, 18, 26, 26, 26, 22,
            21, 00, 00, 00, 21, 00, 00, 00, 00, 17, 16, 16, 16, 16, 20, 00, 17, 16, 16, 16, 16, 20, 00, 00, 00, 00, 21, 00, 00, 00, 21,
            21, 00, 00, 00, 21, 00, 00, 00, 00, 17, 16, 16, 16, 16, 20, 00, 17, 16, 16, 16, 16, 20, 00, 00, 00, 00, 21, 00, 00, 00, 21,
            21, 00, 00, 00, 21, 00, 00, 00, 00, 17, 16, 16, 16, 16, 28, 00, 25, 16, 16, 16, 16, 20, 00, 00, 00, 00, 21, 00, 00, 00, 21,
            25, 26, 26, 26, 24, 26, 18, 18, 18, 16, 16, 16, 16, 20, 00, 00, 00, 17, 16, 16, 16, 16, 18, 18, 18, 26, 24, 26, 26, 26, 28,
            00, 00, 00, 00, 00, 00, 17, 24, 24, 24, 24, 24, 24, 24, 26, 26, 26, 24, 24, 24, 24, 24, 24, 24, 20, 00, 00, 00, 00, 00, 00,
            00, 00, 00, 19, 18, 18, 20, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 17, 18, 18, 22, 00, 00, 00,
            19, 18, 18, 16, 16, 16, 24, 18, 18, 18, 22, 00, 00, 00, 00, 00, 00, 00, 00, 00, 19, 18, 18, 18, 24, 16, 16, 16, 18, 18, 22,
            17, 16, 16, 16, 16, 20, 00, 17, 16, 16, 16, 18, 18, 22, 00, 00, 00, 19, 18, 18, 16, 16, 16, 20, 00, 17, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 28, 00, 25, 16, 16, 16, 16, 16, 16, 22, 00, 19, 16, 16, 16, 16, 16, 16, 28, 00, 25, 16, 16, 16, 16, 20,
            17, 16, 16, 24, 28, 00, 00, 00, 25, 24, 16, 16, 16, 16, 20, 00, 17, 16, 16, 16, 16, 24, 28, 00, 00, 00, 25, 24, 16, 16, 20,
            17, 16, 20, 00, 00, 00, 00, 00, 00, 00, 17, 16, 24, 16, 20, 00, 17, 16, 24, 16, 20, 00, 00, 00, 00, 00, 00, 00, 17, 16, 20,
            17, 16, 16, 18, 22, 00, 00, 00, 19, 18, 16, 20, 00, 17, 20, 00, 17, 20, 00, 17, 16, 18, 22, 00, 00, 00, 19, 18, 16, 16, 20,
            25, 16, 16, 16, 16, 22, 00, 19, 16, 16, 16, 16, 18, 16, 20, 00, 17, 16, 18, 16, 16, 16, 16, 22, 00, 19, 16, 16, 16, 16, 28,
            00, 17, 16, 16, 16, 20, 00, 17, 16, 16, 24, 24, 24, 24, 24, 26, 24, 24, 24, 24, 24, 16, 16, 20, 00, 17, 16, 16, 16, 20, 00,
            00, 25, 24, 16, 16, 16, 18, 16, 16, 20, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 17, 16, 16, 18, 16, 16, 16, 24, 28, 00,
            00, 00, 00, 17, 16, 24, 16, 16, 16, 16, 18, 22, 00, 00, 00, 00, 00, 00, 00, 19, 18, 16, 16, 16, 16, 24, 16, 20, 00, 00, 00,
            00, 19, 18, 16, 28, 00, 25, 16, 16, 16, 24, 28, 00, 00, 00, 00, 00, 00, 00, 25, 24, 16, 16, 16, 28, 00, 25, 16, 18, 22, 00,
            00, 17, 16, 20, 00, 00, 00, 17, 16, 20, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 17, 16, 20, 00, 00, 00, 17, 16, 20, 00,
            19, 16, 16, 20, 00, 00, 00, 25, 24, 16, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 16, 24, 28, 00, 00, 00, 17, 16, 16, 22,
            17, 16, 16, 16, 22, 00, 00, 00, 00, 17, 16, 16, 16, 24, 24, 16, 24, 24, 16, 16, 16, 20, 00, 00, 00, 00, 19, 16, 16, 16, 20,
            17, 16, 16, 16, 20, 00, 19, 18, 18, 24, 16, 16, 20, 00, 00, 21, 00, 00, 17, 16, 16, 24, 18, 18, 22, 00, 17, 16, 16, 16, 20,
            17, 24, 24, 16, 20, 00, 17, 16, 20, 00, 25, 16, 16, 18, 18, 24, 18, 18, 16, 16, 28, 00, 17, 16, 20, 00, 17, 16, 24, 24, 20,
            21, 00, 00, 17, 20, 00, 17, 24, 16, 22, 00, 17, 24, 24, 28, 00, 25, 24, 24, 20, 00, 19, 16, 24, 20, 00, 17, 20, 00, 00, 21,
            17, 18, 18, 16, 16, 18, 20, 00, 17, 24, 18, 20, 00, 00, 00, 00, 00, 00, 00, 17, 18, 24, 20, 00, 17, 18, 16, 16, 18, 18, 20,
            25, 24, 24, 24, 24, 16, 16, 18, 20, 00, 17, 16, 18, 18, 22, 00, 19, 18, 18, 16, 20, 00, 17, 18, 16, 16, 24, 24, 24, 24, 28,
            00, 00, 00, 00, 00, 17, 16, 16, 20, 00, 17, 16, 16, 16, 20, 00, 17, 16, 16, 16, 20, 00, 17, 16, 16, 20, 00, 00, 00, 00, 00,
            19, 18, 18, 26, 26, 24, 24, 24, 28, 00, 25, 16, 16, 16, 20, 00, 17, 16, 16, 16, 28, 00, 25, 24, 24, 24, 26, 26, 18, 18, 22,
            17, 16, 28, 00, 00, 00, 00, 00, 00, 00, 00, 17, 16, 16, 20, 00, 17, 16, 16, 20, 00, 00, 00, 00, 00, 00, 00, 00, 25, 16, 20,
            17, 20, 00, 00, 19, 18, 18, 18, 18, 18, 18, 24, 24, 24, 20, 00, 17, 24, 24, 24, 18, 18, 18, 18, 18, 18, 22, 00, 00, 17, 20,
            25, 24, 26, 26, 24, 24, 24, 24, 24, 24, 28, 00, 00, 00, 25, 26, 28, 00, 00, 00, 25, 24, 24, 24, 24, 24, 24, 26, 26, 24, 28
      
        };
        
    }

    public void createLevelMedium(){
        N_BLOCKS = 15;
        SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

        ghostQuantity = 2;
        spawnGhostX = new int[] { 5 + BLOCK_SIZE, 5 + BLOCK_SIZE * (N_BLOCKS - 2) };
        spawnGhostY = new int[] { 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1) };
        ghostSpeed = 4;
        detectionRadius = 50;

        spawnPacmanX = 5 + BLOCK_SIZE * 7;
        spawnPacmanY = 5 + BLOCK_SIZE * 5;

        maxScore = 328;
        // maxScore = 15;

        levelData = new short[] { //0 = BLUE, 1 = LEFT BORDER, 2 = TOP BORDER, 4 = RIGHT BORDER, 8 = BOTTOM BORDER, 16 = DOTS
            00, 19, 26, 22, 00, 19, 26, 26, 26, 22, 00, 19, 26, 22, 00,
            19, 28, 00, 25, 18, 28, 00, 00, 00, 25, 18, 28, 00, 25, 22,
            21, 00, 00, 00, 21, 00, 00, 23, 00, 00, 21, 00, 00, 00, 21,
            17, 22, 00, 19, 24, 26, 26, 16, 26, 26, 24, 22, 00, 19, 20,
            17, 24, 26, 20, 00, 00, 00, 21, 00, 00, 00, 17, 26, 24, 20,
            21, 00, 00, 17, 18, 18, 18, 24, 18, 18, 18, 20, 00, 00, 21,
            25, 18, 26, 24, 16, 24, 20, 00, 17, 24, 16, 24, 26, 18, 28,
            00, 21, 00, 00, 21, 00, 21, 00, 21, 00, 21, 00, 00, 21, 00,
            19, 24, 22, 00, 21, 00, 21, 00, 21, 00, 21, 00, 19, 24, 22,
            21, 00, 17, 18, 24, 26, 24, 18, 24, 26, 24, 18, 20, 00, 21,
            17, 18, 24, 20, 00, 00, 00, 21, 00, 00, 00, 17, 24, 18, 20,
            17, 28, 00, 25, 18, 26, 26, 16, 26, 26, 18, 28, 00, 25, 20,
            21, 00, 00, 00, 21, 00, 00, 29, 00, 00, 21, 00, 00, 00, 21,
            25, 22, 00, 19, 24, 22, 00, 00, 00, 19, 24, 22, 00, 19, 28,
            00, 25, 26, 28, 00, 25, 26, 26, 26, 28, 00, 25, 26, 28, 00,
    
        };
    }
    public void createLevelEasy(){
        N_BLOCKS = 15;
        SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

        ghostQuantity = 2;
        spawnGhostX = new int[] { 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1) };
        spawnGhostY = new int[] { 5, 5 + BLOCK_SIZE * (N_BLOCKS - 1) };
        ghostSpeed = 3;
        detectionRadius = 50;

        spawnPacmanX = 5 + BLOCK_SIZE * 7;
        spawnPacmanY = 5 + BLOCK_SIZE * 6;

        maxScore = 179;
        // maxScore = 10;
        levelData = new short[] { //0 = BLUE, 1 = LEFT BORDER, 2 = TOP BORDER, 4 = RIGHT BORDER, 8 = BOTTOM BORDER, 16 = DOTS
            19, 26, 26, 26, 18, 18, 26, 26, 26, 18, 18, 26, 26, 26, 22,
            21, 00, 00, 00, 17, 20, 00, 00, 00, 17, 20, 00, 00, 00, 21,
            21, 00, 19, 18, 16, 16, 18, 26, 18, 16, 16, 18, 22, 00, 21,
            21, 00, 17, 16, 16, 16, 20, 00, 17, 16, 16, 16, 20, 00, 21,
            17, 18, 24, 24, 24, 16, 20, 00, 17, 16, 24, 24, 24, 18, 20,
            17, 20, 00, 00, 00, 17, 16, 18, 16, 20, 00, 00, 00, 17, 20,
            17, 16, 18, 18, 18, 16, 24, 24, 24, 16, 18, 18, 18, 16, 20,
            17, 16, 16, 16, 16, 20, 00, 00, 00, 17, 16, 16, 16, 16, 20,
            17, 16, 24, 24, 24, 16, 18, 18, 18, 16, 24, 24, 24, 16, 20,
            17, 20, 00, 00, 00, 17, 16, 24, 16, 20, 00, 00, 00, 17, 20,
            17, 24, 18, 18, 18, 16, 20, 00, 17, 16, 18, 18, 18, 24, 20,
            21, 00, 17, 16, 16, 16, 20, 00, 17, 16, 16, 16, 20, 00, 21,
            21, 00, 25, 24, 16, 16, 24, 26, 24, 16, 16, 24, 28, 00, 21,
            21, 00, 00, 00, 17, 20, 00, 00, 00, 17, 20, 00, 00, 00, 21,
            25, 26, 26, 26, 24, 24, 26, 26, 26, 24, 24, 26, 26, 26, 28,
    
        };
        
    }

    public Level(int level) {
        if (level == 1) {
            createLevelEasy();
        }
        else if (level == 2) {
            createLevelMedium();
        }
        else if (level == 3) {
            createLevelHard();
        }
        initVariables();
        initLevel();
    }

    private void initVariables(){
        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(800, 800);

    }

    private void initLevel(){
        int i;
        for(i = 0; i < N_BLOCKS * N_BLOCKS; i++){
            screenData[i] = levelData[i];
        }
    }

    // public void paintComponent(Graphics g){
    //     super.paintComponent(g);

    //     Graphics2D g2d = (Graphics2D) g;
    //     g2d.setColor(Color.black);
    //     g2d.fillRect(0, 0, d.width, d.height);

    //     drawMaze(g2d);

    //     Toolkit.getDefaultToolkit().sync();
    //     g2d.dispose();
    // }

    public void drawMaze(Graphics2D g2d){
        short i = 0;
        int x, y;
        for(y = 0; y < SCREEN_SIZE; y+=BLOCK_SIZE){
            for(x = 0; x < SCREEN_SIZE; x+= BLOCK_SIZE){
                g2d.setColor(new Color(0, 72, 251));
                g2d.setStroke(new BasicStroke(5));

                if((levelData[i] == 0)){
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }
                if((screenData[i] & 1) != 0){
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }
                if((screenData[i] & 2) != 0){
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }
                if((screenData[i] & 4) != 0){
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }
                if((screenData[i] & 8) != 0){
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);
                }
                if((screenData[i] & 16) != 0){
                    g2d.setColor(new Color(255, 255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);

                }
                i++;
            }
        }
    }

}