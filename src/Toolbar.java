import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {

    private StringListener stringListener;
    private JButton helloButton;
    private JButton goodbyeButton;

    public Toolbar() {

        setBorder(BorderFactory.createEtchedBorder());


        helloButton = new JButton("Hello");
        goodbyeButton = new JButton("Goodbye");

        helloButton.addActionListener(this);
        goodbyeButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(helloButton);
        add(goodbyeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
//        textPanel.appendText(clicked.getText());

        if(clicked == helloButton){
            if(stringListener != null){
                stringListener.textEmitted("Hello");
            }
        }else if(clicked == goodbyeButton){
            if(stringListener != null){
                stringListener.textEmitted("Good bye");
            }
        }
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }
}