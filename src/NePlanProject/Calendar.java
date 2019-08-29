package NePlanProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.Locale;

import static javax.swing.SwingConstants.CENTER;

public class Calendar extends JPanel {
    private java.util.Calendar cal;
    private DefaultTableModel model;
    private JLabel label;
    private CalendarListener calListener;
    private JLabel yearLabel;

    public Calendar() {
        Dimension dim = new Dimension(255, 200);
        setPreferredSize(dim);
        setLayout(new BorderLayout());

        cal = new GregorianCalendar();
        label = new JLabel();
        yearLabel = new JLabel();
        yearLabel.setHorizontalAlignment(CENTER);
        label.setHorizontalAlignment(CENTER);

        JButton b1 = new JButton("<-");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cal.add(java.util.Calendar.MONTH, -1);
                updateMonth();
            }
        });

        JButton b2 = new JButton("->");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cal.add(java.util.Calendar.MONTH, +1);
                updateMonth();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(yearLabel, BorderLayout.NORTH);
        panel.add(b1, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        panel.add(b2, BorderLayout.EAST);


        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        JTable table = new JTable(model);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        JScrollPane pane = new JScrollPane(table);
        table.setSelectionBackground(new Color(110, 110, 110));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                table.setCellSelectionEnabled(true);
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (table.getValueAt(row, col) != null) {
                    int day = (int) table.getValueAt(row, col);
                    int month = cal.get(java.util.Calendar.MONTH);
                    int year = cal.get(java.util.Calendar.YEAR);
                    CalendarEvent ev = new CalendarEvent(this, day, month, year);

                    if (calListener != null) {
                        calListener.calEventOccured(ev);
                    }
                }

            }
        });

        add(panel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);

        updateMonth();
        setVisible(true);

    }


    void updateMonth() {

        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        String month = cal.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, Locale.US);
        int year = cal.get(java.util.Calendar.YEAR);
        yearLabel.setText(Integer.toString(year));
        label.setText(month);

        int startDay = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        int weeks = cal.getActualMaximum(java.util.Calendar.WEEK_OF_MONTH);

        model.setRowCount(0);
        model.setRowCount(weeks + 2);

        int i = startDay - 1;
        for (int day = 1; day <= numberOfDays; day++) {
            model.setValueAt(day, i / 7, i % 7);
            i = i + 1;
        }
    }

    public void setCalendarListener(CalendarListener listener) {
        this.calListener = listener;
    }
}

