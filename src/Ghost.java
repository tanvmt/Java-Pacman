import java.awt.Image;
import javax.swing.ImageIcon;

class Ghost {
    private int x,y;
    private int speed;
    private int dx, dy;
    private Image defaultIcon;
    private int detectionRadius;

    public Ghost(int x, int y, int speed, int dx, int dy, int detectionRadius) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.detectionRadius = detectionRadius;
        this.defaultIcon = new ImageIcon("src/Image/ghost.gif").getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;
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
    
    public int getDetectionRadius() {
        return detectionRadius;
    }

    public void setDetectionRadius(int detectionRadius) {
        this.detectionRadius = detectionRadius;
    }

    public Image getDefaultIcon() {
        return defaultIcon;
    }
}