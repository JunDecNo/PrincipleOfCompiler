import jdk.jshell.execution.Util;

import javax.lang.model.element.NestingKind;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;

public class LL_1_Grammar implements ActionListener {
    private JFrame jFrame;
    private JPanel Left,Right,Left_Top,Left_Cet,Left_Bot,Right_Top,Right_Cet,Right_Bot;
    private JPanel Btn_Group,Exp_Group,Crt_Group,Pre_Group,Sen_Group,Dis_Group;
    private JLabel input_label,first_label,follow_label,predict_label,analyze_label,sentence_label,result_label;
    private JLabel exp_top,exp_cet,exp_bot;
    private JButton open_file_btn,confirm_btn,save_file_btn,first_btn,follow_btn,predict_btn;
    private JButton ana_btn,oneDis_btn,allDis_btn,save_ana_btn;
    private JTextArea Input_Area;
    private JTextField Sentence_Area;
    private JScrollPane Input_Area_Pane,First_Pane,Follow_pane,Predict_pane,Result_pane;
    private DefaultTableModel First_Model,Follow_Model,Predict_Model,Result_Model;
    private JTable first_table,follow_table,predict_table,result_table;
    private ArrayList<LLNode>Node_List=new ArrayList<>();
    private ArrayList<String>Vn=new ArrayList<>(),V=new ArrayList<>(),Vt=new ArrayList<>();
    private Map<String, Integer> isNullMap=new HashMap<>();//是否为空的字典
    private Map<String,ArrayList<Character>>FirstMap=new HashMap<>();//first集合
    private Map<String,ArrayList<Character>>FollowMap=new HashMap<>();//follow集合
    private Map<String,ArrayList<Character>>SelectMap=new HashMap<>();
    private ArrayList<Vector<String>>Final=new ArrayList<>(),Save;//用来保存分析结果集合。
    char epsilon='$',end='#';
    String notice_str;
    LL_1_Grammar(){
        Init();//初始化
        Setting();//设置参数
        AddComponent();//添加组件
        AddListener();//设置监听器
        ComponentControl();
    }
    void Init(){
        //框架声明
        jFrame=new JFrame("LL(1)预测分析");
        //左部
        Left=new JPanel();
        Left_Top=new JPanel();
        Left_Cet=new JPanel();
        Left_Bot=new JPanel();
        Btn_Group=new JPanel();
        Exp_Group=new JPanel();
        Pre_Group=new JPanel();
        Input_Area=new JTextArea(7,20);
        Input_Area_Pane=new JScrollPane(Input_Area);
        Crt_Group=new JPanel();
        First_Model=new DefaultTableModel();
        Follow_Model=new DefaultTableModel();
        Predict_Model=new DefaultTableModel();
        Result_Model=new DefaultTableModel();
        first_table=new JTable();
        follow_table=new JTable();
        predict_table=new JTable();
        result_table=new JTable();
        First_Pane=new JScrollPane(first_table);
        Follow_pane=new JScrollPane(follow_table);
        Predict_pane=new JScrollPane(predict_table);
        Result_pane=new JScrollPane(result_table);
        //右部
        Right=new JPanel();
        Right_Top=new JPanel();
        Right_Cet=new JPanel();
        Sen_Group=new JPanel();
        Dis_Group=new JPanel();
        Right_Bot=new JPanel();
        //组件声明
        input_label=new JLabel("文法输入---->");
        open_file_btn=new JButton("打开文件");
        confirm_btn=new JButton("确认文法");
        save_file_btn=new JButton("保存文件");
        first_btn=new JButton("生成First集");
        follow_btn=new JButton("生成Follow集");
        predict_btn=new JButton("构造预测分析");
        save_ana_btn=new JButton("保存分析结果");
        Sentence_Area=new JTextField(40);
        ana_btn=new JButton("分析");
        oneDis_btn=new JButton("单步显示");
        allDis_btn=new JButton("一键显示");
        exp_top=new JLabel("注意事项:请输入满足LL(1)最简判别的2型文法。一行一个产生式");
        exp_cet=new JLabel("注意事项:请输入形式如S->A的产生式，空格用_表示，空用$表示");
        exp_bot=new JLabel("注意事项:开始符为第一个产生式的左部，非终结符用大写字母表示");
        first_label=new JLabel("First集---->");
        follow_label=new JLabel("Follow集---->");
        predict_label=new JLabel("预测分析表---->");
        analyze_label=new JLabel("分析句子---->");
        sentence_label=new JLabel("待分析句子:");
        result_label=new JLabel("分析结果---->");
    }
    void Setting(){
        //布局设置
        jFrame.setLayout(new BorderLayout());
        //左部
        Left_Top.setLayout(new BorderLayout());
        Left_Cet.setLayout(new BorderLayout());
        Left_Bot.setLayout(new BorderLayout());
        Left.setLayout(new BorderLayout());
        Btn_Group.setLayout(Styles.GetFlowLayout(30));
        Exp_Group.setLayout(new BorderLayout());
        //左中
        Crt_Group.setLayout(Styles.GetFlowLayout(50));
        first_table.setModel(First_Model);
        follow_table.setModel(Follow_Model);
        first_table.setPreferredScrollableViewportSize(new Dimension(200, 160));
        follow_table.setPreferredScrollableViewportSize(new Dimension(200, 160));
        //右部
        Right_Top.setLayout(new BorderLayout());
        predict_table.setModel(Predict_Model);
        predict_table.setPreferredScrollableViewportSize(new Dimension(100, 160));
        result_table.setModel(Result_Model);
        result_table.setPreferredScrollableViewportSize(new Dimension(100, 300));
        Sentence_Area.setPreferredSize(new Dimension (100,25));
        Right_Cet.setLayout(new BorderLayout());
        Sen_Group.setLayout(Styles.GetFlowLayout(5));
        Dis_Group.setLayout(Styles.GetFlowLayout(15));
        Right_Bot.setLayout(new BorderLayout());
        Pre_Group.setLayout(new BorderLayout());
        Right.setLayout(new BorderLayout());
        jFrame.setBounds(100,100,1000,650);
        jFrame.setVisible(true);
        predict_table.setFont( new Font("微软雅黑",Font.PLAIN,14));
        predict_table.setRowHeight(20);
        predict_table.setEnabled(false);
        result_table.setFont( new Font("微软雅黑",Font.PLAIN,14));
        result_table.setRowHeight(20);
        result_table.setEnabled(false);
    }
    void AddComponent(){
        //左部
        Left_Top.add(input_label,BorderLayout.NORTH);
        //按钮
        Btn_Group.add(open_file_btn);
        Btn_Group.add(confirm_btn);
        Btn_Group.add(save_file_btn);
        Left_Top.add(Btn_Group,BorderLayout.CENTER);
        //解释
        Exp_Group.add(exp_top,BorderLayout.NORTH);
        Exp_Group.add(exp_cet,BorderLayout.CENTER);
        Exp_Group.add(exp_bot,BorderLayout.SOUTH);
        Left_Top.add(Exp_Group,BorderLayout.SOUTH);
        //中部
        Left_Cet.add(Input_Area_Pane,BorderLayout.NORTH);
        Crt_Group.add(first_btn);Crt_Group.add(follow_btn);
        Left_Cet.add(Crt_Group,BorderLayout.CENTER);
        Left_Cet.add(first_label,BorderLayout.SOUTH);
        //下方
        Left_Bot.add(First_Pane,BorderLayout.NORTH);
        Left_Bot.add(follow_label,BorderLayout.CENTER);
        Left_Bot.add(Follow_pane,BorderLayout.SOUTH);
        Left.add(Left_Top,BorderLayout.NORTH);
        Left.add(Left_Cet,BorderLayout.CENTER);
        Left.add(Left_Bot,BorderLayout.SOUTH);
        //右部
        Pre_Group.add(predict_btn,BorderLayout.WEST);
        Right_Top.add(predict_label,BorderLayout.NORTH);
        Right_Top.add(Pre_Group,BorderLayout.CENTER);
        Right_Top.add(Predict_pane,BorderLayout.SOUTH);
        Right_Cet.add(analyze_label,BorderLayout.NORTH);
        Sen_Group.add(sentence_label);
        Sen_Group.add(Sentence_Area);
        Right_Cet.add(Sen_Group,BorderLayout.CENTER);
        Dis_Group.add(ana_btn);
        Dis_Group.add(oneDis_btn);
        Dis_Group.add(allDis_btn);
        Dis_Group.add(save_ana_btn);
        Right_Cet.add(Dis_Group,BorderLayout.SOUTH);
        Right_Bot.add(result_label,BorderLayout.NORTH);
        Right_Bot.add(Result_pane,BorderLayout.CENTER);
        Right.add(Right_Top,BorderLayout.NORTH);
        Right.add(Right_Cet,BorderLayout.CENTER);
        Right.add(Right_Bot,BorderLayout.SOUTH);
        //主框架
        jFrame.add(Left,BorderLayout.WEST);
        jFrame.add(Right,BorderLayout.CENTER);
    }
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
    void ComponentControl(){
        first_btn.setEnabled(false);
        follow_btn.setEnabled(false);
        predict_btn.setEnabled(false);
        ana_btn.setEnabled(false);
        oneDis_btn.setEnabled(false);
        allDis_btn.setEnabled(false);
        save_ana_btn.setEnabled(false);
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
    void Save_Ana(){
        StringBuilder res= new StringBuilder("步骤\t\t\t\t\t分析栈\t\t\t\t剩余字符串\t\t\t\t推导所用的产生式或匹配\n");
        if (Save.isEmpty()){
            Utils.Notice(jFrame,"输入结果为空，无法保存");
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
        Utils.SaveFile(jFrame,res.toString());
    }
    void Open_File(){
        String str=Utils.OpenFile(jFrame);
        if (!str.isEmpty()){
            Input_Area.setText(str);
            ComponentControl();
        }
        else Utils.Notice(jFrame,"输入文件数据为空");
    }
    void Save_File(){
        if (Input_Area.getText().equals(""))Utils.Notice(jFrame,"输入串为空，无法保存");
        else Utils.SaveFile(jFrame,Input_Area.getText());
    }
    void Analyze(){//分析句子//终止
        Final.clear();
        Result_Model.setRowCount(0);
        Result_Model.setColumnCount(0);
        String sentence=Sentence_Area.getText();
        int produce=0;
        if (sentence.isEmpty())Utils.Notice(jFrame,"输入句子为空，分析失败");
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
            Utils.Notice(jFrame,"分析成功");
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
            if (notice_str.equals("接受"))Utils.Notice(jFrame,"分析完成，匹配成功");
            else if(notice_str.equals("未找到产生式"))Utils.Notice(jFrame,"分析完成,未找到产生式");
            else if(notice_str.contains("不匹配"))Utils.Notice(jFrame,"分析完成,终结符匹配不成功");
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
            if (notice_str.equals("接受"))Utils.Notice(jFrame,"分析完成，匹配成功");
            else if(notice_str.equals("未找到产生式"))Utils.Notice(jFrame,"分析完成,未找到产生式");
            else if(notice_str.contains("不匹配"))Utils.Notice(jFrame,"分析完成,终结符匹配不成功");
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
            Node_List=GetNodeList(Input_Area.getText());
            Vn=Utils.GetVn(Node_List);
            V=Utils.GetV(Node_List);
            Vt=Utils.GetVt(Node_List);
            CanNull();
        }else{
            Utils.Notice(jFrame,"请保证输入文法不为空");return;
        }
        if (isExistLeftRecursive()==1){
            Utils.Notice(jFrame,"包含有直接左递归，不是LL(1)文法");return;
        }else if (isExistLeftRecursive()==-1){
            Utils.Notice(jFrame,"包含有间接左递归，不是LL(1)文法");return;
        }
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
            Utils.Notice(jFrame,"SELECT集存在交集，不是LL(1)文法");
        }
        else{
            Utils.Notice(jFrame,"是正确的LL(1)文法");
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
}
