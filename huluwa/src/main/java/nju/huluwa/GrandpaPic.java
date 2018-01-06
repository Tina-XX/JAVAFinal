package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载爷爷图片
public class GrandpaPic extends Thing2D{
    public GrandpaPic(int x, int y) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("grandpa.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
