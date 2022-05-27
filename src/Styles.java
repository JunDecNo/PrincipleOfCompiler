import java.awt.*;

public class Styles {
    static FlowLayout GetFlowLayout(int distance){
        FlowLayout flowLayout=new FlowLayout();
        flowLayout.setHgap(distance);
        return flowLayout;
    }
}
