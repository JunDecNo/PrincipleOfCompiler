import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class NFA_DFA_MFA {
    JFrame fa_frame;
    JPanel North,Center,west,center,east,bottom_nfa,read_nfa_panel,
            bottom_dfa,read_dfa_panel,bottom_mfa,read_mfa_panel;
    JLabel exp_label,input_label,nfa,dfa,mfa,lab18,lab19,lab21,lab20,lab27,lab26,exam_label;
    JTextField jTextField;
    JButton verify,reset,read_nfa,create_nfa,save_nfa,
            read_dfa,create_dfa,save_dfa,read_mfa,create_mfa,save_mfa;
    JTable nfa_table,dfa_table,mfa_table;
    JScrollPane nfa_scroll,dfa_scroll,mfa_scroll;
    String Column[]={"起始状态","接受符号","到达状态"};
    DefaultTableModel model_nfa,model_dfa,model_mfa;
    NFA_DFA_MFA(){
        Init();
        Layout();
        Setting();
    }
    void Init(){
        fa_frame=new JFrame("NFA_DFA_MFA");
        North=new JPanel();Center=new JPanel();
        west=new JPanel();center=new JPanel();east=new JPanel();
        bottom_nfa=new JPanel();read_nfa_panel=new JPanel();
        bottom_dfa=new JPanel();read_dfa_panel=new JPanel();
        bottom_mfa=new JPanel();read_mfa_panel=new JPanel();
        exp_label=new JLabel("表达式---->");
        input_label=new JLabel("请输入一个正规式：");
        exam_label=new JLabel("例如: (a*|b)*  ");
        jTextField=new JTextField(30);
        //Button
        verify=new JButton("验证正规式");
        reset=new JButton("重置");
        read_nfa=new JButton("读入NFA文件");
        create_nfa=new JButton("生成NFA文件");
        save_nfa=new JButton("保存");
        read_dfa=new JButton("读入DFA文件");
        create_dfa=new JButton("生成DFA文件");
        save_dfa=new JButton("保存");
        read_mfa=new JButton("读入MFA文件");
        create_mfa=new JButton("生成MFA文件");
        save_mfa=new JButton("保存");
        nfa=new JLabel("正规式---->NFA");
        dfa=new JLabel("NFA---->DFA");
        mfa=new JLabel("DFA---->MFA");
        //bottom_label
        lab18=new JLabel("初始状态集：label18");
        lab19=new JLabel("终止状态集：label19");
        lab21=new JLabel("初始状态集：label21");
        lab20=new JLabel("终止状态集：label20");
        lab26=new JLabel("初始状态集：label26");
        lab27=new JLabel("终止状态集：label27");
        //table
        nfa_table=new JTable();
        dfa_table=new JTable();
        mfa_table=new JTable();
        nfa_scroll=new JScrollPane(nfa_table);
        dfa_scroll=new JScrollPane(dfa_table);
        mfa_scroll=new JScrollPane(mfa_table);
    }
    void Layout(){
        //上方
        fa_frame.setLayout(new FlowLayout());
        North.add(exp_label);
        North.add(input_label);
        North.add(jTextField);
        North.add(exam_label);
        North.add(verify);
        North.add(reset);
        //中左
        read_nfa_panel.add(read_nfa);
        read_nfa_panel.add(create_nfa);
        read_nfa_panel.add(save_nfa);
        bottom_nfa.setLayout(new BorderLayout());
        bottom_nfa.add(lab18,BorderLayout.NORTH);
        bottom_nfa.add(lab19,BorderLayout.CENTER);
        bottom_nfa.add(read_nfa_panel,BorderLayout.SOUTH);
        west.setLayout(new BorderLayout());
        west.add(nfa,BorderLayout.NORTH);
        west.add(nfa_scroll,BorderLayout.CENTER);
        west.add(bottom_nfa,BorderLayout.SOUTH);
        //中中
        read_dfa_panel.add(read_dfa);
        read_dfa_panel.add(create_dfa);
        read_dfa_panel.add(save_dfa);
        bottom_dfa.setLayout(new BorderLayout());
        bottom_dfa.add(lab21,BorderLayout.NORTH);
        bottom_dfa.add(lab20,BorderLayout.CENTER);
        bottom_dfa.add(read_dfa_panel,BorderLayout.SOUTH);
        center.setLayout(new BorderLayout());
        center.add(dfa,BorderLayout.NORTH);
        center.add(dfa_scroll,BorderLayout.CENTER);
        center.add(bottom_dfa,BorderLayout.SOUTH);
        //中右
        read_mfa_panel.add(read_mfa);
        read_mfa_panel.add(create_mfa);
        read_mfa_panel.add(save_mfa);
        bottom_mfa.setLayout(new BorderLayout());
        bottom_mfa.add(lab27,BorderLayout.NORTH);
        bottom_mfa.add(lab26,BorderLayout.CENTER);
        bottom_mfa.add(read_mfa_panel,BorderLayout.SOUTH);
        east.setLayout(new BorderLayout());
        east.add(mfa,BorderLayout.NORTH);
        east.add(mfa_scroll,BorderLayout.CENTER);
        east.add(bottom_mfa,BorderLayout.SOUTH);
        Center.setLayout(new BorderLayout());
        Center.add(west,BorderLayout.WEST);
        Center.add(center,BorderLayout.CENTER);
        Center.add(east,BorderLayout.EAST);
        //父组件
        fa_frame.add(North);
        fa_frame.add(Center);
    }
    void Setting(){
        model_nfa=(DefaultTableModel) nfa_table.getModel();
        model_dfa=(DefaultTableModel) dfa_table.getModel();
        model_mfa=(DefaultTableModel) mfa_table.getModel();
        model_nfa.addColumn(Column[0]);model_nfa.addColumn(Column[1]);model_nfa.addColumn(Column[2]);
        model_dfa.addColumn(Column[0]);model_dfa.addColumn(Column[1]);model_dfa.addColumn(Column[2]);
        model_mfa.addColumn(Column[0]);model_mfa.addColumn(Column[1]);model_mfa.addColumn(Column[2]);
        nfa_table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        dfa_table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        mfa_table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        create_nfa.setEnabled(false);create_dfa.setEnabled(false);create_mfa.setEnabled(false);
        fa_frame.setBounds(100,100,900,500);
        fa_frame.setVisible(true);
    }
    public static void main(String[]args){
        new NFA_DFA_MFA();
    }
}
