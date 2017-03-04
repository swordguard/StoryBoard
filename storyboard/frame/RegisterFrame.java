package storyboard.frame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
//import java.awt.List;
import java.util.*;
import java.util.List;

import org.jdom.output.Format;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Element;

import java.io.IOException;

import org.jdom.JDOMException;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jTextFieldPwd.setEchoChar('*');
        jTextFieldRePwd.setEchoChar('*');
        jButtonRegister.setHorizontalTextPosition(SwingConstants.LEADING);
        jButtonRegister.setSelected(true);
        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRegister();
            }
        });

        jButtonCancel.setText("Close");
        jButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(false);
            }
        });
        JPanel p = new JPanel(new GridLayout(8, 2));
        jLabelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelPwd.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelRePwd.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelZipcode.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTele.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        p.add(jLabelUsername);
        p.add(jTextFieldUsername);
        p.add(jLabelPwd);
        p.add(jTextFieldPwd);
        p.add(jLabelRePwd);
        p.add(jTextFieldRePwd);
        p.add(jLabelAddress);
        p.add(jTextFieldAddress);
        p.add(jLabelZipcode);
        p.add(jTextFieldZipcode);
        p.add(jLabelTele);
        p.add(jTextFieldTele);
        p.add(jLabelEmail);
        p.add(jTextFieldEmail);
        p.add(jButtonRegister);
        p.add(jButtonCancel);

        this.setTitle("Register Window.");
        this.getContentPane().add(p);
        this.setBounds(300, 200, 450, 450);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void doRegister() {

        if (jTextFieldUsername.getText().toString().equals("")) { // || this.jTextFieldUsername == null) {
            showWarning(jLabelUsername.getText() + " must be filled in!");
            return;
        }
        if (jTextFieldPwd.getPassword().equals("")) { // || jTextFieldPwd == null) {
            showWarning(jLabelPwd.getText().toString() + " must be filled in!");
            return;
        }
        if (jTextFieldRePwd.getPassword().equals("")) { // || jTextFieldPwd == null) {
            showWarning(jLabelRePwd.getText().toString() +
                        " must be filled in!");
            return;
        }
        if (!this.getPwdTostr(jTextFieldRePwd.getPassword()).equals(this.
                getPwdTostr(jTextFieldPwd.getPassword()))) {
            showWarning(jLabelPwd.getText().toString() + " and " +
                        jLabelRePwd.getText().toString() + " do not match!");
            return;
        }
        if(checkUser(jTextFieldUsername.getText().toString())){
        	showWarning(jTextFieldUsername.getText().toString()+" already exists, try a another name!");
        	return;
        }

        String file = "XML/users.xml";
    	File fin = null;
		File dir = new File(".");
		try {
			fin = new File(dir.getCanonicalPath() + File.separator + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String xmlPath = fin.toString();
        
        XMLOutputter outputter = null;
        Element username = new Element("username");
        Element password = new Element("password");
        Element address = new Element("address");
        Element zipcode = new Element("zipcode");
        Element telephone = new Element("telephone");
        Element email = new Element("email");
        Element user = new Element("user");
        Element bFrozen = new Element("bFrozen");
        //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false); //这表示使用的是默认的解析器
        try {
            Document doc = saxBuilder.build(xmlPath); //得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element usersinfo = doc.getRootElement(); //得到根元素：在JDOM中所有的节点（DOM中的概念）都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。

            username.setText(jTextFieldUsername.getText().toString());
            password.setText(getPwdTostr(jTextFieldRePwd.getPassword()));
            address.setText(jTextFieldAddress.getText().toString());
            zipcode.setText(jTextFieldZipcode.getText().toString());
            telephone.setText(jTextFieldTele.getText().toString());
            email.setText(jTextFieldEmail.getText().toString());
            bFrozen.setText("false");
            //username.setText(jTextFieldUsername.getText().toString());
            user.addContent(username);
            user.addContent(password);
            user.addContent(address);
            user.addContent(zipcode);
            user.addContent(telephone);
            user.addContent(email);
            user.addContent(bFrozen);
            user.setAttribute("bAdmin", "false");

            usersinfo.addContent(user);

            Format format = Format.getCompactFormat();
            format.setIndent("  "); //设置xml文件的缩进为2个空格
            outputter = new XMLOutputter(format); //在元素后换行，每一层元素缩排2格
            outputter.output(doc, new FileOutputStream(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) { ////////////////////
            e.printStackTrace();
        }
				showWarning("Registration is done successfully!");
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

    public String getPwdTostr(char[] c) {
        String pwd = "";
        char[] p = c;
        for (int i = 0; i < p.length; i++) {
            pwd = pwd + p[i];
        }
        return pwd;
    }

    
    
    
		public boolean checkUser(String name){
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
			//String xmlPath = "src/storyboard/XML/users.xml";
    //使用JDOM首先要指定使用什么解析器。
        SAXBuilder saxBuilder = new SAXBuilder(false);//这表示使用的是默认的解析器
        try{
            Document doc = saxBuilder.build(xmlPath);//得到Document，我们以后要进行的所有操作都是对这个Document操作的：
            Element usersinfo = doc.getRootElement();//得到根元素：在JDOM中所有的节点（DOM中的概念都是一个org.jdom.Element类，当然他的子节点也是一个org.jdom.Element类。
            List users = usersinfo.getChildren("user");//得到元素（节点）的集合：这表示得到“books”元素的所在名称为“book”的元素，并把这些元素都放到一个List集合中
            //rowNumber = goodslist.size();
            //System.out.println(rowNumber);
            Iterator ite = users.iterator();
            while(ite.hasNext()){

                Element user = (Element)ite.next();

                String _name = user.getChildTextTrim("username").toString();
                if(name.toString().equals(_name)){
                	existed = true;
                	return existed;
                }
           }
        }catch(JDOMException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }
			return existed;
		}
    JLabel jLabelUsername = new JLabel("User name");
    JTextField jTextFieldUsername = new JTextField(20);
    JLabel jLabelPwd = new JLabel("Password");
    JPasswordField jTextFieldPwd = new JPasswordField(20);

    JLabel jLabelRePwd = new JLabel("Confirm password");
    JPasswordField jTextFieldRePwd = new JPasswordField(20);
    JLabel jLabelAddress = new JLabel("Address");
    JTextField jTextFieldAddress = new JTextField(20);
    JLabel jLabelZipcode = new JLabel("Zip Code");
    JTextField jTextFieldZipcode = new JTextField(20);
    JLabel jLabelTele = new JLabel("Telephone");
    JTextField jTextFieldTele = new JTextField(20);
    JLabel jLabelEmail = new JLabel("Email");
    JTextField jTextFieldEmail = new JTextField(20);
    JButton jButtonRegister = new JButton();
    JButton jButtonCancel = new JButton();

    public String getUsername() {
        return jTextFieldUsername.getText().toString();
    }
}
