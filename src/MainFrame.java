import com.sun.jdi.event.StepEvent;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Vector;

public class MainFrame implements ActionListener {
    JMenuBar jMenuBar;
    JMenu file,edit,comp,addi;
    JMenuItem file_open,file_save,file_close,edit_yes,edit_no,comp_lex,comp_auto,comp_LL,comp_LR,comp_dma,addi_gram;
    JToolBar jToolBar;
    JButton open,save,exit,yes_edit,no_edit,lex_ana,auto_sys,LL_ana,LR_ana;
    JTextArea source,error;
    JTable export;
    JFrame jf;
    JPanel WEST,EAST,EAST_TOP,EAST_BOTTOM;
    JScrollPane Source,Export,Error;
    JLabel L_source,L_export,L_error;
    ImageIcon icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon9;
    FileDialog open_file,save_file;
    String Column[]={"行号","单词","类型","是否合法","单词码"};
    DefaultTableModel tableModel;
    MainFrame(){
        jf=new JFrame("编译原理实验窗口");
        WEST=new JPanel();
        EAST=new JPanel();
        EAST_TOP=new JPanel();
        EAST_BOTTOM=new JPanel();
        jMenuBar=new JMenuBar();
        open_file=new FileDialog(jf,"打开文件",FileDialog.LOAD);
        save_file=new FileDialog(jf,"保存文件",FileDialog.SAVE);
        jToolBar=new JToolBar("快捷工具栏");
        file=new JMenu("   文件(F)   ");
        edit=new JMenu("   编辑(E)   ");
        comp=new JMenu("   编译(B)   ");
        addi=new JMenu("   附加(A)   ");
        file_open=new JMenuItem("打开(O) Ctrl+O");
        file_close=new JMenuItem("关闭(Q) Ctrl+Q");
        file_save=new JMenuItem("保存(S) Ctrl+S");
        edit_yes=new JMenuItem("允许编辑");
        edit_no=new JMenuItem("禁止编辑");
        comp_lex=new JMenuItem("词法分析");
        comp_auto=new JMenuItem("自动机系统");
        comp_LL=new JMenuItem("LL（1）分析");
        comp_LR=new JMenuItem("LR（0）分析");
        comp_dma=new JMenuItem("NFA_DFA_MFA");
        addi_gram=new JMenuItem("文法分析");
        open=new JButton("打开");
        save=new JButton("保存");
        exit=new JButton("退出");
        yes_edit=new JButton("允许编辑");
        no_edit=new JButton("禁止编辑");
        lex_ana=new JButton("词法分析");
        auto_sys=new JButton("自动机系统");
        LL_ana=new JButton("LL(1)分析");
        LR_ana=new JButton("LR(0)分析");
        L_source=new JLabel("源程序-----------------");
        L_export=new JLabel("Token序列--------------");
        L_error=new JLabel("词法错误----------------");
        icon1=new ImageIcon("src/images/open.png");
        icon2=new ImageIcon("src/images/save.png");
        icon3=new ImageIcon("src/images/exit.png");
        icon4=new ImageIcon("src/images/allow.png");
        icon5=new ImageIcon("src/images/ban.png");
        icon6=new ImageIcon("src/images/lex_ana.png");
        icon7=new ImageIcon("src/images/auto_sys.png");
        icon8=new ImageIcon("src/images/LL_ana.png");
        icon9=new ImageIcon("src/images/LR_ana.png");
        source=new JTextArea(20,35);
        export=new JTable();
        tableModel = (DefaultTableModel)export.getModel();
        tableModel.addColumn(Column[0]);tableModel.addColumn(Column[1]);
        tableModel.addColumn(Column[2]);tableModel.addColumn(Column[3]);
        tableModel.addColumn(Column[4]);

        error=new JTextArea(8,35);
        Source=new JScrollPane(source);
        Export=new JScrollPane(export);
        Error=new JScrollPane(error);
        //
        file.add(file_open);
        file.add(file_save);
        file.addSeparator();
        file.add(file_close);
        edit.add(edit_yes);
        edit.addSeparator();
        edit.add(edit_no);
        comp.add(comp_lex);
        comp.add(comp_auto);
        comp.addSeparator();
        comp.add(comp_LL);
        comp.add(comp_LR);
        comp.addSeparator();
        comp.add(comp_dma);
        addi.add(addi_gram);
        //
        jMenuBar.add(file);
        jMenuBar.add(edit);
        jMenuBar.add(comp);
        jMenuBar.add(addi);
        jToolBar.add(open);
        jToolBar.add(save);
        jToolBar.add(exit);
        jToolBar.add(yes_edit);
        jToolBar.add(no_edit);
        jToolBar.addSeparator();
        jToolBar.add(lex_ana);
        jToolBar.add(auto_sys);
        jToolBar.add(LL_ana);
        jToolBar.add(LR_ana);
        AddListener();
        SetShortCuts();
        //
        AddButtonImage();

        //
        WEST.setLayout(new BorderLayout());
        EAST.setLayout(new BorderLayout());
        EAST_TOP.setLayout(new BorderLayout());
        EAST_BOTTOM.setLayout(new BorderLayout());
        jf.setJMenuBar(jMenuBar);
        jf.add(jToolBar,BorderLayout.NORTH);
        WEST.add(L_source,BorderLayout.NORTH);
        WEST.add(Source,BorderLayout.CENTER);
        EAST_TOP.add(L_export,BorderLayout.NORTH);
        EAST_TOP.add(Export,BorderLayout.CENTER);
        EAST_BOTTOM.add(L_error,BorderLayout.NORTH);
        EAST_BOTTOM.add(Error,BorderLayout.CENTER);
        EAST.add(EAST_TOP,BorderLayout.NORTH);
        EAST.add(EAST_BOTTOM,BorderLayout.SOUTH);
        jf.add(WEST,BorderLayout.WEST);
        jf.add(EAST,BorderLayout.EAST);
        jf.setSize(840, 680);
        jf.setLocation(100, 100);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        source.requestFocus();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==open||e.getSource()==file_open)file_open();
        else if(e.getSource()==save||e.getSource()==file_save)file_save();
        else if(e.getSource()==exit||e.getSource()==file_close)System.exit(0);
        else if(e.getSource()==edit_no||e.getSource()==no_edit)source.setEditable(false);
        else if(e.getSource()==edit_yes||e.getSource()==yes_edit)source.setEditable(true);
        else if(e.getSource()==lex_ana||e.getSource()==comp_lex)Lex_Ana();
        else if(e.getSource()==comp_dma)new NFA_DFA_MFA();
        else if(e.getSource()==addi_gram)new Grammar();
    }
    void SetShortCuts(){
        file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
    }
    void AddListener(){
        open.addActionListener(this);file_open.addActionListener(this);
        save.addActionListener(this);file_save.addActionListener(this);
        exit.addActionListener(this);file_close.addActionListener(this);
        edit_no.addActionListener(this);no_edit.addActionListener(this);
        edit_yes.addActionListener(this);yes_edit.addActionListener(this);
        lex_ana.addActionListener(this);comp_lex.addActionListener(this);
        comp_dma.addActionListener(this);
        addi_gram.addActionListener(this);
    }
    void file_open(){
        File file;
        open_file.setVisible(true);
        String DirPath=open_file.getDirectory();
        String FileName=open_file.getFile();
        if (DirPath!=null&&FileName!=null){
            file=new File(DirPath,FileName);//实例文件对象
            source.setText(null);//对于打开新文件，在对象建立的情况下，就清空文本域
            try {
                BufferedReader bufferedReader=new BufferedReader(new FileReader(file));//创建文件读取缓冲流
                String line=null;//初始化字符串，用于获取每行的字符串
                while((line=bufferedReader.readLine())!=null){
                    source.append(line+'\n');//在每次读取一行时，换行
                }
                bufferedReader.close();//关闭缓冲流
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("文件读取失败");
            }
        }
    }
    void file_save(){
        save_file.setVisible(true);
        String DirPath=save_file.getDirectory();
        String FileName=save_file.getFile();
        File file;
        if (DirPath!=null&&FileName!=null){
            file=new File(DirPath,FileName);//实例文件对象
            try {
                BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));//创建文件读取缓冲流
                String string=source.getText();//获取文本域的字符串
                bufferedWriter.write(string);
                bufferedWriter.close();//关闭缓冲流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    void Lex_Ana(){
        tableModel.setRowCount(0);
        error.setText("");
        boolean bool=false;
        String text=source.getText();
        if (text.isEmpty())JOptionPane.showMessageDialog(jf,"请输入源代码","提示",JOptionPane.INFORMATION_MESSAGE);
            Lexcial lexcial= new Lexcial(text);
            for (int j=0;j<lexcial.result.size();j++){
                Vector<String> value = new Vector<String>();
                value.add(String.valueOf(lexcial.result.get(j).row_number));
                value.add(String.valueOf(lexcial.result.get(j).word));
                value.add(String.valueOf(lexcial.result.get(j).type));
                if(lexcial.result.get(j).legal){
                    value.add("合法");
                }else {
                    value.add("不合法");
                    error.append("行数："+value.get(0)+"  单词："+value.get(1)+"  类型："+value.get(2)+"\n");
                    bool=true;
                }
                value.add(String.valueOf(lexcial.result.get(j).word_number));
                tableModel.addRow(value);
            }
        if (!bool&&lexcial.result.size()>0)error.append("无词法错误！！！");
    }
    void AddButtonImage(){
        icon1.setImage(icon1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon5.setImage(icon5.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon6.setImage(icon6.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon7.setImage(icon7.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon8.setImage(icon8.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon9.setImage(icon9.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        open.setIcon(icon1);save.setIcon(icon2);exit.setIcon(icon3);yes_edit.setIcon(icon4);no_edit.setIcon(icon5);
        lex_ana.setIcon(icon6);auto_sys.setIcon(icon7);LL_ana.setIcon(icon8);LR_ana.setIcon(icon9);
    }
    public static void main(String []args) {
        new MainFrame();
    }
}