package storyboard.tablemodel;

import java.util.List;
import java.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.JDOMException;

import java.io.*;

public class UsersinfoDataModel extends Observable{
    private DefaultTableModel dtmUsersinfo = null;

    public JTable createTable(){
        JTable usersinfoTable = null;
        
        
        String file = "XML/users.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String usersfile = fin.toString();
        //String usersfile = "storyboard/XML/users.xml";
        SAXBuilder saxBuilder = new SAXBuilder(false);
        Vector vUserData = new Vector();
        try{
            Document doc = saxBuilder.build(usersfile);
            Element root = doc.getRootElement();
            List users = root.getChildren("user");//.getChildren("user");
            Iterator ite = users.iterator();
            while(ite.hasNext()){
                Element userNode = (Element)ite.next();
                String _username = userNode.getChildTextTrim("username");
                String _address = userNode.getChildTextTrim("address");
                String _zipcode = userNode.getChildTextTrim("address");
                String _tel = userNode.getChildTextTrim("telephone");
                String _email = userNode.getChildTextTrim("email");
                Vector newRow = new Vector();
                newRow.add(_username);
                newRow.add(_address);
                newRow.add(_zipcode);
                newRow.add(_tel);
                newRow.add(_email);
                vUserData.add(newRow);
            }
            Vector vColName = new Vector();
            vColName.add("User ID");
            vColName.add("User Name");
            vColName.add("Adress");
            vColName.add("Zip Code");
            vColName.add("Telephone");
            vColName.add("Email");
            DefaultTableModel dtm = new DefaultTableModel(vUserData,vColName);
            setDTMUsersinfo(dtm);
            usersinfoTable = new JTable(dtm);
        }catch(JDOMException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return usersinfoTable;
    }

    public void setDTMUsersinfo(DefaultTableModel dtmUsersinfo){
        this.dtmUsersinfo = dtmUsersinfo;
    }

    public DefaultTableModel getDTMUsersinfo(){
        System.out.println("getDTMUsersinfo()");
        return this.dtmUsersinfo;
    }

    public UsersinfoDataModel() {
        this.createTable();
    }
}
