package nju.huluwa;
public class Shejing extends Creature {
    public Shejing(int x,int y,Map map,Field field){
        positions = map.getPositions();
        this.field=field;
        this.x=x;
        this.y=y;
        dead=false;
        opp=true;
    }
    @Override
    public String getName() {
        return "15";
    }
    private int x;
    private int y;
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
                        if ((x > 0 && y > 0) && !positions.get(x - 1).get(y).getValid()) {
                            Creature stand = positions.get(x - 1).get(y).getHolder();
                            //蛇精在爷爷死之前攻击力非常高，爷爷死了之后就打不过暴走的葫芦娃了
                            if (!stand.IsDead() && !stand.getopp()) {
                                if (isGrandpaDead) {
                                    this.beaten();
                                    field.Save("15"+" dead "+"\r\n");
                                }
                                else {
                                    if(!stand.IsDead()) {
                                        stand.beaten();
                                        field.Save(stand.getName() + " dead " + "\r\n");
                                    }
                                }
                                OneFight = true;
                            }
                            if (OneFight) {
                                try {
                                    this.field.repaint();
                                    //蛇精打完一次攻击技能要冷却非常久，此时不能行动
                                    Thread.sleep(rand.nextInt(5000));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        if (!OneFight && !this.IsDead()) {
                            if (x > 0 && y >= 0) {
                                //蛇精会向前、向上、向下去主动寻找攻击葫芦娃和爷爷
                                boolean isStand = false;
                                boolean isStandUp = false;
                                boolean isStandDown = false;
                                for (int i = x - 1; i > 0; i--) {
                                    if (positions.get(x - 1).get(y).getValid() && (!positions.get(i - 1).get(y).getValid() && (!positions.get(i - 1).get(y).getHolder().getopp()))) {
                                        isStand = true;
                                        break;
                                    }
                                }
                                if ((y > 0) && !isStand && positions.get(x).get(y - 1).getValid()) {
                                    for (int i = x - 1; i >= 0; i--) {
                                        if (!positions.get(i).get(y - 1).getValid() && (!positions.get(i).get(y - 1).getHolder().getopp())) {
                                            isStandUp = true;
                                            break;
                                        }
                                    }
                                }
                                if ((y > 0) && !isStandUp && positions.get(x).get(y + 1).getValid()) {
                                    for (int i = x - 1; i >= 0; i--) {
                                        if (!positions.get(i).get(y - 1).getValid() && (!positions.get(i).get(y - 1).getHolder().getopp())) {
                                            isStandDown = true;
                                            break;
                                        }
                                    }
                                }
                                if (isStand) {
                                    synchronized (positions.get(x - 1).get(y)) {
                                        new MoveForward(this).move(positions, x - 1, y);
                                        field.Save("15"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            //蛇精跑的特别快
                                            Thread.sleep(rand.nextInt(100) + 200);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (isStandUp) {
                                    synchronized (positions.get(x).get(y - 1)) {

                                        new MoveForward(this).move(positions, x, y - 1);
                                        field.Save("15"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            Thread.sleep(rand.nextInt(100) + 200);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (isStandDown) {
                                    synchronized (positions.get(x).get(y + 1)) {

                                        new MoveForward(this).move(positions, x, y + 1);
                                        field.Save("15"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            Thread.sleep(rand.nextInt(100) + 200);
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
