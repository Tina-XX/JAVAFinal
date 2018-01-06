package nju.huluwa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.*;

public class Field extends JPanel{

    private final int OFFSET = 70;

    ArrayList<Brothers> brother=new ArrayList<>();
    Xiezijing xiezi;
    ArrayList<Monsters> monster=new ArrayList<>();
    static Queue huluQueue;
    Shejing shejing;
    Grandpa yeye;

    private Background background;
    private HuluPic hulupic;
    private XiezijingPic xiezipic;
    private MonsterPic monsterPic;
    private ShejingPic shejingPic;
    private DeadBomb deadBomb;
    private GrandpaPic grandpaPic;
    private boolean deadAll=true;

    static Map map;

    //当前状态
    public boolean win = false;
    public boolean restart=false;
    static protected FileOutputStream out;


    public boolean isDeadAll(){
        return  win;
    }

    ExecutorService exec = Executors.newCachedThreadPool();

   /* private String level =
            "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n";*/
   //复盘存储初始化写文件
   public void InitSave() {
       File file = new File("./record.txt");
       try {
           file.createNewFile();
           out = new FileOutputStream(file);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }


    public Field() {
        InitSave();
        setFocusable(true);
        PutOnCreature();
        addKeyListener(new TAdapter());
    }


    public final void PutOnCreature() {
        map=new Map(15,15);

        //葫芦娃
        for(int i=0;i<7;i++)
            brother.add(new Brothers(COLORS.values()[i],SENIORITY.values()[i],map,this));
        huluQueue=new Queue(brother);

        //蝎子精
        xiezi=new Xiezijing(11,3,map,this);

        //小喽啰
        for(int i=0;i<6;i++)
            monster.add(new Monsters(map,this,i));

        //蛇精
        shejing=new Shejing(14,3,map,this);

        //爷爷
        yeye=new Grandpa(map,this);

        //放上MAP了
        map.PutOnMap(huluQueue,xiezi,monster,shejing,yeye);



    }

    public void buildWorld(Graphics g) {
        ArrayList world = new ArrayList();

        background=new Background(0, 0);
        world.add(background);

        deadAll=true;

        for(int i=0;i<7;i++) {
            hulupic = new HuluPic(OFFSET*huluQueue.getCreat(i).getX(),OFFSET*huluQueue.getCreat(i).getY(),(Brothers) huluQueue.getCreat(i));
            world.add(hulupic);
            if(huluQueue.getCreat(i).IsDead()){
                deadBomb=new DeadBomb(OFFSET*huluQueue.getCreat(i).getX(),OFFSET*huluQueue.getCreat(i).getY());
                world.add(deadBomb);
            }
        }

        xiezipic=new XiezijingPic(OFFSET*xiezi.getX(),OFFSET*xiezi.getY());
        world.add(xiezipic);
        if(xiezi.IsDead()){
            deadBomb=new DeadBomb(OFFSET*xiezi.getX(),OFFSET*xiezi.getY());
            world.add(deadBomb);
        }else {
            deadAll=false;
        }

        for(int i=0;i<6;i++) {
            monsterPic = new MonsterPic(OFFSET*monster.get(i).getX(),OFFSET*monster.get(i).getY());
            world.add(monsterPic);
            if(monster.get(i).IsDead()){
                deadBomb=new DeadBomb(OFFSET*monster.get(i).getX(),OFFSET*monster.get(i).getY());
                world.add(deadBomb);
            }else {
                deadAll=false;
            }
        }

        shejingPic=new ShejingPic(OFFSET*shejing.getX(),OFFSET*shejing.getY());
        world.add(shejingPic);
        if(shejing.IsDead()){
            deadBomb=new DeadBomb(OFFSET*shejing.getX(),OFFSET*shejing.getY());
            world.add(deadBomb);
        }else {
            deadAll=false;
        }

        grandpaPic=new GrandpaPic(OFFSET*yeye.getX(),OFFSET*yeye.getY());
        world.add(grandpaPic);
        if(yeye.IsDead()){
            deadBomb=new DeadBomb(OFFSET*yeye.getX(),OFFSET*yeye.getY());
            world.add(deadBomb);
        }else {
            deadAll=false;
        }


        for (int i = 0; i < world.size(); i++) {

            Thing2D item = (Thing2D) world.get(i);

            if (item instanceof Background){
                g.drawImage(item.getImage(), item.x(), item.y(), 1100,650,this);
            }else if(item instanceof HuluPic) {
                g.drawImage(item.getImage(), item.x() , item.y() , 60,70,this);
            }else if (item instanceof XiezijingPic){
                g.drawImage(item.getImage(), item.x(), item.y(), 95,70,this);
            }else if (item instanceof MonsterPic){
                g.drawImage(item.getImage(), item.x(), item.y(), 75,70,this);
            }else if (item instanceof ShejingPic){
                g.drawImage(item.getImage(), item.x(), item.y(), 50,66,this);
            } else if (item instanceof DeadBomb){
                g.drawImage(item.getImage(), item.x(), item.y(), 40,60,this);
            }else if (item instanceof GrandpaPic){
                g.drawImage(item.getImage(), item.x(), item.y(), 40,60,this);
            }

            if(deadAll)
                win=true;
            if (win&&!restart) {
                DrawWin(g);
                colseSave();
            }

        }
    }

    //赢了！输出点什么
    private void DrawWin(Graphics g){
        g.setFont(new Font("Tahoma",Font.BOLD,26));
        g.setColor(new Color(221, 40, 45));
        g.drawString("WINNER WINNER! CHICKEN DINNER!", 30, 70);
    }

    //写完复盘存储文件，关闭文件
    private void colseSave() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    //一路跟随存储文件
    public void Save(String str) {
        byte bt[] = new byte[1024];
        bt = str.getBytes();
        try {
            out.write(bt, 0, bt.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        for(int i=0;i<7;i++)
            Save(brother.get(i).getName()+" init " + brother.get(i).getX()+" "+brother.get(i).getY()+"\r\n");
        for(int i=0;i<6;i++)
            Save((monster.get(i).getName()+" init " + monster.get(i).getX()+" "+monster.get(i).getY()+"\r\n"));
        Save("14"+" init " + yeye.getX()+" "+yeye.getY()+"\r\n");
        Save("15"+" init " + shejing.getX()+" "+shejing.getY()+"\r\n");
        Save("16"+" init " + xiezi.getX()+" "+xiezi.getY()+"\r\n");
        exec.execute(yeye);
        exec.execute(shejing);
        exec.execute(xiezi);
        for (int i = 0; i < 7; i++) {
            exec.execute(huluQueue.getCreat(i));
            if (i < 6)
                exec.execute(monster.get(i));
        }
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            //空格键开始
            if (key == KeyEvent.VK_SPACE) {
                startGame();
            //L键选择精彩回放文件
            } else if (key == KeyEvent.VK_L) {
                restart = true;
                LoadRecord loadfile = new LoadRecord(brother, xiezi, monster, shejing, yeye);
                loadfile.Loading();
                exec.execute(loadfile);
                repaint();
            }
        }
    }

}