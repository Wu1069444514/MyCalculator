
import javax.swing.*;
import javax.xml.stream.events.Comment;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.math.*;

import string2exp.Calculate;




public class Calculator extends JFrame  implements ActionListener {
    /*------定义按钮------*/
    //定义数字按钮
    private JButton button_0;
    private JButton button_1;
    private JButton button_2;
    private JButton button_3;
    private JButton button_4;
    private JButton button_5;
    private JButton button_6;
    private JButton button_7;
    private JButton button_8;
    private JButton button_9;

    //定义四则运算按钮运算按钮
    private JButton button_add;
    private JButton button_sub;
    private JButton button_mul;
    private JButton button_div;

    //定义三角函数按钮
    private JButton button_sin;
    private JButton button_cos;
    private JButton button_tan;
    private JButton button_arcsin;
    private JButton button_arccos;
    private JButton button_arctan;

    //定义对数按钮
    //private JButton button_log;
    private JButton button_ln;
    private JButton button_log10;

    //定义幂函数按钮
    private JButton button_squ;
    private JButton button_cub;
    private JButton button_power;

    //定义指数函数
    private JButton button_10x;
    private JButton button_exp;

    //定义根式运算按钮
    private JButton button_squ_r;
    //private JButton button_cub_r;
    private JButton button_rad;

    //定义其他运算符
    private JButton button_equ;
    private JButton button_bra_l;       //左括号
    private JButton button_bra_r;       //右括号
    private JButton button_point;       //小数点

    //其他运算
    private JButton button_fac;         //阶乘
    private JButton button_mod;         //阶乘

    //常量
    private JButton button_pi;          //π
    private JButton button_e;

    //功能按键
    private JButton button_del;         //删除
    private JButton button_c;           //清除
    private JButton button_mpl;         //MC+
    private JButton button_msu;         //MC-
    private JButton button_mr;         //MCR
    private JButton button_mc;         //MCR
    private JButton button_ms;         //MCR

    //页面按键
    private JButton button_page1;
    private JButton button_page2;

    //标题栏按钮
    private JButton w1;
    private JButton w2;
    private JButton w3;


    /*----------其他部件-------*/
    //结果显示区
    private JTextField dis_expr;        //显示算式
    private JTextField dis_resu;        //显示结果
    //private JTextArea  dis_expr;         //显示框
    //private JTextArea  dis_resu;

    /*---------窗口布局-------*/
    private CardLayout card;            //卡片布局
    private GridLayout  grid;           //网格布局
    private BorderLayout border;        //区域布局


    /*显示面板*/
    private JPanel  optionPanel;        //操作面板
    private JPanel  switchPanel;        //切换面板
    private JPanel  dispPanel;          //按钮面板
    private JPanel  dispPanel1;         //按钮显示面板1
    private JPanel  dispPanel2;         //按钮显示面板2
    private JPanel  funcPanel;          //功能区总面板
    private JPanel  titlePanel;         //标题框面板
    private JPanel  resPanel;           //结果显示面板

    /*---------图片-------*/
    private   ImageIcon page1;
    private   ImageIcon page2;
    private   Image tray;

    /*----系统托盘----*/
    private TrayIcon trayIcon;      //添加托盘
    private SystemTray systemTray;  //系统托盘

    /*----标签-----*/
    private JLabel title;
    private JLabel m;

    /*----常量------*/
    private int mouseAtX = 0;
    private int mouseAtY = 0;
    private int WIDTH;
    private boolean extend;

    //结果字符串
    private ArrayList temp;
    private String Dexpression ;
    private String Dresult;
    private String firstOp;     //二元运算符第一个操作数
    private String input;
    private String jud;         //上一个输入内容

    //运算
    private ArrayList expression;  //存储表达式
    private Double result;

    //堆栈
    private Stack braStack;
    private Stack MStack;

    //数字开始
    private boolean start = true;
    private boolean mem;
    //private  boolean  bra ;
    private boolean ifPower;
    private boolean ifRad;
    private boolean ifMod;
    private boolean ifMul;
    private boolean ifDiv;

    //索引号
    private int SqrIndex;           //sqr索引
    private int CubIndex;
    private int SqrRIndex;            //平方根索引

    //int CubRIndex = 0           //三角函数
    private int sinIndex ;
    private int cosIndex ;
    private int tanIndex ;
    private int asinIndex ;
    private int acosIndex;
    private int atanIndex;

    private int expIndex;
    private int log10Index;
    private int lnIndex;
    private int facIndex ;
    private int tenIndex;

    private int pointIndex;

    private int braIndex;

