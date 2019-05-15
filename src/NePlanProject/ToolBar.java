package NePlanProject;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Date;

public class ToolBar extends JPanel {
    public JButton addEventButton;
    private JLabel dateLabel;

    public ToolBar() {
        setBorder(BorderFactory.createEtchedBorder());
        addEventButton = new JButton("Add an event...");
        String date = new Date().toString().substring(0, 10);
        dateLabel = new JLabel(date);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        dateLabel.setBorder(new EtchedBorder());
        add(addEventButton);
        add(dateLabel);
    }

    public void setDateLabel(String date) {
        this.dateLabel.setText(date);
    }
}
