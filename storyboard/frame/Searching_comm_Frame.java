package storyboard.frame;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import org.jdom.Document;

import java.util.Iterator;

import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

import storyboard.interfaces.IAreas;
import storyboard.interfaces.ICatalogs;
import storyboard.tablemodel.Searched_commodities;

public class Searching_comm_Frame extends JFrame implements IAreas,ICatalogs {

    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JLabel jLabel_Area = new JLabel();
    JComboBox jComboBox1 = new JComboBox();
    JLabel jLabel_Catalog = new JLabel();
    JComboBox jComboBox2 = new JComboBox();
    JLabel jLabel_Name = new JLabel();
    JTextField jText_Name = new JTextField(10);
    //JLabel jLabel_Desc = new JLabel();
    //JTextArea jText_Desc = new JTextArea();
    JButton search = new JButton("Search");
    JButton cancel = new JButton("Cancel");

    public Searching_comm_Frame() {
        try {
            this.setDefaultCloseOperation(1);
            //setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void loadAreas(){

    	
    	String file = "XML/areas.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
        //String xmlPath = "storyboard/XML/areas.xml";
    //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false);//这表示使用的是默认的解析器
        try{
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element areas = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List area = areas.getChildren("area"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
            //int rowNumber = area.size();
            //System.out.println(rowNumber);
            Iterator ite = area.iterator();
            jComboBox1.addItem(new String("Select"));
            while (ite.hasNext()) {
                Element a = (Element)ite.next();
                jComboBox1.addItem(a.getText());
            }
        }catch(JDOMException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }

    }
    public void loadCatalogs(){
    	
    	String file = "XML/catalogs.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();

    //String xmlPath = "storyboard/XML/catalogs.xml";
//使用JDOM首先要指定使用什么解析器。
    SAXBuilder saxBuilder = new SAXBuilder(false);//这表示使用的是默认的解析器
    try{
        Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
        Element catalogs = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
        List catalog = catalogs.getChildren("catalog"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
        //int rowNumber = area.size();
        //System.out.println(rowNumber);
        Iterator ite = catalog.iterator();
        jComboBox2.addItem(new String("Select"));
        while (ite.hasNext()) {
            Element cata = (Element)ite.next();
            jComboBox2.addItem(cata.getText());
        }
    }catch(JDOMException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }

}

    private void jbInit() throws Exception {
        //jLabel_Area.setFont(new java.awt.Font("宋体", Font.BOLD, 16));
        jLabel_Area.setText("Area:");
        //jLabel_Catalog.setFont(new java.awt.Font("宋体", Font.BOLD, 16));
        jLabel_Catalog.setText("Catalog:");
        jLabel_Name.setText("Name:");

        p1.add(jLabel_Area);
        p1.add(jComboBox1);
        loadAreas();

        p1.add(jLabel_Catalog);
        p1.add(jComboBox2);
        loadCatalogs();

        p1.add(jLabel_Name);
        p1.add(jText_Name);
        //p1.add(jLabel_Desc);
        //p1.add(jText_Desc);
        search.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                                new Searched_commodities(jComboBox1.getSelectedItem().toString(),
                                        jComboBox2.getSelectedItem().toString(),
                                        jText_Name.getText().trim().toString());
                        }
                }
        );
        cancel.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                                //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                setVisible(false);
                                //disPose();
                        }
                });
        p2.add(search);
        p2.add(cancel);
        //this.getContentPane().add(search);
        setLayout(new GridLayout(3,2));
        add(p1);
        add(p2);
        this.setTitle("Searching window.");
        this.setBounds(300,150,500,300);
        this.setVisible(true);
    }

}
