package NePlanProject;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MainFrame extends JFrame {

    private ToolBar toolbar;

    private JFileChooser fileChooser;

    private TablePanel tablePanel;

    private Controller controller;

    private Calendar calendar;

    private EventAddDialog eventAdder;




    public MainFrame() {
        super("NePlan");

        setLayout(new BorderLayout());

        toolbar = new ToolBar();
        tablePanel = new TablePanel();
        calendar = new Calendar();
        eventAdder = new EventAddDialog();

        controller = new Controller();

        tablePanel.setData(controller.getEvents());
        tablePanel.setEventTableListener(new EventTableListener() {
           public void rowDeleted(int row) {
               controller.removeEvent(row);
           }
        });

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new NePlanFileFilter());

        setJMenuBar(createMenuBar());

        toolbar.addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventAdder.setVisible(true);

            }
        });

        eventAdder.setDialogListener(new DialogListener() {
            @Override
            public void dialogEventOccured(DialogEvent e) {
                controller.addEvent(e);
                tablePanel.refresh();
            }
        });

        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void calEventOccured(CalendarEvent e) {
//                System.out.println("Received in MainFrame: ");
//                System.out.println(e.getDate());
                String date = e.getDate().toString();
                date = date.substring(0, 10);
                toolbar.setDateLabel(date);
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(calendar, BorderLayout.EAST);
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
//        JMenu windowMenu = new JMenu("Window");

        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem =  new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        fileMenu.setMnemonic(KeyEvent.VK_F); // хоткей
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Couldn't load data from file",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Couldn't save data from file",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you really want to exit the application?", "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION);
                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }

            }
        });

        return menuBar;
    }
}
