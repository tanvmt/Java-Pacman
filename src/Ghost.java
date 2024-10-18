import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

class Ghost {
    private int x,y;
    private int speed;
    private int dx, dy;
    private Color color;
    private boolean isFrightened;
    Image defaultIcon, frightenedIcon, changeStateIcon;

    public Ghost(int x, int y, Color color, int speed, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.color = color;
        // this.isFrightened = false;
        defaultIcon = new ImageIcon("src/Image/ghost.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
        // frightenedIcon = new ImageIcon("src/frightened_ghosts.gif").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
        // changeStateIcon = new ImageIcon("src/ghosts_change_state.gif").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;
    }

    // public void setFrightened(boolean isFrightened) {
    //     this.isFrightened = isFrightened;
    //     if (isFrightened) {
    //         color = Color.BLUE;
    //     }
    // }
    
    // public void reset(int startX, int startY) {
    //     this.x = startX;
    //     this.y = startY;
    //     this.isFrightened = false;
    //     this.color = Color.RED;
    // }

    public void update() {
        move();
    }

    public Color getColor(){
        return this.color;
    }

    public int getX(){
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }
}