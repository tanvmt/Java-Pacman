public class Player extends Window{
    private String namePlayer;
    private int Score;

    Player(String namePlayer,int Score){
        this.namePlayer = namePlayer;
        this.Score = Score;
    }

    public void setNamePlayer(String namePlayer){
        this.namePlayer = namePlayer;
    }

    public void setScore(int Score){
        this.Score = Score;
    }

    public String getNamePlayer(){
        return this.namePlayer;
    }

    public int getScore(){
        return this.Score;
    }
    
}
