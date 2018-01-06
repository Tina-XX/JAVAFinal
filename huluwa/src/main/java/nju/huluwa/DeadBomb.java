package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//生物死亡之后会有爆炸特效
public class DeadBomb extends Thing2D{
    public DeadBomb(int x, int y) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("dead.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
