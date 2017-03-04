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
                //通知观察者上传已完成
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
        //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
        try {
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element areas = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List area = areas.getChildren("area"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
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
        //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
        try {
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element catalogs = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List catalog = catalogs.getChildren("catalog"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
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
            //使用JDOM首先要指定使用什么解析器。
            SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
            try {
                Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
                Element goodsinfo = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。

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
                format.setIndent("  "); //设置xml文件的缩进为2个空格
                outputter = new XMLOutputter(format); //在元素后换行，每一层元素缩排2格
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