    //构造函数
    public Calculator()
    {
        //基本显示设置
        super("MyCalculator");
        this.setLocation(500,150);
        this.setSize(560,700);
        this.setMinimumSize(new Dimension(480, 550));
        this.setResizable(true);       //可自由调整大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           //点击×退出程序
        this.setLayout(new BorderLayout());                            //设置布局为Border
        this.setUndecorated(true);                                     //使标题栏不可见
        this.addWindowStateListener(new WindowStateListener() {        //调整最大最小化
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState() == JFrame.MAXIMIZED_BOTH)
                {
                    extend = true;
                }
                if(e.getOldState() == JFrame.ICONIFIED && extend == true)
                {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
                else if(e.getOldState() == JFrame.NORMAL && e.getNewState() == ICONIFIED)
                {
                    extend = false;
                }
                else if(e.getOldState() == JFrame.ICONIFIED)
                {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });

        //堆栈
        braStack = new Stack();
        MStack = new Stack();

        Font f=new Font("宋体",Font.BOLD,16);             //设置字体
        Color color = new Color(230, 230, 230, 244);     //设置颜色
        Color numcolor = new Color(243, 243, 243, 244);

        //显示框所占位置
        //表达式显示框
        dis_expr = new JTextField();
        dis_expr.setPreferredSize(new Dimension(0,30));          //设置显示框大小
        dis_expr.setBackground(new Color(226, 226, 226, 244));
        dis_expr.setEnabled(false);
        dis_expr.setBorder(null);                                                //隐藏边框
        //dis_expr.setOpaque(false);
        //dis_expr.se
        //display.setMaximumSize(new Dimension(0, 50));
        //display.setMinimumSize(new Dimension(0, 150));


        //运算结果显示框
        dis_resu = new JTextField();
        dis_resu.setPreferredSize(new Dimension(0,50));          //设置显示框大小
        dis_resu.setBackground(new Color(226, 226, 226, 244));
        dis_resu.setHorizontalAlignment(SwingConstants.RIGHT);
        dis_resu.setEnabled(false);
        dis_resu.setBorder(null);                                                //隐藏边框
        //dis_resu.setOpaque(false);
        //display.setMaximumSize(new Dimension(0, 50));
        //display.setMinimumSize(new Dimension(0, 150));

        //设置字体风格
        dis_expr.setFont(new Font("黑体",Font.PLAIN,25));
        dis_expr.setDisabledTextColor(new Color(120, 120, 120, 244));
        dis_expr.setHorizontalAlignment(SwingConstants.RIGHT);
        dis_expr.repaint();

        dis_resu.setFont(new Font("黑体",Font.PLAIN,50));
        dis_resu.setDisabledTextColor(new Color(0, 0, 0, 244));
        dis_resu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        dis_resu.repaint();

        //设置内容
        Dexpression = new String("");
        Dresult = new String("0");
        expression = new ArrayList();
        dis_expr.setText(Dexpression);
        dis_resu.setText(Dresult);
        temp = new ArrayList();


        //显示框面板
        //空白面板
        JPanel EmpNorDispPanel = new JPanel();
        EmpNorDispPanel.setPreferredSize(new Dimension(0,30));          //设置显示框大小
        EmpNorDispPanel.setBackground(new Color(226, 226, 226, 244));

        JPanel EmpEasDispPanel = new JPanel();
        EmpEasDispPanel.setPreferredSize(new Dimension(10,0));          //设置显示框大小
        EmpEasDispPanel.setBackground(new Color(226, 226, 226, 244));

        JPanel EmpSouDispPanel = new JPanel();
        EmpSouDispPanel.setLayout(null);
        EmpSouDispPanel.setPreferredSize(new Dimension(0,20));          //设置显示框大小
        EmpSouDispPanel.setBackground(new Color(226, 226, 226, 244));

        JPanel EmpDispPanel = new JPanel();
        EmpDispPanel.setPreferredSize(new Dimension(10,0));          //设置显示框大小
        EmpDispPanel.setBackground(new Color(226, 226, 226, 244));
        EmpDispPanel.setLayout(new BorderLayout());

        //增加存储显示
        m = new JLabel();
        m.setText("");
        m.setFont(new Font("宋体",Font.BOLD,20));
        m.setBounds(10,0,20,15);                //设置组件位置
        EmpSouDispPanel.add(m);


        EmpDispPanel.add("North",dis_expr);
        EmpDispPanel.add("Center",dis_resu);
        EmpDispPanel.add("South",EmpSouDispPanel);



        //总面板

        resPanel = new JPanel();
        resPanel.setLayout(new BorderLayout());
        resPanel.add("North",EmpNorDispPanel);
        resPanel.add("East",EmpEasDispPanel);
        resPanel.add("Center",EmpDispPanel);



        resPanel.repaint();
        //resPanel.add("South",dis_resu);



        titlePanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(0,50));          //设置空白面板大小

        add("North",titlePanel);

        add("Center",resPanel);

        //功能区所占位置
        border = new BorderLayout();
        funcPanel = new JPanel();
        funcPanel.setLayout(border);

        add("South",funcPanel);

        //操作条显示
        optionPanel = new JPanel();
        optionPanel.setPreferredSize(new Dimension(0,50));                              //设置操作条大小
        optionPanel.setLayout(new GridLayout(1,5));
        //optionPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));                //设置边框

        Font fo=new Font("Arial", Font.ITALIC,26);                                        //设置字体


        button_del = new JButton("←");                                //创建操作按钮
        button_c = new JButton("CE");
        button_mpl = new JButton("M+");
        button_msu = new JButton("M-");
        button_mc = new JButton("MC");
        button_mr = new JButton("MR");
        button_ms = new JButton("MS");

        setButtonArtribute(button_del,fo,color,"false");            //按钮属性
        setButtonArtribute(button_c,fo,color,"false");
        setButtonArtribute(button_mpl,fo,color,"false");
        setButtonArtribute(button_msu,fo,color,"false");
        setButtonArtribute(button_mc,fo,color,"false");
        setButtonArtribute(button_mr,fo,color,"false");
        setButtonArtribute(button_ms,fo,color,"false");




        optionPanel.add(button_mc);
        optionPanel.add(button_mr);
        optionPanel.add(button_mpl);
        optionPanel.add(button_msu);
        optionPanel.add(button_ms);
        optionPanel.add(button_del);
        optionPanel.add(button_c);

        funcPanel.add("North",optionPanel);

        //切换面板显示
        switchPanel = new JPanel();
        grid = new GridLayout(5,1);
        switchPanel.setPreferredSize(new Dimension(70,0));      //设置切换面板大小
        switchPanel.setLayout(grid);
        //switchPanel.setBackground(color);
        //page1 = new ImageIcon("img\\page1.png");
        //page2 = new ImageIcon("img\\page2.png");

        Font fp=new Font("Arial", Font.ITALIC,26);                  //设置字体

        button_page1 = new JButton("+/-");
        button_page2 = new JButton("sin");

        setButtonArtribute(button_page1,fp,color,"emp");                //设置按钮属性
        setButtonArtribute(button_page2,fp,color,"emp");

        JPanel EmpSwJPanel1 = new JPanel();
        JPanel EmpSwJPanel2 = new JPanel();
        JPanel EmpSwJPanel3 = new JPanel();

        EmpSwJPanel1.setBackground(color);
        EmpSwJPanel2.setBackground(color);
        EmpSwJPanel3.setBackground(color);

        switchPanel.add(button_page1);
        switchPanel.add(button_page2);
        switchPanel.add(EmpSwJPanel1);
        switchPanel.add(EmpSwJPanel2);
        switchPanel.add(EmpSwJPanel3);

        funcPanel.add("West",switchPanel);

        //按钮面板显示
        dispPanel = new JPanel();
        dispPanel.setPreferredSize(new Dimension(0,400));      //设置切换面板大小
        card = new CardLayout();        //设置卡片布局
        dispPanel.setLayout(card);
        funcPanel.add("Center",dispPanel);


        //第一页按钮
        dispPanel1 = new JPanel();
        grid = new GridLayout(5,5,1,1);              //设置Grid布局
        dispPanel1.setLayout(grid);
        dispPanel.add(dispPanel1);

        //第一行
        button_10x = new JButton("10^x ");
        button_squ = new JButton("x²");
        button_cub = new JButton("x³");
        button_exp = new JButton("exp");
        button_div = new JButton("÷");

        setButtonArtribute(button_10x,f,color,"true");
        setButtonArtribute(button_squ,f,color,"true");
        setButtonArtribute(button_cub,f,color,"true");
        setButtonArtribute(button_exp,f,color,"true");
        setButtonArtribute(button_div,f,color,"true");

        dispPanel1.add(button_10x);
        dispPanel1.add(button_squ);
        dispPanel1.add(button_cub);
        dispPanel1.add(button_exp);
        dispPanel1.add(button_div);


        //第二行
        button_pi = new JButton("π");
        button_7 = new JButton("7");
        button_8 = new JButton("8");
        button_9 = new JButton("9");
        button_mul = new JButton("×");

        setButtonArtribute(button_pi,f,color,"true");
        setButtonArtribute(button_7,f,numcolor,"true");
        setButtonArtribute(button_8,f,numcolor,"true");
        setButtonArtribute(button_9,f,numcolor,"true");
        setButtonArtribute(button_mul,f,color,"true");

        dispPanel1.add(button_pi);
        dispPanel1.add(button_7);
        dispPanel1.add(button_8);
        dispPanel1.add(button_9);
        dispPanel1.add(button_mul);


        //第三行
        button_e = new JButton("e");
        button_4 = new JButton("4");
        button_5 = new JButton("5");
        button_6 = new JButton("6");
        button_add = new JButton("＋");

        setButtonArtribute(button_e,f,color,"true");
        setButtonArtribute(button_4,f,numcolor,"true");
        setButtonArtribute(button_5,f,numcolor,"true");
        setButtonArtribute(button_6,f,numcolor,"true");
        setButtonArtribute(button_add,f,color,"true");

        dispPanel1.add(button_e);
        dispPanel1.add(button_4);
        dispPanel1.add(button_5);
        dispPanel1.add(button_6);
        dispPanel1.add(button_add);


        //第四行
        button_fac = new JButton("n!");
        button_1 = new JButton("1");
        button_2 = new JButton("2");
        button_3 = new JButton("3");
        button_sub = new JButton("－");

        setButtonArtribute(button_fac,f,color,"true");
        setButtonArtribute(button_1,f,numcolor,"true");
        setButtonArtribute(button_2,f,numcolor,"true");
        setButtonArtribute(button_3,f,numcolor,"true");
        setButtonArtribute(button_sub,f,color,"true");

        dispPanel1.add(button_fac);
        dispPanel1.add(button_1);
        dispPanel1.add(button_2);
        dispPanel1.add(button_3);
        dispPanel1.add(button_sub);


        //第五行
        button_bra_l = new JButton("(");
        button_bra_r = new JButton(")");
        button_0 = new JButton("0");
        button_point = new JButton(".");
        button_equ = new JButton("=");

        setButtonArtribute(button_bra_l,f,color,"true");
        setButtonArtribute(button_bra_r,f,color,"true");
        setButtonArtribute(button_0,f,numcolor,"true");
        setButtonArtribute(button_point,f,color,"true");
        setButtonArtribute(button_equ,f,color,"true");

        dispPanel1.add(button_bra_l);
        dispPanel1.add(button_bra_r);
        dispPanel1.add(button_0);
        dispPanel1.add(button_point);
        dispPanel1.add(button_equ);


        //第二页按钮
        dispPanel2 = new JPanel();
        grid = new GridLayout(5,5,1,1);
        dispPanel2.setLayout(grid);
        dispPanel.add(dispPanel2);

        //第一行

        button_squ_r= new JButton("√");
        //button_cub_r= new JButton("³√");
        button_rad= new JButton("y√x");
        button_ln= new JButton("ln");
        button_mod= new JButton("mod");
        button_log10= new JButton("log10");


        setButtonArtribute(button_squ_r,f,color,"true");
        //setButtonArtribute(button_cub_r,f,color,"true");
        setButtonArtribute(button_rad,f,color,"true");
        setButtonArtribute(button_ln,f,color,"true");
        setButtonArtribute(button_mod,f,color,"true");
        setButtonArtribute(button_log10,f,color,"true");


        dispPanel2.add(button_squ_r);
        //dispPanel2.add(button_cub_r);
        dispPanel2.add(button_rad);
        dispPanel2.add(button_ln);
        dispPanel2.add(button_mod);
        dispPanel2.add(button_log10);

        //第二行
        button_power= new JButton("x^y");
        //button_log= new JButton("log");
        button_sin = new JButton("sin");
        button_cos = new JButton("cos");
        button_tan= new JButton("tan");


        setButtonArtribute(button_power,f,color,"true");
        //setButtonArtribute(button_log,f,color,"true");
        setButtonArtribute(button_sin,f,color,"true");
        setButtonArtribute(button_cos,f,color,"true");
        setButtonArtribute(button_tan,f,color,"true");

        dispPanel2.add(button_power);
        //dispPanel2.add(button_log);
        dispPanel2.add(button_sin);
        dispPanel2.add(button_cos);
        dispPanel2.add(button_tan);

        //第三行
        button_arcsin= new JButton("arcsin");
        button_arccos= new JButton("arccos");
        button_arctan= new JButton("arctan");



        //空白按钮(第三行)
        JButton b1 = new JButton();
        JButton b2 = new JButton();

        setButtonArtribute(button_arcsin,f,color,"true");
        setButtonArtribute(button_arccos,f,color,"true");
        setButtonArtribute(button_arctan,f,color,"true");
        setButtonArtribute(b1,f,color,"true");
        setButtonArtribute(b2,f,color,"true");

        dispPanel2.add(button_arcsin);
        dispPanel2.add(button_arccos);
        dispPanel2.add(button_arctan);

        dispPanel2.add(b1);
        dispPanel2.add(b2);

        //第四行
        JButton b3 = new JButton();
        JButton b4 = new JButton();
        JButton b5 = new JButton();
        JButton b6 = new JButton();
        JButton b7 = new JButton();

        setButtonArtribute(b3,f,color,"true");
        setButtonArtribute(b4,f,color,"true");
        setButtonArtribute(b5,f,color,"true");
        setButtonArtribute(b6,f,color,"true");
        setButtonArtribute(b7,f,color,"true");

        dispPanel2.add(b3);
        dispPanel2.add(b4);
        dispPanel2.add(b5);
        dispPanel2.add(b6);
        dispPanel2.add(b7);

        //第五行

        JButton b8 = new JButton();
        JButton b9 = new JButton();
        JButton b10 = new JButton();
        JButton b11= new JButton();
        JButton b12= new JButton();
        JButton b13= new JButton();
        JButton b14= new JButton();

        setButtonArtribute(b8,f,color,"true");
        setButtonArtribute(b9,f,color,"true");
        setButtonArtribute(b10,f,color,"true");
        setButtonArtribute(b11,f,color,"true");
        setButtonArtribute(b12,f,color,"true");
        setButtonArtribute(b13,f,color,"true");
        setButtonArtribute(b14,f,color,"true");

        dispPanel2.add(b8);
        dispPanel2.add(b9);
        dispPanel2.add(b10);
        dispPanel2.add(b11);
        dispPanel2.add(b12);
        dispPanel2.add(b13);
        //dispPanel2.add(b14);


        //托盘图标
        /*page1 = new ImageIcon("img\\page1.png");
        trayIcon = new TrayIcon(page1.getImage());
        systemTray = SystemTray.getSystemTray();        //获得系统托盘对象
        try{
            systemTray.add(trayIcon);
        }catch (AWTException e){

        };*/

        //

        //实现标题栏
        Font b = new Font("宋体",Font.BOLD,18);

        title = new JLabel("科学计算器");                                  //标题标签
        title.setFont(b);



        w1 = new JButton("—");
        w2 = new JButton("□");
        w3 = new JButton("×");

        WIDTH = this.getWidth();                                            //获取当前窗口宽度

        titlePanel.setLayout(null);
        title.setBounds(10,0,100,50);                //设置组件位置
        w3.setBounds(WIDTH-80,0,90,50);
        w2.setBounds(WIDTH-130,0,90,50);
        w1.setBounds(WIDTH-180,0,90,50);


        w1.setContentAreaFilled(false);      //除去默认的背景填充
        w2.setContentAreaFilled(false);      //除去默认的背景填充
        w3.setContentAreaFilled(false);      //除去默认的背景填充

        setButtonArtribute(w3,b,color,"false");                     //设置按钮属性，隐藏边框
        setButtonArtribute(w2,b,color,"false");
        setButtonArtribute(w1,b,color,"false");

        titlePanel.add(w1);
        titlePanel.add(w2);
        titlePanel.add(w3);
        titlePanel.add(title);

        //设置拖拽窗口
        addMouseListener(new MouseAdapter()                     //重写鼠标点击函数
        {
            public void mousePressed(MouseEvent e)
            {
                mouseAtX = e.getPoint().x;                      //获取鼠标x，y坐标
                mouseAtY = e.getPoint().y;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()         //重写鼠标拖拽函数
        {
            public void mouseDragged(MouseEvent e)
            {
                setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
            }
        });

        //添加监视器
        addActionListenerButton();

        //设置可见放在最后
        this.setVisible(true);         //设置可见

    }

    //添按钮加监视
    public void addActionListenerButton()
    {

        //换页按钮
        button_page1.addActionListener(this);                                 //添加监视
        button_page2.addActionListener(this);

        //窗口按钮
        w1.addActionListener(this);
        w2.addActionListener(this);
        w3.addActionListener(this);

        //数字按钮
        button_0.addActionListener(this);
        button_1.addActionListener(this);
        button_2.addActionListener(this);
        button_3.addActionListener(this);
        button_4.addActionListener(this);
        button_5.addActionListener(this);
        button_6.addActionListener(this);
        button_7.addActionListener(this);
        button_8.addActionListener(this);
        button_9.addActionListener(this);

        //四则运算按钮
        button_add.addActionListener(this);
        button_sub.addActionListener(this);
        button_mul.addActionListener(this);
        button_div.addActionListener(this);

        //三角函数按钮
        button_sin.addActionListener(this);
        button_cos.addActionListener(this);
        button_tan.addActionListener(this);
        button_arcsin.addActionListener(this);
        button_arccos.addActionListener(this);
        button_arctan.addActionListener(this);

        //对数按钮
        //button_log.addActionListener(this);
        button_ln.addActionListener(this);
        button_log10.addActionListener(this);

        //幂函数按钮
        button_squ.addActionListener(this);
        button_cub.addActionListener(this);
        button_power.addActionListener(this);

        //指数函数
        button_10x.addActionListener(this);
        button_exp.addActionListener(this);

        //根式运算按钮
        button_squ_r.addActionListener(this);
        //button_cub_r.addActionListener(this);
        button_rad.addActionListener(this);

        //其他运算符
        button_equ.addActionListener(this);
        button_bra_l.addActionListener(this);
        button_bra_r.addActionListener(this);
        button_point.addActionListener(this);
        button_mod.addActionListener(this);

        //其他运算
        button_fac.addActionListener(this);


        //常量
        button_pi.addActionListener(this);
        button_e.addActionListener(this);

        //功能按键
        button_del.addActionListener(this);
        button_c.addActionListener(this);
        button_mpl.addActionListener(this);
        button_msu.addActionListener(this);
        button_mr.addActionListener(this);
        button_mc.addActionListener(this);
        button_ms.addActionListener(this);

    }

    //设置按钮属性
    public void setButtonArtribute(JButton b,Font f,Color c,String border)
    {

        switch (border)
        {
            case "false": b.setBorder(null);break;                  //除去边
            case "emp":b.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));break;  //设置隐藏边
        }

        //b.setContentAreaFilled(false);      //除去默认的背景填充
        b.setFocusPainted(false);           //除去焦点的框
        b.setFont(f);                       //设置字体
        b.setBackground(c);                 //设置背景颜色

    }

