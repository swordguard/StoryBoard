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
        //ʹ��JDOM����Ҫָ��ʹ��ʲô��������
        SAXBuilder saxBuilder = new SAXBuilder(false);//���ʾʹ�õ���Ĭ�ϵĽ�����
        try{
            Document doc = saxBuilder.build(xmlPath);//�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element usersinfo = doc.getRootElement();//�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List userslist = usersinfo.getChildren("user");//�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������

            Iterator ite = userslist.iterator();
            while(ite.hasNext()){

                Element users = (Element)ite.next();
                String username = users.getChildTextTrim("username");//ȡ��Ԫ�ص���Ԫ�أ�Ϊ��Ͳ�Ԫ�أ���ֵ��ע����ǣ�����ȷ��bookԪ�ص���Ϊ��name������Ԫ��ֻ��һ����
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
