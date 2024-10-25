import java.awt.Image;
import javax.swing.ImageIcon;

class Ghost {
    private int x,y;
    private int speed;
    private int dx, dy;
    private Image defaultIcon;
    private int detection_radius;

    public Ghost(int x, int y, int speed, int dx, int dy, int detection_radius) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.detection_radius = detection_radius;
        this.defaultIcon = new ImageIcon("src/Image/ghost.gif").getImage().getScaledInstance(14, 14, Image.SCALE_DEFAULT);
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
    
    public int getDetection_radius() {
        return detection_radius;
    }

    public void setDetection_radius(int detection_radius) {
        this.detection_radius = detection_radius;
    }

    public Image getDefaultIcon() {
        return defaultIcon;
    }
}