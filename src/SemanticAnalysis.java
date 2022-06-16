import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SemanticAnalysis extends JFrame implements ActionListener {
    public SemanticAnalysis() {
        initComponents();
    }
    public static void main(String args[]) {
        new SemanticAnalysis().setVisible(true);
    }
    private JButton Reset;
    private JButton allDis;
    private ButtonGroup buttonGroup1;
    private JButton create;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JTable jTable1;
    private JTable jTable2;
    private JTextField jTextField;
    private JButton oneDis;
    private JButton save;
    private JButton start;
    private DefaultTableModel AnaLysisModel,MidModel;
    private void initComponents() {
        AnaLysisModel=new DefaultTableModel();
        MidModel=new DefaultTableModel();
        AnaLysisModel=Utils.SetColumnName(AnaLysisModel,new String [] {
                "步骤", "状态栈", "符号栈", "剩余输入串", "动作", "语义栈"
        });
        MidModel=Utils.SetColumnName(MidModel,new String [] {
                "步骤", "运算符", "data_1", "data_2", "result"
        });
        buttonGroup1 = new ButtonGroup();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jTextField = new JTextField();
        Reset = new JButton();
        jLabel3 = new JLabel();
        jSeparator1 = new JSeparator();
        jLabel4 = new JLabel();
        create = new JButton();
        save = new JButton();
        jScrollPane2 = new JScrollPane();
        jTable2 = new JTable();
        jSeparator2 = new JSeparator();
        jSeparator3 = new JSeparator();
        start = new JButton();
        oneDis = new JButton();
        allDis = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("语义分析(算术表达式)");
        setLocation(200,100);
        jLabel1.setText("控制台");
        jLabel2.setText("输入算数表达式：");
        Reset.setText("重置");
        jLabel3.setText("分析过程");
        jLabel4.setText("中间代码");
        create.setText("生成中间代码");
        save.setText("保存");
        jTable2.setModel(MidModel);
        jScrollPane2.setViewportView(jTable2);
        start.setText("开始分析");
        oneDis.setText("一步显示");
        allDis.setText("全部显示");
        jTable1.setModel(AnaLysisModel);
        jScrollPane1.setViewportView(jTable1);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator1))
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(Reset, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(start, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(oneDis, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(allDis, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(create, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(save, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 104, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator2)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(create)
                                        .addComponent(save))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(Reset)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(start)
                                                        .addComponent(oneDis)
                                                        .addComponent(allDis))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );

        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==create);
        else if (e.getSource()==save);
        else if (e.getSource()==Reset);
        else if (e.getSource()==oneDis);
        else if (e.getSource()==allDis);
        else if (e.getSource()==start);
    }
}
