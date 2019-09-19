package com.lastlysly.view;

import com.lastlysly.utils.CommonConstants;
import com.lastlysly.utils.MyUtils;
import com.lastlysly.utils.MyZHConverterUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-16 15:56
 **/
public class MainView extends JXFrame implements ActionListener {
    /**
     * 北部面板
     */
    private JPanel northPanel;
    /**
     * 中部面板
     */
    private JPanel centerPanel;
    private JPanel centerRightPanel;
    private JPanel centerLeftPanel;

    private static MyZHConverterUtils myZHConverterUtils;


    /**
     * 南部面板
     */
    private JPanel southPanel;

    private JScrollPane jScrollPane;

    /**
     * 文件选择器
     */
    private JFileChooser jFileChooser;

    private JTextArea jTextArea;

    /**
     * 文件输入框
     */
    private JTextField filePathInput;

    /**
     * 选择文件夹
     */
    private JButton chooseFilePathBtn;

    /**
     * 转换按钮
     */
    private JButton changeBtn;

    private JComboBox comboBox;


    /**
     * 保存（新增词组对应）
     */
    private JButton saveBtn;

    private JTextField keyText;
    private JTextField valueText;

    private JList jList;

    private InfiniteProgressPanel glasspane = new InfiniteProgressPanel();

    /**
     *
     */
    public MainView(String title) throws HeadlessException {
        super(title);
        this.setLocation(300, 200);
        this.setSize(800, 600);
        //设置窗口大小可自由控制
        this.setResizable(true);

        //顶部组件
        northPanel = new JPanel();
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        glasspane.setBounds(100, 100, (dimension.width) / 2, (dimension.height) / 2);
        this.setGlassPane(glasspane);

        JLabel ipLabel = new JLabel("文件夹/文件路径：");
        filePathInput = new JTextField(20);
        filePathInput.setEnabled(false);
        chooseFilePathBtn = new JButton("选择");

        comboBox=new JComboBox();
        comboBox.addItem(CommonConstants.zhSimple2zhTw);
        comboBox.addItem(CommonConstants.zhTw2zhSimple);

        changeBtn = new JButton("确认转换");
        northPanel.add(ipLabel);
        northPanel.add(filePathInput);
        northPanel.add(chooseFilePathBtn);
        northPanel.add(comboBox);
        northPanel.add(changeBtn);

        //中部组件
        centerRightPanel = new JPanel();
        centerRightPanel.setBackground(Color.cyan);
        centerLeftPanel = new JPanel();
        centerLeftPanel.setBackground(Color.CYAN);

        keyText = new JTextField(10);
        valueText = new JTextField(10);
        saveBtn = new JButton("添加词组定义");

        centerLeftPanel.add(keyText,BorderLayout.NORTH);
        centerLeftPanel.add(valueText,BorderLayout.CENTER);
        centerLeftPanel.add(saveBtn,BorderLayout.SOUTH);
        jTextArea = new JTextArea();
        jTextArea.setBackground(Color.PINK);
        jTextArea.setMargin(new Insets(10, 10, 10, 10));
        //自动换行
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        jScrollPane = new JScrollPane(jTextArea);
        //垂直滚动条自动出现
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JSplitPane vsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerLeftPanel, jScrollPane);




        jList = new JList();
        refreshListData();
        jList.setBackground(Color.orange);
        Font font=new Font("Dialog", 1,18);
        jList.setFont(font);
        Border bord=BorderFactory.createTitledBorder("词组转换定义");
        jList.setBorder(bord);
        jScrollPane = new JScrollPane(jList);
        // 创建一个水平JSplitPane，左边是p1,右边是p2
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vsp, jScrollPane);
        sp.setDividerLocation(400);
        this.add(northPanel,BorderLayout.NORTH);
        this.add(sp,BorderLayout.CENTER);
        this.setVisible(true);

        /**
         *
         * 0：EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。
         * 1：HIDE_ON_CLOSE，只隐藏界面，setVisible(false)。
         * 2：DISPOSE_ON_CLOSE,隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。
         * 3：EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。
         *
         */
//        this.setDefaultCloseOperation(2);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int exi = JOptionPane.showConfirmDialog (null,
                        "要退出该程序吗？", "提示",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (exi == JOptionPane.YES_OPTION)
                {
                    System.exit (0);
                }
                else
                {
                    return;
                }


            }
        });
        chooseFilePathBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        changeBtn.addActionListener(this);

    }

    public static void main(String[] args) {
        myZHConverterUtils = new MyZHConverterUtils();
        new MainView("简繁转换客户端");
    }

    /**
     * Invoked when an action occurs.
     *  事件监听器
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * 文件选择
         */
        if (e.getSource() == chooseFilePathBtn){
            jFileChooser.showDialog(new JLabel(), "选择");
            File file=jFileChooser.getSelectedFile();
            if (file != null){
                filePathInput.setText(file.getPath());
            }
        }

        /**
         * 保存新词组对应
         */
        if (e.getSource() == saveBtn){
            String key = keyText.getText();
            String value = valueText.getText();

            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
                try {
                    myZHConverterUtils.storeDataToProperties(key,value);
                    JOptionPane.showMessageDialog(this, "成功插入一条词组对应信息", "提示",JOptionPane.WARNING_MESSAGE);
                    refreshListData();
                    jTextArea.append("成功插入一条词组对应信息：" + key + " <===> " + value + "\n");
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(this, "键值对不能为空", "提示",JOptionPane.WARNING_MESSAGE);
            }


        }

        /**
         * 确认转换
         */
        if (e.getSource() == changeBtn){
            glasspane.start();//开始动画加载效果 frame.setVisible(true);   // Later, to disable,在合适的地方关闭动画效果 glasspane.stop();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String converType = comboBox.getSelectedItem().toString();
                    System.out.println(converType);
                    String filePath = filePathInput.getText();
                    jTextArea.append("开始扫描..." + filePath +"\n");
                    jTextArea.setCaretPosition(jTextArea.getText().length());
                    try {
                        MyUtils.scanFolderAndConver(myZHConverterUtils,filePath,converType,jTextArea);
                        JOptionPane.showMessageDialog(MainView.getFrames()[0], "转换成功" , "提示",JOptionPane.WARNING_MESSAGE);
                        glasspane.stop();
                        jTextArea.append("done..." + "\n");
                        jTextArea.setCaretPosition(jTextArea.getText().length());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainView.getFrames()[0], "异常：" + e1.getMessage(), "异常",JOptionPane.ERROR_MESSAGE);
                        glasspane.stop();
                        jTextArea.append("转换异常..." + "\n");
                        jTextArea.setCaretPosition(jTextArea.getText().length());
                    }
                }
            }).start();


        }
    }

    /**
     * 刷新list数据
     */
    private void refreshListData(){

        java.util.List<String> listModel = new ArrayList<String>();
        Properties properties = myZHConverterUtils.getCharMap();
        for (String key2 : properties.stringPropertyNames()) {
            listModel.add(key2 + " <===> " + properties.getProperty(key2));
        }
        jList.setListData(listModel.toArray());
    }

}
