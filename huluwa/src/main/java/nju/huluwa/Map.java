package nju.huluwa;
import java.util.ArrayList;


public class Map <T extends Creature>{

    private ArrayList<ArrayList<Position>> positions;

    int length,width;

    public Map(int n,int m){
        length=n;
        width=m;
        this.positions=new ArrayList<>();
        for(int i=0;i<n;i++){
            positions.add(new ArrayList<>());
            for(int j=0;j<m;j++)
                positions.get(i).add(new Position(i,j));
        }
    }

    public void PutOnMap(Queue huluQueue,T xie,ArrayList<T> mon,Shejing shejing,Grandpa yeye){

        for(int i=0;i<7;i++){
            this.positions.get(i).get(i+1).setHolder(huluQueue.getCreat(i));
            huluQueue.getCreat(i).setXY(i,i+1);
        }
        for(int i=14;i>11;i--){
            this.positions.get(i).get(14-i).setHolder(mon.get(i-12));
            mon.get(i-12).setXY(i,14-i);
        }
        for(int i=14;i>11;i--){
            this.positions.get(i).get(i-8).setHolder(mon.get(i-9));
            mon.get(i-9).setXY(i,i-8);
        }

        this.positions.get(11).get(3).setHolder(xie);
        this.positions.get(14).get(3).setHolder(shejing);
        this.positions.get(7).get(0).setHolder(yeye);
        yeye.setXY(7,0);

    }

    public ArrayList<ArrayList<Position>> getPositions() {
        return positions;
    }

    //打印阵型
    /*public void output(){
        for(int i=0;i<length;i++) {
            for (int j = 0; j < width; j++) {
                if(positions.get(i).get(j).getValid())
                    System.out.print(positions.get(i).get(j).getHolder().getName());
                else
                    System.out.print("🌿");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }*/
}
