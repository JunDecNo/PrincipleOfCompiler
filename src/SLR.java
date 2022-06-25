import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;

public class SLR{
    //数据部分
    public String notice_str;
    public ArrayList<LLNode> Node_List=new ArrayList<>(),Item_List=new ArrayList<>();//产生式、项目集合
    public ArrayList<ClusterNode>Cluster_List=new ArrayList<>();//簇集合
    public Map<Integer,ArrayList<ArrayList<Character>>> ClusterFollow=new HashMap<>();//用于保存每个项目集的follow集合
    public ArrayList<String>Vn=new ArrayList<>(),V=new ArrayList<>(),Vt=new ArrayList<>();
    public ArrayList<Vector<String>>Final=new ArrayList<>(),Save;//用来保存分析结果集合。
    public ArrayList<Integer>Conflict=new ArrayList<>();
    public Map<Integer,ArrayList<Character>>VtList=new HashMap<>();
    public Map<String,ArrayList<Character>>FollowMap;
    char dot='.',end='#';
    public DefaultTableModel Project_Model=new DefaultTableModel(),
            Struct_Model=new DefaultTableModel(),Result_Model=new DefaultTableModel();
    boolean Semantic=false;
    private String Grammar,Sentence;
    //结束
    // End of variables declaration
    public SLR(String Grammar) {
        //初始化
        this.Grammar=Grammar;
        StructItemCluster();
        Vn=Utils.GetVn(Node_List);
        V=Utils.GetV(Node_List);
        Vt=Utils.GetVt(Node_List);
        //构造项目集规范族
        SLR_1(Grammar);
        Project_Show();
        Struct_SLR_1_Table();
    }
    //方法设置
    public void SetSentence(String string){
        this.Sentence=string;
    }
    void SenAnalyze(){
        Final.clear();
        Result_Model.setRowCount(0);
        Result_Model.setColumnCount(0);
        String sentence=this.Sentence;
        int produce = 0;
        ArrayList<Integer> status = new ArrayList<>();//状态栈
        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<String>symbol = new ArrayList<>();//符号栈和输入串
        //栈初始化
        status.add(0);
        symbol.add("#");
        for (char ch : sentence.toCharArray()) chars.add(ch);//将句子加入
        chars.add(end);
        while(true){
            boolean exit=false;
            Vector<String>vector=new Vector<>();
            produce++;
            //获取状态和输入串
            int now = status.get(status.size() - 1);//最后一个状态
            char ch = chars.get(0);
            int col = Utils.GetTableColumnIndex(Struct_Model, String.valueOf(ch));
            vector.add(String.valueOf(produce));
            vector.add(Utils.IntListToString(status));
            vector.add(Utils.StrListToString(symbol));
            vector.add(Utils.CharListToString(chars));
            String opt="";//获取操作
            if (col!=-1) opt = (String) Struct_Model.getValueAt(now,col);
            else {//未找到字符
                vector.add("归约失败，不是合法的终结符");
                exit=true;
            }
            if (opt.startsWith("S")){//移进项目
                vector.add(opt);vector.add("");
                //进行操作
                status.add(Integer.valueOf(opt.substring(1)));//移入状态
                symbol.add(String.valueOf(ch));
                chars.remove(0);//删除第一个
            }else if (opt.startsWith("r")){//进行归约操作
                //选择产生式
                LLNode node=Node_List.get(Integer.parseInt(opt.substring(1)));
                int right=node.V.length();
                //从状态栈和符号栈中删除right个
                for(int j = 0;j<right;j++){
                    status.remove(status.size()-1);
                    symbol.remove(symbol.size()-1);
                }
                //进行GOTO操作
                col=Utils.GetTableColumnIndex(Struct_Model,node.Vn);
                int next = Integer.parseInt(Struct_Model.getValueAt(status.get(status.size()-1),col).toString());
                status.add(next);
                symbol.add(node.Vn);
                vector.add(opt);vector.add(String.valueOf(next));
            }else if (opt.equals("acc")){
                vector.add("接受，匹配成功");
                exit=true;
            }else if (col!=-1){
                vector.add("失败，归约失败");
                exit=true;
            }
            Final.add(vector);
            if (exit)break;
        }
        String []str={"步骤","状态栈","符号栈","输入串","ACTION","GOTO"};
        for(String s:str){
            Result_Model.addColumn(s);
        }//添加字符串
        notice_str = Final.get(Final.size()-1).get(4);
        Save=new ArrayList<>(Final);
    }
    void Project_Show(){
        Project_Model.setRowCount(0);Project_Model.setColumnCount(0);
        Project_Model.addColumn("状态");Project_Model.addColumn("项目簇信息");
        for (ClusterNode cluster:Cluster_List){
            Vector<String>vector=new Vector<>();
            vector.add(String.valueOf(cluster.S));vector.add(Cluster2String(cluster.cluster));
            Project_Model.addRow(vector);
        }
    }
    void Struct_LR_0_Table(){
        Struct_Model.setRowCount(0);Struct_Model.setColumnCount(0);
        //使用该方法，证明不存在移进-归约冲突
        //初始化列数据
        int rows=Project_Model.getRowCount();
        Struct_Model.addColumn("状态");
        for (String vt:Vt)Struct_Model.addColumn(vt);
        Struct_Model.addColumn("#");
        for (int i=1;i<Vn.size();i++)Struct_Model.addColumn(Vn.get(i));//建立完毕
        for (ClusterNode node : Cluster_List) {//遍历状态，添加行
            Vector<String>vector=new Vector<>();
            vector.add(String.valueOf(node.S));
            //获取接受字符串
            int pos=node.cluster.get(0).V.indexOf(dot);
            if (pos == node.cluster.get(0).V.length() - 1){//归约
                LLNode get=new LLNode(node.cluster.get(0));
                get.V=get.V.substring(0,get.V.length()-1);
                if (get.Vn.equals("E'")){//接受项目
                    for (String vt:Vt)vector.add("");
                    vector.add("acc");
                }else{//归约项目
                    for (int i=0;i<Node_List.size();i++){
                        if (Node_List.get(i).Vn.equals(get.Vn)&&Node_List.get(i).V.equals(get.V)){
                            for (String vt:Vt)vector.add("r"+i);
                            vector.add("r"+i);
                        }
                    }
                }
            }
            else{//移进
                for (String vt:Vt){   // 终结符
                    ArrayList<LLNode> tmp=GOTO(node.cluster, vt.charAt(0));
                    if (tmp.isEmpty())vector.add("");
                    else{
                        for (int i=0;i<rows;i++){
                            if (Cluster2String(tmp).equals(Project_Model.getValueAt(i,1))){
                                vector.add("S"+i);
                            }
                        }
                    }
                }
                vector.add("");
                //非终结符
                for (String vn:Vn){
                    if (vn.equals("E'"))continue;
                    ArrayList<LLNode> tmp=GOTO(node.cluster,vn.charAt(0));
                    if (tmp.isEmpty())vector.add("");
                    else{
                        for (int i=0;i<rows;i++){
                            if (Cluster2String(tmp).equals(Project_Model.getValueAt(i,1))){
                                vector.add(String.valueOf(i));
                            }
                        }
                    }
                }
            }
            Struct_Model.addRow(vector);
        }
    }
    void SLR_1(String Grammar){
        VtList.clear();
        //求出归约项目的Follow集，通过LL_1类建造
        LL_1_Grammar ll1Grammar=new LL_1_Grammar(Grammar);
        FollowMap=ll1Grammar.FollowMap;
        ArrayList<Character>tt=new ArrayList<>();
        tt.add('#');
        FollowMap.put("E'",tt);
        boolean is=true;
        for (ClusterNode node:Cluster_List){
            int shift=0,rudece=0;
            boolean isExist=false;
            for (LLNode n:node.cluster){
                int pos=n.V.indexOf(dot);
                if (pos==n.V.length()-1){
                    rudece++;
                }
                else shift++;
                if ((shift>0&&rudece>0)||rudece>1){
                    isExist=true;
                    break;
                }
            }
            //判断是否可以用SLR方法解决
            if (isExist){//node存在冲突
                Conflict.add(node.S);//标记冲突状态，将存在归约的加入
                ArrayList<Character>res=new ArrayList<>();//用来保存结果，判断是否存在交集
                ArrayList<Character>tmp=new ArrayList<>();
                for (LLNode n:node.cluster){
                    int pos=n.V.indexOf(dot);
                    if (pos==n.V.length()-1){//归约项目,将非终结符加入
                        if (FollowMap.get(n.Vn)!=null) res.addAll(FollowMap.get(n.Vn));//follow集合加入
                        //如果是归约项目，则需要保存产生式和follow集合，通过follow集选择产生式子
                        //Map(state,LLNode,follow),再followA选择产生式A，因为不可能出现两个A，所以直接标注产生式序号
                    }else if (!Character.isUpperCase(n.V.charAt(pos+1))){//移入项目,且接受符号为终结符
                        res.add(n.V.charAt(pos+1));
                        tmp.add(n.V.charAt(pos+1));
                    }
                }
                //将终结符集合加入VtMap中
                VtList.put(node.S,tmp);//移进符号
                //判断是否存在交集
                if (ll1Grammar.isExistSame(res)){
                    is=false;break;
                }
            }
        }
    }
    void Struct_SLR_1_Table(){
        Struct_LR_0_Table();
        //对存在冲突的归约进行修改
        int rows=Project_Model.getRowCount();
        for (ClusterNode node:Cluster_List){//该状态存在冲突
            if (Conflict.contains(node.S)){
                //对每一列进行修改
                for (String vt:Vt){
                    int row=node.S;
                    int col=Vt.indexOf(vt)+1;
                    if (VtList.get(node.S).contains(vt.charAt(0))){//移进
                        ArrayList<LLNode> tmp=GOTO(node.cluster, vt.charAt(0));
                        for (int i=0;i<rows;i++){
                            if (Cluster2String(tmp).equals(Project_Model.getValueAt(i,1))){//找到了下一状态
                                Struct_Model.setValueAt("S"+i,row,col);
                            }
                        }
                    }
                }
            }
            for (String vt:Vt) {
                int row = node.S;
                int col = Vt.indexOf(vt) + 1;
                if (Struct_Model.getValueAt(row,col).toString().contains("S"))continue;
                //归约
                boolean is=true;
                for (LLNode n:node.cluster){
                    if(n.V.charAt(n.V.length()-1)==dot&&FollowMap.get(n.Vn).contains(vt.charAt(0))){//归约项目
                        for (int i=0;i<Node_List.size();i++){//找到产生式位置
                            if (Node_List.get(i).Vn.equals(n.Vn)&&n.V.equals(Node_List.get(i).V+dot)){
                                Struct_Model.setValueAt("r"+i,row,col);
                                is=false;
                                break;
                            }
                        }
                        if(!is)break;
                    }
                }
                if (is)Struct_Model.setValueAt("",row,col);
            }

        }
    }
    void StructItemCluster(){
        Node_List=GetNodeList(Grammar);//拓广文法
        Item_List.clear();
        Cluster_List.clear();
        //构造项目集
        for (LLNode node:Node_List){
            int len=node.V.length();
            for (int i=0;i<len;i++){
                Item_List.add(new LLNode(node.Vn, node.V.substring(0,i)+"."+node.V.substring(i,len)));
            }
            Item_List.add(new LLNode(node.Vn,node.V+"."));
        }
        //构造第一个cluster
        int index=0;
        ArrayList<LLNode>tmp=new ArrayList<>();
        tmp.add(Item_List.get(0));//初始集
        Cluster_List.add(new ClusterNode(index,Closure(tmp)));//加入到Cluster集合中
        Queue<ArrayList<LLNode>>queue=new LinkedList<>();
        queue.add(Closure(tmp));
        while(!queue.isEmpty()) {
            ArrayList<LLNode> cluster = queue.poll();//获取未产生的簇
            ArrayList<Character> chars = GetChar(cluster);//获取输入符号
            //对所有符号进行运算
            for (char ch : chars) {
                ArrayList<LLNode> next = GOTO(cluster, ch);
                if (!isContain(Cluster_List, new ClusterNode(index, next))) {
                    index++;
                    Cluster_List.add(new ClusterNode(index, next));
                    queue.offer(next);
                }
            }
        }
    }
    boolean isContain(ArrayList<ClusterNode>source,ClusterNode element){
        for (ClusterNode node:source){
            if (Cluster2String(node.cluster).equals(Cluster2String(element.cluster))){
                return true;
            }
        }
        return false;
    }
    ArrayList<LLNode> Closure(ArrayList<LLNode> list){
        //遍历数组，如果.的后面是非终结符，就将终结符的以.开头的加入
        ArrayList<LLNode>result=new ArrayList<>(list);
        Queue<LLNode>queue=new LinkedList<>(list);
        while (!queue.isEmpty()){
            LLNode node=queue.poll();
            if (node.V.indexOf(dot)==node.V.length()-1)continue;
            if (Character.isUpperCase(node.V.charAt(node.V.indexOf(dot)+1))){
                for (LLNode n:Item_List){
                    if (node.V.charAt(node.V.indexOf(dot)+1)==n.Vn.toCharArray()[0]
                            &&!n.Vn.contains("E'") &&n.V.startsWith(".")){//.开头的项目加入
                        if (isContainer(result, n)){//如果不包含节点
                            if (n.V.equals(".$")){//.$的清空，视为规约项目，不将其加入队列
                                result.add(new LLNode(n.Vn,"$."));
                            }else{
                                result.add(new LLNode(n));
                                queue.add(new LLNode(n));
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    String Cluster2String(ArrayList<LLNode>source){
        StringBuilder stringBuilder=new StringBuilder();
        for (LLNode node:source)
            stringBuilder.append(node.Vn).append("->").append(node.V).append("  ");
        return stringBuilder.toString();
    }
    ArrayList<LLNode> GetNodeList(String source){
        String[]strings=source.split("\n");
        String str="E'->"+strings[0].split("->")[0]+"\n"+source;
        strings=str.split("\n");
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
    ArrayList<LLNode> GOTO(ArrayList<LLNode> source,char receive){
        ArrayList<LLNode>next=new ArrayList<>();
        for (LLNode node:source){
            String v=node.V;
            int pos=node.V.indexOf(dot);//点的位置
            if (pos<v.length()-1&&v.charAt(pos+1)==receive){
                LLNode add=new LLNode(node.Vn,v.substring(0,pos)+v.charAt(pos+1)+"."+v.substring(pos+2));
                if (isContainer(next, add)){
                    next.add(new LLNode(add));
                }
            }
        }
        return Closure(next);
    }
    ArrayList<Character> GetChar(ArrayList<LLNode>source){
        ArrayList<Character>res=new ArrayList<>();
        for (LLNode node:source){
            int pos=node.V.indexOf(dot);
            if (pos<node.V.length()-1){
                char ch=node.V.charAt(node.V.indexOf(dot)+1);//.之后的字符
                if (!res.contains(ch))res.add(ch);
            }
        }
        return res;
    }
    boolean isContainer(ArrayList<LLNode>source, LLNode element){
        for (LLNode node:source){
            if (node.Vn.equals(element.Vn)&&node.V.equals(element.V))return false;
        }
        return true;
    }
    //设置结束
}