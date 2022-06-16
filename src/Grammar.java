import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Component.CENTER_ALIGNMENT;

//暂时只有赋值表达式
public class Grammar implements ActionListener {
    JFrame jFrame;
    JPanel input,output,star;
    JLabel jLabel,input_label,notice,output_label;
    JButton start;
    JTextField jTextField;
    JTextArea jTextArea;
    Grammar(){
        jFrame=new JFrame("算法表达式检验");
        input=new JPanel();
        output=new JPanel();
        star=new JPanel();
        jLabel=new JLabel("算法表达式检验");
        input_label=new JLabel("输入框---->");
        notice=new JLabel("输入C/C++的赋值语句或者算术表达式判断是否合法");
        jTextField=new JTextField(30);
        start=new JButton("开始检验");
        output_label=new JLabel("输出框---->");
        jTextArea=new JTextArea(5,5);
        start.addActionListener(this);
        Setting();
    }
    void Setting(){
        jFrame.setBounds(100,100,500,320);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jLabel,BorderLayout.NORTH);
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jLabel.setHorizontalAlignment(0);
        input.setLayout(new BorderLayout());
        output.setLayout(new BorderLayout());
        input.add(input_label,BorderLayout.NORTH);
        star.add(notice);
        star.add(jTextField);
        star.add(start);
        input.add(star,BorderLayout.CENTER);
        output.add(output_label,BorderLayout.NORTH);
        output.add(jTextArea,BorderLayout.SOUTH);
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font("宋体", Font.BOLD, 20));
        jTextArea.setAlignmentX(CENTER_ALIGNMENT);
        jTextArea.setAlignmentY(CENTER_ALIGNMENT);
        jTextField.setPreferredSize(new Dimension(50,30));
        jTextField.setFont(new Font("宋体", Font.PLAIN, 20));
        input_label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        output_label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        jFrame.setResizable(false);
        jFrame.add(input,BorderLayout.CENTER);
        jFrame.add(output,BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==start)Analyse();
    }
    void Analyse(){
        String string=jTextField.getText();
        char []chars=string.toCharArray();
        boolean legal=true;
        int equal=0,left=0,index=0;
        String formal="";
        Lexcial lexcial=new Lexcial(string);
        if (string.isEmpty())
            JOptionPane.showMessageDialog(jFrame,"请输入表达式 or 语句","提示",JOptionPane.INFORMATION_MESSAGE);
        for(int i=0;i<chars.length;i++){
            if((chars[i]>='A'&&chars[i]<='Z')||
                    (chars[i]>='a'&&chars[i]<='z')||
                    (chars[i]>='0'&&chars[i]<='9')||
                    lexcial.isAlgo(String.valueOf(chars[i]))>-1||
                    chars[i]=='.'||chars[i]==';'||
                    chars[i]=='('||chars[i]==')'){
                if (chars[i]=='='){
                    equal++;
                    if(equal==1){
                        if(lexcial.isAlgo(chars[i-1]+"=")>-1){
                            formal=chars[i-1]+"=";
                        }else{
                            formal="=";
                        }
                    }
                }

                if(equal>1){
                    legal=false;break;
                }
            }
            else {
                legal=false;break;
            }
        }
        for(int i=0;i<lexcial.result.size();i++){
            if(!lexcial.result.get(i).legal){
                legal=false;break;
            }
            if(i==lexcial.result.size()-1){
                if(lexcial.result.get(i).type.equals("算符")){
                    legal=false;break;
                }
            }
        }
        if (legal&&equal==0){//不是赋值情况
            for(int i=0;i<lexcial.result.size();i++){
                if(i==0){
                    if(lexcial.result.get(i).type.equals("算符")){
                        legal=false;break;
                    }else if(lexcial.result.get(i).word.equals("(")){
                        left++;continue;
                    }
                    else continue;
                }
                MidNode before=new MidNode(lexcial.result.get(i-1)),
                        now=new MidNode(lexcial.result.get(i));
                if (now.type.equals("标识符")){
                    if(before.type.equals("标识符")){
                        legal=false;break;
                    }
                }else if(now.type.equals("算符")){
                    if(before.type.equals("算符")){
                        if(before.word.equals("++")||before.word.equals("--")){
                            if(now.word.equals("++")||now.word.equals("--")){ legal=false;break; }
                            else continue;
                        }
                        else { legal=false;break; }
                    }
                }else if(now.word.equals("(")){
                    if(before.type.equals("标识符")){
                        legal=false;break;
                    }else{
                        left++;
                    }
                }else if(now.word.equals(")")){
                    if(before.type.equals("算符")||before.word.equals("(")){
                        legal=false;break;
                    }else {
                        if(left>0)left--;
                        else {
                            legal=false;break;
                        }
                    }
                }
            }
        }
        else if(equal==1){//赋值情况
            for(int i=0;i<lexcial.result.size();i++){
                if(lexcial.result.get(i).word.equals(formal))index=i;
            }
            if(index==0)legal=false;
            for (int k=0;k<index;k++){
                if(!lexcial.result.get(k).type.equals("标识符")){
                    legal=false;break;
                }
            }
            for(int i=index;i<lexcial.result.size();i++){
                if(i==0){
                    if(lexcial.result.get(i).type.equals("算符")){
                        legal=false;break;
                    }else if(lexcial.result.get(i).word.equals("(")){
                        left++;continue;
                    }
                    else continue;
                }
                MidNode before=new MidNode(lexcial.result.get(i-1)),
                        now=new MidNode(lexcial.result.get(i));
                if (now.type.equals("标识符")){
                    if(before.type.equals("标识符")){
                        legal=false;break;
                    }
                }else if(now.type.equals("算符")){
                    if(before.type.equals("算符")){
                        if(before.word.equals("++")||before.word.equals("--"))continue;
                        else { legal=false;break; }
                    }
                }else if(now.word.equals("(")){
                    if(before.type.equals("标识符")){
                        legal=false;break;
                    }else{
                        left++;
                    }
                }else if(now.word.equals(")")){
                    if(before.type.equals("算符")||before.word.equals("(")){
                        legal=false;break;
                    }else {
                        if(left>0)left--;
                        else {
                            legal=false;break;
                        }
                    }
                }
            }
        }
        if (legal){
            jTextArea.setText("无语法错误！！！！！");
        }else {
            jTextArea.setText("存在语法错误！！！！");
        }
    }
}
