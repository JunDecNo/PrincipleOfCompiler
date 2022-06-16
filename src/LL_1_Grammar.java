import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class LL_1_Grammar extends JFrame implements ActionListener {
    private JTextArea Input_Area;
    private JTextField Sentence_Area;
    private JButton allDis_btn;
    private JButton ana_btn;
    private JButton confirm_btn;
    private JButton first_btn;
    private JTable first_table;
    private JButton follow_btn;
    private JTable follow_table;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JButton oneDis_btn;
    private JButton open_file_btn;
    private JButton predict_btn;
    private JTable predict_table;
    private JTable result_table;
    private JButton save_ana_btn;
    private JButton save_file_btn;
    public DefaultTableModel First_Model,Follow_Model,Predict_Model,Result_Model;
    public ArrayList<LLNode> Node_List=new ArrayList<>();//存储产生式
    public ArrayList<String>Vn=new ArrayList<>(),V=new ArrayList<>(),Vt=new ArrayList<>();//存储字符
    public Map<String, Integer> isNullMap=new HashMap<>();//是否为空的字典
    public Map<String,ArrayList<Character>>FirstMap=new HashMap<>();//first集合
    public Map<String,ArrayList<Character>>FollowMap=new HashMap<>();//follow集合
    public Map<String,ArrayList<Character>>SelectMap=new HashMap<>();//Select集合
    public ArrayList<Vector<String>>Final=new ArrayList<>(),Save;//用来保存分析结果集合。
    char epsilon='$',end='#';
    String notice_str;//通知字符串
    LL_1_Grammar(){
        initComponents();
        AddListener();//设置监听器
        ComponentControl();
    }
    LL_1_Grammar(String str){//为LR分析构造
        Node_List=GetNodeList(str);
        Vn=Utils.GetVn(Node_List);
        V=Utils.GetV(Node_List);
        Vt=Utils.GetVt(Node_List);
        CanNull();
        First();
        Follow();
        Select();
    }
    //布局设置,使用NetBeans创建
    @SuppressWarnings("unchecked")
    private void initComponents() {
        First_Model=new DefaultTableModel();
        Follow_Model=new DefaultTableModel();
        Predict_Model=new DefaultTableModel();
        Result_Model=new DefaultTableModel();
        jLabel1 = new JLabel();
        open_file_btn = new JButton();
        confirm_btn = new JButton();
        save_file_btn = new JButton();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jScrollPane1 = new JScrollPane();
        Input_Area = new JTextArea();
        first_btn = new JButton();
        follow_btn = new JButton();
        jSeparator1 = new JSeparator();
        jLabel5 = new JLabel();
        jSeparator2 = new JSeparator();
        jScrollPane2 = new JScrollPane();
        first_table = new JTable();
        jLabel6 = new JLabel();
        jSeparator3 = new JSeparator();
        jScrollPane3 = new JScrollPane();
        follow_table = new JTable();
        jLabel7 = new JLabel();
        jSeparator4 = new JSeparator();
        predict_btn = new JButton();
        jScrollPane4 = new JScrollPane();
        predict_table = new JTable();
        jLabel8 = new JLabel();
        jSeparator5 = new JSeparator();
        jLabel9 = new JLabel();
        Sentence_Area = new JTextField();
        ana_btn = new JButton();
        oneDis_btn = new JButton();
        allDis_btn = new JButton();
        save_ana_btn = new JButton();
        jLabel10 = new JLabel();
        jSeparator6 = new JSeparator();
        jScrollPane5 = new JScrollPane();
        result_table = new JTable();

        setTitle("LL(1)自顶向下分析");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 730));

        jLabel1.setText("文法输入");

        open_file_btn.setText("打开文件");

        confirm_btn.setText("确认文法");

        save_file_btn.setText("保存文件");

        jLabel2.setText("注意事项:请输入满足LL_1_Grammar(1)最简判别的2型文法。一行一个产生式");

        jLabel3.setText("注意事项:请输入形式如S->A的产生式，空格用_表示，空用$表示");

        jLabel4.setText("注意事项:开始符为第一个产生式的左部，非终结符用大写字母表示");

        Input_Area.setColumns(20);
        Input_Area.setRows(5);
        jScrollPane1.setViewportView(Input_Area);

        first_btn.setText("生成First集");

        follow_btn.setText("生成Follow集");

        jLabel5.setText("First集");

        first_table.setModel(First_Model);
        jScrollPane2.setViewportView(first_table);

        jLabel6.setText("Follow集");

        follow_table.setModel(Follow_Model);
        jScrollPane3.setViewportView(follow_table);

        jLabel7.setText("预测分析表");

        predict_btn.setText("构造预测分析表");

        predict_table.setModel(Predict_Model);
        jScrollPane4.setViewportView(predict_table);

        jLabel8.setText("分析句子");

        jLabel9.setText("待分析句子：");

        ana_btn.setText("分析");

        oneDis_btn.setText("单步显示");

        allDis_btn.setText("一键显示");

        save_ana_btn.setText("保存结果");

        jLabel10.setText("分析结果");

        result_table.setModel(Result_Model);
        jScrollPane5.setViewportView(result_table);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator2))
                                        .addComponent(jScrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(open_file_btn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(confirm_btn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(save_file_btn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(first_btn, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(follow_btn)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator3)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator4))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator5))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(ana_btn, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(oneDis_btn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(allDis_btn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(save_ana_btn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                                .addGap(37, 37, 37))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator6))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(predict_btn)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane5)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Sentence_Area)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel1)
                                                                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel7))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(open_file_btn)
                                                                        .addComponent(confirm_btn)
                                                                        .addComponent(save_file_btn)
                                                                        .addComponent(predict_btn))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jLabel3)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jLabel4)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jScrollPane4, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(first_btn)
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(follow_btn)
                                                                                .addComponent(jLabel8))))
                                                        .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(5, 5, 5)
                                                                .addComponent(jLabel5))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel9)
                                                                        .addComponent(Sentence_Area, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                                        .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                                                .addGap(7, 7, 7)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(ana_btn)
                                                        .addComponent(oneDis_btn)
                                                        .addComponent(allDis_btn)
                                                        .addComponent(save_ana_btn))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>
    //布局结束
    //方法设置
    void Save_Ana(){
        StringBuilder res= new StringBuilder();
        res.append("----------------------------LL(1)文法-----------------------------------\n");
        for (LLNode node:Node_List){
            res.append(node.Vn).append("->").append(node.V).append("\n");
        }
        res.append("-----------------------------句子分析过程-----------------------------------\n");
        res.append("步骤\t\t\t\t\t分析栈\t\t\t\t剩余字符串\t\t\t\t推导所用的产生式或匹配\n");
        if (Save.isEmpty()){
            Utils.Notice(this,"输入结果为空，无法保存");
            return;
        }
        for (int i=0;i<Save.size();i++){
            Vector<String> vector=Save.get(i);
            for (String s:vector){
                res.append(s).append("\t\t\t\t\t");
            }
            res.append("\n");
            Save.remove(i);i--;
        }
        res.append("分析结果:");
        if (notice_str.equals("接受"))res.append("分析完成，匹配成功");
        else if(notice_str.equals("未找到产生式"))res.append("分析完成,未找到产生式");
        else if(notice_str.contains("不匹配"))res.append("分析完成,终结符匹配不成功");
        Utils.SaveFile(this,res.toString());
    }
    void Open_File(){
        String str=Utils.OpenFile(this);
        if (!str.isEmpty()){
            DataPreprocess(str);
            ComponentControl();
        }
        else Utils.Notice(this,"输入文件数据为空");
    }
    void Save_File(){
        if (Input_Area.getText().equals(""))Utils.Notice(this,"输入串为空，无法保存");
        else Utils.SaveFile(this,Input_Area.getText());
    }
    void Analyze(){//分析句子//终止
        Final.clear();
        Result_Model.setRowCount(0);
        Result_Model.setColumnCount(0);
        String sentence=Sentence_Area.getText();
        int produce=0;
        if (sentence.isEmpty())Utils.Notice(this,"输入句子为空，分析失败");
        else{
            //初始化剩余字符串与符号栈
            ArrayList<Character>chars=new ArrayList<>(),symbol=new ArrayList<>();
            symbol.add(end);//加入#
            symbol.add(Vn.get(0).charAt(0));//将开始符号加入
            for (char ch:sentence.toCharArray())chars.add(ch);//将句子加入
            chars.add(end);//将结束符加入
            while(symbol.size()!=0){
                produce++;
                Vector<String>vector=new Vector<>();
                char pre_ana=chars.get(0),status=symbol.get(symbol.size()-1);//分别获取分析栈最后一个，符号串第一个
                if (!Character.isUpperCase(status)){//为终结符
                    if (pre_ana==status){
                        if (status==end){
                            vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                            vector.add(list2str(chars));vector.add("接受");
                            Final.add(vector);
                        }else{
                            vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                            vector.add(list2str(chars));vector.add(status+"匹配");
                            Final.add(vector);
                        }
                        //删除符号串的第一个和分析栈的最后一个
                        chars.remove(0);symbol.remove(symbol.size()-1);
                        continue;//进行下一次循环
                    }else{
                        vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                        vector.add(list2str(chars));vector.add(status+"不匹配");
                        Final.add(vector);
                        break;
                    }
                }
                boolean isExist=true;
                for (LLNode node:Node_List){
                    if (node.Vn.equals(String.valueOf(status)) ){
                        if (SelectMap.get(node.Vn+"->"+node.V).contains(pre_ana)){//匹配到了产生式
                            if (node.V.equals("$")){//使用推出空的产生式
                                vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                                vector.add(list2str(chars));vector.add("选择"+node.Vn+"->"+node.V);
                                Final.add(vector);
                                symbol.remove(symbol.size()-1);
                            }else{
                                vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                                vector.add(list2str(chars));vector.add("选择"+node.Vn+"->"+node.V);
                                Final.add(vector);
                                //将V逆序加入分析栈中
                                symbol.remove(symbol.size()-1);
                                char[]tmp=node.V.toCharArray();
                                for (int j=tmp.length-1;j>=0;j--)symbol.add(tmp[j]);
                            }
                            isExist=false;
                        }
                    }
                }
                if (isExist){//没有找到产生式
                    vector.add(String.valueOf(produce));vector.add(list2str(symbol));
                    vector.add(list2str(chars));vector.add("未找到产生式");
                    Final.add(vector);
                    break;
                }
            }
            Utils.Notice(this,"分析成功");
            oneDis_btn.setEnabled(true);
            allDis_btn.setEnabled(true);
            save_ana_btn.setEnabled(true);
            String []str={"步骤","分析栈","剩余字符串","推导所用的产生式或匹配"};
            for(String s:str){
                Result_Model.addColumn(s);
            }//添加字符串
            notice_str = Final.get(Final.size()-1).get(3);
            Save=new ArrayList<>(Final);
        }
    }
    String list2str(ArrayList<Character>source){
        StringBuilder str= new StringBuilder();
        for (char ch:source) str.append(ch);
        return str.toString();
    }
    void One_Show(){
        if (Final.isEmpty()){
            if (notice_str.equals("接受"))Utils.Notice(this,"分析完成，匹配成功");
            else if(notice_str.equals("未找到产生式"))Utils.Notice(this,"分析完成,未找到产生式");
            else if(notice_str.contains("不匹配"))Utils.Notice(this,"分析完成,终结符匹配不成功");
        }else{
            Result_Model.addRow(Final.get(0));
            Final.remove(0);
        }
    }
    void Result_Show(){
        for (int i=0;i<Final.size();i++){
            Result_Model.addRow(Final.get(i));
            Final.remove(i);i--;
        }
        if (Final.isEmpty()){
            if (notice_str.equals("接受"))Utils.Notice(this,"分析完成，匹配成功");
            else if(notice_str.equals("未找到产生式"))Utils.Notice(this,"分析完成,未找到产生式");
            else if(notice_str.contains("不匹配"))Utils.Notice(this,"分析完成,终结符匹配不成功");
        }
    }
    //show first collection
    void First_Show(){
        first_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        First_Model.setRowCount(0);
        First_Model.setColumnCount(0);//全部清空
        First_Model.addColumn("Vn");
        //创建列
        for (String vt:Vt){
            First_Model.addColumn(vt);
        }
        //根据first集合创建行
        for(String vn:Vn){
            ArrayList<Character>first_collection=FirstMap.get(vn);
            Vector<String>vector=new Vector<>();
            vector.add(vn);
            for (int i=1;i<First_Model.getColumnCount();i++){
                char ch=First_Model.getColumnName(i).charAt(0);
                if (first_collection.contains(ch)){
                    vector.add("1");
                }else vector.add("");
            }
            First_Model.addRow(vector);
        }
    }
    void Follow_Show(){
        follow_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Follow_Model.setRowCount(0);
        Follow_Model.setColumnCount(0);//全部清空
        Follow_Model.addColumn("Vn");
        //创建列
        for (String vt:Vt){
            if (!vt.equals("$")) Follow_Model.addColumn(vt);
        }
        Follow_Model.addColumn(end);
        //根据follow集合创建行
        for(String vn:Vn){
            ArrayList<Character>follow_collection=FollowMap.get(vn);
            Vector<String>vector=new Vector<>();
            vector.add(vn);
            for (int i=1;i<Follow_Model.getColumnCount();i++){
                char ch=Follow_Model.getColumnName(i).charAt(0);
                if (follow_collection.contains(ch)){
                    vector.add("1");
                }else vector.add("");
            }
            Follow_Model.addRow(vector);
        }
    }
    void Predict_Show(){
        predict_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Predict_Model.setRowCount(0);
        Predict_Model.setColumnCount(0);//全部清空
        Predict_Model.addColumn("Vn");
        //创建列
        for (String vt:Vt){
            if (!vt.equals("$")) Predict_Model.addColumn(vt);
        }
        Predict_Model.addColumn(end);
        for (String vn:Vn){
            Vector<String>vector=new Vector<>();//行数据
            vector.add(vn);
            for (int i=1;i<Follow_Model.getColumnCount();i++){//每一行
                boolean isnull=true;
                char ch=Follow_Model.getColumnName(i).charAt(0);//终结符
                for (LLNode node:Node_List){
                    if (node.Vn.equals(vn)&&SelectMap.get(node.Vn+"->"+node.V).contains(ch)){
                        vector.add("->"+node.V);isnull=false;break;
                    }
                }
                if (isnull)vector.add("");
            }
            Predict_Model.addRow(vector);
        }
    }
    //修改isNull能到空的集合
    void CanNull(){
        //复制产生式用于删除
        ArrayList<LLNode>Tmp_List=new ArrayList<>();
        for (LLNode node:Node_List){
            LLNode tmp=new LLNode(node);
            Tmp_List.add(tmp);
        }
        for (String vn:Vn) isNullMap.put(vn,0);
        //第一次扫描
        for (String vn: Vn) {//对每个非终结符进行操作
            for (int i=0;i<Tmp_List.size();i++){//遍历所有产生式
                if (Tmp_List.get(i).Vn.equals(vn)){
                    if (Tmp_List.get(i).V.equals("$")) {//可以到空，置为1
                        isNullMap.put(vn,1);
                    }
                    if (isContainVt(Tmp_List.get(i).V)) {//包含终结符删除
                        Tmp_List.remove(i);i--;
                    }
                }
            }
            if (Utils.GetNodeV(vn,Tmp_List).isEmpty())isNullMap.put(vn,-1);
        }
        //删除产生式
        for (int i=0;i<Tmp_List.size();i++){
            if (isNullMap.get(Tmp_List.get(i).Vn)!=0){
                Tmp_List.remove(i);i--;
            }
        }
        //第二次扫描
        while(!Tmp_List.isEmpty()){//如果存在未定则继续循环
            for (int i=0;i<Tmp_List.size();i++){
                if (isNullMap.get(Tmp_List.get(i).Vn) == 0){
                    boolean Can=true,Zero=false;
                    ArrayList<String> tmp = Utils.GetNodeV(Tmp_List.get(i).Vn,Tmp_List);//获取左部
                    for (String str:tmp){//判断每个产生式子
                        Can=true;
                        char []chars=str.toCharArray();
                        for (char ch:chars){//一个产生式而言
                            if (isNullMap.get(String.valueOf(ch))==-1){//如果有否，则无法到达
                                Can=false;
                            }else if(isNullMap.get(String.valueOf(ch))==0){//存在未定，则判断为否，设置为0
                                Can=false;Zero=true;
                            }
                        }
                        if (Can){//可以推导出空，则赋值为1
                            isNullMap.put(Tmp_List.get(i).Vn,1);
                            break;//说明非终结符已定，退出
                        }
                    }
                    if (!Can&&!Zero){//如果所有产生式中都不存在0，同时不存在未定，置为-1
                        isNullMap.put(Tmp_List.get(i).Vn,-1);
                    }
                    //删除所有已定产生式
                    for (int j=0;j<Tmp_List.size();j++){
                        if (isNullMap.get(Tmp_List.get(j).Vn)!=0){
                            Tmp_List.remove(j);j--;
                        }
                    }
                }
            }
        }
    }
    //构造First集合
    void First(){
        for (LLNode node:Node_List){
            ArrayList<Character>add=new ArrayList<>();
            FirstMap.put(node.Vn,add);
        }//初始化
        while (true){
            int Size=0,Res=0;
            for (String vn:Vn)Size+=FirstMap.get(vn).size();
            for (String s : Vn) {//非终结符
                for (LLNode node : Node_List) {//锁定产生式
                    if (node.Vn.equals(s)) {
                        char []chars= node.V.toCharArray();//将产生式右部拆分成数组
                        char first =chars[0];
                        if (!Character.isUpperCase(first) && first != epsilon) {
                            if(!FirstMap.get(s).contains(first)) FirstMap.get(s).add(first);//如果第一个为终结符，则加入first
                        } else {
                            for (char aChar:chars){
                                if (Character.isUpperCase(aChar)){
                                    //非终结符可以推导出空，加入
                                    if(isNullMap.get(String.valueOf(aChar))==1){
                                        FirstMap.put(s,Add(FirstMap.get(s), FirstMap.get(String.valueOf(aChar))));
                                    }
                                    else if(isNullMap.get(String.valueOf(aChar))==-1){//非终结符不可以推导出空
                                        FirstMap.put(s,Add(FirstMap.get(s), FirstMap.get(String.valueOf(aChar))));
                                        break;
                                    }
                                }else if(aChar==epsilon){
                                    FirstMap.get(s).add(aChar);
                                }else{
                                    FirstMap.get(s).add(aChar);break;
                                }
                            }
                        }
                        FirstMap.put(s,Set(FirstMap.get(s)));
                    }
                }
            }
            for (String vn:Vn)Res+=FirstMap.get(vn).size();
            if (Res==Size)break;
        }
    }
    //构造Follow集合
    void Follow(){
        for (LLNode node:Node_List){
            ArrayList<Character>add=new ArrayList<>();
            FollowMap.put(node.Vn,add);
        }//初始化
        //将#加入开始符号
        FollowMap.get(Vn.get(0)).add(end);
        while (true){
            int Size=0,Res=0;
            for (String vn:Vn)Size+=FollowMap.get(vn).size();
            for (String s : Vn) {//非终结符
                for (LLNode node : Node_List) {
                    if (node.V.contains(s)){//锁定产生式
                        char[]tmp=node.V.toCharArray();//将V装华为字符数组
                        String follow_str = null;//后跟字符串
                        for (int i=0;i<tmp.length-1;i++){
                            if (tmp[i]==s.toCharArray()[0]){//找到了位置
                                follow_str= node.V.substring(i+1);//将后面的赋值给follow_str
                            }
                        }
                        //依次判断情况
                        if (follow_str==null){//为空直接将左部的Follow集加入
                            FollowMap.put(s,Add(FollowMap.get(s),FollowMap.get(node.Vn)));
                        }
                        else{//不为空，则加入后跟First集合
                            FollowMap.put(s,Add(FollowMap.get(s),First(follow_str)));
                            if(Str_Null(follow_str)){
                                FollowMap.put(s,Add(FollowMap.get(s),FollowMap.get(node.Vn)));
                            }
                        }
                        FollowMap.put(s,Set(FollowMap.get(s)));
                    }
                }
            }
            for (String vn:Vn)Res+=FollowMap.get(vn).size();
            if (Res==Size)break;
        }
    }
    //构造SELECT集合
    void Select(){
        //创建一个SELECT集合
        for (LLNode node:Node_List){
            String str=node.Vn+"->"+node.V;
            ArrayList<Character>add;
            if (node.V.toCharArray()[0]==epsilon){
                add=new ArrayList<>(FollowMap.get(node.Vn));
            }
            else if (Str_Null(node.V)){//不为空
                add=new ArrayList<>(Add(First(node.V),FollowMap.get(node.Vn)));

            }else{
                add=new ArrayList<>(First(node.V));
            }
            SelectMap.put(str,add);
        }
    }
    //判断是否是LL(1)文法
    void isLL_1(){
        //初始化NodeList
        if (!Input_Area.getText().isEmpty()){
            if (DataPreprocess(Input_Area.getText()))return;
            Node_List=GetNodeList(Input_Area.getText());
            for (LLNode node:Node_List)
                if (node.Vn.length()>1){
                    //不是2型文法，弹出提示
                    Utils.Notice(this,"左部存在多个符号，不是2型文法");
                    return;
                }
            Vn=Utils.GetVn(Node_List);
            V=Utils.GetV(Node_List);
            Vt=Utils.GetVt(Node_List);
            CanNull();
        }else{
            Utils.Notice(this,"请保证输入文法不为空");return;
        }
        if (isExistLeftRecursive()==1){
            Utils.Notice(this,"包含有直接左递归，不是LL(1)文法");return;
        }else if (isExistLeftRecursive()==-1){
            Utils.Notice(this,"包含有间接左递归，不是LL(1)文法");return;
        }//判断是否存在左递归
        First();
        Follow();
        Select();
        boolean isSame=false;
        for (String vn:Vn){
            ArrayList<Character>tmp=new ArrayList<>();
            for (LLNode node:Node_List){//将所有的集合加在一起，在查看是否存在相同元素
                if (vn.equals(node.Vn)){
                    tmp.addAll(SelectMap.get(node.Vn+"->"+node.V));
                }
            }
            if (isExistSame(tmp))isSame=true;
        }
        if (isSame){
            Utils.Notice(this,"SELECT集存在交集，不是LL(1)文法");
        }
        else{
            Utils.Notice(this,"是正确的LL(1)文法");
            first_btn.setEnabled(true);
            follow_btn.setEnabled(true);
            predict_btn.setEnabled(true);
            ana_btn.setEnabled(true);
        }
    }
    int isExistLeftRecursive(){
        ArrayList<LLNode> tmp=new ArrayList<>(Node_List);
        for (LLNode node_i:Node_List){
            if (node_i.Vn.equals(node_i.V.substring(0,1)))return 1;
            for (LLNode node_j:Node_List){
                if (node_i.Vn.equals(node_j.V.substring(0,1))&&node_j.Vn.equals(node_i.V.substring(0,1))){
                    System.out.println(node_i.Vn+" "+node_j.V);
                    return -1;
                }
            }
        }
        return 0;
    }
    boolean isExistSame(ArrayList<Character>input){
        for (int i=0;i<input.size();i++){
            for (int j=0;j<i;j++){
                if (input.get(j)==input.get(i))return true;
            }
        }
        return false;
    }
    ArrayList<Character>Set(ArrayList<Character>source){
        ArrayList<Character>res=new ArrayList<>();
        for (Character ch:source){
            if (!res.contains(ch))res.add(ch);
        }
        return res;
    }
    ArrayList<Character>Add(ArrayList<Character> dest,ArrayList<Character>source){//将source加入dest
        for (Character character : source){
            if (!dest.contains(character))dest.add(character);
        }
        return dest;
    }
    //将输入字符串转为LLNode型数组
    ArrayList<LLNode> GetNodeList(String source){
        String[]strings=source.split("\n");
        ArrayList<LLNode>result=new ArrayList<>();
        for (String string : strings) {
            String Vn = string.split("->")[0];
            String[] Vs = string.split("->")[1].split("\\|");
            for (String v : Vs) {
                LLNode node = new LLNode(Vn, v);
                result.add(node);
            }
        }
        return result;
    }
    //是否包含非终结符
    boolean isContainVt(String source){
        for (char s : source.toCharArray()) {
            if (!Character.isUpperCase(s)&&s!=epsilon)return true;
        }
        return false;
    }
    //求字符串的First集
    ArrayList<Character> First(String str){
        ArrayList<Character>res=new ArrayList<>();
        char[]chars=str.toCharArray();
        if (!Character.isUpperCase(chars[0])&&chars[0]!=epsilon){//第一个是终结符
            res.add(chars[0]);
            return res;
        }
        else{
            for (char aChar:chars){
                if (Character.isUpperCase(aChar)){
                    //非终结符可以推导出空，加入
                    if(isNullMap.get(String.valueOf(aChar))==1){
                        res=Add(res,FirstMap.get(String.valueOf(aChar)));
                    }
                    else if(isNullMap.get(String.valueOf(aChar))==-1){//非终结符不可以推导出空
                        res=Add(res,FirstMap.get(String.valueOf(aChar)));
                        break;
                    }
                }else if (aChar==epsilon){
                    res.add(aChar);
                }else{
                    res.add(aChar);break;
                }
            }
        }
        for(int i=0;i<res.size();i++){
            if (res.get(i)==epsilon){
                res.remove(i);i--;
            }
        }
        return res;
    }
    //判断字符串是否能推导出空
    boolean Str_Null(String str){
        char[]chars=str.toCharArray();
        if (chars[0]==epsilon)return true;
        for (char aChar : chars) {
            if (!Character.isUpperCase(aChar)&&aChar!=epsilon)return false;
            else if (isNullMap.get(String.valueOf(aChar)) == -1&&aChar!=epsilon) return false;
        }
        return true;
    }
    void ComponentControl(){
        first_btn.setEnabled(false);
        follow_btn.setEnabled(false);
        predict_btn.setEnabled(false);
        ana_btn.setEnabled(false);
        oneDis_btn.setEnabled(false);
        allDis_btn.setEnabled(false);
        save_ana_btn.setEnabled(false);
    }
    boolean DataPreprocess(String str){//数据预处理,提高程序的健壮性
        str = str.replace("\40","");
        String[]strings=str.split("\n");
        StringBuffer result=new StringBuffer();
        boolean error=false;
        for (String s:strings){//对每行元素进行处理
            if (s.equals(""))continue;
            if (s.length()<4){
                result.append(s).append("\t<---不正确产生式位置\n");
                error=true;
            }
            else {
                if (s.contains("->")){
                    result.append(s);
                    if (s.substring(0,s.indexOf("-")).isEmpty()||s.substring(s.indexOf("->")+1).isEmpty()){
                        result.append("\t<---不正确产生式位置");
                        error=true;
                    }

                }else{
                    char[]chars=s.toCharArray();
                    int VnEnd=0;
                    for (int i=0;i<chars.length;i++){
                        if (!Character.isLetter(chars[i])){
                            VnEnd=i;break;
                        }
                    }
                    result.append(s, 0, VnEnd).append("->").append(s.substring(VnEnd+1));
                    if (s.substring(0,VnEnd).isEmpty()||s.substring(VnEnd+1).isEmpty()){
                        Utils.Notice(this,"存在输入式不合法");
                        result.append("\t<---不正确产生式位置");
                        error=true;
                    }
                }
                result.append("\n");
            }
        }
        if (error)Utils.Notice(this,"存在输入式不合法");
        Input_Area.setText(result.toString());
        return error;
    }
    //设置结束
    //监听设置
    void AddListener(){
        open_file_btn.addActionListener(this);
        confirm_btn.addActionListener(this);
        save_file_btn.addActionListener(this);
        first_btn.addActionListener(this);
        follow_btn.addActionListener(this);
        predict_btn.addActionListener(this);
        ana_btn.addActionListener(this);
        allDis_btn.addActionListener(this);
        oneDis_btn.addActionListener(this);
        save_ana_btn.addActionListener(this);
        Sentence_Area.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                oneDis_btn.setEnabled(false);
                allDis_btn.setEnabled(false);
                save_ana_btn.setEnabled(false);
            }
            @Override
            public void focusLost(FocusEvent e) { }
        });
        Input_Area.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ComponentControl();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==open_file_btn)Open_File();
        else if (e.getSource()==confirm_btn)isLL_1();
        else if (e.getSource()==save_file_btn)Save_File();
        else if (e.getSource()==first_btn)First_Show();
        else if (e.getSource()==follow_btn)Follow_Show();
        else if (e.getSource()==predict_btn)Predict_Show();
        else if (e.getSource()==ana_btn)Analyze();
        else if (e.getSource()==oneDis_btn)One_Show();
        else if (e.getSource()==allDis_btn)Result_Show();
        else if (e.getSource()==save_ana_btn)Save_Ana();
    }
    //设置结束
}
