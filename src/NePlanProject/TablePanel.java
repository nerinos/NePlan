package NePlanProject;

import model.EventClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;


public class TablePanel extends JPanel {

    private JTable table;
    private EventTableModel tableModel;
    private JPopupMenu popup;
    private EventTableListener eventTableListener;

    public TablePanel() {

        tableModel = new EventTableModel();
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new MultipleLine());
        table.setBackground(new Color(103, 105, 107)); // бекграунд в таблице
        table.setSelectionBackground(new Color(59, 59, 59)); // цвет выбранной строки в таблице
        popup = new JPopupMenu();

        JMenuItem removeItem = new JMenuItem("Delete event");
        popup.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                table.getSelectionModel().setSelectionInterval(row, row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(table, e.getX(), e.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String name = (String) table.getValueAt(row, 1);
                String time = (String) table.getValueAt(row, 0);


                if (eventTableListener != null) {
                    eventTableListener.rowDeleted(name, time);
                    tableModel.deleteRow(row);
                }
            }
        });
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER); //таблица в центр BorderLayout'a
    }

    public void setData(List<EventClass> db) {
        tableModel.setData(db);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void setEventTableListener(EventTableListener listener) {
        this.eventTableListener = listener;
    }

}
