package storyboard.frame;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.table.*;

import storyboard.tablemodel.ViewUsers;

public class ViewUsersFrame extends JFrame implements Observer {
    ViewUsers Model;
     
    public ViewUsersFrame(ViewUsers m) {
        Model = m;
        Model.addView(this);// 加入观察者
        //System.out.println("Model = usersinfoDataModel;");
        JScrollBar hBar = new JScrollBar(JScrollBar.HORIZONTAL);
        JScrollBar vBar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, 100);
        JTable t = new JTable(Model.getDTM());
        //System.out.println(Model.getDTM().getValueAt(0,0));
		JScrollPane jScrollPaneUserList = new JScrollPane(t);
        //usersPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL,0,10,0,100));
        this.setTitle("List of Users.");
		
        this.setBounds(300, 300, 500, 200);
        this.getContentPane().setLayout(new BorderLayout());

        DefaultBoundedRangeModel vmodel = new DefaultBoundedRangeModel(0, 1, 0,
                100);
        vmodel.setExtent(10);
        vBar.setModel(vmodel);
        //this.getContentPane().add(vBar,BorderLayout.EAST);
        //this.getContentPane().add(hBar,BorderLayout.SOUTH);
        //hBar.addAdjustmentListener(this);
        jScrollPaneUserList.add(hBar);
        jScrollPaneUserList.add(vBar);
        //usersPane.add(hBar,2);//BorderLayout.SOUTH);
        this.getContentPane().add(jScrollPaneUserList);

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    
    public void update(Observable o, Object value) {
    	//System.out.println("update begins");
/*  被观察者通知观察者后，此函数就执行了

*/
    	String [] str = value.toString().split("#");
    	String name = str[0];
    	String bFrozen = str[1];
    	DefaultTableModel dtm = Model.getDTM();
    	for(int i=0;i<dtm.getRowCount();i++){
    		if(dtm.getValueAt(i,1).equals(name)){
    			dtm.setValueAt(bFrozen,i,7);
    			//System.out.println(dtm.getValueAt(i,7).toString());
    			break;
    		}
    	}
        //System.out.println("udpate ends");
    }

    
}
