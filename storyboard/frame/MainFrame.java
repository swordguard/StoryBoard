 package storyboard.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.*;
import java.util.Observer;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import storyboard.tablemodel.ViewUsers;
import storyboard.tablemodel.View_commodities;

import java.util.Observable;

public class MainFrame extends JFrame implements Observer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane = null;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuCommodity = new JMenu();
    JMenuItem jMenuView = new JMenuItem();
    JMenuItem jMenuSearch = new JMenuItem();
    JMenuItem jMenuUpload = new JMenuItem();
    JMenuItem jMenuModify = new JMenuItem();
    JMenuItem jMenuDelete = new JMenuItem();
    JMenu jMenuUsers = new JMenu();
    JMenuItem jMenuViewUsers = new JMenuItem();
    JMenuItem jMenuDeregister = new JMenuItem();
    JMenuItem jMenuFreezeUnfreezeUsers = new JMenuItem();
    JMenuItem jMenuLogout = new JMenuItem();
    //JMenuItem jMenuUnfreezeUsers = new JMenuItem();
    JScrollPane view_comm_scrollPane = null;
    View_commodities M;
    ViewUsers usersModel;
    //UsersinfoDataModel usersinfoDataModel = null;
    JTable commTable = null;
    String username;
    boolean bAdmin = false;

    public MainFrame(View_commodities view_commodities, String username1,
                     boolean bAdmin1) {
        try {
            jbInit(view_commodities, username1, bAdmin1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setBAdmin(boolean b){
    	this.bAdmin = b;
    }
    public boolean getBAdmin(){
    	return this.bAdmin;
    }

    private void jbInit(View_commodities view_commodities, String username1,
                        boolean bAdmin1) throws Exception {
        //this.view_commodities = view_commodities;
        M = view_commodities;
        //usersinfoDataModel = new UsersinfoDataModel();
        usersModel = new ViewUsers();
        this.username = username1;
        //this.bAdmin = bAdmin1;
        this.setBAdmin(bAdmin1);
        // System.out.println(bAdmin);
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            //jbInit(view_commodities);
            contentPane = (JPanel) getContentPane();
            
            contentPane.setLayout(borderLayout1);
            setSize(new Dimension(600, 300));
            setTitle("welcome to CIEP");
            jMenuFile.setText("File");
            jMenuFileExit.setText("Exit");
            jMenuFileExit.addActionListener(new
                                            MainFrame_jMenuFileExit_ActionAdapter(this));
            jMenuCommodity.setText("Commodity");
            jMenuView.setText("View");
            jMenuView.setEnabled(false);
            jMenuView.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //new View_commodities();
                }
            }
            );
            jMenuSearch.setText("Search");
            jMenuSearch.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Searching_comm_Frame();
                }
            }
            );
            jMenuUpload.setText("Upload");
            jMenuUpload.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Upload_comm_Frame((View_commodities) M, username,
                                          bAdmin);
                }
            }
            );
            jMenuModify.setText("Modify Record");
            jMenuModify.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new DeleteFrame((View_commodities)M,username,bAdmin);
                }
            }
            );
            
            jMenuDelete.setText("Delete Record");
            jMenuDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new DeleteFrame((View_commodities)M,username,bAdmin);
                }
            }
            );

            jMenuUsers.setText("Users");
            jMenuViewUsers.setText("View all users");
            jMenuViewUsers.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new ViewUsersFrame(usersModel);
                }
            });
            jMenuDeregister.setText("Deregister");
            jMenuFreezeUnfreezeUsers.setText("Freeze/Unfreeze user");
            jMenuFreezeUnfreezeUsers.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new FreezeAndUnfreezeFrame(usersModel);
                }
            });
            jMenuLogout.setText("Logout");
            jMenuLogout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                     //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    System.exit(1);
                    //LoginWindow.showMe();
                }
            });

            jMenuBar1.add(jMenuFile);
            jMenuBar1.add(jMenuCommodity);
            jMenuBar1.add(jMenuUsers);
            jMenuFile.add(jMenuFileExit);
              //jMenuCommodity.add(jMenuView);
            jMenuCommodity.add(jMenuSearch);
            jMenuCommodity.add(jMenuUpload);
            jMenuCommodity.add(jMenuModify);
            jMenuCommodity.add(jMenuDelete);
            jMenuUsers.add(jMenuViewUsers);
            jMenuUsers.add(jMenuDeregister);
            jMenuUsers.add(jMenuFreezeUnfreezeUsers);
             jMenuUsers.add(jMenuLogout);
            //jMenuUsers.add(jMenuUnfreezeUsers);
            setJMenuBar(jMenuBar1);

            view_commodities.addView(this);
            commTable = view_commodities.commodities;
            view_comm_scrollPane = new JScrollPane(commTable);
            this.getContentPane().add(view_comm_scrollPane);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        jMenuModify.setEnabled(false);
        jMenuDeregister.setEnabled(false);
        jMenuFreezeUnfreezeUsers.setEnabled(false);
				if(getBAdmin() == true){

        			jMenuFreezeUnfreezeUsers.setEnabled(true);
        }

    }

    public void update(Observable o, Object value) {
        //System.out.println("update() has been executed");
        String allStr = value.toString();
        String area, catalog, text_name, desc, username2, arr[];
        arr = allStr.split("#");
        area = arr[0].toString();
        //String id = arr[0].toString();
        catalog = arr[1].toString();
        text_name = arr[2].toString();
        desc = arr[3].toString();
        username2 = arr[4].toString();
        if (M.isAdd()) {
            M.addToModel(area, catalog, text_name, desc, username2);
            M.setBAdd(false);
            //System.out.println("Add notification send out!");
        }
        if (M.isUpdate()) {
            M.updateModel(area, catalog, text_name, desc);
        }
        if (M.isDelete()) {
            M.deleteFromModel(area, catalog, text_name, desc, username2);
            M.setBDelete(false);
            //System.out.println("Delete notification send out!");
        }
        //System.out.println("Observer MainFrame.update(Observable o,Object value) ends");
    }



    /** 
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    transient Vector actionListeners;
    JMenuItem jMenuItem1 = new JMenuItem();
    
    
    
    public synchronized void addActionListener(ActionListener l) {
    }

    public synchronized void removeActionListener(ActionListener l) {
    }

    protected void fireActionPerformed(ActionEvent actionEvent) {
        if (actionListeners != null) {
            Vector listeners = actionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ActionListener) listeners.elementAt(i)).actionPerformed(
                        actionEvent);
            }
        }
    }
}


class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    MainFrame adaptee;

    MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}
