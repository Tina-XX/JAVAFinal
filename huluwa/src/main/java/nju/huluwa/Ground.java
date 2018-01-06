package nju.huluwa;


import javax.swing.JFrame;


public final class Ground extends JFrame {
    public Ground() {
        InitUI();
    }

    public void InitUI() {
        Field field = new Field();
        add(field);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650 );
        setLocationRelativeTo(null);
        setTitle("FIGHT!!!");
    }

}