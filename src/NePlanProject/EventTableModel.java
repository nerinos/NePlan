package NePlanProject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.EventClass;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EventTableModel extends AbstractTableModel {
    private List<EventClass> db;

    private String[] colNames = {"Time", "Name", "Description"};

    public EventTableModel() {
    }

    public void setData(List<EventClass> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public void deleteRow(int row) {
        db = new ArrayList<>(db);
        if (db.size() != 0) {
            db.remove(row);            //removes a row based on number from the data
            fireTableRowsDeleted(row, row);    //updates the table
        }
        db = Collections.unmodifiableList(db);
    }

    @Override
    public Object getValueAt(int row, int column) {
        EventClass event = db.get(row);

        switch (column) {
            case 0:
                return event.getDate().toString().substring(11, 16);
            case 1:
                return event.getName();
            case 2:
                return event.getDescription();
        }

        return null;
    }
}
