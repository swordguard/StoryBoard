package storyboard.tablemodel;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;

import org.jdom.JDOMException;

import javax.swing.JFrame;
import javax.swing.JTable;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;

import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Iterator;

import org.jdom.Document;

import java.util.Vector;

import org.jdom.input.SAXBuilder;
import org.jdom.Element;

public class Searched_commodities {
    String area,catalog,name;
    public Searched_commodities(String area,String catalog,String name) {
        try {
            jbInit(area,catalog,name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit(String area,String catalog,String name) throws Exception {
        showSearched_commodities(area,catalog,name);
    }
    JTable commodityTable = null;

    JFrame invFrame = new JFrame("List of existed commodities.");
    JScrollPane ScrollPane;

    int rowNumber=0;//, columnNumber = 5;

    public JTable createTable(String area,String catalog,String name){	//create a table to show data
        this.area = area;
        this.catalog = catalog;
        this.name = name;
        DefaultTableModel dtm = null;
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
            //rowNumber = goodslist.size();
            //System.out.println(rowNumber);
            Iterator ite = goodslist.iterator();
            while(ite.hasNext()){

                Element goods = (Element)ite.next();
                String _username = goods.getChildTextTrim("username");//取得元素的子元素（为最低层元素）的值：注意的是，必须确定book元素的名为“name”的子元素只有一个。
                String _area = goods.getChildTextTrim("area").toString();
                String _catalog = goods.getChildTextTrim("catalog").toString();
                String _name = goods.getChildTextTrim("name").toString();
                String description = goods.getChildTextTrim("description");
                //System.out.println("area = "+area+",length(area)="+area.length());
                //System.out.println("name = "+name+",length(name)="+name.length());
                if(name.equals(new String(""))){// 分2种情况，空和非空
                    if(_area.equals(area) && _catalog.equals(catalog)){
                        Vector newRow = new Vector();
                        newRow.add(++rowNumber);
                        newRow.add(_area);
                        newRow.add(_catalog);
                        newRow.add(_name);
                        newRow.add(description);
                        newRow.add(_username);
                        row.addElement(newRow);
                    }else if(area.equals("Select") && catalog.equals("Select")){
                        Vector newRow = new Vector();
                        newRow.add(++rowNumber);
                        newRow.add(_area);
                        newRow.add(_catalog);
                        newRow.add(_name);
                        newRow.add(description);
                        newRow.add(_username);
                        row.addElement(newRow);

                    }else if(area.equals("Select") && !catalog.equals("Select")){
                        if(_catalog.equals(catalog)){
                            Vector newRow = new Vector();
                            newRow.add(++rowNumber);
                            newRow.add(_area);
                            newRow.add(_catalog);
                            newRow.add(_name);
                            newRow.add(description);
                            newRow.add(_username);
                            row.addElement(newRow);
                        }
                    }else if(!area.equals("Select") && catalog.equals("Select")){
                        if(_area.equals(area)){
                            Vector newRow = new Vector();
                            newRow.add(++rowNumber);
                            newRow.add(_area);
                            newRow.add(_catalog);
                            newRow.add(_name);
                            newRow.add(description);
                            newRow.add(_username);
                            row.addElement(newRow);
                        }
                    }else{
                        if(_area.equals(area) && _catalog.equals(catalog)){
                            Vector newRow = new Vector();
                            newRow.add(++rowNumber);
                            newRow.add(_area);
                            newRow.add(_catalog);
                            newRow.add(_name);
                            newRow.add(description);
                            newRow.add(_username);
                            row.addElement(newRow);
                        }
                    }
                }
                else{//非空
                  if(name.length()>_name.length()){

                  }else{
                    if(subString(name,_name)){
                        if(_area.equals(area) && _catalog.equals(catalog)){
                            Vector newRow = new Vector();
                            newRow.add(++rowNumber);
                            newRow.add(_area);
                            newRow.add(_catalog);
                            newRow.add(_name);
                            newRow.add(description);
                            newRow.add(_username);
                            row.addElement(newRow);
                        }else if(area.equals("Select") && catalog.equals("Select")){
                            //new View_commodities();
                            Vector newRow = new Vector();
                            newRow.add(++rowNumber);
                            newRow.add(_area);
                            newRow.add(_catalog);
                            newRow.add(_name);
                            newRow.add(description);
                            newRow.add(_username);
                            row.addElement(newRow);
                        }else if(area.equals("Select") && !catalog.equals("Select")){
                            if(_catalog.equals(catalog)){
                                Vector newRow = new Vector();
                                newRow.add(++rowNumber);
                                newRow.add(_area);
                                newRow.add(_catalog);
                                newRow.add(_name);
                                newRow.add(description);
                                newRow.add(_username);
                                row.addElement(newRow);
                            }
                        }else if(!area.equals("Select") && catalog.equals("Select")){
                            if(_area.equals(area)){
                                Vector newRow = new Vector();
                                newRow.add(++rowNumber);
                                newRow.add(_area);
                                newRow.add(_catalog);
                                newRow.add(_name);
                                newRow.add(description);
                                newRow.add(_username);
                                row.addElement(newRow);
                            }
                        }else{
                            if(_area.equals(area) && _catalog.equals(catalog)){
                                Vector newRow = new Vector();
                                newRow.add(++rowNumber);
                                newRow.add(_area);
                                newRow.add(_catalog);
                                newRow.add(_name);
                                newRow.add(description);
                                newRow.add(_username);
                                row.addElement(newRow);
                            }
                        }
                    }else{
                        //System.out.println("No information!");

                    }
                }
               }
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
        table = new JTable(dtm);

        return table;
    }
    public void showSearched_commodities(String area,String catalog,String name){

        commodityTable = createTable(area,catalog,name);
        ScrollPane = new JScrollPane(commodityTable);
        invFrame.getContentPane().add(ScrollPane);
        invFrame.setBounds(300, 150, 500, 300);
        invFrame.setVisible(true);
        invFrame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e){

                    invFrame.dispose();
                }
            });
    }
    public boolean subString(String s2,String s1){
        boolean substring = false;
        if(s2.length()>s1.length()){
        	substring = false;
        	return substring;
        }
        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                if(s2.charAt(j) != s1.charAt(i)){
                    break;
                }else{
                    ++i;
                }

                if(j == s2.length()-1){
                    substring = true;
                }
            }
        }
        return substring;
    }
}
