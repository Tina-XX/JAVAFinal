package nju.huluwa;

import java.util.ArrayList;
import java.util.Random;

public abstract class Creature implements Runnable {
    protected boolean dead;//存活状态
    ArrayList<ArrayList<Position>> positions;
    static public Field field;
    //当前坐标
    protected int x;
    protected int y;
    protected boolean opp;//属于正派还是反派
    static boolean isGrandpaDead;//爷爷是否存活


    Random rand =new Random(330);

    abstract String getName();
    abstract int getX();
    abstract int getY();
    abstract void setXY(int x,int y);
    abstract public void run();
    public boolean IsDead(){
        return dead;
    }
    //被打死了
    public void beaten(){
        dead=true;
        positions.get(x).get(y).MoveAway();
    }
    public boolean getopp(){
        return opp;
    }
    public Field getField(){
        return field;
    }
    //复盘时要复活该生物，
    public void setLive(){
        dead=false;
    }
}
