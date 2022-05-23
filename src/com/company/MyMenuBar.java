package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
//Описываем класс, который создает меню
class MyMenuBar extends JMenuBar {
    MyFrame mf;
    JMenu mReference, mGame, mHelp;
    JMenuItem again, easy, medium, hard, shb, hb, chmp, about;
    public MyMenuBar(MyFrame mf) {
        super();
        setBounds(0,0,1366,20);
        this.mf=mf;
        //Создаем пункты меню
        Font f = new Font ("TimesRoman", Font.PLAIN, 13);
        mGame = new JMenu();
        mGame.setFont(f);
        mGame.setText("Game");
        mHelp = new JMenu();
        mHelp.setFont(f);
        mHelp.setText("Help");
        mReference = new JMenu();
        mReference.setFont(f);
        mReference.setText("Reference");
        again = new JMenuItem();
        again.setFont(f);
        again.setText("Again");
        easy = new JMenuItem();
        easy.setFont(f);
        easy.setText("Easy (field 9x9, 10 bombs)");
        medium = new JMenuItem();
        medium.setFont(f);
        medium.setText("Medium (field 16x16, 40 bombs)");
        hard = new JMenuItem();
        hard.setFont(f);
        hard.setText("Hard (field 16x30, 99 bombs)");
        shb = new JMenuItem();
        shb.setFont(f);
        shb.setText("Show bombs");
        hb = new JMenuItem();
        hb.setFont(f);
        hb.setText("Hide bombs");
        chmp = new JMenuItem();
        chmp.setFont(f);
        chmp.setText("Champions");
        about = new JMenuItem();
        about.setFont(f);
        about.setText("About Program");
        //Устанавливаем горячие клавиши
        mGame.setMnemonic(KeyEvent.VK_ALT);
        again.setAccelerator(KeyStroke.getKeyStroke("F2"));
        easy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        medium.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        hard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        //Добавляем слушатель событий
        again.addActionListener(new MenuActionListener(mf,this));
        easy.addActionListener(new MenuActionListener(mf,this));
        medium.addActionListener(new MenuActionListener(mf,this));
        hard.addActionListener(new MenuActionListener(mf,this));
        shb.addActionListener(new MenuActionListener(mf,this));
        hb.addActionListener(new MenuActionListener(mf,this));
        chmp.addActionListener(new MenuActionListener(mf,this));
        about.addActionListener(new MenuActionListener(mf,this));
        //Добавляем пункты меню
        mGame.add(again);
        mGame.addSeparator();
        mGame.add(easy);
        mGame.add(medium);
        mGame.add(hard);
        mGame.addSeparator();
        mGame.add(chmp);
        mReference.add(about);
        add(mGame);
        mHelp.add(shb);
        mHelp.add(hb);
        add(mHelp);
        add(mReference);
        mf.add(this);
    }
}
//Описываем класс, который обрабатывает событие при нажатии на пункт меню
class MenuActionListener implements ActionListener {
    MyFrame mf;
    MyMenuBar mb;
    public MenuActionListener(MyFrame mf, MyMenuBar mb) {
        this.mf=mf;
        this.mb=mb;
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mb.again) {
            mf.getContentPane().setBackground((new Color(64,64,64)));
            mf.createField(mf.n, mf.m, mf.k);
        }
        if (ae.getSource() == mb.easy) {
            mf.getContentPane().setBackground(new Color(64,64,64));
            mf.createField(9,9,10);
        }
        if (ae.getSource() == mb.medium) {
            mf.getContentPane().setBackground(new Color(64,64,64));
            mf.createField(16,16,40);
        }
        if (ae.getSource() == mb.hard) {
            mf.getContentPane().setBackground(new Color(64,64,64));
            mf.createField(16,30,99);
        }
        if (ae.getSource() == mb.shb) {
            mf.showbombs();
        }
        if (ae.getSource() == mb.hb) {
            mf.hidebombs();
        }
        if (ae.getSource() == mb.chmp) {
            new RecordsFrame();
        }
        if (ae.getSource() == mb.about) {
            new AboutProgramFrame(mf);
        }
    }
}
