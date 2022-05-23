package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Создаем класс который определяет ячейки
class CellPanel extends JPanel implements MouseListener {
    MyFrame mf;
    RecordImage ri;
    CellRecord cr;
    int i1,j1;
    public CellPanel(MyFrame mf, CellRecord cr, RecordImage ri) {
        super();
        this.cr=cr;
        this.ri=ri;
        this.mf=mf;
        this.i1=cr.i1;
        this.j1=cr.j1;
        setBounds(cr.x,cr.y,16,16);
        setLayout(null);
        addMouseListener(this);
    }
    int p=-100;
    //С помощью методов MouseListener реализовуем алгоритм плавного нажатия на ячейки
    public void mousePressed (MouseEvent me) {
        if (me.getButton() == 1) {
            if (cr.a[i1][j1] >= 0 && cr.a[i1][j1] <= 100) {
                p = 0;
                mf.f = 0;
                repaint();
            } else if ((cr.a[i1][j1] >= 1001 && cr.a[i1][j1] <= 1008)) {
                mf.g = 0;
            }
        } else if (me.getButton() == 3)
            mf.clickHandler(i1, j1, me.getButton());
    }
    public void	mouseClicked(MouseEvent e){}
    public void	mouseReleased(MouseEvent me){
        if (cr.a[i1][j1]!=1000 && mf.f==0) {
            mf.clickHandler(i1, j1, me.getButton());
            mf.f = 1;
        }
        else if (cr.a[i1][j1]!=1000 && mf.g==0) {
            mf.clickHandler(i1, j1, me.getButton());
            mf.g = 1;
        }
    }
    public void	mouseEntered(MouseEvent e){
        if (mf.f==0) {
            p = 0;
            repaint();
        }
    }
    public void	mouseExited(MouseEvent e){
        if (mf.f==0) {
            p = -100;
            repaint();
        }
        mf.f=1;
    }
    public void draw(int p1) {
        p = p1;
        repaint();
    }
    //В зависимости от параметра р устанавливаем картинку
    public void paint (Graphics g) {
        super.paint(g);
        switch (p) {
            case -100:
                g.drawImage(ri.but, 0, 0, 16, 16, null);
                break;
            case 100:
                g.drawImage(ri.bombr, 0, 0, 16, 16, null);
                break;
            case 101:
                g.drawImage(ri.bomb, 0, 0, 16, 16, null);
                break;
            case 102:
                g.drawImage(ri.flag, 0, 0, 16, 16, null);
                break;
            case 103:
                g.drawImage(ri.flagw, 0, 0, 16, 16, null);
                break;
            case 0:
                g.drawImage(ri.img0, 0, 0, 16, 16, null);
                break;
            case 1:
                g.drawImage(ri.img1, 0, 0, 16, 16, null);
                break;
            case 2:
                g.drawImage(ri.img2, 0, 0, 16, 16, null);
                break;
            case 3:
                g.drawImage(ri.img3, 0, 0, 16, 16, null);
                break;
            case 4:
                g.drawImage(ri.img4, 0, 0, 16, 16, null);
                break;
            case 5:
                g.drawImage(ri.img5, 0, 0, 16, 16, null);
                break;
            case 6:
                g.drawImage(ri.img6, 0, 0, 16, 16, null);
                break;
            case 7:
                g.drawImage(ri.img7, 0, 0, 16, 16, null);
                break;
            case 8:
                g.drawImage(ri.img8, 0, 0, 16, 16, null);
                break;
        }
    }
}
