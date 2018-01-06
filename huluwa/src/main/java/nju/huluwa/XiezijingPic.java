package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载蝎子精图像
public class XiezijingPic extends Thing2D{
    public XiezijingPic(int x, int y) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("xiezi.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
