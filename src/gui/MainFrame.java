package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import static java.awt.event.ActionEvent.CTRL_MASK;

/**
 * Created by ming.li on 18/12/2014.
 */
public class MainFrame extends JFrame {

    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private TablePanel tablePanel;
    private Controller controller;
    private PrefsDialog prefsDialog;
    private Preferences prefs;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 600));
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();
        controller = new Controller();
        prefsDialog = new PrefsDialog(this);

        prefs = Preferences.userRoot().node("db");

        tablePanel.setData(controller.getPeople());
        tablePanel.setPersonTableListener(new PersonTableListener() {
            @Override
            public void rowDeleted(int row) {
                controller.removePerson(row);
            }
        });

        prefsDialog.setPrefsListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int port) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);
            }
        });

        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);
        prefsDialog.setDefaults(user, password, port);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());


        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Can't save into database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void refreshEventOccured() {
                connect();
                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Can't load from database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
                }
                tablePanel.refresh();
            }
        });

        formPanel.setFormListener(new FormListener() {
                                      @Override
                                      public void formEventOccurred(FormEvent e) {
                                          controller.addPerson(e);
                                          tablePanel.refresh();
                                      }
                                  }
        );

        add(formPanel, BorderLayout.WEST);
        add(toolbar, BorderLayout.NORTH);
//        add(textPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can't connect to database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //File->Export Data, File->Import Data, File->Exit
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        //Window->Show->Person Form
        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem prefsItem = new JMenuItem("Preferences...");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });


        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, CTRL_MASK));
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, CTRL_MASK));
        exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, CTRL_MASK));
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, CTRL_MASK));

        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFIle(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*String text = JOptionPane.showInputDialog(gui.MainFrame.this,
                        "Enter your user name.",
                        "Enter User Name", JOptionPane.OK_OPTION | JOptionPane.QUESTION_MESSAGE);
                System.out.println(text);*/

                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you really want to exit the application?",
                        "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        return menuBar;
    }
}
