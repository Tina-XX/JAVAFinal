package nju.huluwa;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

//复盘程序
public class LoadRecord implements Runnable{
    ArrayList<Brothers> brother=new ArrayList<>();
    Xiezijing xiezi;
    ArrayList<Monsters> monster=new ArrayList<>();
    Shejing shejing;
    Grandpa yeye;

    Field field;

    String filename;
    BufferedReader in = null;
    File file;

    public class FileDialogTest extends Frame
    {
        TextField fileName = new TextField(50);
        FileDialog fileDialog = new FileDialog(this, "请选择一个要打开的文件：", FileDialog.LOAD);
        public FileDialogTest()
        {
            //限定文件后缀名必须为.txt
            fileDialog.setFilenameFilter(new FilenameFilter() {
                public boolean accept(File dir, String name)
                {
                    return name.endsWith(".txt");
                }
            });
            fileDialog.setVisible(true);
            fileDialog.getFilenameFilter().accept(new File(fileDialog.getDirectory()), fileDialog.getFile());
            filename = fileDialog.getDirectory()+fileDialog.getFile();
            System.out.print(filename);
        }
    }
    public LoadRecord(ArrayList<Brothers> brothers,Xiezijing xie,ArrayList<Monsters> mon,Shejing shejing,Grandpa yeye){
        this.xiezi=xie;
        this.shejing=shejing;
        this.yeye=yeye;
        for(int i=0;i<6;i++)
            this.monster.add(mon.get(i));
        for(int i=0;i<7;i++)
            this.brother.add(brothers.get(i));
        this.field=xiezi.field;
    }

    public void Loading(){
        new FileDialogTest();
    }

    public void run(){
        synchronized (field) {
            Creature creature;
            file = new File(filename);
            try {
                in = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            try {
                while ((tempString = in.readLine()) != null) {
                    // 显示行号
                    System.out.println(tempString);
                    String command[] = tempString.split(" ");
                    switch (command[0]) {
                        case "1":
                            creature = brother.get(0);
                            break;
                        case "2":
                            creature = brother.get(1);
                            break;
                        case "3":
                            creature = brother.get(2);
                            break;
                        case "4":
                            creature = brother.get(3);
                            break;
                        case "5":
                            creature = brother.get(4);
                            break;
                        case "6":
                            creature = brother.get(5);
                            break;
                        case "7":
                            creature = brother.get(6);
                            break;
                        case "8":
                            creature = monster.get(0);
                            break;
                        case "9":
                            creature = monster.get(1);
                            break;
                        case "10":
                            creature = monster.get(2);
                            break;
                        case "11":
                            creature = monster.get(3);
                            break;
                        case "12":
                            creature = monster.get(4);
                            break;
                        case "13":
                            creature = monster.get(5);
                            break;
                        case "14":
                            creature = yeye;
                            break;
                        case "15":
                            creature = shejing;
                            break;
                        case "16":
                            creature = xiezi;
                            break;
                        default:
                            creature = yeye;
                            break;
                    }
                    if (command[1].equals("move")) {
                        creature.setXY(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                        try {
                            this.field.repaint();
                            Thread.sleep(80);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (command[1].equals("dead")) {
                        creature.beaten();
                        try {
                            this.field.repaint();
                            Thread.sleep(80);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (command[1].equals("init")) {
                        creature.setXY(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                        creature.setLive();
                        if (command[0].equals("16")) {
                            try {
                                this.field.repaint();
                                Thread.sleep(80);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    line++;

                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        field.restart=false;
        this.field.repaint();
    }

}


