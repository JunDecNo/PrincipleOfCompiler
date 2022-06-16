import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class tes extends JFrame {
    tes(){
        setVisible(true);
        //主题图片
        JLabel labImg = new JLabel();
        Image fileImg = new ImageIcon("E:\\LearnFile\\DIP\\sy_one\\lotus.png").getImage();
        Image titleImg = Utils.imageScale(fileImg, 1, 1);
        labImg.setIcon(new ImageIcon(titleImg));
        labImg.setBounds(60,100,titleImg.getWidth(null),titleImg.getHeight(null));
        add(labImg);

    }
    public static void main(String[]agrs){
        new tes();

    }

}