    //响应按键
    public void actionPerformed(ActionEvent e)
    {

        //标题栏图标
        //最小化
        if(e.getSource() == w1)
        {
            setExtendedState(JFrame.ICONIFIED);                     //最小化
        }

        //最大化
        else if(e.getActionCommand() == "□")
        {
            setExtendedState(JFrame.MAXIMIZED_BOTH);                //最大化
            WIDTH = this.getWidth();                                //重新设定按钮位置
            w3.setBounds(WIDTH-80,0,90,45);
            w2.setBounds(WIDTH-130,0,90,45);
            w1.setBounds(WIDTH-180,0,90,45);
            w2.setText("口");

            dis_expr.setFont(new Font("黑体",Font.PLAIN,80));
            dis_expr.setDisabledTextColor(new Color(120, 120, 120, 244));
            dis_resu.setFont(new Font("黑体",Font.PLAIN,200));
            dis_resu.setDisabledTextColor(new Color(0, 0, 0, 244));

            dis_expr.setPreferredSize(new Dimension(0,80));          //设置显示框大小
            dis_resu.setPreferredSize(new Dimension(0,200));          //设置显示框大小
        }

        //正常化
        else if(e.getActionCommand() == "口")
        {
            setExtendedState(JFrame.NORMAL);                        //正常化
            WIDTH = this.getWidth();                                //重新设定按钮位置
            w3.setBounds(WIDTH-80,0,90,45);
            w2.setBounds(WIDTH-130,0,90,45);
            w1.setBounds(WIDTH-180,0,90,45);
            w2.setText("□");

            dis_expr.setFont(new Font("黑体",Font.PLAIN,20));
            dis_expr.setDisabledTextColor(new Color(120, 120, 120, 244));
            dis_resu.setFont(new Font("黑体",Font.PLAIN,50));
            dis_resu.setDisabledTextColor(new Color(0, 0, 0, 244));

            dis_expr.setPreferredSize(new Dimension(0,20));          //设置显示框大小
            dis_resu.setPreferredSize(new Dimension(0,50));          //设置显示框大小

        }

        //退出
        else if(e.getSource() == w3)
        {
            System.exit(0);                                 //退出程序
        }

        //界面切换
        else if(e.getSource() == button_page1)
        {
            card.first(dispPanel);                                  //显示第一个按钮界面
        }
        else if(e.getSource() == button_page2)                           //显示第二个按钮界面
        {
            card.last(dispPanel);
        }
        //清空键
        else if(e.getSource() == button_c)
        {
            Dexpression = "";
            Dresult = "0";
            temp.clear();
            expression.clear();
        }
        //回退
        else if(e.getSource() == button_del)
        {
            if(Dresult.length()>1)
            {
                Dresult = Dresult.substring(0,Dresult.length()-1);
            }
            else
            {
                Dresult = "0";
            }
        }


        //输入数字键和运算符
        else
        {
            if(e.getSource()==button_0 || e.getSource()==button_1
                    ||e.getSource()==button_2||e.getSource()==button_3
                    ||e.getSource()==button_4 ||e.getSource()==button_5
                    ||e.getSource()==button_6 ||e.getSource()==button_7
                    ||e.getSource()==button_8 ||e.getSource()==button_9)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //四则运算
            else if(e.getSource() == button_add||e.getSource() == button_sub
                    ||e.getSource() == button_mul||e.getSource() == button_div)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //幂函数和根
            else if(e.getSource() == button_squ || e.getSource() == button_cub
                    || e.getSource() == button_squ_r)
            {
                input = e.getActionCommand();
                temp.add(input);

            }
            //三角函数
            else if(e.getSource() == button_sin || e.getSource() == button_cos
                    || e.getSource() == button_tan|| e.getSource() == button_arcsin
                    || e.getSource() == button_arccos|| e.getSource() == button_arctan)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //其他运算
            else if(  e.getSource() == button_10x||e.getSource() == button_exp
                    || e.getSource() == button_fac|| e.getSource() == button_ln
                    || e.getSource() == button_log10)
            {
                if(e.getSource() == button_10x)
                    input = "ten";
                else
                    input = e.getActionCommand();
                temp.add(input);
            }
            //常量
            else  if(e.getSource() == button_e || e.getSource()== button_pi)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //小数点
            else  if(e.getSource() == button_point)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //括号
            else  if(e.getSource() == button_bra_r || e.getSource() == button_bra_l)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //二元运算
            else  if(e.getSource() == button_power || e.getSource() == button_rad
            ||e.getSource() == button_mod)
            {
                input = e.getActionCommand();
                temp.add(input);
            }
            //存储功能
            else if(e.getSource() == button_mc || e.getSource() == button_mr
                    ||e.getSource() == button_mpl || e.getSource() == button_msu
                    ||e.getSource() == button_ms)
            {
                input = e.getActionCommand();
                mem = true;
            }
            //等于
            else if(e.getSource() == button_equ)
            {
                input = e.getActionCommand();
                temp.add(input);
            }

            addTextField(input);

        }



        dis_expr.setText(Dexpression);
        dis_resu.setText(Dresult);
    }

