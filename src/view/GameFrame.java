package view;

import control.GameProcess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameFrame {
    JFrame gameFrame;//框体

    JPanel operatePanel;//操作面板
    JPanel viewPanel;//显示面板
    GameProcess gameControl;//游戏控制器

    //地图控制属性
    boolean isRunning = false;//是否处于繁殖状态
    int multipyCycle = 2;//繁殖周期
    int multipyCount = 0;//繁殖次数
    int row = 10;//地图大小
    int col = 10;

    int[][] cells = new int[10][10];//细胞阵列

    //世界绘制属性
    int sideLength = 20;//每个方格长宽
    int adjustLength = 50;//开始位置距窗体左上边界距离

    //文本框
    JTextField multipyCountField;
    //按钮
    JButton changeButton;
    JButton reStartButton;
    JButton startButton;

    /*****
     * 构造函数
     */
    public GameFrame() {
        gameFrame = new JFrame("细胞生命游戏");
        //大小不可变
        gameFrame.setResizable(false);

        //设置位置
        Dimension screnSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1200;
        int height = 800;
        gameFrame.setBounds((screnSize.width - width) / 2, (screnSize.height - height) / 2, width, height);
        //设置布局
        gameFrame.setLayout(null);

        gameControl = new GameProcess(row, col);
        //获取细胞世界
        cells = gameControl.getAllCellStatus();
        //初始化面板
        operatePanel = getOperatePanel();
        viewPanel = getViewPanel();

        gameFrame.add(viewPanel);
        gameFrame.add(operatePanel);

        //设置窗体可见
        gameFrame.setVisible(true);

        gameFrame.addMouseListener(new ClickMonitor());

    }

    /****
     * 主函数
     */
    public static void main(String[] args) {
        new GameFrame();
    }

    /***************************
     * 操作界面
     */
    private JPanel getOperatePanel() {
        JPanel operatePanel = new JPanel();

        //设置背景色及面板位置
        operatePanel.setBounds(800, 0, 360, 800);

        //设置布局
        operatePanel.setLayout(new GridLayout(12, 1, 10, 10));
        //创建组件

        //宽度
        JPanel mapWidthPanel = new JPanel();
        JLabel mapWidthLabel = new JLabel("地图行数：");
        JTextField mapWidthFiled = new JTextField(20);
        mapWidthFiled.setText(String.valueOf(col));
        mapWidthPanel.add(mapWidthLabel);
        mapWidthPanel.add(mapWidthFiled);

        //高度
        JPanel mapLengthPanel = new JPanel();
        JLabel mapLengthLabel = new JLabel("地图列数：");
        JTextField mapLengthField = new JTextField(20);
        mapLengthField.setText(String.valueOf(row));
        mapLengthPanel.add(mapLengthLabel);
        mapLengthPanel.add(mapLengthField);

        JPanel multipyCountPanel = new JPanel();
        JLabel multipyCountLabel = new JLabel("繁殖次数：");
        multipyCountField = new JTextField(20);
        multipyCountField.setText(String.valueOf(multipyCount));
        //设置次数为不可更改
        multipyCountField.setEditable(false);
        multipyCountPanel.add(multipyCountLabel);
        multipyCountPanel.add(multipyCountField);

        JPanel multipyCycPanel = new JPanel();
        JLabel multipyCycLabel = new JLabel("繁殖周期(s)：");
        JTextField multipyCycField = new JTextField(20);
        multipyCycField.setText(String.valueOf(multipyCycle));
        multipyCycPanel.add(multipyCycLabel);
        multipyCycPanel.add(multipyCycField);

        //添加按钮
        changeButton = new JButton("修改设置");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //清除绘制图案
                clearPaint(gameFrame.getGraphics());
                //读取控件值到成员变量中
                row = Integer.parseInt(mapWidthFiled.getText());
                col = Integer.parseInt(mapLengthField.getText());
                multipyCycle = Integer.parseInt(multipyCycField.getText());
                multipyCount = 0;
                multipyCountField.setText(String.valueOf(multipyCount));
                //初始化游戏地图
                gameControl = new GameProcess(row, col);

                //绘制背景板
                paintGirdLines(gameFrame.getGraphics());

                //为窗体添加监听
            }
        });

        reStartButton = new JButton("生成地图");
        reStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //设置随机地图
                gameControl.setDefaultWorld();
                cells = gameControl.getAllCellStatus();

                //绘制题图
                paintGirdLines(gameFrame.getGraphics());
                paintCells(gameFrame.getGraphics());

            }
        });

        startButton = new JButton("开始繁殖");
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new MultipyThread().start();
            }

        });

        JButton stopButton = new JButton("停止繁殖");
        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = false;
                changeButton.setEnabled(true);
                reStartButton.setEnabled(true);
                startButton.setEnabled(true);
            }
        });


        //组件添加到面板
        //空白组件
        JLabel spaceLabel = new JLabel("");
        operatePanel.add(spaceLabel);
        operatePanel.add(spaceLabel);
        operatePanel.add(mapWidthPanel);
        operatePanel.add(mapLengthPanel);
        operatePanel.add(multipyCountPanel);
        operatePanel.add(multipyCycPanel);
        operatePanel.add(changeButton);
        operatePanel.add(reStartButton);
        operatePanel.add(startButton);
        operatePanel.add(stopButton);

        return operatePanel;
    }

    /*******
     * 显示界面
     */
    private JPanel getViewPanel() {
        JPanel viewPanel = new JPanel();

        viewPanel.setBounds(0, 0, 800, 800);

        return viewPanel;
    }

    /**********
     * 清除绘制图案
     */
    public void clearPaint(Graphics g) {
        g.setColor(new Color(238, 238, 238));
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= col; j++) {

                g.fillRect(j * sideLength + adjustLength, i * sideLength + adjustLength, sideLength, sideLength);

            }
        }
    }

    /**************
     * 绘制网格线
     */
    public void paintGirdLines(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        //画网格 横向
        for (int i = 0; i <= row; i++) {
            g.drawLine(adjustLength, i * sideLength + adjustLength, col * sideLength + adjustLength, i * sideLength + adjustLength);
        }
        //纵向
        for (int i = 0; i <= col; i++) {
            g.drawLine(i * sideLength + adjustLength, adjustLength, i * sideLength + adjustLength, row * sideLength + adjustLength);
        }
    }

    /*****
     * 绘制网格中的细胞
     */
    public void paintCells(Graphics g) {
        g.setColor(new Color(0, 0, 0));

        //绘制格子
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (1 == cells[i][j]) {
                    g.fillRect(j * sideLength + adjustLength, i * sideLength + adjustLength, sideLength, sideLength);
                }
            }
        }
    }

    /*****
     * 绘制特定位置的细胞
     */
    public void createCell(Graphics g, int row, int col) {
        g.setColor(new Color(0, 0, 0));

        g.fillRect(col * sideLength + adjustLength, row * sideLength + adjustLength, sideLength, sideLength);
    }

    /*********
     * 消除某一位置的细胞
     */
    public void killCell(Graphics g, int row, int col) {
        g.setColor(new Color(238, 238, 238));
        g.fillRect(col * sideLength + adjustLength, row * sideLength + adjustLength, sideLength, sideLength);
        clearPaint(g);
    }

    class MultipyThread extends Thread {
        @Override
        public void run() {
            isRunning = true;    //是否正在繁殖
            changeButton.setEnabled(false);
            reStartButton.setEnabled(false);
            startButton.setEnabled(false);

            //按钮设置为不可点击
            while (isRunning) {
                gameControl.cellMultiply();//细胞世界改变一次
                cells = gameControl.getAllCellStatus();//获取改变后细胞生存数组

                clearPaint(gameFrame.getGraphics());//清除原图
                paintGirdLines(gameFrame.getGraphics());//绘制新图
                paintCells(gameFrame.getGraphics());//绘制细胞
                multipyCount++;//繁殖一次，周期加一
                multipyCountField.setText(String.valueOf(multipyCount));//设置当前周期

                try {
                    Thread.sleep(multipyCycle * 1000);//线程休眠multipyCycle秒
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    class ClickMonitor implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            int point_x = e.getX();
            int point_y = e.getY();

            if ((point_x < adjustLength) || (point_x > col * sideLength + adjustLength) || (point_y < adjustLength) || (point_y > row * sideLength + adjustLength)) {
                return;
            }
            int col = (point_x - adjustLength) / sideLength;
            int row = (point_y - adjustLength) / sideLength;

            boolean beforeStatus = gameControl.changeCellStatus(row, col);
            if (beforeStatus) {
                killCell(gameFrame.getGraphics(), row, col);
            } else {
                createCell(gameFrame.getGraphics(), row, col);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }
}

