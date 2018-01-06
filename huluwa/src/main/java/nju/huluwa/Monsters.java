package nju.huluwa;
public class Monsters extends Creature{
    private int number;
   // private int x;
   // private int y;

    public Monsters(Map map,Field field,int num){
        number=num;
        positions = map.getPositions();
        this.field=field;
        dead=false;
        opp=true;
    }

    @Override
    public String getName() {
        switch (number){
            case 0:return "8";
            case 1:return "9";
            case 2:return "10";
            case 3:return "11";
            case 4:return "12";
            case 5:return "13";
            default:return "";
        }

    }
    @Override
    public int getX(){
        return this.x;
    }
    @Override
    public int getY(){
        return this.y;
    }
    @Override
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public void run(){
        while (!Thread.interrupted()) {
            if (!field.isDeadAll()) {
                if (!this.IsDead()) {
                    boolean OneFight = false;
                    synchronized (this) {
                        if ((x > 0 && y >= 0) && !positions.get(x - 1).get(y).getValid()) {

                            Creature stand = positions.get(x - 1).get(y).getHolder();
                            //小罗喽能打死爷爷但是打不过葫芦娃
                            if (!stand.IsDead() && (stand instanceof Brothers)) {
                                this.beaten();
                                field.Save(getName()+" dead "+"\r\n");
                                OneFight = true;
                            } else if (stand instanceof Grandpa) {
                                synchronized (stand) {
                                    if(!stand.IsDead()) {
                                        stand.beaten();
                                        field.Save("14" + " dead " + "\r\n");
                                        OneFight = true;
                                    }
                                }
                            }
                            if (OneFight) {
                                try {
                                    this.field.repaint();
                                    //打完一次歇一会儿
                                    Thread.sleep(rand.nextInt(400));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        if (!OneFight && !this.IsDead()) {
                            if (x > 0 && y >= 0) {
                                synchronized (positions.get(x - 1).get(y)) {
                                    if (positions.get(x - 1).get(y).getValid()) {

                                        new MoveForward(this).move(positions, x - 1, y);
                                        field.Save(getName()+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            //小罗喽的移动速度
                                            Thread.sleep(rand.nextInt(200) + 300);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
