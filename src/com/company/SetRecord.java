package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
//Описываем класс, который устанавливает рекорд
class SetRecord {
    MyFrame mf;
    long k;
    String s;
    int d;
    public SetRecord(MyFrame mf,long k, String s, int d) {
        this.mf=mf;
        this.k=k;
        this.s=s;
        this.d=d;
        setRecord();
    }
    //метод который устанавливет рекорд
    public void setRecord() {
        try {
            Scanner sc=new Scanner(new File("records.txt"));
            for (int i=1;i<d;i++) {
                for (int j=1;j<=4;j++) {
                    sc.nextLine();
                }
            }
            int m=sc.nextInt();
            int s1=sc.nextInt();
            if ((m==k/60 && k%60<s1) || m>k/60) {
                new CongratsFrame(this,mf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Записываем новый рекорд в файл
    public void write(String s1) {
        try {
            Scanner sc=new Scanner(new File("records.txt"));
            FileWriter fw=new FileWriter("records1.txt");
            fw.write("");
            int i;
            for (i=1;i<d;i++) {
                for (int j = 1; j <= 4; j++) {
                    String s= sc.nextLine();
                    fw.write(s+"\n");
                }
            }
            fw.write(k/60+" "+k%60+"\n");
            fw.write(s+"\n");
            fw.write("          \n");
            fw.write(s1+"\n");
            for (int j = 1; j <= 4; j++) {
                sc.nextLine();
            }
            for (int g=i;g<3;g++) {
                for (int j = 1; j <= 4; j++) {
                    String s= sc.nextLine();
                    fw.write(s+"\n");
                }
            }
            sc.close();
            fw.close();
            FileWriter fw1 = new FileWriter("records.txt");
            Scanner sc1=new Scanner(new File("records1.txt"));
            for (int g=1;g<=3;g++) {
                for (int j = 1; j <= 4; j++) {
                    String s= sc1.nextLine();
                    fw1.write(s+"\n");
                }
            }
            sc1.close();
            fw1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new RecordsFrame();
    }
}
