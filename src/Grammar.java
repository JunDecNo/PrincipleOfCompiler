/*
非终结符只能是标识符
S：语句
Vt：标识符，常量，表达式，函数
Vn：标识符
<程序>  ->   <声明> | <程序> <函数>
<声明>  ->   #include<stdio.h>|#include<string.h>|............
<函数>  ->   <修饰符> <标识符> <形参> <复合语句>
<修饰符>->   void | char | int | double
<外部声明> → <函数定义> | <声明>
<函数定义> → <类型标识符> <说明符> <复合语句>
<类型标识符> → void | char | int | float
说明符 → <指针 ><直接说明符>|<直接说明符>
指针 → <*>|<*><指针>
<参数列表> → <参数声明> | <参数列表 ,参数声明>
<参数声明> → <说明符声明><标识符>
<复合语句> → < 语句列表 > | <声明列表 语句列表>
<语句列表> → <语句> | <语句列表> <语句>
<语句> → <复合语句> | <表达式语句> |  <循环语句> | <条件语句>
<表达式语句> → <表达式;>
<循环语句> → < WHILE（表达式）语句> | < FOR（表达式语句表达式语句）语句> | < FOR（表达式语句表达式语句表达式）语句>
<条件语句> → < IF（表达式）语句> | < IF（表达式）语句 > < ELSE 语句>
<表达式语句> → <赋值表达式> | <逻辑表达式> | <算术表达式 E>
<赋值表达式> → <变量> = <算术表达式>
<逻辑表达式> → (<表达式> | <数字> ) <逻辑运算符> (<表达式> | <数字>)
<赋值运算符> →  <乘法赋值> | <除法赋值> | <求余赋值> | <加法赋值> | <减法赋值>
 */

import java.util.ArrayList;

//暂时只有赋值表达式
public class Grammar {
    //定义变量
    private String N,T;
    //private String[]list,type;//终结符和非终结符
    public ArrayList list=new ArrayList<String>();
    public ArrayList num=new ArrayList<Integer>();
    public ArrayList leg=new ArrayList<Boolean>();
    public ArrayList type=new ArrayList<String>();
    public ArrayList res=new ArrayList<Integer>();
    public ArrayList semi=new ArrayList<Integer>();
    Lexcial lexcial;
    int all=0;
    //初始化
    Grammar(String str){
        lexcial=new Lexcial(str);
        list=lexcial.list;
        num=lexcial.num;
        leg=lexcial.leg;
        type=lexcial.type;
        Judge();
    }
    void Judge(){
        int i=0;
        boolean assign=false,mark=false,num=false;
        for (i=0;i<list.size();i++){
            //判断单词属性
            if (type.get(i).equals("标识符"))mark=true;
            else if (IsAssign(String.valueOf(list.get(i))))assign=true;
            //判断是否合法
            if (!mark&&!assign) {//左端存在非终结符
                res.add("0");
                assign=false;mark=true;num=false;//初始化
                while(!list.get(i).equals(";")) i++;
                all++;
            }else if(assign){
                int begin=i;
                boolean bool=true;
                while(!list.get(i).equals(";")) i++;
                for (int j=i;j<i;j++){
                    if((lexcial.type.get(j).equals("运算符")&&lexcial.type.get(j).equals("运算符"))||
                    lexcial.type.get(j).equals("界符")||lexcial.type.get(j).equals("关键字")){
                        bool=false;break;
                    }

                }

            }
        }

    }
    boolean IsAssign(String string){
        if (lexcial.isAlgo(string)>=lexcial.isAlgo("+=")&&
        lexcial.isAlgo(string)<= lexcial.isAlgo("<<=")){
            return true;
        }
        return false;
    }
    public static void main(String[]args){
        Grammar grammar=new Grammar("a=a+1;");
        System.out.println(grammar.all);
    }
}
