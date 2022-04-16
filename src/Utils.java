import javax.swing.*;
import java.awt.*;
import java.io.*;

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
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));//创建文件读取缓冲流
                bufferedWriter.write(str);
                bufferedWriter.close();//关闭缓冲流
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else return false;
        return true;
    }
}
class MidList{
    public int row_number;
    public String word;
    public boolean legal;
    public String type;
    public int word_number;
    MidList(int row_number,String word,boolean legal,String type,int word_number){
        this.row_number=row_number;
        this.word=word;
        this.legal=legal;
        this.type=type;
        this.word_number=word_number;
    }
    MidList(MidList midList){
        this.row_number=midList.row_number;
        this.word=midList.word;
        this.legal=midList.legal;
        this.type=midList.type;
        this.word_number=midList.word_number;
    }
}
class FaList{
    public int start;
    public char receive;
    public int end;
    FaList(int start,char receive,int end){
        this.start=start;
        this.receive=receive;
        this.end=end;
    }
    FaList(FaList faList){
        this.start=faList.start;
        this.receive=faList.receive;
        this.end= faList.end;
    }
}
