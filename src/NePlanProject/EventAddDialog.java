package NePlanProject;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EventAddDialog extends JDialog {

    private JLabel eventLabel;
    private JTextField eventField;
    private JTextArea eventDescription;
    private JLabel label;
    private JButton addBtn;
    private JDateChooser dateChooser;
    private Date date;
    private SpinnerDateModel sm;
    private JSpinner spinner;
    private DialogListener dialogListener;


    public EventAddDialog() {
        super(new JFrame(), "New Event", true);
        label = new JLabel("Fill in the fields and press the button");
        eventLabel = new JLabel("Event name:");
        eventField = new JTextField(10);
        eventDescription = new JTextArea(8, 20);
        eventDescription.setLineWrap(true);
        eventDescription.setWrapStyleWord(true);
        Border innerBorder = BorderFactory.createTitledBorder("Event description");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        eventDescription.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        eventDescription.setBackground(Color.DARK_GRAY);
        addBtn = new JButton("Add");
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setForeground(new Color(255, 255, 255));
        date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
        spinner.setEditor(de);
        spinner.setBackground(Color.DARK_GRAY);


        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eventName = eventField.getText();
                eventField.setText("");
                String description = eventDescription.getText();
                eventDescription.setText("");
                Date date = dateChooser.getDate();
                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
                String time = localDateFormat.format(spinner.getValue());
                int hours = Integer.valueOf(time.substring(0, 2));
                int minutes = Integer.valueOf(time.substring(3, 5));

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, hours);
                cal.set(Calendar.MINUTE, minutes);
                date = cal.getTime();
                DialogEvent ev = new DialogEvent(this, eventName, description, date);
                if (dialogListener != null) {
                    dialogListener.dialogEventOccured(ev);
                }

                setVisible(false);
            }
        });
        setBounds(750, 250, 400, 350);
        setMinimumSize(new Dimension(400, 350));
        setForeground(Color.DARK_GRAY);
        layoutComponents();

    }


    public void layoutComponents() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        // первая строка

        gc.gridy = 0;
        gc.gridwidth = 2;


        gc.weightx = 1;
        gc.weighty = 2.0;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(label, gc);
        gc.gridwidth = 1;

        // вторая строка
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.gridx = 0;

        gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(0, 0, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END; // элементы ближе к левому краю
        add(eventLabel, gc);

        gc.gridx = 1;

        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(eventField, gc);

        // третья

        gc.gridy++;

        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.insets = new Insets(0, 20, 0, 20);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.LINE_START; // элементы ближе к левому краю

        add(eventDescription, gc);

        // третья

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.insets = new Insets(0, 10, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END; // элементы ближе к левому краю своей клетки
        add(new JLabel("Select Date: ", 10), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 10, 0, 10);
        gc.anchor = GridBagConstraints.LINE_START; // элементы ближе к левому краю
        add(dateChooser, gc);

        // следующая

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.insets = new Insets(0, 10, 0, 5);
        gc.anchor = GridBagConstraints.LINE_END; // элементы ближе к левому краю своей клетки
        add(new JLabel("Select Time: ", 10), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 10, 0, 10);
        gc.anchor = GridBagConstraints.LINE_START; // элементы ближе к левому краю
        add(spinner, gc);


        // Следующая строка
        gc.gridwidth = 2;
        gc.gridy++;
        gc.ipady = 15;
        gc.weightx = 1;
        gc.weighty = 2.0;
        gc.gridx = 0;
        gc.insets = new Insets(0, 10, 5, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.PAGE_END;
        add(addBtn, gc);
        gc.ipady = 0;
    }

    public void setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
    }
}