    //表达式结果显示
    public void addTextField(String input)
    {
        char inputChar = input.charAt(0);

        jud = new String();

        //索引号
        //幂运算根运算
        SqrIndex =  Dexpression.lastIndexOf("sqr");
        CubIndex =  Dexpression.lastIndexOf("cub");
        SqrRIndex =  Dexpression.lastIndexOf("√");
        //CubRIndex =  Dexpression.lastIndexOf("³√");

        //三角函数
        expIndex =  Dexpression.lastIndexOf("exp");
        log10Index =  Dexpression.lastIndexOf("log10");
        lnIndex =  Dexpression.lastIndexOf("ln");
        facIndex =  Dexpression.lastIndexOf("fac");
        tenIndex =  Dexpression.lastIndexOf("10^");

        //其他运算
        sinIndex =  Dexpression.lastIndexOf("sin");
        cosIndex =  Dexpression.lastIndexOf("cos");
        tanIndex =  Dexpression.lastIndexOf("tan");
        asinIndex =  Dexpression.lastIndexOf("arcsin");
        acosIndex =  Dexpression.lastIndexOf("arccos");
        atanIndex =  Dexpression.lastIndexOf("arctan");

        pointIndex =  Dresult.lastIndexOf(".0");
        braIndex = Dexpression.lastIndexOf("(");

        //上一个输入
        if(temp.size()>=2)
        {
            jud = (temp.get(temp.size() - 2).toString());
        }

        //最后一个运算符
        String last = new String();     //最后一个运算符
        if(Dexpression.length()>1)
        {
            last = Dexpression.substring(Dexpression.length()-1,Dexpression.length());
        }

        //输入数字
        if((int)inputChar >= 48 &&  (int)inputChar <= 57)
        {
            if(Dresult == "0")
                Dresult = "";
            //dis_resu.setText(Dresult+String.valueOf(input));
            if(start || mem )
            {
                start = false;
                mem = false;
                //Dexpression = Dexpression.concat(Dresult);
                Dresult = "";
            }
            if (Dresult.length() < 15)
            {
                if(pointIndex == Dresult.length()-2 && pointIndex >= 0)
                    Dresult = Dresult.substring(0,Dresult.length()-1);
                Dresult = Dresult.concat(input);
            }

            if(temp.size()>=2)
            {
                jud = (temp.get(temp.size() - 2).toString());
                if(inOtherCal(jud,true))
                {
                    switch(jud)
                    {

                        case "x²":
                        {
                            Dexpression = Dexpression.substring(0,SqrIndex);
                            break;
                        }
                        //三次方
                        case "x³":
                        {
                            Dexpression = Dexpression.substring(0,CubIndex);
                            break;
                        }
                        //平方根
                        case "√":
                        {
                            Dexpression = Dexpression.substring(0,SqrRIndex);
                            break;
                        }
                        //sin
                        case "sin":
                        {
                            Dexpression = Dexpression.substring(0,sinIndex);
                            break;
                        }
                        //cos
                        case "cos":
                        {
                            Dexpression = Dexpression.substring(0,cosIndex);
                            break;
                        }
                        //tan
                        case "tan":
                        {
                            Dexpression = Dexpression.substring(0,tanIndex);
                            break;
                        }

                        //asin
                        case "arcsin":
                        {
                            Dexpression = Dexpression.substring(0,asinIndex);
                            break;
                        }

                        //acos
                        case "arccos":
                        {
                            Dexpression = Dexpression.substring(0,acosIndex);
                            break;
                        }
                        //atan
                        case "arctan":
                        {
                            Dexpression = Dexpression.substring(0,atanIndex);
                            break;
                        }
                        //sin
                        case "exp":
                        {
                            Dexpression = Dexpression.substring(0,expIndex);
                            break;
                        }

                        case "log10":
                        {
                            Dexpression = Dexpression.substring(0,log10Index);
                            break;
                        }

                        case "ln":
                        {
                            Dexpression = Dexpression.substring(0,lnIndex);
                            break;
                        }

                        case "ten":
                        {
                            Dexpression = Dexpression.substring(0,tenIndex);
                            break;
                        }
                        case "n!":
                        {
                            Dexpression = Dexpression.substring(0,facIndex);
                            break;
                        }
                        case ")":
                        {
                            Dexpression = Dexpression.substring(0,braIndex);
                            break;
                        }
                        default:break;
                    }
                    Dresult = "";
                    Dresult = Dresult.concat(input);
                }

            }
        }

        //输入四则运算符
        else if (input.equals( "＋" )|| input.equals( "－") || input.equals( "×" )
                ||input.equals( "÷")) {
            start = true;                   //输入运算符时结果显示不变
            //Dresult = "";


            if((last.equals("＋" )|| last.equals("－")  || last.equals("×")  || last.equals("÷"))&&((int)jud.charAt(0) < 48 || (int)jud.charAt(0) > 57)&&!(jud.equals("e")||jud.equals("π")))       //连续输入四则运算符
            {
                Dexpression = Dexpression.substring(0,Dexpression.length()-1).concat(input);
                expression.set(expression.size()-1,input);
                //Dexpression = Dexpression.concat(Dresult);
            }
            else  if(last.equals(")"))
            {
                Dexpression = Dexpression.concat(input);
                if(jud.equals(")"))
                {
                    expression.add(")");
                }
            }
            else if(inOtherCal(jud,false))                                              //根运算和幂运算后输入四则运算符
            {
                Dexpression = Dexpression.concat(input);
            }
            else
            {
                Dexpression = Dexpression.concat(Dresult);
                if(ifPower)
                {
                    Dresult = String.valueOf(Math.pow(Double.valueOf(firstOp).doubleValue(),Double.valueOf(Dresult).doubleValue()));
                    ifPower = false;
                }
                else if(ifRad)
                {
                    Dresult = String.valueOf(Math.pow(Double.valueOf(firstOp).doubleValue(),1.0/Double.valueOf(Dresult).doubleValue()));
                    ifRad = false;
                }
                else if(ifMod)
                {
                    Dresult = String.valueOf((Double.valueOf(firstOp).doubleValue()%Double.valueOf(Dresult).doubleValue()));
                    ifMod = false;
                }
                Dexpression = Dexpression.concat(input);
            }

            //加入表达式
            if((last.equals("＋" )|| last.equals("－")  || last.equals("×")  || last.equals("÷"))&&((int)jud.charAt(0) < 48 || (int)jud.charAt(0) > 57)&&!(jud.equals("e")||jud.equals("π")))       //连续输入四则运算符
            {
                expression.set(expression.size()-1,input);
                //Dexpression = Dexpression.concat(Dresult);
            }
            else
            {
                result = Double.valueOf(Dresult).doubleValue();
                if(!jud.equals(")"))
                {
                    expression.add(result);
                }
                expression.add(input);
                //System.out.println(expression);
            }

            if(input.equals("＋" )|| input.equals("－"))
            {
                Dresult = String.valueOf(Cal(expression)) ;
            }

        }

        //输入二元运算符
        else if(input.equals("x^y")|| input.equals("y√x")||input.equals("mod"))
        {
            if(inOtherCal(jud,true))
            {
                firstOp = Dresult;
                mem = true;
                //if()
                if(input.equals("x^y"))
                {
                    Dexpression = Dexpression.concat("^");
                    ifPower = true;
                }
                else if(input.equals("y√x"))
                {
                    Dexpression = Dexpression.concat(" yroot ");
                    ifRad = true;
                }
                else
                {
                    Dexpression = Dexpression.concat(" mod ");
                    ifMod = true;
                }
            }
            else
            {
                switch(input)
                {

                    case "x^y":
                    {
                        if(!(jud.equals("x^y") || jud.equals("y√x")||jud.equals("mod")))
                        {
                            mem = true;
                            firstOp = Dresult;
                            ifPower = true;
                            Dexpression = Dexpression.concat(Dresult+"^");
                        }
                        break;
                    }
                    case  "y√x":
                    {
                        if(!(jud.equals("x^y") || jud.equals("y√x")||jud.equals("mod")))
                        {
                            mem = true;
                            firstOp = Dresult;
                            Dexpression = Dexpression.concat(Dresult+" yroot ");
                            ifRad = true;
                        }
                        break;
                    }
                    case  "mod":
                    {
                        if(!(jud.equals("x^y") || jud.equals("y√x")||jud.equals("mod")))
                        {
                            mem = true;
                            firstOp = Dresult;
                            Dexpression = Dexpression.concat(Dresult+" mod ");
                            ifMod = true;
                        }
                        break;
                    }

                }
            }
        }

        //其他运算符
        else if(inOtherCal(input,false))
        {
            otherCal(input);
        }

        //常量
        else if(input.equals("e") || input.equals("π"))
        {
            switch (input)
            {
                case "π":
                {
                    Dresult = String.valueOf(Math.PI);
                    break;
                }
                case "e":
                {
                    Dresult = String.valueOf(Math.E);
                    break;
                }
                default:break;
            }
            if(inOtherCal(jud,true))
            {
                switch(jud)
                {

                    case "x²":
                    {
                        Dexpression = Dexpression.substring(0,SqrIndex);
                        break;
                    }
                    //三次方
                    case "x³":
                    {
                        Dexpression = Dexpression.substring(0,CubIndex);
                        break;
                    }
                    //平方根
                    case "√":
                    {
                        Dexpression = Dexpression.substring(0,SqrRIndex);
                        break;
                    }
                    //sin
                    case "sin":
                    {
                        Dexpression = Dexpression.substring(0,sinIndex);
                        break;
                    }
                    //cos
                    case "cos":
                    {
                        Dexpression = Dexpression.substring(0,cosIndex);
                        break;
                    }
                    //tan
                    case "tan":
                    {
                        Dexpression = Dexpression.substring(0,tanIndex);
                        break;
                    }

                    //asin
                    case "arcsin":
                    {
                        Dexpression = Dexpression.substring(0,asinIndex);
                        break;
                    }

                    //acos
                    case "arccos":
                    {
                        Dexpression = Dexpression.substring(0,acosIndex);
                        break;
                    }
                    //atan
                    case "arctan":
                    {
                        Dexpression = Dexpression.substring(0,atanIndex);
                        break;
                    }
                    //sin
                    case "exp":
                    {
                        Dexpression = Dexpression.substring(0,expIndex);
                        break;
                    }

                    case "log10":
                    {
                        Dexpression = Dexpression.substring(0,log10Index);
                        break;
                    }

                    case "ln":
                    {
                        Dexpression = Dexpression.substring(0,lnIndex);
                        break;
                    }

                    case "ten":
                    {
                        Dexpression = Dexpression.substring(0,tenIndex);
                        break;
                    }
                    case "n!":
                    {
                        Dexpression = Dexpression.substring(0,facIndex);
                        break;
                    }
                    case ")":
                    {
                        Dexpression = Dexpression.substring(0,braIndex);
                        break;
                    }
                    default:break;
                }
                Dresult = "";
                switch (input)
                {
                    case "π":
                    {
                        Dresult = String.valueOf(Math.PI);
                        break;
                    }
                    case "e":
                    {
                        Dresult = String.valueOf(Math.E);
                        break;
                    }
                    default:break;
                }
            }
        }

        //小数点
        else if(input.equals("."))
        {
            Dresult = String.valueOf(Double.valueOf(Dresult).doubleValue());
        }

        //左括号
        else if(input.equals("("))
        {
            if(!jud.equals("("))
            {
                if(inOtherCal(jud,true))
                {
                    switch(jud)
                    {

                        case "x²":
                        {
                            Dexpression = Dexpression.substring(0,SqrIndex);
                            break;
                        }
                        //三次方
                        case "x³":
                        {
                            Dexpression = Dexpression.substring(0,CubIndex);
                            break;
                        }
                        //平方根
                        case "√":
                        {
                            Dexpression = Dexpression.substring(0,SqrRIndex);
                            break;
                        }
                        //sin
                        case "sin":
                        {
                            Dexpression = Dexpression.substring(0,sinIndex);
                            break;
                        }
                        //cos
                        case "cos":
                        {
                            Dexpression = Dexpression.substring(0,cosIndex);
                            break;
                        }
                        //tan
                        case "tan":
                        {
                            Dexpression = Dexpression.substring(0,tanIndex);
                            break;
                        }

                        //asin
                        case "arcsin":
                        {
                            Dexpression = Dexpression.substring(0,asinIndex);
                            break;
                        }

                        //acos
                        case "arccos":
                        {
                            Dexpression = Dexpression.substring(0,acosIndex);
                            break;
                        }
                        //atan
                        case "arctan":
                        {
                            Dexpression = Dexpression.substring(0,atanIndex);
                            break;
                        }
                        //sin
                        case "exp":
                        {
                            Dexpression = Dexpression.substring(0,expIndex);
                            break;
                        }

                        case "log10":
                        {
                            Dexpression = Dexpression.substring(0,log10Index);
                            break;
                        }

                        case "ln":
                        {
                            Dexpression = Dexpression.substring(0,lnIndex);
                            break;
                        }

                        case "ten":
                        {
                            Dexpression = Dexpression.substring(0,tenIndex);
                            break;
                        }
                        case "n!":
                        {
                            Dexpression = Dexpression.substring(0,facIndex);
                            break;
                        }
                        case ")":
                        {

                            Dexpression = Dexpression.substring(0,braIndex);
                            break;
                        }
                        default:break;
                    }
                    Dexpression = Dexpression.concat("(");
                    expression.add("(");
                }
                else
                {
                    Dexpression = Dexpression.concat("(");
                    expression.add("(");
                }
                braStack.add(input);
            }

        }
        //右括号
        else if(input.equals(")"))
        {
            if(!braStack.empty())
            {
                if(inOtherCal(jud,true))
                {
                    Dexpression = Dexpression.concat(")");
                }
                else
                {
                    expression.add(Double.valueOf(Dresult).doubleValue());
                    Dexpression = Dexpression.concat(Dresult + ")");
                }
                braStack.pop();
            }
        }
        //存储功能

        else if(input.equals("MC")||input.equals("MR")
                ||input.equals("M+")||input.equals("M-")
                ||input.equals("MS"))
        {
            switch(input)
            {
                case "MC":
                {
                    MStack.clear();
                    m.setText("");
                    break;
                }
                case "MR":
                {
                    if(!MStack.empty())
                        Dresult = String.valueOf(MStack.peek());
                    break;
                }
                case "M+":
                {
                    m.setText("M");
                    MStack.push(Dresult);
                    break;
                }
                case "M-":
                {
                    if(!MStack.empty())
                    {
                        Dresult = String.valueOf( MStack.pop());
                    }
                    if(MStack.empty())
                    {
                        m.setText("");
                    }
                    break;
                }
                case "MS":
                {
                    if(MStack.empty())
                    {
                        MStack.push(Dresult);
                    }
                    else
                    {
                        MStack.pop();
                        MStack.push((Dresult));
                    }
                    break;
                }
            }
        }

        else if(input.equals("="))
        {
            Dexpression = "";
            mem = true;
            result = Double.valueOf(Dresult).doubleValue();

            if(jud.equals("＋" )|| jud.equals("－")  || jud.equals("×")  || jud.equals("÷"))
            {
                Dresult = "0";
                expression.clear();
            }
            else if(!jud.equals(")"))
            {
                expression.add(result);
            }
            else
            {
                expression.add(")");
            }

            //计算并显示结果
            Dresult = String.valueOf(Cal(expression)) ;
            //System.out.println(expression);

            expression.clear();
            //expression.add(input);
            //System.out.println(expression);
        }

    }

