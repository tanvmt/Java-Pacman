
public class Pacman {
    private int pacManX,pacManY;
    private int speed = 6;
    private int dPacmanX,dPacmanY;
    
    
    public Pacman(int pacManX,int pacManY,int dPacmanX,int dPacmanY){
        this.pacManX = pacManX;
        this.pacManY = pacManY;
        this.dPacmanX = dPacmanX;
        this.dPacmanY = dPacmanY;
    }
    


    public void setDirection(int dx, int dy) {
        this.dPacmanX = dx;
        this.dPacmanY = dy;
    }

    public void move() {
        pacManX += dPacmanX * speed;
        pacManY += dPacmanY * speed;
    }
    
    public int getPacManX() {
        return pacManX;
    }

    public void setPacManX(int pacManX) {
        this.pacManX = pacManX;
    }

    public int getPacManY() {
        return pacManY;
    }

    public void setPacManY(int pacManY) {
        this.pacManY = pacManY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getdPacmanX() {
        return dPacmanX;
    }

    public void setdPacmanX(int dPacmanX) {
        this.dPacmanX = dPacmanX;
    }

    public int getdPacmanY() {
        return dPacmanY;
    }

    public void setdPacmanY(int dPacmanY) {
        this.dPacmanY = dPacmanY;
    }

     
}

