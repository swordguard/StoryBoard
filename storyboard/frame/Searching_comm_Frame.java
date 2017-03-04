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
    //ʹ��JDOM����Ҫָ��ʹ��ʲô��������
        SAXBuilder saxBuilder = new SAXBuilder(false);//���ʾʹ�õ���Ĭ�ϵĽ�����
        try{
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element areas = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List area = areas.getChildren("area"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
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
//ʹ��JDOM����Ҫָ��ʹ��ʲô��������
    SAXBuilder saxBuilder = new SAXBuilder(false);//���ʾʹ�õ���Ĭ�ϵĽ�����
    try{
        Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
        Element catalogs = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
        List catalog = catalogs.getChildren("catalog"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
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
        //jLabel_Area.setFont(new java.awt.Font("����", Font.BOLD, 16));
        jLabel_Area.setText("Area:");
        //jLabel_Catalog.setFont(new java.awt.Font("����", Font.BOLD, 16));
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