    //其他运算计算
    public void otherCal(String input)
    {

        String temExp = new String();
        String temRes = new String();
        temExp = input;
        temRes = Dresult;

        switch(input) {

            case "x²": {
                temExp = "sqr";
                temRes = String.valueOf(Math.pow(Double.valueOf(Dresult).doubleValue(), 2));
                break;
            }
            //三次方
            case "x³": {
                temExp = "cub";
                temRes = String.valueOf(Math.pow(Double.valueOf(Dresult).doubleValue(), 3));
                break;
            }
            //平方根
            case "√": {
                temRes = String.valueOf(Math.pow(Double.valueOf(Dresult).doubleValue(), 0.5));
                break;
            }
            //sin
            case "sin": {

                temRes = String.valueOf(Math.sin(Double.valueOf(Dresult).doubleValue()));
                break;
            }
            //cos
            case "cos": {
                temRes = String.valueOf(Math.cos(Double.valueOf(Dresult).doubleValue()));
                break;
            }
            //tan
            case "tan": {
                temRes = String.valueOf(Math.tan(Double.valueOf(Dresult).doubleValue()));
                break;
            }

            //asin
            case "arcsin": {
                temRes = String.valueOf(Math.asin(Double.valueOf(Dresult).doubleValue()));
                break;
            }

            //acos
            case "arccos": {
                temRes = String.valueOf(Math.acos(Double.valueOf(Dresult).doubleValue()));
                break;
            }
            //atan
            case "arctan": {
                temRes = String.valueOf(Math.atan(Double.valueOf(Dresult).doubleValue()));
                break;
            }
            //sin
            case "exp": {
                temRes = String.valueOf(Math.exp(Double.valueOf(Dresult).doubleValue()));
                break;
            }

            case "log10": {
                temRes = String.valueOf(Math.log10(Double.valueOf(Dresult).doubleValue()));
                break;
            }

            case "ln": {
                temRes = String.valueOf(Math.log(Double.valueOf(Dresult).doubleValue()));
                break;
            }

            case "ten": {
                temExp = "10^";
                temRes = String.valueOf(Math.pow(10,Double.valueOf(Dresult).doubleValue()));
                break;
            }
            case "n!": {
                temExp = "fac";
                temRes = String.valueOf(fact(Double.valueOf(Dresult).intValue()));
                break;
            }
            default: {
                break;
            }
        }
        switch(jud) {

            case "x²": {
                Dexpression = Dexpression.substring(0, SqrIndex) + temExp +"(" + Dresult + ")";
                break;
            }
            //三次方
            case "x³": {
                Dexpression = Dexpression.substring(0, CubIndex) + temExp +"(" + Dresult + ")";
                break;
            }
            //平方根
            case "√": {
                Dexpression = Dexpression.substring(0, SqrRIndex) + temExp +"(" + Dresult + ")";
                break;
            }
            //sin
            case "sin": {
                Dexpression = Dexpression.substring(0, sinIndex) + temExp +"("+ Dresult + ")";
                break;
            }
            //cos
            case "cos": {
                Dexpression = Dexpression.substring(0, cosIndex) + temExp +"("+ Dresult + ")";
                break;
            }
            //tan
            case "tan": {
                Dexpression = Dexpression.substring(0, tanIndex) + temExp +"(" + Dresult + ")";
                break;
            }

            //asin
            case "arcsin": {
                Dexpression = Dexpression.substring(0, asinIndex) + temExp +"(" + Dresult + ")";
                break;
            }

            //acos
            case "arccos": {
                Dexpression = Dexpression.substring(0, acosIndex) + temExp +"("+ Dresult + ")";
                break;
            }
            //atan
            case "arctan": {
                Dexpression = Dexpression.substring(0, atanIndex) + temExp +"(" + Dresult + ")";
                break;
            }
            //sin
            case "exp": {
                Dexpression = Dexpression.substring(0, expIndex) + temExp +"("+ Dresult + ")";
                break;
            }

            case "log10": {
                Dexpression = Dexpression.substring(0, log10Index) + temExp +"(" + Dresult + ")";
                break;
            }

            case "ln": {
                Dexpression = Dexpression.substring(0, lnIndex) + temExp +"(" + Dresult + ")";
                break;
            }

            case "ten": {
                Dexpression = Dexpression.substring(0, tenIndex) + temExp +"("+ Dresult + ")";
                break;
            }
            case "n!": {
                Dexpression = Dexpression.substring(0, facIndex) + temExp +"(" + Dresult + ")";
                break;
            }
            case ")": {
                Dexpression = Dexpression.substring(0, braIndex) + temExp +"("+ Dresult + ")";
                break;
            }
            default:
            {
                Dexpression = Dexpression.concat(temExp +"(" + Dresult + ")");
                break;
            }
        }
        Dresult = temRes;
    }

