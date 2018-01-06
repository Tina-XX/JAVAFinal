package nju.huluwa;
public class Xiezijing extends Creature {
    private int x;
    private int y;
    public Xiezijing(int x,int y,Map map,Field field){
        positions = map.getPositions();
        this.field=field;
        this.x=x;
        this.y=y;
        dead=false;
        opp=true;
    }
    @Override
    public String getName() {
        return "16";
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
                            //蝎子精在爷爷死之前攻击力非常高，爷爷死后打不过暴走的葫芦娃
                            if (!stand.IsDead() && !stand.getopp()) {
                                if (!isGrandpaDead) {
                                    if(!stand.IsDead()) {
                                        stand.beaten();
                                        field.Save(stand.getName() + " dead " + "\r\n");
                                    }
                                }
                                else {
                                    this.beaten();
                                    field.Save("16"+" dead "+"\r\n");
                                }
                                OneFight = true;
                            }
                            if (OneFight) {
                                try {
                                    this.field.repaint();
                                    //蝎子精打完一次之后攻击技能要冷却非常久，此时不能有任何行动
                                    Thread.sleep(rand.nextInt(6000));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        if (!OneFight && !this.IsDead()) {
                            if (x > 0 && y >= 0) {
                                //蝎子精会向前、向上下两行去主动去找、攻击葫芦娃和爷爷
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
                                if ((y < 14) && !isStandUp && positions.get(x).get(y + 1).getValid()) {
                                    for (int i = x - 1; i >= 0; i--) {
                                        if (!positions.get(i).get(y + 1).getValid() && (positions.get(i).get(y + 1).getHolder().getopp())) {
                                            isStandDown = true;
                                            break;
                                        }
                                    }
                                }
                                if (!isStand && !isStandDown && !isStandUp) {
                                    if ((y > 1) && positions.get(x).get(y - 1).getValid()) {
                                        for (int i = x - 1; i >= 0; i--) {
                                            if (!positions.get(i).get(y - 2).getValid() && (positions.get(i).get(y - 2).getHolder().getopp())) {
                                                isStandUp = true;
                                                break;
                                            }
                                        }
                                    }
                                    if ((y < 13) && !isStandUp && positions.get(x).get(y + 1).getValid()) {
                                        for (int i = x - 1; i >= 0; i--) {
                                            if (!positions.get(i).get(y + 2).getValid() && (positions.get(i).get(y + 2).getHolder().getopp())) {
                                                isStandDown = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (isStand) {
                                    //蝎子精移动的非常快
                                    synchronized (positions.get(x - 1).get(y)) {
                                        new MoveForward(this).move(positions, x - 1, y);
                                        field.Save("16"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            Thread.sleep(rand.nextInt(100) + 100);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (isStandUp) {
                                    synchronized (positions.get(x).get(y - 1)) {

                                        new MoveForward(this).move(positions, x, y - 1);
                                        field.Save("16"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            Thread.sleep(rand.nextInt(100) + 100);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (isStandDown) {
                                    synchronized (positions.get(x).get(y + 1)) {

                                        new MoveForward(this).move(positions, x, y + 1);
                                        field.Save("16"+" move " + x+" "+y+"\r\n");
                                        try {
                                            this.field.repaint();
                                            Thread.sleep(rand.nextInt(100) + 100);
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
