package nju.huluwa;
import java.util.ArrayList;

//移动一格
public class MoveForward <T extends Creature> implements Layout{
    T creature;
    MoveForward(T creature){this.creature=creature;}
    @Override
    public void move(ArrayList<ArrayList<Position>> positions,int toX,int toY) {
        int x=creature.getX();
        int y=creature.getY();

        positions.get(x).get(y).MoveAway();
        positions.get(toX).get(toY).setHolder(creature);
        creature.setXY(toX, toY);

    }
}
