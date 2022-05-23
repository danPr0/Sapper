package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
////Описываем класс, который создает окно с рекордами
class RecordsFrame extends JFrame {
    LevelLabel le,lm,lh;
    ResultLabel ler,lmr,lhr;
    Image chmp;
    public RecordsFrame() {
        super("Champions");
        setBounds(250,200,300,175);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        getContentPane().add(new ResetButton(this));
        champ();
    }
    public void champ() {
        le=new LevelLabel("Easy:",5);
        lm=new LevelLabel("Medium:",35);
        lh=new LevelLabel("Hard:",65);
        getContentPane().add(le);
        getContentPane().add(lm);
        getContentPane().add(lh);
        //Считываем рекорды(имя и время) из текстового файла и записываем их в текстовое поле
        try {
            chmp = ImageIO.read(new File("Cup.png"));
            Scanner sc=new Scanner(new File("records.txt"));
            String s1,s2,s3;
            sc.nextLine();
            s1=sc.nextLine();
            s2=sc.nextLine();
            s3=sc.nextLine();
            ler=new ResultLabel(s1,s2,s3,5);
            sc.nextLine();
            s1=sc.nextLine();
            s2=sc.nextLine();
            s3=sc.nextLine();
            lmr=new ResultLabel(s1,s2,s3,35);
            sc.nextLine();
            s1=sc.nextLine();
            s2=sc.nextLine();
            s3=sc.nextLine();
            lhr=new ResultLabel(s1,s2,s3,65);
            getContentPane().add(ler);
            getContentPane().add(lmr);
            getContentPane().add(lhr);
            setIconImage(chmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reset() {
        ler.setText("59 : 59          Anonymous");
        lmr.setText("59 : 59          Anonymous");
        lhr.setText("59 : 59          Anonymous");
    }
}
//Описываем класс, который добавляет кнопку, которая сбрасывает все результаты
class ResetButton extends JButton implements ActionListener {
    RecordsFrame recFr;
    public ResetButton(RecordsFrame recFr) {
        super();
        this.recFr=recFr;
        setBounds(50,105,180,20);
        setBackground(new Color(225,225,225));
        JLabel l=new JLabel();
        setLayout(null);
        l.setBounds(0,0,180,20);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        l.setText("Reset all results");
        l.setHorizontalAlignment(CENTER);
        add(l);
        addActionListener(this);
    }
    //При нажатие записываем самые большие значения в файл с рекордами
    public void actionPerformed(ActionEvent ae){
        try {
            FileWriter fw=new FileWriter("records.txt");
            fw.write("");
            for(int i=0;i<3;i++) {
                fw.write("59 59\n");
                fw.write("59 : 59\n");
                fw.write("          \n");
                fw.write("Anonymous\n");
            }
            recFr.reset();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}