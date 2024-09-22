import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends Window implements ActionListener {
    private Ghost ghost;
    Timer timer;
    int[] dx, dy;
    private int ghost_dx, ghost_dy;
    private int count;

    Game() {

        
        setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        initVariables();
        initGhosts();
    }

    void initVariables() {
        dx = new int[] { 1, 0, -1, 0 };
        dy = new int[] { 0, 1, 0, -1 };
        timer = new Timer(40, e -> this.updateGame());
        timer.start();
    }

    void initGhosts() {
        ghost = new Ghost(getMAX_X() / 4 + 50, getMAX_Y() / 4 + 50, Color.RED, 6, 1, 0);
    }
    
    void drawMaze(Graphics2D g2D) {
        g2D.setColor(Color.BLUE);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRect(getMAX_X() / 4, getMAX_Y() / 4, getMAX_X() / 2, getMAX_Y() / 2);
    }
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        drawMaze(g2D);

        ghost.update();   // Cập nhật vị trí
            ghost.draw(g); 
    }


    // Cập nhật trạng thái của tất cả ma
    public void updateGame() {
        
            ghost.move();  // Di chuyển từng con ma
       
        repaint();  // Cập nhật lại màn hình
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
