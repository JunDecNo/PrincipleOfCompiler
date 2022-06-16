import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Detail extends javax.swing.JFrame{
    public Detail() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        Link = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        jLabel6.setText("编译原理实验窗口");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("编译原理实验");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("词法分析");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("变量");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("方法");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("自动机系统");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("变量");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("方法");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("LL(1)词法分析");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("变量");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("方法");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("LR词法分析");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("变量");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("方法");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("语义分析");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("变量");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("方法");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree);
        jTextArea.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jLabel1.setText("编写人：张治军");

        jLabel2.setText("指导老师：杜安红");

        jLabel3.setText("班级：19级计算机科学与技术");

        Date.setForeground(new java.awt.Color(255, 0, 51));
        Date.setText("最后修改日期：2022/6/13");

        Link.setForeground(new java.awt.Color(0, 153, 255));
        Link.setText("访问GitHub");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(Link, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel6)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(Date)
                                        .addComponent(Link))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );

        pack();
        AddListener();
    }// </editor-fold>



    public static void main(String args[]) {
        new Detail().setVisible(true);
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel Date;
    private javax.swing.JLabel Link;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTree jTree;
    private javax.swing.JFrame TmpFrame;
    // End of variables declaration
    void Display(String[]strings){
        String father=strings[1],son=strings[2];
        String name=father+"----"+son;
        //在文档中读取[2][3][5][6][8][9][11][12][14][15]
        if (father.equals("词法分析")){
            if(son.equals("变量"))jTextArea.setText(Utils.GetFileString("src/detail.txt",0,15));
            if(son.equals("方法")){
                jTextArea.setText(Utils.GetFileString("src/detail.txt",0,14));
                ShowImage("src/DetailImage/Lexcial.png",name);
            }
        }else if(father.equals("自动机系统")){
            if(son.equals("变量"))jTextArea.setText(Utils.GetFileString("src/detail.txt",17,33));
            if(son.equals("方法")){
                jTextArea.setText(Utils.GetFileString("src/detail.txt",0,14));
                ShowImage("src/DetailImage/NFA_DFA_MFA.png",name);
            }
        }else if(father.equals("LL(1)词法分析")){
            if(son.equals("变量"))jTextArea.setText(Utils.GetFileString("src/detail.txt",35,63));
            if(son.equals("方法")){
                jTextArea.setText(Utils.GetFileString("src/detail.txt",0,14));
                ShowImage("src/DetailImage/LL_1_Grammar.png",name);
            }
        }else if(father.equals("LR词法分析")){
            if(son.equals("变量"))jTextArea.setText(Utils.GetFileString("src/detail.txt",65,111));
            if(son.equals("方法")){
                jTextArea.setText(Utils.GetFileString("src/detail.txt",0,14));
                ShowImage("src/DetailImage/LR_Series.png",name);
            }
        }else if(father.equals("语义分析")){
            if(son.equals("变量"))jTextArea.setText(Utils.GetFileString("src/detail.txt",114,132));
            if(son.equals("方法")){
                ShowImage("src/images/allow.png",name);
            }
        }
    }
    void AddListener(){
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String str= String.valueOf(jTree.getSelectionPath());
                String[]arrs=str.substring(1,str.length()-1).split(", ");
                if(arrs.length==3) {
                    Display(arrs);
                }
            }
        });
        Link.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String url="https://github.com/XifaNiu/Principle_OF_Compilation";
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    void ShowImage(String path,String name){
        if (TmpFrame!=null)TmpFrame.setVisible(false);
        TmpFrame=new JFrame(name);
        TmpFrame.setLocation(this.getX()+this.getWidth(),this.getY());
        JLabel labImg = new JLabel();
        Image fileImg = new ImageIcon(path).getImage();
        double x_scale=1.0*this.getWidth()/fileImg.getWidth(null),
                y_scale=1.0*this.getHeight()/fileImg.getHeight(null);
        Image titleImg;
        if (x_scale<y_scale) titleImg = Utils.imageScale(fileImg, x_scale, x_scale);
        else titleImg = Utils.imageScale(fileImg, y_scale, y_scale);
        labImg.setIcon(new ImageIcon(titleImg));
        labImg.setBounds(0,0,titleImg.getWidth(null),titleImg.getHeight(null));
        TmpFrame.add(labImg);
        TmpFrame.setSize(titleImg.getWidth(null),titleImg.getHeight(null));
        TmpFrame.setVisible(true);
        TmpFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image titleImg;
                Image fileImg = new ImageIcon(path).getImage();
                double x_scale=1.0*TmpFrame.getWidth()/fileImg.getWidth(null),
                        y_scale=1.0*TmpFrame.getHeight()/fileImg.getHeight(null);
                if (x_scale<y_scale) titleImg = Utils.imageScale(fileImg, x_scale, x_scale);
                else titleImg = Utils.imageScale(fileImg, y_scale, y_scale);
                labImg.setIcon(new ImageIcon(titleImg));
                labImg.setBounds(0,0,titleImg.getWidth(null),titleImg.getHeight(null));
                TmpFrame.add(labImg);
            }
        });
    }

}
