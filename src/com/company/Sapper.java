package com.company;
//Подключаем нужные нам библиотеки
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
class Sapper {
    public static void main(String[] args) {
        //Создаем окно
        new MyFrame();
    }
}
//создаем класс в котором будут храниться параметры каждой ячейки (запись)
class CellRecord {
    int i1, j1, x, y;
    int[][] a;
    int[][] b;
    public CellRecord( int i1, int j1, int x, int y, int[][] a, int[][] b){
        this.i1 = i1;
        this.j1 = j1;
        this.x = x;
        this.y = y;
        this.a = a;
        
        this.b = b;
    }
}
//создаем класс в котором будут храниться изображения (запись)
class RecordImage {
    Image but, bomb, bombr, bombm, flag, flagw, img0, img1, img2, img3, img4, img5, img6, img7, img8;
    public RecordImage() {
        try {
            but = ImageIO.read(new File("But.png"));
            bomb = ImageIO.read(new File("Bomb.png"));
            bombr = ImageIO.read(new File("BombR.png"));
            bombm = ImageIO.read(new File("BombM.png"));
            flag = ImageIO.read(new File("Flag.png"));
            flagw = ImageIO.read(new File("FlagW.png"));
            img0 = ImageIO.read(new File("Number0.png"));
            img1 = ImageIO.read(new File("Number1.png"));
            img2 = ImageIO.read(new File("Number2.png"));
            img3 = ImageIO.read(new File("Number3.png"));
            img4 = ImageIO.read(new File("Number4.png"));
            img5 = ImageIO.read(new File("Number5.png"));
            img6 = ImageIO.read(new File("Number6.png"));
            img7 = ImageIO.read(new File("Number7.png"));
            img8 = ImageIO.read(new File("Number8.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class MyFrame extends JFrame  {
    //Объявляем запись, которая будет хранить изображения
    RecordImage ri;
    //Объявляем поля для текста
    TextLabel bombstext, timetext;
    LeftBombsLabel bombsleft;
    TimeLabel timelab;
    //Объявляем поток, который будет считать время
    TimeThread timecount;
    public MyFrame() {
        //Вызываем конструктор родительского класса и передаем туда имя нашего окна
        super("Sapper");
        //Определяем параметры окна(цвет и размер)
        setBounds(200,100,506, 350);
        getContentPane().setBackground(new Color(64, 64, 64));
        //Вызываем менеджер компановки
        getContentPane().setLayout(null);
        //Назначем операцию на закрытие окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Создаем панель меню
        new MyMenuBar(this);
        bombstext=new TextLabel("Bombs left:", 5, 25,90,20);
        timetext=new TextLabel("Time:", 300,25,45,20);
        bombsleft=new LeftBombsLabel(110,25,20,20);
        timelab=new TimeLabel(360,25,50,20);
        getContentPane().add(bombstext);
        getContentPane().add(timetext);
        getContentPane().add(bombsleft);
        getContentPane().add(timelab);
        //Делаем окно видимым
        setVisible(true);
        //Создаем класс, который будет хранить изобажения
        ri=new RecordImage();
        //Вызываем метод, который добавит поле 9*9 с 10 бомбами
        createField(9,9,10);
        //Добавляем иконку окна
        setIconImage(ri.bombm);
    }
    //Создаем массив, который будет показывать для каждого элемента, сколько мин вокруг
    //Если ячейка будет открыта, то ее значение в массиве a[][] увеличивается на 1000
    int[][] a=new int[18][32];
    //Создаем массив бомб (0 и 1)
    int[][] b=new int[18][32];
    int n=0,m=0,k=10,r,v,g=1,f=1,k3=k,h=0;
    //Создаем массив обЪектов, которые являються ссылками на ячейки
    CellPanel[][] cp=new CellPanel[19][31];
    //Объявляем метод, который добавляет поле
    public void createField(int n1, int m1, int k1) {
        v=0;
        //Если время шло, то останавливаем поток
        if (h==1) {
            timecount.f=false;
            timelab.setText("00 : 00");
            h=0;
        }
        //Зарисовуем старое поле(только те ячейки, которые в этом нуждаются)
        for (int i=1;i<=n;i++) {
            for (int j=1;j<=m;j++) {
                if ((a[i][j]>=1000 && a[i][j]<=1008) || a[i][j]==102 || a[i][j]==100) {
                    cp[i][j].draw(-100);
                }
            }
        }
        //Если новое полем меньше старого, то удаляем ненужные ячейки, оставляя нужные для нового поля
        if (m1<m && m!=0) {
            for (int i=n1+1;i<=n;i++) {
                for (int j=1;j<=m;j++) {
                    remove(cp[i][j]);
                }
            }
            for (int i=1;i<=n;i++) {
                for (int j=m1+1;j<=m;j++) {
                    remove(cp[i][j]);
                }
            }
        }
        //Обнуляем массив цифр
        for (int i=1;i<=n1;i++) {
            for (int j=1;j<=m1;j++) {
                a[i][j]=0;
            }
        }
        //Добавляем границы массива цифр
        for (int i=0;i<n1+2;i++) {
            a[i][0] = 10000;
            a[i][m1+1] = 10000;
        }
        for (int j=0;j<m1+2;j++) {
            a[0][j] = 10000;
            a[n1+1][j] = 10000;
        }
        //Обнуляем массив бомб
        for (int i=1;i<=16;i++) {
            for (int j=1;j<=30;j++) {
                b[i][j]=0;
            }
        }
        //Распределяем мины по полю случайным образаом
        for(int i=1;i<=k1;) {
            int d=(int)(Math.random()*(n1)+1);
            int c=(int)(Math.random()*(m1)+1);
            if (b[d][c]==0) {
                b[d][c]=1;
                i++;
            }
        }
        //Инициализируем массив цифр
        for (int i=1;i<=n1;i++) {
            for (int j=1;j<=m1;j++) {
                if (b[i][j]==0)
                    a[i][j]=b[i][j-1]+b[i-1][j-1]+b[i+1][j-1]+b[i-1][j]+b[i+1][j]+b[i][j+1]+b[i+1][j+1]+b[i-1][j+1];
                else a[i][j]=100;
            }
        }
        //Добавляем недостающие нам ячейки(панельки), если новое поле больше старого
        for (int i=n+1;i<=n1;i++) {
            for (int j=1;j<=m1;j++) {
                cp[i][j]=new CellPanel(this, new CellRecord(i,j,(j-1)*16+5,(i-1)*16+50,a,b), ri);
                getContentPane().add(cp[i][j]);
            }
        }
        if (n!=0) {
            for (int i=1;i<=n;i++) {
                for (int j=m+1;j<=m1;j++) {
                    cp[i][j]=new CellPanel(this, new CellRecord(i,j,(j-1)*16+5,(i-1)*16+50,a,b), ri);
                    getContentPane().add(cp[i][j]);
                }
            }
        }
        //Характеристики старого поля заменяем на новые
        n=n1;
        m=m1;
        k=k1;
        k3=k;
        //Обнуляем счетчик мин
        bombsleft.count(k);
        //Перерисовуем окно
        repaint();
    }
    //Осущевствляем рекурсивный алгоритм "Нуль-области", чтобы при нажатии на поле, рядом с которым нет мин,
    //  открывались и другие такие же поля
    int k2=-1;
    //Объявляем рекусивную функцию
    public void zero(int i, int j) {
        if (a[i][j]==0) {
            a[i][j]=k2;
            zero(i+1,j);
            zero(i-1,j);
            zero(i,j+1);
            zero(i,j-1);
            zero(i-1,j+1);
            zero(i-1,j-1);
            zero(i+1,j+1);
            zero(i+1,j-1);
        }
    }
    //Объявляем метод, который обрабатывает результат рекурсивной функции
    public void nul(int i, int j) {
        zero(i, j);
        for (int i1 = 1; i1 <= n; i1++) {
            for (int j1 = 1; j1 <= m; j1++) {
                if (a[i1][j1] == k2) {
                    cp[i1][j1].draw(0);
                    a[i1][j1]=1000;
                    if (a[i1 - 1][j1] >= 1 && a[i1 - 1][j1] <= 8) {
                        cp[i1 - 1][j1].draw(a[i1 - 1][j1]);
                        a[i1 - 1][j1]+=1000;
                    }
                    if (a[i1 + 1][j1] >= 1 && a[i1 + 1][j1] <= 8) {
                        cp[i1 + 1][j1].draw(a[i1 + 1][j1]);
                        a[i1 + 1][j1]+=1000;
                    }
                    if (a[i1][j1 - 1] >= 1 && a[i1][j1 - 1] <= 8) {
                        cp[i1][j1 - 1].draw(a[i1][j1 - 1]);
                        a[i1][j1 - 1]+=1000;
                    }
                    if (a[i1 - 1][j1 - 1] >= 1 && a[i1 - 1][j1 - 1] <= 8) {
                        cp[i1 - 1][j1 - 1].draw(a[i1 - 1][j1 - 1]);
                        a[i1 - 1][j1 - 1]+=1000;
                    }
                    if (a[i1 + 1][j1 - 1] >= 1 && a[i1 + 1][j1 - 1] <= 8) {
                        cp[i1 + 1][j1 - 1].draw(a[i1 + 1][j1 - 1]);
                        a[i1 + 1][j1 - 1]+=1000;
                    }
                    if (a[i1][j1 + 1] >= 1 && a[i1][j1 + 1] <= 8) {
                        cp[i1][j1 + 1].draw(a[i1][j1 + 1]);
                        a[i1][j1 + 1]+=1000;
                    }
                    if (a[i1 - 1][j1 + 1] >= 1 && a[i1 - 1][j1 + 1] <= 8) {
                        cp[i1 - 1][j1 + 1].draw(a[i1 - 1][j1 + 1]);
                        a[i1 - 1][j1 + 1]+=1000;
                    }
                    if (a[i1 + 1][j1 + 1] >= 1 && a[i1 + 1][j1 + 1] <= 8) {
                        cp[i1 + 1][j1 + 1].draw(a[i1 + 1][j1 + 1]);
                        a[i1 + 1][j1 + 1]+=1000;
                    }
                }

            }
        }
        k2--;
    }
    //Объявляем метод который покажет нам все бомбы на поле
    public void showbombs() {
        for (int i=1;i<=n;i++) {
            for (int j=1;j<=m;j++) {
                if (a[i][j]==100) {
                    cp[i][j].draw(101);
                }
            }
        }
    }
    //Объявляем метод который спрячет все бомбы на поле
    public void hidebombs() {
        for (int i=1;i<=n;i++) {
            for (int j=1;j<=m;j++) {
                if (a[i][j]==100) {
                    cp[i][j].draw(-100);
                }
            }
        }
    }
    //Объявляем метод, который будет обрабатывать наше нажатие
    public void clickHandler(int i, int j, int f) {
        if (h==0 && a[i][j]!=100) {
            timecount=new TimeThread(timelab);
            timecount.start();
            timelab.setText("00 : 00");
            h=1;
        }
        if (f == 3 &&  a[i][j]<1000) { //если была нажата правая кнопка мыши и ячейка не открыта
            if (a[i][j] != 102 && k3>0) { //если нет флага то ставим его
                cp[i][j].draw(102);
                a[i][j] = 102;
                k3--;
                bombsleft.count(k3);
            }
            else if (a[i][j] == 102) { //если флаг есть, то убираем его
                cp[i][j].draw(-100);
                a[i][j] = b[i][j - 1] + b[i - 1][j - 1] + b[i + 1][j - 1] + b[i - 1][j] + b[i + 1][j] + b[i][j + 1] +
                        b[i + 1][j + 1] + b[i - 1][j + 1];
                k3++;
                bombsleft.count(k3);
            }
        }
        if (f == 1 && a[i][j]!=102) { //если была нажата левая кнопка мыши и ячейка без флага
            if (a[i][j]>1000) {//если ячейка уже нажата то, проверим можем ли мы открыть все ячейки вокруг
                                //нажав на данную ячейку(это можно сделать  только если кол-во флагов
                                // вокруг ячейки равняется кол-ву бомб
               //считаем флаги
               int g=0;
               if (a[i][j-1]==102) g++;
               if (a[i][j+1]==102) g++;
               if (a[i-1][j-1]==102) g++;
               if (a[i-1][j+1]==102) g++;
               if (a[i-1][j]==102) g++;
               if (a[i+1][j-1]==102) g++;
               if (a[i+1][j+1]==102) g++;
               if (a[i+1][j]==102) g++;
               r=0;
               //если кол-во флагов равно кол-ву бомб то проверяем все ячейки вокруг и если можно то открываем
                //если в какой-то ячейке бомба то все бомбы взрываются, а неправильно поставленный флаг
                //показывается пользователю определнной картинкой
               if (a[i][j]-1000==g) {
                  if (a[i][j-1]>=1 && a[i][j-1]<=100) {
                      if (a[i][j - 1] == 100)
                          r++;
                      cp[i][j - 1].draw(a[i][j - 1]);
                      a[i][j-1]+=1000;
                  }
                  else if (a[i][j-1]==0) nul(i,j-1);
                   if (a[i][j+1]>=1 && a[i][j+1]<=100) {
                       if (a[i][j + 1] == 100)
                           r++;
                       cp[i][j + 1].draw(a[i][j + 1]);
                       a[i][j+1]+=1000;
                   }
                   else if (a[i][j+1]==0) nul(i,j+1);
                   if (a[i-1][j-1]>=1 && a[i-1][j-1]<=100) {
                       if (a[i - 1][j - 1] == 100)
                           r++;
                       cp[i - 1][j - 1].draw(a[i - 1][j - 1]);
                       a[i-1][j-1]+=1000;
                   }
                   else if (a[i-1][j-1]==0) nul(i-1,j-1);
                   if (a[i-1][j+1]>=1 && a[i-1][j+1]<=100) {
                       if (a[i - 1][j + 1] == 100)
                           r++;
                       cp[i - 1][j + 1].draw(a[i - 1][j + 1]);
                       a[i-1][j+1]+=1000;
                   }
                   else if (a[i-1][j+1]==0) nul(i-1,j+1);
                   if (a[i-1][j]>=1 && a[i-1][j]<=100) {
                       if (a[i - 1][j] == 100)
                           r++;
                       cp[i - 1][j].draw(a[i - 1][j]);
                       a[i-1][j]+=1000;
                   }
                   else if (a[i-1][j]==0) nul(i-1,j);
                   if (a[i+1][j-1]>=1 && a[i+1][j-1]<=100) {
                       if (a[i + 1][j - 1] == 100)
                           r++;
                       cp[i + 1][j - 1].draw(a[i + 1][j - 1]);
                       a[i+1][j-1]+=1000;
                   }
                   else if (a[i+1][j-1]==0) nul(i+1,j-1);
                   if (a[i+1][j+1]>=1 && a[i+1][j+1]<=100) {
                       if (a[i + 1][j + 1] == 100)
                           r++;
                       cp[i + 1][j + 1].draw(a[i + 1][j + 1]);
                       a[i+1][j+1] += 1000;
                   }
                   else if (a[i+1][j+1]==0) nul(i+1,j+1);
                   if (a[i+1][j]>=1 && a[i+1][j]<=100) {
                       if (a[i + 1][j] == 100)
                           r++;
                       cp[i + 1][j].draw(a[i + 1][j]);
                       a[i + 1][j] += 1000;
                   }
                   else if (a[i+1][j]==0) nul(i+1,j);
               }
               //обрабатываем ситуацию если была нажата бомба
               if (r>0) {
                   getContentPane().setBackground(new Color(64, 0, 0));
                   if (h==1) {
                       timecount.f=false;
                   }
                   v=1;
                   for (int i1 = 1; i1 <= n; i1++) {
                       for (int j1 = 1; j1 <= m; j1++) {
                           if (b[i1][j1]==0 && a[i1][j1]==102)
                               cp[i1][j1].draw(103);
                           else if (b[i1][j1] == 1 && a[i1][j1]!=102 && a[i1][j1]!=1100)
                               cp[i1][j1].draw(101);
                           //всем элементам массива присваиваем 1000, чтобы не происходило больше никаких нажатий
                           a[i1][j1] = 1000;
                       }
                   }
               }
            }
            else if (a[i][j] != 0 && b[i][j] != 1) { //если ячейка была нажата и это не бомба то открываем ее
                cp[i][j].draw(a[i][j]);
                a[i][j]+=1000;
            }
            if (b[i][j] == 1) { //если это бомба-конец игры
                cp[i][j].draw(100);
                v=1;
                getContentPane().setBackground(new Color(64, 0, 0));
                if (h==1) {
                    timecount.f=false;
                }
                for (int i1 = 1; i1 <= n; i1++) {
                    for (int j1 = 1; j1 <= m; j1++) {
                        if (b[i1][j1]==0 && a[i1][j1]==102)
                            cp[i1][j1].draw(103);
                        else if (b[i1][j1] == 1 && a[i1][j1]!=102 &! (i1==i && j1==j)) {
                            cp[i1][j1].draw(101);
                        }
                        a[i1][j1] = 1000;
                    }
                }
            }
            if (a[i][j]==0) //если в ячейке ноль то выполняем рекурсивный алгоритм "Нуль-области"
                nul(i,j);
        }
        //Проверяем на выигрыш
        int q=0;
        for (int i1=1;i1<=n;i1++) {
            for (int j1=1;j1<=m;j1++) {
               if (a[i1][j1]==102 || a[i1][j1]>=1000)
                   q++;
            }
        }
        if (q==n*m && v==0) {
            getContentPane().setBackground(new Color(0, 64, 0));
            for (int i1=1;i1<=n;i1++) {
                for (int j1=1;j1<=m;j1++) {
                    a[i1][j1]=1000;
                }
            }
            timecount.f=false;
            int d1=1;
            switch (m) {
                case 9: {
                    d1 = 1;
                    break;
                }
                case 16: {
                    d1 = 2;
                    break;
                }
                case 30: {
                    d1 = 3;
                    break;
                }
            }
            //Создаем класс, который устанавлвает рекорд
            new SetRecord(this, timelab.k, timelab.s1, d1);
        }
    }
}
//Описываем класс, который добавляет элементы текста на окно
class TextLabel extends JLabel {
    public TextLabel(String s, int x, int y,int w,int h) {
        super();
        setBounds(x,y,w,h);
        setOpaque(false);
        setLayout(null);
        setForeground(Color.white);
        setFont(new Font("Arial",Font.BOLD+Font.ITALIC,16));
        setText(s);
    }
}
//Описываем класс, который показывает, сколько бомб осталось
class LeftBombsLabel extends JLabel {
    public LeftBombsLabel(int x, int y,int w,int h) {
        super();
        setBounds(x,y,w,h);
        setOpaque(false);
        setLayout(null);
        setForeground(Color.white);
        setFont(new Font("Arial",Font.BOLD,16));
    }
    //метод, который преобразовует кол-во бомб в строку
    public void count(int k) {
        String s1;
        s1=Integer.toString(k);
        setText(s1);
    }
}
//Описываем класс, который показывает, сколько времени прошло с момента начала игры
class TimeLabel extends JLabel {
    String s1="00 : 00";
    long k=0;
    public TimeLabel(int x, int y,int w,int h) {
        super();
        setBounds(x,y,w,h);
        setOpaque(false);
        setLayout(null);
        setForeground(Color.white);
        setFont(new Font("Arial",Font.BOLD,16));
        setText("00 : 00");
    }
    //метод, который из кол-ва секунд сделает строку с минутами и секундами
    public void count(long k) {
        this.k=k;
        String sm,ss;
        long m,s;
        m=k/60;
        s=k-m*60;
        sm=Long.toString(m);
        ss=Long.toString(s);
        if (m<10)
            sm="0"+sm;
        if (s<10)
            ss="0"+ss;
        s1=sm+" : "+ss;
        setText(s1);
    }
}

//Описываем класс, который добавляет текст(уровни)
class LevelLabel extends JLabel {
    String s;
    public LevelLabel(String s,int y) {
        super();
        this.s=s;
        setOpaque(false);
        setLayout(null);
        setBounds(10,y,50,30);
        setForeground(Color.black);
        setFont(new Font("Arial", Font.PLAIN, 12));
        setText(s);
    }
}
//Описываем класс, который добавляет результат к каждому уровню
class ResultLabel extends JLabel {
    public ResultLabel(String s,String s1,String s2,int y) {
        super();
        setOpaque(false);
        setLayout(null);
        setBounds(100,y,195,30);
        setForeground(Color.black);
        setFont(new Font("Arial", Font.PLAIN, 13));
        setText(s+s1+s2);
    }
}
//Описываем класс, который создаст модальное окно "О программе"
class AboutProgramFrame extends JDialog{
    public AboutProgramFrame(MyFrame mf) {
        super(mf,Dialog.ModalityType.DOCUMENT_MODAL);
        setTitle("About program");
        setBounds(350,200,190,130);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel l=new JLabel();
        l.setBounds(30,20,135,95);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        l.setVerticalAlignment(SwingConstants.TOP);
        l.setText("<html>Name : Sapper<br/>" +
                  "Version : 1.0<br/> " +
                  "Creator : Dan</html>");
        add(l);
        setVisible(true);
    }
}
