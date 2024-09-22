import java.awt.Color;
import java.awt.Graphics;

class Ghost {
    private int x,y;
    private int speed;
    private int dx, dy;
    private Color color;
    private boolean isFrightened;

    public Ghost(int x, int y, Color color, int speed, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.color = color;
        this.isFrightened = false;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;
    }

    public void setFrightened(boolean isFrightened) {
        this.isFrightened = isFrightened;
        if (isFrightened) {
            color = Color.BLUE;
        }
    }
    
    public void reset(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.isFrightened = false;
        this.color = Color.RED;
    }

    public void update() {
        move();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 30, 30); // Vẽ ma dưới dạng hình tròn
    }
}