import javax.swing.*;
import java.awt.*;

/**
 * Created by ming.li on 18/12/2014.
 */
public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;

    public MainFrame() {
        super("Hello World");

        setLayout(new BorderLayout());

        toolbar = new Toolbar();
        textPanel = new TextPanel();
        formPanel = new FormPanel();

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
                                          textPanel.appendText(name + " : " + occupation + " : " + ageCat + " : " + empCat + "\n");
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
}
