package nju.huluwa;

//存储位置
public class Position <T extends Creature>{
    private int x;
    private int y;
    private T holder;
    private boolean valid;



    public Position(int x,int y){
        this.x=x;
        this.y=y;
        valid=true;
    }

    public Creature getHolder() {
        return holder;
    }

    public void setHolder(T holder) {
        this.holder = holder;
        valid=false;
    }

    public void MoveAway(){valid=true;}

    public boolean getValid(){
        return valid;
    }


}
