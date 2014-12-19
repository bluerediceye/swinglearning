import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by ming.li on 18/12/2014.
 */
public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 400));
        toolbar = new Toolbar();
        textPanel = new TextPanel();
        formPanel = new FormPanel();
        fileChooser = new JFileChooser();

        setJMenuBar(createMenuBar());


        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }
        });

        formPanel.setFormListener(new FormListener() {
                                      @Override
                                      public void formEventOccurred(FormEvent e) {
                                          String name = e.getName();
                                          String occupation = e.getOccupation();
                                          int ageCat = e.getAgeCategory();
                                          String empCat = e.getEmpCat();
                                          String gender = e.getGender();
                                          textPanel.appendText(name + " : "
                                                  + occupation + " : "
                                                  + ageCat + " : "
                                                  + empCat +  " : " + gender + "\n");
                                      }
                                  }
        );

        add(formPanel, BorderLayout.WEST);
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);

        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
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
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*String text = JOptionPane.showInputDialog(MainFrame.this,
                        "Enter your user name.",
                        "Enter User Name", JOptionPane.OK_OPTION | JOptionPane.QUESTION_MESSAGE);
                System.out.println(text);*/

                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you really want to exit the application?",
                        "Confirm exit",JOptionPane.OK_CANCEL_OPTION );
                if(action == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
            }
        });

        return menuBar;
    }
}
