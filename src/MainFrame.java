import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {
    // 变量声明
    private JButton LL_ana;
    private JButton LR_ana;
    private JButton SE_ana;
    private JMenu about;
    private JMenu addi;
    private JMenuItem addi_LR;
    private JMenuItem addi_gram;
    private JMenuItem author;
    private JButton auto_sys;
    private JMenu comp;
    private JMenuItem comp_LL;
    private JMenuItem comp_LR;
    private JMenuItem comp_auto;
    private JMenuItem comp_dma;
    private JMenuItem comp_lex;
    private JMenuItem comp_sem;
    private JMenuItem detail;
    private JMenu edit;
    private JMenuItem edit_no;
    private JMenuItem edit_yes;
    private JTextArea error;
    private JButton exit;
    private JTable export;
    private JMenu file;
    private JMenuItem file_exit;
    private JMenuItem file_open;
    private JMenuItem file_save;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JMenuBar jMenuBar1;
    private JMenuItem set_text;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane5;
    private JPopupMenu.Separator jSeparator1;
    private JToolBar.Separator jSeparator10;
    private JSeparator jSeparator11;
    private JSeparator jSeparator12;
    private JSeparator jSeparator13;
    private JPopupMenu.Separator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JPopupMenu.Separator jSeparator4;
    private JPopupMenu.Separator jSeparator5;
    private JPopupMenu.Separator jSeparator6;
    private JToolBar jToolBar;
    private JButton lex_ana;
    private JButton no_edit;
    private JButton open;
    private JButton save;
    private JMenu setting;
    private JTextArea source;
    private JMenuItem set_theme;
    private JButton yes_edit;
    private javax.swing.JMenuItem set_style;
    private javax.swing.JPopupMenu.Separator jSeparator14;

    //数据变量
    FileDialog open_file=new FileDialog(this, "打开文件", FileDialog.LOAD),
            save_file=new FileDialog(this, "保存文件", FileDialog.SAVE);
    String[] Column ={"行号","单词","类型","是否合法","单词码"};
    DefaultTableModel tableModel=new DefaultTableModel();
    ArrayList<MidNode> list=new ArrayList<>();
    //声明结束
    MainFrame() {
        initComponents();
    }
    MainFrame(String str){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (str.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
    }
    //布局设置,使用NetBeans创建
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jToolBar = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        source = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        export = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        error = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        file_open = new javax.swing.JMenuItem();
        file_save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        file_exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        edit_yes = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        edit_no = new javax.swing.JMenuItem();
        comp = new javax.swing.JMenu();
        comp_lex = new javax.swing.JMenuItem();
        comp_auto = new javax.swing.JMenuItem();
        comp_dma = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        comp_LL = new javax.swing.JMenuItem();
        comp_LR = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        comp_sem = new javax.swing.JMenuItem();
        addi = new javax.swing.JMenu();
        addi_gram = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        addi_LR = new javax.swing.JMenuItem();
        setting = new javax.swing.JMenu();
        set_theme = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        set_text = new javax.swing.JMenuItem();
        set_style = new javax.swing.JMenuItem();
        about = new javax.swing.JMenu();
        author = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        detail = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("编译原理实验窗口");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToolBar.setBorder(null);
        jToolBar.setRollover(true);

        jLabel1.setText("源程序");

        jLabel2.setText("Token序列");

        source.setColumns(20);
        source.setRows(5);
        jScrollPane1.setViewportView(source);

        export.setModel(tableModel);
        jScrollPane2.setViewportView(export);

        jLabel3.setText("词法错误");

        error.setColumns(20);
        error.setRows(5);
        jScrollPane5.setViewportView(error);

        file.setText("文件(F)");

        file_open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        file_open.setText("打开(O) Ctrl+O");

        file.add(file_open);

        file_save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        file_save.setText("保存(S) Ctrl+S");
        file.add(file_save);
        file.add(jSeparator1);

        file_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        file_exit.setText("关闭(Q) Ctrl+Q");

        file.add(file_exit);

        jMenuBar1.add(file);

        edit.setText("编辑(E)");

        edit_yes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.ALT_DOWN_MASK));
        edit_yes.setText("允许编辑");
        edit.add(edit_yes);
        edit.add(jSeparator5);

        edit_no.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK));
        edit_no.setText("禁止编辑");
        edit.add(edit_no);

        jMenuBar1.add(edit);

        comp.setText("编译(B)");

        comp_lex.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_lex.setText("词法分析");

        comp.add(comp_lex);

        comp_auto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_auto.setText("自动机系统");
        comp.add(comp_auto);

        comp_dma.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_dma.setText("NFA_DFA_MFA");
        comp.add(comp_dma);
        comp.add(jSeparator2);

        comp_LL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_LL.setText("LL(1)分析");
        comp.add(comp_LL);

        comp_LR.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_LR.setText("LR(0)/SLR(1)分析");

        comp.add(comp_LR);
        comp.add(jSeparator3);

        comp_sem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        comp_sem.setText("语义分析");
        comp.add(comp_sem);

        jMenuBar1.add(comp);

        addi.setText("附加(A)");

        addi_gram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        addi_gram.setText("算术表达式检验");
        addi.add(addi_gram);
        addi.add(jSeparator4);

        addi_LR.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK));
        addi_LR.setText("(LA)LR(1)文法");
        addi.add(addi_LR);

        jMenuBar1.add(addi);

        setting.setText("设置(S)");

        set_theme.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_DOWN_MASK));
        set_theme.setText("主题");
        setting.add(set_theme);
        setting.add(jSeparator14);

        set_text.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_DOWN_MASK));
        set_text.setText("文本");

        setting.add(set_text);

        set_style.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_DOWN_MASK));
        set_style.setText("样式");
        setting.add(set_style);

        jMenuBar1.add(setting);

        about.setText("关于(O)");

        author.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        author.setText("作者");
        about.add(author);
        about.add(jSeparator6);

        detail.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        detail.setText("详细说明");
        about.add(detail);

        jMenuBar1.add(about);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator12))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jSeparator13))
                                                        .addComponent(jScrollPane5))
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel1)
                                                                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        pack();
        AddButton();
        AddListener();
    }// </editor-fold>
    private void AddButton(){
        //icon
        ImageIcon icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon9,icon10;
        icon1=new ImageIcon(MainFrame.class.getResource("/images/open.png"));
        icon2=new ImageIcon(MainFrame.class.getResource("/images/save.png"));
        icon3=new ImageIcon(MainFrame.class.getResource("/images/exit.png"));
        icon4=new ImageIcon(MainFrame.class.getResource("/images/allow.png"));
        icon5=new ImageIcon(MainFrame.class.getResource("/images/ban.png"));
        icon6=new ImageIcon(MainFrame.class.getResource("/images/lex_ana.png"));
        icon7=new ImageIcon(MainFrame.class.getResource("/images/auto_sys.png"));
        icon8=new ImageIcon(MainFrame.class.getResource("/images/LL_ana.png"));
        icon9=new ImageIcon(MainFrame.class.getResource("/images/LR_ana.png"));
        icon10=new ImageIcon(MainFrame.class.getResource("/images/semantic.png"));
        //button
        open=new JButton("打开");
        save=new JButton("保存");
        exit=new JButton("退出");
        yes_edit=new JButton("允许编辑");
        no_edit=new JButton("禁止编辑");
        lex_ana=new JButton("词法分析");
        auto_sys=new JButton("自动机系统");
        LL_ana=new JButton("LL(1)分析");
        LR_ana=new JButton("LR(0)分析");
        SE_ana=new JButton("语义分析");
        icon1.setImage(icon1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon5.setImage(icon5.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon6.setImage(icon6.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon7.setImage(icon7.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon8.setImage(icon8.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon9.setImage(icon9.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        icon10.setImage(icon10.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        open.setIcon(icon1);save.setIcon(icon2);exit.setIcon(icon3);yes_edit.setIcon(icon4);no_edit.setIcon(icon5);
        lex_ana.setIcon(icon6);auto_sys.setIcon(icon7);LL_ana.setIcon(icon8);LR_ana.setIcon(icon9);SE_ana.setIcon(icon10);
        jToolBar.add(open);
        jToolBar.add(save);
        jToolBar.add(exit);
        jToolBar.addSeparator();
        jToolBar.add(yes_edit);
        jToolBar.add(no_edit);
        jToolBar.addSeparator();
        jToolBar.add(lex_ana);
        jToolBar.add(auto_sys);
        jToolBar.addSeparator();
        jToolBar.add(LL_ana);
        jToolBar.add(LR_ana);
        jToolBar.addSeparator();
        jToolBar.add(SE_ana);
    }
    //布局结束
    public static void main(String args[]) {
        new MainFrame().setVisible(true);
    }
    //监听设置
    void AddListener(){
        open.addActionListener(this);file_open.addActionListener(this);
        save.addActionListener(this);file_save.addActionListener(this);
        exit.addActionListener(this);file_exit.addActionListener(this);
        edit_no.addActionListener(this);no_edit.addActionListener(this);
        edit_yes.addActionListener(this);yes_edit.addActionListener(this);
        lex_ana.addActionListener(this);comp_lex.addActionListener(this);
        comp_dma.addActionListener(this);auto_sys.addActionListener(this);comp_auto.addActionListener(this);
        addi_gram.addActionListener(this);addi_LR.addActionListener(this);
        author.addActionListener(this);detail.addActionListener(this);
        LL_ana.addActionListener(this);comp_LL.addActionListener(this);
        comp_LR.addActionListener(this);LR_ana.addActionListener(this);
        SE_ana.addActionListener(this);comp_sem.addActionListener(this);
        set_theme.addActionListener(this);set_text.addActionListener(this);set_style.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //文件
        if(e.getSource()==open||e.getSource()==file_open)file_open();
        else if(e.getSource()==save||e.getSource()==file_save)SelectSave();
        else if(e.getSource()==exit||e.getSource()==file_exit)System.exit(0);
        //编辑
        else if(e.getSource()==edit_no||e.getSource()==no_edit)source.setEditable(false);
        else if(e.getSource()==edit_yes||e.getSource()==yes_edit)source.setEditable(true);
        //编译
        else if(e.getSource()==lex_ana||e.getSource()==comp_lex)Lex_Ana();
        else if(e.getSource()==comp_dma||e.getSource()==comp_auto||e.getSource()==auto_sys)new NFA_DFA_MFA();
        else if(e.getSource()==comp_LL||e.getSource()==LL_ana)new LL_1_Grammar().setVisible(true);
        else if(e.getSource()==comp_LR||e.getSource()==LR_ana)new LR_Series().setVisible(true);
        else if(e.getSource()==SE_ana||e.getSource()==comp_sem)new SemanticAnalysis().setVisible(true);
        //附加
        else if(e.getSource()==addi_gram)new Grammar();
        else if(e.getSource()==addi_LR)new LR_Series().setVisible(true);
        //设置
        else if(e.getSource()==set_theme)Set_Theme();
        else if(e.getSource()==set_text)Set_Text();
        else if(e.getSource()==set_style)Set_Style();
        //关于
        else if(e.getSource()==author)Author();
        else if(e.getSource()==detail)Detail();
    }
    //结束

    //方法设置
    //打开文件
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
    //保存文件
    //作者信息
    void Author(){
        JOptionPane.showMessageDialog(this,
                "老师：杜安红\n作者声明：19111301035张治军\n专业：19级计算机科学与技术","作者",
                JOptionPane.INFORMATION_MESSAGE);
    }
    //词法分析方法
    void Lex_Ana(){
        tableModel=Utils.SetColumnName(tableModel,Column);
        list.clear();
        error.setText("");
        boolean bool=false;
        String text=source.getText();
        if (text.isEmpty())JOptionPane.showMessageDialog(this,"请输入源代码","提示",JOptionPane.INFORMATION_MESSAGE);
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
            list.add(lexcial.result.get(j));
            tableModel.addRow(value);
        }
        if (!bool&&lexcial.result.size()>0)error.append("无词法错误！！！");
    }

    private void SelectSave(){
        int index=JOptionPane.showOptionDialog(this,"请选择保存内容","选择保存方式",JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,new String[]{"源文件","词法分析结果"},-1);
        if (index==0){
            Utils.SaveFile(this,source.getText());
        }else if(index==1){
            save_file.setVisible(true);
            String DirPath=save_file.getDirectory();
            String FileName=save_file.getFile();
            File file;
            if (DirPath!=null&&FileName!=null){
                file=new File(DirPath,FileName);//实例文件对象
                try {
                    BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));//创建文件读取缓冲流
                    StringBuffer stringBuffer= new StringBuffer();//获取文本域的字符串
                    stringBuffer.append("------------------------------源代码------------------------------\n");
                    stringBuffer.append(source.getText()).append("\n");
                    stringBuffer.append("------------------------------Token序列------------------------------\n");
                    stringBuffer.append("行号\t\t\t\t单词\t\t\t\t类型\t\t\t\t是否合法\t\t\t 单词码\n");
                    for (MidNode midList : list) {
                        stringBuffer.append(midList.row_number).append("\t\t\t\t")
                                .append(midList.word).append("\t\t\t\t");
                        if (midList.type.length()==3){
                            stringBuffer.append(midList.type).append("\t\t\t");
                        }else{
                            stringBuffer.append(midList.type).append("\t\t\t\t");
                        }
                        stringBuffer.append(midList.legal).append("\t\t\t\t")
                                .append(midList.word_number).append("\n");
                    }
                    stringBuffer.append("------------------------------错误代码------------------------------\n");
                    stringBuffer.append(error.getText());
                    bufferedWriter.write(String.valueOf(stringBuffer));
                    bufferedWriter.close();//关闭缓冲流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void Set_Style() {
        Set_Theme();
    }

    private void Detail() {

    }

    private void Set_Text() {
        ChooseText chooseText=new ChooseText(source.getFont(),source);
        chooseText.setVisible(true);
    }

    private void Set_Theme() {
        //获取所有的主题
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        String []str=new String[infos.length];
        for (int i=0;i<str.length;i++)str[i]=infos[i].getName();
        int index=JOptionPane.showOptionDialog(this,"选择主题","选择主题",
                1,3,null,str,0);
        try
        {
            UIManager.setLookAndFeel(infos[index].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //设计方法结束
}
