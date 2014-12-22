package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ming.li on 22/12/2014.
 */
public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private JTextField userField;
    private JPasswordField passwordField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);
        userField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        layoutControl();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer value = (Integer) portSpinner.getValue();
                String user = userField.getText();
                String password = new String(passwordField.getPassword());
                if (prefsListener != null) {
                    prefsListener.preferencesSet(user, password, value);
                }
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(340, 250);
        setLocationRelativeTo(parent);
    }

    private void layoutControl() {
        JPanel controlPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Database preferences");

        controlPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);
        //First row

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.gridx = 0;
        controlPanel.add(new JLabel("User: "), gc);

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.gridx++;
        controlPanel.add(userField, gc);

        //next row
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.gridx = 0;
        controlPanel.add(new JLabel("Password: "), gc);

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.gridx++;
        controlPanel.add(passwordField, gc);

        //Next row
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.gridx = 0;
        controlPanel.add(new JLabel("Port: "), gc);

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.gridx++;
        controlPanel.add(portSpinner, gc);

        //Button panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        //Add sub panels to dialog
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setDefaults(String user, String password, int port) {
        userField.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }

    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }
}