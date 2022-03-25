import java.awt.*;
import java.util.ArrayList;

public class Lexcial {
    private String[] c_code={
            "auto","break","case","char","const","continue",
            "default","do","double","else","enum","extern",
            "float","for","goto","if","int","include","long","register",
            "return","short","signed","sizeof","static","struct",
            "switch","typedef","unsigned","union","void","volatile","while"},
            c_algo={"+","-","*","/","%","++","--",">","<","==",">=","<=","!=",
            "&&","||","!","&","|","^","<<",">>","+=","-=","*=","/=","=",
            "%=","&=","|=","^=",">>=","<<=","->","~","?",":"},
            c_sep={",",";","(",")","[","]","{","}","\"","\'","\\","/*","*/","//","#","\40"},
            c_type={"算符","界符","常数","标识符","关键字"};
    private char[]chars;
    private String[] strings;
    private int row,base=c_code.length,algo=c_algo.length,sep=c_sep.length;
    public ArrayList list=new ArrayList<String>();
    public ArrayList num=new ArrayList<Integer>();
    public ArrayList leg=new ArrayList<Boolean>();
    public ArrayList type=new ArrayList<String>();
    public ArrayList result=new ArrayList<MidList>();
    public
    Lexcial(String str){
        chars=(str+" ").toCharArray();
        GetLex();
    }
    void GetLex(){
        int begin=0,j,k;
        char []tmp;
        for (int i=0;i<chars.length;i++){
            if (isSep(String.valueOf(chars[i]))>-1||isAlgo(String.valueOf(chars[i]))>-1) {
                if(begin==i)continue;
                String one=String.valueOf(chars[begin]);
                //界符和算符(begin为界符和算符的情况)
                if (isSep(one)>-1||isAlgo(one)>-1){
                    String two=one+chars[i];
                    if (isSep(two)>-1||isSep(one)>-1&&!one.equals("\40")){
                        if (isSep(two)>-1){//两个字符的界符
                            list.add(two);
                            num.add(isSep(two)+base+algo);
                            leg.add(true);
                            type.add(c_type[1]);
                            result.add(new MidList(row,two,true,c_type[1],isSep(two)+base+algo));
                            begin=i+1;
                        }else{//begin为界符
                            list.add(one);
                            num.add(isSep(one)+base+algo);
                            leg.add(true);
                            type.add(c_type[1]);
                            begin=i;
                        }
                    }else if(isAlgo(one)>-1&&!one.equals("\40")){//算符
                        if(isAlgo(two)>-1){
                            list.add(two);
                            num.add(isAlgo(two)+base);
                            leg.add(true);
                            type.add(c_type[0]);
                            begin=i+1;
                        }else{
                            list.add(one);
                            num.add(isAlgo(one)+base);
                            leg.add(true);
                            type.add(c_type[0]);
                            begin=i;
                        }
                    }
                }
                else{
                    //begin为字母时的情况
                    //记录到界符或者运算符处的字符串进行判断
                    //对字符串进行判断
                    //关键字
                    tmp=new char[i-begin];//获取字符串
                    for (j=begin,k=0;j<i;j++,k++){//复制字符串
                        tmp[k]=chars[j];
                    }
                    String str_tmp=String.valueOf(tmp);
                    if (isBase(str_tmp)>-1){//关键字
                        num.add(isBase(str_tmp));
                        list.add(str_tmp);
                        leg.add(true);
                        type.add(c_type[4]);
                    }
                    //标识符和常量
                    else {
                        boolean isNum = true;//判断是否为数字
                        int dotNum = 0;
                        for (int kk = 0; kk < tmp.length; kk++) {
                            if (tmp[kk] < '0' || tmp[kk] > '9') {
                                if (tmp[kk]=='.'){
                                    dotNum++;continue;
                                }
                                isNum = false;
                                break;
                            }
                        }
                        if (isNum) {//数字
                            if (dotNum<=1){
                                list.add(str_tmp);
                                num.add(90);
                                leg.add(true);
                                type.add(c_type[2]);
                            }else{
                                list.add(str_tmp);
                                num.add(90);
                                leg.add(false);
                                type.add(c_type[2]);
                            }
                        } else if ('0' <= tmp[0] && tmp[0] <= '9') {//非法的标识符
                            list.add(str_tmp);
                            num.add(89);
                            leg.add(false);
                            type.add(c_type[3]);
                        } else {//合法的标识符
                            list.add(str_tmp);
                            num.add(89);
                            leg.add(true);
                            type.add(c_type[3]);
                        }
                    }
                    begin=i;
                }
            }
            else{//扫描到字母时
                String one=String.valueOf(chars[begin]);
                if (isSep(one)>-1||isAlgo(one)>-1&&chars[begin]!='\40'){
                    if(isAlgo(one)>-1){//单个运算符或者分隔符
                        num.add(isAlgo(one)+base);
                        list.add(one);
                        leg.add(true);
                        type.add(c_type[0]);
                    }
                    else if(isSep(one)>-1&&!one.equals("\40")){
                        num.add(isSep(one)+base+algo);
                        list.add(one);
                        leg.add(true);
                        type.add(c_type[1]);
                    }
                    begin=i;
                }
            }
            if(chars[begin]=='\40'&&begin!= chars.length-1)begin=i;
        }
    }
    int isBase(String string){
        for (int i=0;i<base;i++){
            if (string.equals(c_code[i]))return i;
        }
        return -1;
    }
    int isAlgo(String string){
        for (int i=0;i<algo;i++){
            if (string.equals(c_algo[i]))return i;
        }
        return -1;
    }
    int isSep(String string){
        for (int i=0;i<sep;i++){
            if (string.equals(c_sep[i]))return i;
        }
        return -1;
    }
//    public static void main(String[]agrs){
//        Lexcial lexcial=new Lexcial("auto");
//        System.out.println(lexcial.list.toString());
//        System.out.println(lexcial.list.size());
//        System.out.println(lexcial.type.toString());
//        System.out.println(lexcial.num.toString());
//        System.out.println(lexcial.leg.toString());
//    }
}
