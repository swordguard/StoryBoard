package storyboard.frame;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

import org.jdom.output.Format;

import java.io.*;

import org.jdom.JDOMException;

import java.util.List;

import org.jdom.Document;

import java.util.Iterator;

import org.jdom.output.XMLOutputter;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;

import storyboard.tablemodel.View_commodities;

public class DeleteFrame extends JFrame {
    public DeleteFrame(View_commodities view_commodities, String username,
                       boolean bAdmin) {
        try {

            //System.out.println("del");
            jbInit(view_commodities, username, bAdmin);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit(View_commodities view_commodities, String username1,
                        boolean bAdmin1) throws Exception {
        commModel = view_commodities;
        username = username1;
        //boolean bAdmin = bAdmin1;
        isAdmin = bAdmin1;
        jLabel1.setText("Which record would you like to delete forever?");
        jTextFieldRecordNo.setColumns(5);
        jButtonDel.setText("Delete");
        jButtonDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("checked-2");
                String s = jTextFieldRecordNo.getText().toString();
                for (int i = 0; i < s.length(); i++) {
                    if (!(java.lang.Character.isDigit(s.charAt(i)))) {
                        showWarning("Not integer entered!");
                        return;
                    }
                }
                if (s.equals("")) {
                    showWarning("Empty integer entered!");
                    return;
                }
                if (Integer.parseInt(s) < 1) {
                    showWarning("Wrong integer entered!");
                    //System.out.println("checked1");
                    return;
                }
                if (!checkRowID(Integer.parseInt(s))) {
                    showWarning("Wrong integer entered!");
                    return;
                }

                showConfirmation("Are you sure to delete it?");
                if (getSure() == 1) {
                    //System.out.println("checked-1");
                    String area = getAreaByID(s);
                    //System.out.println("checked3");
                    String catalog = getCatalogByID(s);
                    String name = getNameByID(s);
                    String desc = getDescByID(s);
                    String user = getUsernameByID(s);
                    if(!user.equals(username) && !isAdmin){
                        showWarning("You are not allowed.");
                        return;
                    }
                    //System.out.println("checked0");
                    doDeleteFromXMLFile(area, catalog, name, desc, username);
                    //System.out.println("checked1");
                    commModel.setBDelete(true);
                    commModel.notifyViews(area, catalog, name, desc, username);
                    //System.out.println("checked2");
                    showWarning("Deletion completed successfully!");
                    //System.out.println(getID(s));
                    return;
                } else {
                    return;
                }
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        jPanel1.add(jLabel1);
        jPanel1.add(jTextFieldRecordNo);
        jPanel2.add(jButtonDel);
        jPanel2.add(jButtonCancel);
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(jPanel1);
        this.getContentPane().add(jPanel2);
        this.setTitle("Deleting Window");
        this.setBounds(300, 200, 400, 200);
        this.setVisible(true);
    }

    View_commodities commModel = null;
    JLabel jLabel1 = new JLabel();
    JTextField jTextFieldRecordNo = new JTextField();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JButton jButtonDel = new JButton();
    JButton jButtonCancel = new JButton();
    String username;
    boolean isAdmin;
    int sure = -1;

    public boolean checkRowID(int t) {
        boolean existed = false;
        for (int i = 0; i < getRecordCount(); i++) {
            Object o = commModel.getDTM().getValueAt(i, 0);
            String s = o.toString();
            if (t == Integer.parseInt(s)) {
                existed = true;
                return existed;
            }
        }
        return existed;
    }

    public int getRecordCount() {
        int id = -1;
        id = commModel.getDTM().getRowCount();
        return id;
    }

    public String getAreaByID(String s) {
        String area = "";
        int recordID = -1;
        recordID = Integer.parseInt(s) - 1;
        Object o = commModel.getDTM().getValueAt(recordID, 1);
        area = o.toString();
        //System.out.println("getAreaByID() = "+area);
        return area;
    }

    public String getCatalogByID(String s) {
        String catalog = "";
        int recordID = -1;
        recordID = Integer.parseInt(s) - 1;
        Object o = commModel.getDTM().getValueAt(recordID, 2);
        catalog = o.toString();
        return catalog;
    }

    public String getNameByID(String s) {
        String name = "";
        int recordID = -1;
        recordID = Integer.parseInt(s) - 1;
        Object o = commModel.getDTM().getValueAt(recordID, 3);
        name = o.toString();
        return name;
    }

    public String getDescByID(String s) {
        String desc = "";
        int recordID = -1;
        recordID = Integer.parseInt(s) - 1;
        Object o = commModel.getDTM().getValueAt(recordID, 4);
        desc = o.toString();
        return desc;
    }

    public String getUsernameByID(String s) {
           String username = "";
           int recordID = -1;
           recordID = Integer.parseInt(s) - 1;
           Object o = commModel.getDTM().getValueAt(recordID, 5);
           username = o.toString();
           return username;
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

    public void setSure(int i) {
        this.sure = i;
    }

    public int getSure() {
        return this.sure;
    }

    public void showConfirmation(String text){
        //int i=-1;
        JFrame f = new JFrame();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        //System.out.println(text);
        JLabel warningText = new JLabel(text.toString());
        final JDialog dialog = new JDialog(f, "Warning!", true);
        JButton b1 = new JButton("Sure");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSure(1);
                //sure=1;
                dialog.dispose();
            }
        });
        JButton b2 = new JButton("No");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSure( -1);
                //sure=-1;
                dialog.dispose();
            }
        });
        p1.add(warningText);
        p2.add(b1);
        p2.add(b2);
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
        //return i;
    }

    public void doDeleteFromXMLFile(String area, String catalog, String name,
                                    String desc, String username) {
    	
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
        //String s1 = "xml version=&quot;1.0&quot;";
        //String s2 = "encoding=&quot;UTF-8&quot;";
        //ProcessingInstruction pi = new ProcessingInstruction(s1,s2);
        SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
        try {
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            //doc.addContent(pi);

            Element goodsinfo = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List goods = goodsinfo.getChildren("goods"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为book”的元素，并把这些元素都放到一个List集合中

            //System.out.println("checked0");


            for (int i = 1; i < goods.size() + 1; i++) {
                Element current = (Element) goods.get(i - 1);

                String _area = current.getChild("area").getText().toString();
                String _catalog = current.getChild("catalog").getText().
                                  toString();
                String _name = current.getChild("name").getText().toString();
                String _description = current.getChild("description").getText().
                                      toString();
                String _username = current.getChild("username").getText().
                                   toString();
                /**
                 System.out.println("The node "+ i +" text are as follows:");
                 System.out.println(_area);
                 System.out.println(_catalog);
                 System.out.println(_name);
                 System.out.println(_description);
                 System.out.println(_username);
                 System.out.println("================================");
                 **/
                if (_area.equals(area) && _catalog.equals(catalog) &&
                    _name.equals(name) && _description.equals(desc) &&
                        _username.equals(username))  {

                    current.getParentElement().removeContent(current);
                    System.out.println("doDeleteFromXMLFile() is called.");
                    break;
                }
            }
            //System.out.println("checked1");
            Format format = Format.getCompactFormat();
            format.setIndent("  ");
            format.setEncoding("UTF-8");

            outputter = new XMLOutputter(format);
            FileWriter writer = new FileWriter(xmlPath);
            outputter.output(goodsinfo, writer);
            //Writer.flush();
            writer.close();
            //System.out.println("checked2");

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
