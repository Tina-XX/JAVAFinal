package nju.huluwa;
public class Grandpa extends Creature {
    public Grandpa(Map map,Field field){
        positions = map.getPositions();
        this.field=field;
        dead=false;
        opp=false;
        isGrandpaDead=false;
    }
    @Override
    public String getName() {
        return "14";
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
        //实现过程和葫芦娃类似
        while (!Thread.interrupted()) {
            if (field.isDeadAll()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                if (!this.IsDead()) {
                    boolean OneFight = false;
                    synchronized (this) {
                        if ((x > 0 && y >= 0) && !positions.get(x + 1).get(y).getValid()) {
                            Creature stand = positions.get(x + 1).get(y).getHolder();
                            //爷爷攻击力太低了，碰到任意一个反派都会死
                            if (!stand.IsDead() && stand.getopp()) {
                                this.beaten();
                                field.Save("14"+" dead "+"\r\n");
                                //爷爷死了T～T
                                isGrandpaDead = true;
                                OneFight = true;
                            }
                            if (OneFight) {
                                try {
                                    this.field.repaint();
                                    Thread.sleep(rand.nextInt(90));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        if (!OneFight && !this.IsDead()) {
                            if (x > 0 && y >= 0) {
                                synchronized (positions.get(x + 1).get(y)) {
                                    if (positions.get(x + 1).get(y).getValid()) {
                                        new MoveForward(this).move(positions, x + 1, y);
                                        field.Save("14"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            //爷爷的移动速度也很慢
                                            Thread.sleep(rand.nextInt(600) + 300);
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
