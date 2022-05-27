import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Utils {
    static String OpenFile(JFrame jf){
        FileDialog open_file=new FileDialog(jf,"打开文件",FileDialog.LOAD);
        File file;
        StringBuffer stringBuffer=new StringBuffer();
        open_file.setVisible(true);
        String DirPath=open_file.getDirectory();
        String FileName=open_file.getFile();
        if (DirPath!=null&&FileName!=null){
            file=new File(DirPath,FileName);//实例文件对象
            try {
                BufferedReader bufferedReader=new BufferedReader(new FileReader(file));//创建文件读取缓冲流
                String line=null;//初始化字符串，用于获取每行的字符串
                while((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line+'\n');//在每次读取一行时，换行
                }
                bufferedReader.close();//关闭缓冲流
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("文件读取失败");
            }
        }
        return String.valueOf(stringBuffer);
    }
    static boolean SaveFile(JFrame jf,String str) {
        FileDialog save_file = new FileDialog(jf, "保存文件", FileDialog.SAVE);
        File file;
        save_file.setVisible(true);
        String DirPath = save_file.getDirectory();
        String FileName = save_file.getFile();
        if (DirPath != null && FileName != null) {
            file = new File(DirPath, FileName);//实例文件对象
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"gbk"));//创建文件读取缓冲流
                bufferedWriter.write(str);
                bufferedWriter.close();//关闭缓冲流
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else return false;
        return true;
    }
     static void Notice(JFrame jf,String str) {
        JOptionPane.showMessageDialog(jf, str, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
    //获取左部为Vn的右部
    static ArrayList<String>GetNodeV(String NodeVn,ArrayList<LLNode>List){
        ArrayList<String>res=new ArrayList<>();
        for (LLNode node : List)
            if (node.Vn.equals(NodeVn))
                res.add(node.V);
        return res;
    }
    //获取Vn非终结符
    static ArrayList<String> GetVn(ArrayList<LLNode>Node_List){
        ArrayList<String>resStr=new ArrayList<>();
        for (LLNode node : Node_List){
            if (!resStr.contains(node.Vn)) resStr.add(node.Vn);
        }
        return resStr;
    }
    //获取V串
    static ArrayList<String>GetV(ArrayList<LLNode>Node_List){
        ArrayList<String>resStr=new ArrayList<>();
        for (LLNode node : Node_List) {
            if (!resStr.contains(node.V)) resStr.add(node.V);
        }
        return resStr;
    }
    //获取Vt串
    static ArrayList<String>GetVt(ArrayList<LLNode> Node_List){
        ArrayList<String>resStr=new ArrayList<>();
        for (LLNode node : Node_List){
            char []chars=node.V.toCharArray();
            for (char aChar : chars) {
                if (!Character.isUpperCase(aChar)&&!resStr.contains(String.valueOf(aChar))) resStr.add(String.valueOf(aChar));
            }
        }
        return resStr;
    }
    static int GetTableColumnIndex(DefaultTableModel model,String str){
        for (int i=0;i<model.getColumnCount();i++){
            if (model.getColumnName(i).equals(str))return i;
        }
        return -1;
    }
    static String CharListToString(ArrayList<Character>source){
        StringBuilder str= new StringBuilder();
        for (char ch:source) str.append(ch);
        return str.toString();
    }
    static String IntListToString(ArrayList<Integer>source){
        StringBuilder str= new StringBuilder();
        for (Integer i:source){
            if (i>9){
                str.append("(").append(i).append(")");
            }
            else str.append(i);
        }
        return str.toString();
    }
    static String StrListToString(ArrayList<String>source){
        StringBuilder str= new StringBuilder();
        for (String ch:source)str.append(ch);
        return str.toString();
    }
}
class MidNode{
    public int row_number;
    public String word;
    public boolean legal;
    public String type;
    public int word_number;
    MidNode(int row_number,String word,boolean legal,String type,int word_number){
        this.row_number=row_number;
        this.word=word;
        this.legal=legal;
        this.type=type;
        this.word_number=word_number;
    }
    MidNode(MidNode midList){
        this.row_number=midList.row_number;
        this.word=midList.word;
        this.legal=midList.legal;
        this.type=midList.type;
        this.word_number=midList.word_number;
    }
}
class FaNode{
    public int start;
    public char receive;
    public int end;
    FaNode(int start,char receive,int end){
        this.start=start;
        this.receive=receive;
        this.end=end;
    }
    FaNode(){}
    FaNode(FaNode FaNode){
        this.start=FaNode.start;
        this.receive=FaNode.receive;
        this.end= FaNode.end;
    }
}

class MyIntComparator implements Comparator {
    /**
     * o1比o2大，返回-1；o1比o2小，返回1。
     */
    public int compare(Object o1, Object o2) {
        int i1 = ((ArrayList<Integer>)o1).get(0);
        int i2 = ((ArrayList<Integer>)o2).get(0);
        if (i1 > i2){
            return 1;
        }
        if (i1 < i2){
            return -1;
        }
        return 0;
    }
}
class LLNode{
    public String Vn,V;
    LLNode(){}
    LLNode(LLNode node){
        this.Vn=node.Vn;
        this.V=node.V;
    }
    LLNode(String Vn,String V){
        this.Vn=Vn;
        this.V=V;
    }
}
class ClusterNode{
    public int S;
    public ArrayList<LLNode> cluster;
    ClusterNode(){}
    ClusterNode(ClusterNode node){
        this.S=node.S;
        this.cluster=new ArrayList<>(node.cluster);
    }
    ClusterNode(int S,ArrayList<LLNode> cluster){
        this.S=S;
        this.cluster=new ArrayList<>(cluster);
    }
}

