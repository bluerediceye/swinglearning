package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ming.li on 18/12/2014.
 */
public class TextPanel extends JPanel {
    private JTextArea textArea;

    public  TextPanel(){
        textArea = new JTextArea();
        setLayout(new BorderLayout());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text){
        textArea.append(text);
    }
}
