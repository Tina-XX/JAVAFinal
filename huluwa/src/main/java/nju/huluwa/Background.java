package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载背景图片
public class Background extends Thing2D{
    public Background(int x, int y) {
        super(x, y);
        URL loc = this.getClass().getClassLoader().getResource("background.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
