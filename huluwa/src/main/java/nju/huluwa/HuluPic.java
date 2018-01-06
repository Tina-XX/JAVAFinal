package nju.huluwa;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//加载葫芦娃图片
public class HuluPic extends Thing2D{
    public HuluPic(int x, int y,Brothers hulu) {
        super(x, y);
        URL loc=this.getClass().getClassLoader().getResource("hulu1.png");
        switch (hulu.getName()){
            case("1"):loc = this.getClass().getClassLoader().getResource("hulu1.png");
                break;
            case("2"):loc = this.getClass().getClassLoader().getResource("hulu2.png");
                break;
            case("3"):loc = this.getClass().getClassLoader().getResource("hulu3.png");
                break;
            case("4"):loc = this.getClass().getClassLoader().getResource("hulu4.png");
                break;
            case("5"):loc = this.getClass().getClassLoader().getResource("hulu5.png");
                break;
            case("6"):loc = this.getClass().getClassLoader().getResource("hulu6.png");
                break;
            case("7"):loc = this.getClass().getClassLoader().getResource("hulu7.png");
                break;
            default:break;
        }
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
