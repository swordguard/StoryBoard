package storyboard;

import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.*;
import java.io.*;

import org.jdom.JDOMException;

import java.util.List;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import storyboard.frame.MainFrame;
import storyboard.frame.RegisterFrame;
import storyboard.tablemodel.View_commodities;

public class LoginWindow extends JFrame {
    boolean packFrame = false;
    private String username, password, bAdmin = "false", bFrozen = "false";
    private boolean bLogin = false;
    
    RegisterFrame registerFrame = null;
    JLabel jLabel_username = new JLabel();
    JTextField jText_username = new JTextField(15);
    JLabel jLabel_password = new JLabel();
    JPasswordField jPassword_password = new JPasswordField(15);
    JButton jButton_login = new JButton("Login");
    JButton jButton_cancel = new JButton("Cancel");
    JButton jButton_register = new JButton("Register");
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    View_commodities view_commodities = null;

    public LoginWindow(){

        jLabel_username.setText("Username:");
        jLabel_password.setText("Password:");
        jPassword_password.setEchoChar('*');
        //p1.setLayout(new GridLayout(2,2));
        p1.add(jLabel_username);
        p1.add(jText_username);
        p1.setBounds(20, 20, 150, 30);
        p1.add(jLabel_password);
        p1.add(jPassword_password);
        p2.add(jButton_login);
        jButton_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = "";
                char[] p = jPassword_password.getPassword();
                for (int i = 0; i < p.length; i++) {
                    str = str + p[i];
                }
                checkUsernameAndPassword(jText_username.getText().trim().
                                         toString(), str);
                //System.out.println("isLogin()="+isLogin());
                //System.out.println("isFrozen()="+isFrozen());
                //System.out.println("isAdmin()="+isAdmin());
                if (!isLogin() || isFrozen()) {
                    JFrame f =null;// new JFrame();
                    JPanel p1 = new JPanel();
                    JPanel p2 = new JPanel();
                    JLabel warningText = new JLabel("Login failed!");
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
                    dialog.setSize(150, 150);
                    Dimension screenSize = Toolkit.getDefaultToolkit().
                                           getScreenSize();
                    Dimension frameSize = dialog.getSize();
                    if (frameSize.height > screenSize.height) {
                        frameSize.height = screenSize.height;
                    }
                    if (frameSize.width > screenSize.width) {
                        frameSize.width = screenSize.width;
                    }
                    dialog.setLocation((screenSize.width - frameSize.width) / 2,
                                       (screenSize.height - frameSize.height) /
                                       2);
                    dialog.setVisible(true);

                    //System.out.println("Not logged in");
                    return;
                } else {
                    //setVisible(false);
                	dispose();
                    //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    //View_commodities 
                    view_commodities = new View_commodities();
                    MainFrame frame = new MainFrame(view_commodities,getUsername(), isAdmin());
                    if (packFrame) {
                        frame.pack();
                    } else {
                        frame.validate();
                    }
                    Dimension screenSize = Toolkit.getDefaultToolkit().
                                           getScreenSize();
                    Dimension frameSize = frame.getSize();
                    if (frameSize.height > screenSize.height) {
                        frameSize.height = screenSize.height;
                    }
                    if (frameSize.width > screenSize.width) {
                        frameSize.width = screenSize.width;
                    }
                    frame.setLocation((screenSize.width - frameSize.width) / 2,
                                      (screenSize.height - frameSize.height) /
                                      2);
                    frame.setVisible(true);
                }
            }
        });
        p2.add(jButton_cancel);
        jButton_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        p2.add(jButton_register);
        jButton_register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(registerFrame == null){
            		registerFrame = new RegisterFrame();
            	}
            	registerFrame.show();
            }
        });
        this.add(p1);
        this.add(p2);
        this.pack();
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.setTitle("Welcome!");
        this.setSize(400, 200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void checkUsernameAndPassword(String username, String password) {
    	
    	String file = "XML/users.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
       // System.out.println(xmlPath);
        SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
        try {
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element usersinfo = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List users = usersinfo.getChildren("user"); //得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
            Iterator ite = users.iterator();
            while (ite.hasNext()) {
                Element goods = (Element) ite.next();
                String _username = goods.getChildTextTrim("username"); //取得元素的子元素（为最低层元素）的值：注意的是，必须确定book元素的名为“name”的子元素只有一个。
                String _password = goods.getChildTextTrim("password");
                String _bAdmin = goods.getAttributeValue("bAdmin");
                String _bFrozen = goods.getChildTextTrim("bFrozen");
                if (username.equals(_username) && password.equals(_password)) {
                    this.setUsername(_username);
                    this.setAdmin(_bAdmin);
                    this.setFrozen(_bFrozen);
                    this.setLogin(true);
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAdmin(String bAdmin) {
        this.bAdmin = bAdmin;
    }

    public boolean isAdmin() {
        if (bAdmin.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    public void setFrozen(String bFrozen) {
        this.bFrozen = bFrozen;
    }

    public boolean isFrozen() {
        if (bFrozen.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    public void setLogin(boolean bLogin) {
        this.bLogin = bLogin;
    }

    public boolean isLogin() {
        return bLogin;
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new LoginWindow();
            }
        });
    }
}
