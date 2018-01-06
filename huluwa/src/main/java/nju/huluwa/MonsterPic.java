package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载小罗喽图片
public class MonsterPic extends Thing2D{
    public MonsterPic(int x, int y) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("monster.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