    //计算
    public double Cal(ArrayList expression)
    {
        double result = 0;
        String peek = new String();
        String exp = "";
        boolean ifAdd = false;
        if(expression.get(expression.size()-1) == "＋" || expression.get(expression.size()-1) == "－")
        {
            if(expression.get(expression.size()-1) =="＋")
            {
                peek = "＋";
            }
            else
            {
                peek ="－";

            }
            expression.remove(expression.size()-1);
            ifAdd = true;
        }

        exp = conver(expression);
        //exp = expression.toString();

        result = Calculate.conversion(exp);

        if(ifAdd)
        {
            ifAdd = true;
            expression.add(peek);
        }
        return result;
    }

    //转换为字符串
    public String conver(ArrayList expression)
    {
        String result = new String();
        for(int i=0;i<expression.size();i++)
        {
            //String now = expression.get(i).toString();
            switch(expression.get(i).toString())
            {
                case "＋":
                {
                    result = result.concat("+");
                    break;
                }
                case "－":
                {
                    result= result.concat("-");
                    break;
                }
                case "×":
                {
                    result = result.concat("*");
                    break;
                }
                case"÷":
                {
                    result = result.concat("/");
                    break;
                }
                default:
                {
                    result = result.concat(expression.get(i).toString());
                }
            }
        }
        result.replace(" ","");
        return result;
    }
    //计算阶乘
    public int fact(int number) {
        if (number <= 1)
            return 1;
        else
            return number * fact(number - 1);
    }


    //判断字符串是否等于某运算符
    public boolean inOtherCal(String exp,boolean isAll)
    {
        if(isAll)
        {
            if(exp.equals("x²") || exp.equals("x³") ||exp.equals("√")
                    || exp.equals("sin") || exp.equals("cos") ||exp.equals("tan")
                    ||exp.equals("arcsin") ||exp.equals("arccos") ||exp.equals("arctan")
                    ||exp.equals("exp") || exp.equals("log10") ||exp.equals("ln")
                    ||exp.equals("ten") ||exp.equals("n!")||exp.equals(")"))
            {
                return true;
            }
        }
        else
        {
            if(exp.equals("x²") || exp.equals("x³") ||exp.equals("√")
                    || exp.equals("sin") || exp.equals("cos") ||exp.equals("tan")
                    ||exp.equals("arcsin") ||exp.equals("arccos") ||exp.equals("arctan")
                    ||exp.equals("exp") || exp.equals("log10") ||exp.equals("ln")
                    ||exp.equals("ten") ||exp.equals("n!"))
            {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[])
    {
        Calculator calculator = new Calculator();
    }

}







