package storyboard.tablemodel;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import org.jdom.input.*;

import java.util.Vector;
import java.io.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.*;

public  class View_commodities extends Observable{

    public JTable commodities = null;
    DefaultTableModel dtm = null;
    int rowNumber=0;//, columnNumber = 5;
    boolean bUpdate = false,bAdd = false,bDelete = false;

    public View_commodities() {

        //commodities = new JTable();
        commodities = createTable();
    }
    
    
    public void setRowNumber(int i){
    	this.rowNumber = i;
    }
    public int getRowNumber(){
    	return this.rowNumber;
    }

    public JTable createTable(){	////////////create a table to show data, table[0][0] begings
        int line = getRowNumber()+1;
        //System.out.println("in createTable(),getRowNumber()="+getRowNumber());
        Vector row = new Vector();
        Vector colNameVector = new Vector();
        JTable table = null;
        
        String file = "XML/db.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
        
        //String xmlPath = "storyboard/XML/db.xml";
        //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false);//这表示使用的是默认的解析器
        try{
            Document doc = saxBuilder.build(xmlPath);//得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element goodsinfo = doc.getRootElement();//得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List goodslist = goodsinfo.getChildren("goods");//得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中

            Iterator ite = goodslist.iterator();
            while(ite.hasNext()){

                Element goods = (Element)ite.next();
        //String email = goods.getAttributeValue("email");// 取得元素book的属性名为“email”的属性值
       // System.out.println(email);
                String username = goods.getChildTextTrim("username");//取得元素的子元素（为最低层元素）的值：注意的是，必须确定book元素的名为“name”的子元素只有一个。
                String area = goods.getChildTextTrim("area");
                String catalog = goods.getChildTextTrim("catalog");
                String name = goods.getChildTextTrim("name");
                String description = goods.getChildTextTrim("description");

                Vector newRow = new Vector();
                newRow.add(line++);
                newRow.add(area);
                newRow.add(catalog);
                newRow.add(name);
                newRow.add(description);
                newRow.add(username);
                row.addElement(newRow);

            }
        }catch(JDOMException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }
        colNameVector.add("ListID");
        colNameVector.add("Area");
        colNameVector.add("Catalog");
        colNameVector.add("Name");
        colNameVector.add("Description");
        colNameVector.add("Uploader");
        dtm = new DefaultTableModel(row,colNameVector){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        setDTM(dtm);
        setRowNumber(line-1);
        //System.out.println("in createTable(),getRowNumber()="+getRowNumber());
        table = new JTable(dtm);
        return table;
    }
    public void setDTM(DefaultTableModel d){
        this.dtm = d;
    }
    public DefaultTableModel getDTM(){
        return this.dtm;
    }
    

    public void addView(Observer o){
        addObserver(o);
        //System.out.println("addView ends in class View_comm");
    }
    public void deleteView(Observer o){
        deleteObserver(o);
    }
    public void notifyViews(String jCombo_area,String jCombo_catalog,String text_name,String text_desc,String user){
        //System.out.println("notifyObservers() begins");
        String allStr = jCombo_area + "#" +jCombo_catalog+ "#" +text_name+ "#" +text_desc +"#"+user;
        setChanged();
        notifyObservers(allStr);
        //System.out.println("notifyObservers() ends");
    }
    public void updateModel(String jCombo_area,String jCombo_catalog,String text_name,String text_desc){

    }
    public void addToModel(String jCombo_area,String jCombo_catalog,String text_name,String text_desc,String username){
        Vector newRow = new Vector();
        int row = getRowNumber()+1;//from 1 to n;

        //System.out.println("in addToModel,getRowNumber()="+getRowNumber());
        DefaultTableModel d = this.getDTM();

        newRow.add(row++);
        newRow.add(jCombo_area);
        newRow.add(jCombo_catalog);
        newRow.add(text_name);
        newRow.add(text_desc);
        newRow.add(username);
        d.addRow(newRow);
        //System.out.println("addToModel() is called");
        commodities = new JTable(d);
        setRowNumber(row-1);
        //System.out.println("in addToModel,getRowNumber()="+getRowNumber());
    }
    public void deleteFromModel(String area,String catalog,String name,String desc,String username){
        int id = -1,lines=-1,col = 6,recordID = -1;
        DefaultTableModel dtm = this.getDTM();
        lines = dtm.getRowCount();
        //System.out.println("before deletion");
        for(int i=0;i<lines;i++){
            //System.out.println(dtm.getValueAt(i,0).toString());
            if(dtm.getValueAt(i,1).toString().equals(area) && dtm.getValueAt(i,2).toString().equals(catalog)
            && dtm.getValueAt(i,3).toString().equals(name) && dtm.getValueAt(i,4).toString().equals(desc)){
                recordID = i;
                break;
            }
        }

        dtm.removeRow(recordID);
        lines = dtm.getRowCount();
        //System.out.println("after deletion");
        for(int i=1;i<lines+1;i++){

        	dtm.setValueAt(Integer.toString(i),i-1,0);
            //System.out.println(dtm.getValueAt(i,0).toString());
        }
        setRowNumber(lines);
        commodities = new JTable(dtm);
        System.out.println("deleteFromModel() is done in class View_commodities");
    }
    public void setBUpdate(boolean u){
        this.bUpdate = u;
    }
    public boolean isUpdate(){
        return this.bUpdate;
    }
    public void setBAdd(boolean a){
        this.bAdd = a;
        //System.out.println("setBAdd() done");
    }
    public boolean isAdd(){
        return this.bAdd;
    }
    public void setBDelete(boolean d){
        this.bDelete = d;
    }
    public boolean isDelete(){
        return this.bDelete;
    }

    public JTable getCommTable(){
        return commodities;
    }

}
