package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Описываем класс, который создаст модальное окно в котором нужно ввести свой никнейм
class CongratsFrame extends JDialog {
    SetRecord sr;
    JTextField tf;
    MyFrame mf;
    Image congr;
    public CongratsFrame(SetRecord sr, MyFrame mf) {
        super(mf,Dialog.ModalityType.DOCUMENT_MODAL);
        this.sr=sr;
        this.mf=mf;
        setBounds(300,200,260,160);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Congrats!");
        setImage();
        JLabel l=new JLabel();
        l.setBounds(15,10,225,60);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        if (sr.d==1) {
            l.setText("<html>You're the Champion (Easy)!<br/>Enter your nickname:</html>");
        }
        if (sr.d==2) {
            l.setText("<html>You're the Champion (Medium)!<br/>Enter your nickname:</html>");
        }
        if (sr.d==3) {
            l.setText("<html>You're the Champion (Hard)!<br/>Enter your nickname:</html>");
        }
        tf=new JTextField();
        tf.setBounds(30,80,100,22);
        OkButton ok=new OkButton(sr,this);
        getContentPane().add(l);
        getContentPane().add(tf);
        getContentPane().add(ok);
        setVisible(true);
    }
    //добавляем иконку окна
    public void setImage() {
        try {
            congr = ImageIO.read(new File("Cup.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(congr);
    }
}
//Описывает класс, который создает кнопку, при нажатии на которую вызывается класс, который устанавливает рекорд
class OkButton extends JButton implements ActionListener {
    SetRecord sr;
    CongratsFrame congFr;
    public OkButton(SetRecord sr,CongratsFrame congFr) {
        super("OK");
        this.sr=sr;
        this.congFr=congFr;
        setBounds(160,80,55,22);
        addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        congFr.dispose();
        sr.write(congFr.tf.getText());
    }
}
