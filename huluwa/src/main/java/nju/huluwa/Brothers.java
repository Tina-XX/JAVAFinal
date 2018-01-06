package nju.huluwa;


enum COLORS{RED,ORANGE,YELLOW,GREEN,CYAN,BLUE,VIOLET};
enum SENIORITY{老大,老二,老三,老四,老五,老六,老七};

public class Brothers extends Creature{
    private COLORS color;
    private SENIORITY seniority;
    Map map;


    public Brothers(COLORS col,SENIORITY sen,Map map,Field field){
        seniority=sen;
        color=col;
        this.map=map;
        this.field=field;
        positions = map.getPositions();
        dead=false;
        opp=false;
    }

    @Override
    public String getName() {
        switch (seniority){
            case 老大:return "1";
            case 老二:return "2";
            case 老三:return "3";
            case 老四:return "4";
            case 老五:return "5";
            case 老六:return "6";
            case 老七:return "7";
            default:return "-1";
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
    public void run() {
        while (!Thread.interrupted()) {
            //地方全部死亡，线程进入等待状态
            if (field.isDeadAll()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                //判断自己是否存活，死亡则不可进行行动
                if (!this.IsDead()) {
                    boolean OneFight = false;
                    boolean isFight = false;
                    Creature stand = this;
                    //锁住自己，开始判断与自己相邻处是否有敌人
                    synchronized (this) {
                        if ((x < 14 && y < 14) && !positions.get(x + 1).get(y).getValid()) {

                            stand = positions.get(x + 1).get(y).getHolder();
                            isFight = true;
                        } else if ((x > 0 && y > 0) && !positions.get(x - 1).get(y).getValid()) {
                            stand = positions.get(x - 1).get(y).getHolder();
                            isFight = true;
                        }

                        if (isFight) {
                            //确定有敌人，锁住敌人开打
                            synchronized (stand) {
                                if (!stand.IsDead() && (stand instanceof Shejing || stand instanceof Xiezijing)) {
                                    //根据爷爷死亡状态决定葫芦娃的攻击力
                                    if (isGrandpaDead) {
                                        stand.beaten();
                                        field.Save(stand.getName()+" dead "+"\r\n");
                                    }
                                    else {
                                        this.beaten();
                                        field.Save(getName()+" dead "+"\r\n");
                                    }
                                    OneFight = true;
                                } else if (stand instanceof Monsters) {
                                    if(!stand.IsDead()) {
                                        stand.beaten();
                                        //攻击过后技能冷却时间，此时不可有动作
                                        field.Save(stand.getName()+" dead "+"\r\n");
                                        OneFight = true;
                                    }
                                }
                            }
                            if (OneFight) {
                                try {
                                    this.field.repaint();
                                    Thread.sleep(rand.nextInt(1700));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                    //移动前再次判断自己是否存活
                    if (!this.IsDead()) {
                        if (x < 14 && y < 14 && y >= 0) {
                            //葫芦娃要去主动追击敌人
                            boolean isStand = false;
                            boolean isStandUp = false;
                            boolean isStandDown = false;
                            boolean moveBack = false;
                            //判断前方、后方、上一行、下一行是否有敌人，以决定自己的移动方向
                            for (int i = x + 1; i < 14; i++) {
                                if (positions.get(x + 1).get(y).getValid() && (!positions.get(i + 1).get(y).getValid() && (positions.get(i + 1).get(y).getHolder().getopp()))) {
                                    isStand = true;
                                    break;
                                }

                            }
                            for (int i = x - 1; i > 0; i--) {
                                if (positions.get(x - 1).get(y).getValid() && (!positions.get(i - 1).get(y).getValid() && (positions.get(i - 1).get(y).getHolder().getopp()))) {
                                    moveBack = true;
                                    break;
                                }
                            }
                            if ((y > 0) && !isStand && positions.get(x).get(y - 1).getValid()) {
                                for (int i = 0; i < 15; i++) {
                                    if (!positions.get(i).get(y - 1).getValid() && (positions.get(i).get(y - 1).getHolder().getopp())) {
                                        isStandUp = true;
                                        break;
                                    }
                                }
                            }
                            if (!isStandUp && positions.get(x).get(y + 1).getValid()) {
                                for (int i = 0; i < 15; i++) {
                                    if (!positions.get(i).get(y + 1).getValid() && (positions.get(i).get(y + 1).getHolder().getopp())) {
                                        isStandDown = true;
                                        break;
                                    }
                                }
                            }
                            //前后上下都没有敌人时，进一步判断上下两行是否有敌人
                            if (!isStand && !isStandDown && !isStandUp) {
                                if ((y > 1) && positions.get(x).get(y - 1).getValid()) {
                                    for (int i = 0; i < 15; i++) {
                                        if (!positions.get(i).get(y - 2).getValid() && (positions.get(i).get(y - 2).getHolder().getopp())) {
                                            isStandUp = true;
                                            break;
                                        }
                                    }
                                }
                                if ((y < 13) && !isStandUp && positions.get(x).get(y + 1).getValid()) {
                                    for (int i = 0; i < 15; i++) {
                                        if (!positions.get(i).get(y + 2).getValid() && (positions.get(i).get(y + 2).getHolder().getopp())) {
                                            isStandDown = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            //根据判断的移动方向开始移动，移动前锁住即将前往的Position位置，防止两个生物踩在同一块Position
                            if (isStand) {
                                synchronized (positions.get(x + 1).get(y)) {
                                    // if (positions.get(x + 1).get(y).getValid()) {

                                    new MoveForward(this).move(positions, x + 1, y);
                                   // bufferedWriter.append()
                                   // String str=new String(getSeniority()+" move " + x+" "+y+"\r\n");
                                    field.Save(getName()+" move " + x+" "+y+"\r\n");

                                    try {
                                        this.field.repaint();
                                        Thread.sleep(rand.nextInt(200) + 200);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // }
                                }
                            } else if (isStandUp) {
                                synchronized (positions.get(x).get(y - 1)) {
                                    //if (positions.get(x ).get(y-1).getValid()) {

                                    new MoveForward(this).move(positions, x, y - 1);
                                    field.Save(getName()+" move " + x+" "+y+"\r\n");
                                    try {
                                        this.field.repaint();
                                        Thread.sleep(rand.nextInt(200) + 200);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //}
                                }
                            } else if (isStandDown) {
                                synchronized (positions.get(x).get(y + 1)) {
                                    //if (positions.get(x ).get(y+1).getValid()) {

                                    new MoveForward(this).move(positions, x, y + 1);
                                    field.Save(getName()+" move " + x+" "+y+"\r\n");
                                    try {
                                        this.field.repaint();
                                        Thread.sleep(rand.nextInt(200) + 200);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //}
                                }
                            } else if (moveBack) {
                                new MoveForward(this).move(positions, x - 1, y);
                                field.Save(getName()+" move " + x+" "+y+"\r\n");
                                try {
                                    this.field.repaint();
                                    Thread.sleep(rand.nextInt(200) + 200);
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
