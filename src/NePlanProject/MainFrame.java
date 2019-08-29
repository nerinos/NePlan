package NePlanProject;

import controller.Controller;
import model.Database;
import model.EventClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


public class MainFrame extends JFrame {

    private ToolBar toolbar;

    private JFileChooser fileChooser;

    private TablePanel tablePanel;

    private Controller controller;

    private Calendar calendar;
    private ImageIcon icon;

    private EventAddDialog eventAdder;

    private Date tableDate;
    static Thread t;


    public MainFrame() {
        super("neplan");

        setLayout(new BorderLayout());

        toolbar = new ToolBar();
        tablePanel = new TablePanel();
        calendar = new Calendar();
        eventAdder = new EventAddDialog();
        controller = new Controller();
        t = new Thread();

        // Создание и подгрузка из директории

        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "Neplan";
        File neplanDir = new File(path);

        try {
            path += File.separator + "default.neplan";
            File defaultPath = new File(path);
            controller.loadFromFile(defaultPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (neplanDir.exists()) {
            System.out.println(neplanDir + " already exists");
        } else if (neplanDir.mkdirs()) {
            System.out.println(neplanDir + " was created");
        } else {
            System.out.println(neplanDir + " was not created");
        }

        // ИКОНКА ДЛЯ ВИНДЫ

        URL iconURL = getClass().getResource("/NePlanProject/icon/icon.png");
        icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        tablePanel.setData(controller.getEvents(new Date()));
        EventClass nearestEvent = controller.getNearestEvent();
        t.interrupt();
        if (nearestEvent != null) {
            t = new BackgroundCheck(nearestEvent.getDate(), nearestEvent.getName(), nearestEvent.getDescription(), new Database(controller.getAllEvents()));
        }
        tablePanel.setEventTableListener(new EventTableListener() {

            public void rowDeleted(String name, String time) {
                controller.removeEvent(name, time);
                EventClass nearestEvent = controller.getNearestEvent();
                t.interrupt();
                if (nearestEvent != null) {
                    t = new BackgroundCheck(nearestEvent.getDate(), nearestEvent.getName(), nearestEvent.getDescription(), new Database(controller.getAllEvents()));
                }
            }
        });

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new NePlanFileFilter());

        setJMenuBar(createMenuBar());

        toolbar.addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventAdder.setVisible(true);
                tablePanel.refresh();

            }
        });

        eventAdder.setDialogListener(new DialogListener() {
            @Override
            public void dialogEventOccured(DialogEvent e) {
                controller.addEvent(e);
                tablePanel.setData(controller.getEvents(e.getDate()));
                EventClass nearestEvent = controller.getNearestEvent();
                t.interrupt();
                if (nearestEvent != null) {
                    t = new BackgroundCheck(nearestEvent.getDate(), nearestEvent.getName(), nearestEvent.getDescription(), new Database(controller.getAllEvents()));
                }
                toolbar.setDateLabel(e.getDate().toString().substring(0, 10));

            }
        });

        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void calEventOccured(CalendarEvent e) {
//                System.out.println("Received in MainFrame: ");
//                System.out.println(e.getDate());
                String date = e.getDate().toString();
                date = date.substring(0, 10);
                tableDate = e.getDate();
                toolbar.setDateLabel(date);
                tablePanel.setData(controller.getEvents(tableDate));
//                System.out.println(e.getDate());
//                System.out.println(controller.getEvents(e.getDate()));
                tablePanel.refresh();
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(calendar, BorderLayout.EAST);
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                String path = System.getProperty("user.home") + File.separator + "Documents";
                path += File.separator + "Neplan";
                path += File.separator + "default.neplan";
                File neplanDir = new File(path);
                try {
                    controller.saveToFile(neplanDir);
                    System.out.println("Successfully saved to default file directory");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        setVisible(true);


    }

    public static void SetThread(Thread thread) {
        t = thread;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
//        JMenu windowMenu = new JMenu("Window");

        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

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
                        System.out.println(fileChooser.getSelectedFile());
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
                    String path = System.getProperty("user.home") + File.separator + "Documents";
                    path += File.separator + "Neplan";
                    path += File.separator + "default.neplan";
                    File neplanDir = new File(path);
                    try {
                        controller.saveToFile(neplanDir);
                        System.out.println("Successfully saved to default file directory");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }

            }
        });

        return menuBar;
    }
}
