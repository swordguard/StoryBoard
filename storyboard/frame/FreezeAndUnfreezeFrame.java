package storyboard.frame;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;

import java.util.List;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import java.awt.Toolkit;
import java.awt.Dimension;

import org.jdom.*;

import java.io.*;

import org.jdom.output.*;
import org.jdom.input.*;

import storyboard.tablemodel.ViewUsers;

public class FreezeAndUnfreezeFrame extends JFrame {
	ViewUsers Model = null;
    public FreezeAndUnfreezeFrame(ViewUsers usersinfoDataModel) {
        try {

            jbInit(usersinfoDataModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit(ViewUsers usersinfoDataModel) throws Exception {
    	Model = usersinfoDataModel;
        jLabel1.setText("Enter the user name you would like to freeze:");
        jTextField1.setColumns(15);
        jButtonFreeze.setText("Freeze");
        jButtonFreeze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = jTextField1.getText().toString();

                if(!isExisted(name)){
                	showWarning("The user "+name + " does not exist.");
                    return;
                }
                if (getStatus(name).equals("true")) {
                    showWarning(name + " is already frozen.");
                    return;
                } else if(getStatus(name).equals("false")){
                    //System.out.println("check1");
                    doFreeze(name);
                    Model.notifyViews(name,"true");
                    showWarning(name + " is frozen successfully!");
                    return;
                }
            }
        });

        jButtonUnfreeze.setText("Unfreeze");
				jButtonUnfreeze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = jTextField1.getText().toString();
                //checkStatus(name);
                //System.out.println("check0");
                if(!isExisted(name)){
                	showWarning("The user "+name + " does not exist.");
                    return;
                }
                if (getStatus(name).equals("false")) {
                    showWarning(name + " is already unfrozen.");
                    return;
                } else if(getStatus(name).equals("true")){
                    //System.out.println("check1");
                    doUnfreeze(name);
                    Model.notifyViews(name,"false");
                    showWarning(name + " is unfrozen successfully!");
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
        jPanel1.add(jTextField1);
        jPanel2.add(jButtonFreeze);
        jPanel2.add(jButtonUnfreeze);
        jPanel2.add(jButtonCancel);

        this.getContentPane().setLayout(new GridLayout(2, 3));
        this.getContentPane().add(jPanel1);
        this.getContentPane().add(jPanel2);
        this.setTitle("Freeze and Unfreeze user window.");
        this.setBounds(300, 200, 500, 200);
        this.setVisible(true);
    }

    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    JTextField jTextField1 = new JTextField();
    JPanel jPanel2 = new JPanel();
    JButton jButtonFreeze = new JButton();
    JButton jButtonUnfreeze = new JButton();
    JButton jButtonCancel = new JButton();
    boolean bFrozen;

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



    public void doFreeze(String username) {
        String _bFrozen = "true";
        
        
        String file = "XML/users.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
       // String xmlPath = "storyboard/XML/users.xml";
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element usersinfo = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List users = usersinfo.getChildren("user"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            Iterator ite = users.iterator();
            for (int i = 0; i < users.size(); i++) {
                Element current = (Element) users.get(i);
                if (current.getChild("username").getText().toString().equals(
                        username)) {
                    current.getChild("bFrozen").setText(_bFrozen);
                    //System.out.println("modified");
                    break;
                }
            }
					//����Document���޸ĵ�XML�ļ��У�
      XMLOutputter outputter = new XMLOutputter();
      outputter.output(doc,new FileOutputStream(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

		public void doUnfreeze(String username) {
        String _bFrozen = "false";
        
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
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element usersinfo = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List users = usersinfo.getChildren("user"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            Iterator ite = users.iterator();
            for (int i = 0; i < users.size(); i++) {
                Element current = (Element) users.get(i);
                if (current.getChild("username").getText().toString().equals(
                        username)) {
                    current.getChild("bFrozen").setText(_bFrozen);
                    //System.out.println("modified");
                    break;
                }
            }
					//����Document���޸ĵ�XML�ļ��У�
      XMLOutputter outputter = new XMLOutputter();
      outputter.output(doc,new FileOutputStream(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setStatus(String b) {
        if (b.equals("false")) {
            this.bFrozen = false;
        }
        if (b.equals("true")) {
            this.bFrozen = true;
        }
    }

		public boolean isExisted(String s){
			boolean existed = false;
			
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
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element usersinfo = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List users = usersinfo.getChildren("user"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            Iterator ite = users.iterator();
            while (ite.hasNext()) {
                Element user = (Element) ite.next();
                String username = user.getChildTextTrim("username");
                //String bFrozen = user.getChildTextTrim("bFrozen");
                if (username.equals(s)) {
                    existed = true;
                    return existed;
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existed;
		}
		
		
    public String getStatus(String s) {
        String strFrozen="";
        //boolean existed = false;
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
        SAXBuilder saxBuilder = new SAXBuilder(false); //���ʾʹ�õ���Ĭ�ϵĽ�����
        try {
            Document doc = saxBuilder.build(xmlPath); //�õ�Document�������Ժ�Ҫ���е����в������Ƕ����Document�����ģ�
            Element usersinfo = doc.getRootElement(); //�õ���Ԫ�أ���JDOM�����еĽڵ㣨DOM�еĸ������һ��org.jdom.Element�࣬��Ȼ�����ӽڵ�Ҳ��һ��org.jdom.Element�ࡣ
            List users = usersinfo.getChildren("user"); //�õ�Ԫ�أ��ڵ㣩�ļ��ϣ����ʾ�õ���books��Ԫ�ص���������Ϊ��book����Ԫ�أ�������ЩԪ�ض��ŵ�һ��List������
            Iterator ite = users.iterator();
            while (ite.hasNext()) {
                Element user = (Element) ite.next();
                String username = user.getChildTextTrim("username");
                String bFrozen = user.getChildTextTrim("bFrozen");
                if (username.equals(s)) {
                    //existed = true;
                    strFrozen = bFrozen;
                    break;
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strFrozen;
    }
}
