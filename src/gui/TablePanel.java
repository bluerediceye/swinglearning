package gui;

import model.Person;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ming.li on 19/12/2014.
 */
public class TablePanel extends JPanel {

    private JTable table;
    private PersonTableModel tableModel;

    public TablePanel() {
        tableModel = new PersonTableModel();
        table = new JTable(tableModel);
        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);

    }

    public void setData(java.util.List<Person> db){
        tableModel.setData(db);
    }
}
