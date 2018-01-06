package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载蛇精图片
public class ShejingPic extends Thing2D{
    public ShejingPic(int x, int y) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("shejing.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
