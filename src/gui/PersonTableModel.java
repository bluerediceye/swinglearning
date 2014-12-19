package gui;

import model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by ming.li on 19/12/2014.
 */
public class PersonTableModel extends AbstractTableModel {

    private List<Person> db;
    public PersonTableModel() {
    }

    public void setData(List<Person> db){
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
