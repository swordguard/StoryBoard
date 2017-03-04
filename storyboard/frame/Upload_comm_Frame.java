package storyboard.frame;

import javax.swing.*;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

import java.util.List;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.*;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import storyboard.interfaces.IAreas;
import storyboard.interfaces.ICatalogs;
import storyboard.tablemodel.View_commodities;

import java.io.FileOutputStream;

public class Upload_comm_Frame extends JFrame implements IAreas, ICatalogs {
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JLabel jLabel_Area = new JLabel();
    JComboBox jComboBox1 = new JComboBox();
    JLabel jLabel_Catalog = new JLabel();
    JComboBox jComboBox2 = new JComboBox();
    JLabel jLabel_Name = new JLabel();
    JTextField jText_Name = new JTextField(20);

    JLabel jLabel_Desc = new JLabel();
    JTextArea jText_Desc = new JTextArea(3, 20);

    JButton upload = new JButton("Upload");
    JButton cancel = new JButton("Cancel");
    View_commodities M1;
    String username;
    boolean bAdmin;

    public Upload_comm_Frame() {}

    public Upload_comm_Frame(View_commodities M, String username1,
                             boolean bAdmin) {
        //System.out.println("hi");
        //this.view_commodities = view_commodities;
        M1 = M;
        this.username = username1;
        jLabel_Area.setText("Area:");
        jLabel_Area.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel_Catalog.setText("Catalog:");
        jLabel_Catalog.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel_Name.setText("Name:");
        jLabel_Name.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel_Desc.setText("Description:");
        p1.setLayout(new GridLayout(3, 2));
        p1.add(jLabel_Area);
        p1.add(jComboBox1);
        loadAreas();
        p1.add(jLabel_Catalog);
        p1.add(jComboBox2);
        loadCatalogs();
        p1.add(jLabel_Name);
        p1.add(jText_Name);
        upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doUpload(jComboBox1.getSelectedItem().toString(),
                         jComboBox2.getSelectedItem().toString(),
                         jText_Name.getText().toString(),
                         jText_Desc.getText().toString(),
                         username);
                //֪ͨ�۲����ϴ������
                M1.setBAdd(true);
                M1.notifyViews(jComboBox1.getSelectedItem().toString(),
                               jComboBox2.getSelectedItem().toString(),
                               jText_Name.getText().toString(),
                               jText_Desc.getText().toString(),
                               username.toString());
                //System.out.println("notifyViews() passed in Class Upload_comm_Frame");
            }
        }
        );
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setVisible(false);
                //disPose();
            }
        });
        jText_Desc.setLineWrap(true);
        JScrollPane pTextArea = new JScrollPane(jText_Desc);
        p3.setLayout(new GridLayout(2, 1));
        p3.add(jLabel_Desc);
        p3.add(pTextArea);
        p2.add(upload);
        p2.add(cancel);

        setLayout(new GridLayout(3, 2));
        add(p1);
        add(p3);
        //this.add(pTextArea);
        add(p2);
        this.pack();
        this.setTitle("Upload your commodity information.");
        this.setBounds(300, 150, 500, 300);
        this.setVisible(true);

    }

    public void loadAreas() {

    	
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
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element areas = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List area = areas.getChildren("area"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            //int rowNumber = area.size();
            //System.out.println(rowNumber);
            Iterator ite = area.iterator();
            jComboBox1.addItem(new String("Select"));
            while (ite.hasNext()) {
                Element a = (Element) ite.next();
                jComboBox1.addItem(a.getText());
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCatalogs() {

    	
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
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element catalogs = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List catalog = catalogs.getChildren("catalog"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            //int rowNumber = area.size();
            //System.out.println(rowNumber);
            Iterator ite = catalog.iterator();
            jComboBox2.addItem(new String("Select"));
            while (ite.hasNext()) {
                Element cata = (Element) ite.next();
                jComboBox2.addItem(cata.getText());
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doUpload(String textarea, String textcatalog, String textname,
                         String desc, String username) {
        if (textarea.equals("Select") || textcatalog.equals("Select") ||
            textname.equals("") || desc.equals("")) {
            showWarning("All blanks here must be filled in!");
            return;
        } else {
        	
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
            XMLOutputter outputter = null;
            Element goods = null;
            Element area = null;
            Element catalog = null;
            Element name = null;
            Element description = null;
            Element user = null;
            //ʹ��JDOM����Ҫָ��ʹ��ʲô��������
            SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
            try {
                Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
                Element goodsinfo = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ

                goods = new Element("goods");
                area = new Element("area");
                catalog = new Element("catalog");
                name = new Element("name");
                description = new Element("description");
                user = new Element("username");
                area.setText(textarea);
                catalog.setText(textcatalog);
                name.setText(textname);
                description.setText(desc);
                user.setText(username);
                goods.addContent(area);
                goods.addContent(catalog);
                goods.addContent(name);
                goods.addContent(description);
                goods.addContent(user);
                goodsinfo.addContent(goods);

                Format format = Format.getCompactFormat();
                format.setIndent("  "); //����xml�ļ�������Ϊ2���ո�
                outputter = new XMLOutputter(format); //��Ԫ�غ��У�ÿһ��Ԫ������2��
                outputter.output(doc, new FileOutputStream(xmlPath));

				
				showWarning(" Upload successfully!");
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) { ////////////////////
                e.printStackTrace();
            }
        } //if-else
        	System.out.println("doUpload()is called");
    }


	public void showWarning(String text) {
        JFrame f = new JFrame();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        //System.out.println(text);
        JLabel warningText = new JLabel(text.toString());
        final JDialog dialog = new JDialog(f, "Warning!", true);
        JButton b = new JButton("Close");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        p1.add(warningText);
        p2.add(b);
        dialog.getContentPane().setLayout(new GridLayout(2, 1));
        dialog.getContentPane().add(p1);
        dialog.getContentPane().add(p2);
        dialog.setSize(330, 150);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = dialog.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        dialog.setLocation((screenSize.width - frameSize.width) / 2,
                           (screenSize.height - frameSize.height) / 2);
        dialog.setVisible(true);
    }
}
