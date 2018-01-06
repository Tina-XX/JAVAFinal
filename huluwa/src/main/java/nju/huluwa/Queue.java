package nju.huluwa;
import java.util.ArrayList;
import java.util.Collections;

//葫芦娃进队
public class Queue {
    private ArrayList<Creature> huluwaQueue;
    public Queue(ArrayList<? extends Creature> creatures){
        huluwaQueue=new ArrayList<>();
        for(int i=0;i<creatures.size();i++){
            huluwaQueue.add(i,creatures.get(i));
        }
    }

    public Creature getCreat(int i){
        return huluwaQueue.get(i);
    }

    public void ramdom(){
        Collections.shuffle(huluwaQueue);
    }
}
