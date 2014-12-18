import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
