package com.company;

import java.util.Date;
//Описываем класс, который создает поток, считающий время
class TimeThread extends Thread {
    public static void main(String[] args)  {}
    TimeLabel timelab;
    Boolean f=true;
    long s;
    public TimeThread(TimeLabel timelab) {
        this.timelab=timelab;
    }
    public void run() {
        Date d1=new Date();
        long z = d1.getTime();
        while(f) {
            try {
                if (!isInterrupted())
                    Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date d2 = new Date();
            s = d2.getTime();
            s = s - z;
            s = s / 1000;
            if (s>59*61)
                f=false;
            else if (f)
                timelab.count(s);
        }
    }
}
