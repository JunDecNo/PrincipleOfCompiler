import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class SemanticAnalysis extends JFrame implements ActionListener {
    public SemanticAnalysis() {
        initComponents();
    }
    private JButton Reset;
    private JButton allDis;
    private JButton create;
    private JTextField jTextField;
    private JButton oneDis;
    private JButton save;
    private JButton start;
    private DefaultTableModel AnaLysisModel,MidModel;
    private final ArrayList<Vector<String>>Display=new ArrayList<>();//用来保存分析结果集合。
    private final Stack<Double> Semantic=new Stack<>();
    private final Stack<String> MidStack=new Stack<>();
    private int Address,TempIndex;
    private final ArrayList<Vector<String>>MidList=new ArrayList<>();
    private String result;
    //方法设置
    void create(){
        for (Vector<String> vector:MidList){
            MidModel.addRow(vector);
        }
        create.setEnabled(false);
    }
    void save(){
        StringBuilder string=new StringBuilder();
        string.append("---------------------中间代码---------------------\n");
        string.append("步骤\t\t\t运算符\t\t数据1\t\t数据2\t\t结果\n");
        for (Vector<String>vector:MidList){
            string.append(vector.get(0)).append("\t\t\t");
            string.append(vector.get(1)).append("\t\t\t");
            string.append(vector.get(2)).append("\t\t\t");
            string.append(vector.get(3)).append("\t\t\t");
            string.append(vector.get(4)).append("\n");
        }
        string.append("分析结果: ").append(
                AnaLysisModel.getValueAt(AnaLysisModel.getRowCount()-1,AnaLysisModel.getColumnCount()-2));
        string.append("\n分析值: ").append(result);
        Utils.SaveFile(this,string.toString());
    }
    void reset(){
        jTextField.setText("");
        AnaLysisModel.setRowCount(0);
        MidModel.setRowCount(0);
    }
    void oneDis(){
        if (!Display.isEmpty()) AnaLysisModel.addRow(Display.get(0));
        Display.remove(0);
    }
    void allDis(){
        for (int i=0;i<Display.size();i++){
            AnaLysisModel.addRow(Display.get(i));
            Display.remove(i);i--;
        }
    }
    void start(){
        Address=0;TempIndex=0;
        Semantic.clear();
        MidStack.clear();
        MidList.clear();
        AnaLysisModel.setRowCount(0);
        MidModel.setRowCount(0);
        if (!isLegal(jTextField.getText())){
            Utils.Notice(this,"输入字符存在不合法字符");return;
        }
        if (jTextField.getText().isEmpty()){
            Utils.Notice(this,"请输入算术表达式");
            return;
        }
        //匹配串
        String string=jTextField.getText().replaceAll("([0-9]*[.])?[0-9]+","i");
        //获取数字
        String []number=jTextField.getText().split("[+|[-]|*|/|%|^|\\(|\\)]");
        SLR lr=new SLR("E->E+T|E-T|T|-T\nT->T*F|T/F|T%F|F\nF->F^M|M\nM->(E)|i");
        //                          1  2  3  4      5   6   7   8     9  10     11 12
        lr.Semantic=true;
        lr.SetSentence(string);
        lr.SenAnalyze();
        for (Vector<String>vector:lr.Final){
            Vector<String>rows=new Vector<>();
            Vector<String>MidRows=new Vector<>();
            rows.add(vector.get(0));rows.add(vector.get(1));
            //第三列
            if(vector.get(2).contains("i")){
                for (String value : number) {
                    if (!value.equals("")) {
                        rows.add(vector.get(2).replaceAll("i", value));
                        break;
                    }
                }
            }
            else rows.add(vector.get(2));
            //第四列
            String str=vector.get(3);
            int s;
            if (vector.get(2).contains("i"))s=1;
            else s=0;
            for (int i=s;i<number.length;i++){
                if (number[i].equals(""))continue;
                str=str.replaceFirst("i",number[i]);
            }
            rows.add(str);
            rows.add(vector.get(4));
            //改变第五列显示
            String option=vector.get(4);
            double right,left;
            String leftTemp,rightTemp,resultTemp;
            switch (option){//语义
                case "r1":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(left+right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("ADD");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    break;//+
                case "r2":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(left-right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("SUB");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    break;//-
                case "r4":
                    right=Semantic.pop();rightTemp=MidStack.pop();resultTemp=GetTemp();
                    Semantic.push(-right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("MUS");
                    MidRows.add(rightTemp);MidRows.add("_");MidRows.add(resultTemp);
                    break;//-T
                case "r5":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(left*right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("MUL");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    break;//*
                case "r6":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(left/right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("DIV");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    //中间代码语义
                    break;///
                case "r7":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(left%right);
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("MOD");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    break;//%
                case "r9":
                    right=Semantic.pop();left=Semantic.pop();//运算
                    rightTemp=MidStack.pop();leftTemp=MidStack.pop();resultTemp=GetTemp();//代码
                    Semantic.push(Math.pow(left,right));
                    MidStack.push(resultTemp);
                    //中间代码语义
                    MidRows.add(GetAddress());MidRows.add("POW");
                    MidRows.add(leftTemp);MidRows.add(rightTemp);MidRows.add(resultTemp);
                    break;//^
                case "r12":
                    resultTemp=GetTemp();
                    Semantic.push(Double.parseDouble(number[GetFirst(number)]));
                    //中间代码语义
                    MidStack.push(resultTemp);
                    MidRows.add(GetAddress());
                    MidRows.add(":=");
                    MidRows.add(String.valueOf(Semantic.peek()));
                    MidRows.add("_");MidRows.add(resultTemp);
                    break;//移进
            }
            rows.add(GetStack());
            Display.add(rows);
            if (vector.get(4).contains("r12"))number[GetFirst(number)]="";
            if (MidRows.size()==5)MidList.add(MidRows);
        }
        result= String.valueOf(Semantic.pop());
        Utils.Notice(this,"计算结果为："+result);
        ButtonControl(true);
    }
    String GetStack(){
        StringBuilder stringBuilder=new StringBuilder();
        Stack<Double> temp = new Stack<>();
        stringBuilder.append("-");
        while (!Semantic.isEmpty())temp.push(Semantic.pop());
        while (!temp.isEmpty()){
            stringBuilder.append(temp.peek());
            stringBuilder.append("-");
            Semantic.push(temp.pop());
        }
        return stringBuilder.toString();
    }
    int GetFirst(String[]number){
        for (int i=0;i<number.length;i++)
            if (!number[i].equals(""))return i;
            return -1;
    }
    String GetTemp(){
        TempIndex++;
        return "T"+TempIndex;
    }
    String GetAddress(){
        Address++;
        return "("+Address+")";
    }
    boolean isLegal(String str){
        for (char ch:str.toCharArray())
            if (!((ch>='0'&&ch<='9')||(ch=='('||ch==')'||ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='%'||ch=='^'||ch=='.')))
                return false;
        return true;
    }
    void ButtonControl(boolean option){
        oneDis.setEnabled(option);
        allDis.setEnabled(option);
        create.setEnabled(option);
    }
    //结束
    private void initComponents() {
        AnaLysisModel=new DefaultTableModel();
        MidModel=new DefaultTableModel();
        Utils.SetColumnName(AnaLysisModel, new String[]{
                "步骤", "状态栈", "符号栈", "剩余输入串", "动作", "语义栈"
        });
        Utils.SetColumnName(MidModel, new String[]{
                "步骤", "运算符", "data_1", "data_2", "result"
        });
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        jTextField = new JTextField();
        Reset = new JButton();
        JLabel jLabel3 = new JLabel();
        JSeparator jSeparator1 = new JSeparator();
        JLabel jLabel4 = new JLabel();
        create = new JButton();
        save = new JButton();
        JScrollPane jScrollPane2 = new JScrollPane();
        JTable jTable2 = new JTable();
        JSeparator jSeparator2 = new JSeparator();
        JSeparator jSeparator3 = new JSeparator();
        start = new JButton();
        oneDis = new JButton();
        allDis = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable jTable1 = new JTable();
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
        AddListener();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==create)create();
        else if (e.getSource()==save)save();
        else if (e.getSource()==Reset)reset();
        else if (e.getSource()==oneDis)oneDis();
        else if (e.getSource()==allDis)allDis();
        else if (e.getSource()==start)start();
    }
    void AddListener(){
        create.addActionListener(this);
        save.addActionListener(this);
        Reset.addActionListener(this);
        oneDis.addActionListener(this);
        allDis.addActionListener(this);
        start.addActionListener(this);
        jTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ButtonControl(false);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }
}
