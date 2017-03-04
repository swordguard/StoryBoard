package storyboard.tablemodel;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.*;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

import java.util.List;

import org.jdom.Document;

import java.util.Iterator;
import java.util.Vector;

import org.jdom.input.SAXBuilder;
import org.jdom.Element;

public class ViewUsers extends Observable{
    private JTable userTable = null;
    private DefaultTableModel dtm_users = null;


    public JTable createUserTable(){
        Vector row = new Vector();
        Vector colNameVector = new Vector();
        int rowID=0;
        JTable table = null;
        
        String file = "XML/users.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
        
        //String xmlPath = "storyboard/XML/users.xml";
        //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false);//这表示使用的是默认的解析器
        try{
            Document doc = saxBuilder.build(xmlPath);//得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element usersinfo = doc.getRootElement();//得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List userslist = usersinfo.getChildren("user");//得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中

            Iterator ite = userslist.iterator();
            while(ite.hasNext()){

                Element users = (Element)ite.next();
                String username = users.getChildTextTrim("username");//取得元素的子元素（为最低层元素）的值：注意的是，必须确定book元素的名为“name”的子元素只有一个。
                //String password = goods.getChildTextTrim("password");
                String address = users.getChildTextTrim("address");
                String zipcode = users.getChildTextTrim("zipcode");
                String telephone = users.getChildTextTrim("telephone");
                String email = users.getChildTextTrim("email");
                String bAdmin = users.getAttributeValue("bAdmin");
                String bFrozen = users.getChildTextTrim("bFrozen");

                Vector newRow = new Vector();
                newRow.add(++rowID);
                newRow.add(username);
                newRow.add(address);
                newRow.add(zipcode);
                newRow.add(telephone);
                newRow.add(email);
                newRow.add(bAdmin);
                newRow.add(bFrozen);

                row.addElement(newRow);

            }
        }catch(JDOMException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }
        colNameVector.add("UserID");
        colNameVector.add("Username");
        colNameVector.add("Address");
        colNameVector.add("Zip code");
        colNameVector.add("Telephone");
        colNameVector.add("Email");
        colNameVector.add("Administrator");
        colNameVector.add("Frozen");
        dtm_users = new DefaultTableModel(row,colNameVector){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        setDTM(dtm_users);
        table = new JTable(dtm_users);
        return table;

    }
    public void setDTM(DefaultTableModel d){
        this.dtm_users = d;
    }
    public DefaultTableModel getDTM(){
        return dtm_users;
    }
    public ViewUsers() {
        userTable = new JTable();
        userTable = createUserTable();
    }
    public void addView(Observer o){
        addObserver(o);
        //System.out.println("addView ends in class View_comm");
    }
    public void deleteView(Observer o){
        deleteObserver(o);
    }
    public void notifyViews(String name,String bFrozen){
        //System.out.println("notifyObservers() begins");
        String allStr = name + "#" + bFrozen;
        setChanged();
        notifyObservers(allStr);
        //System.out.println("notifyObservers() ends");
    }


}
