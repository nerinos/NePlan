package NePlanProject;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

public class Notification {

    public static void Notification(String name, String description) throws AWTException, MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        Image icon = Toolkit.getDefaultToolkit().createImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(icon, "Tray icon");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon");
        tray.add(trayIcon);

        trayIcon.displayMessage(name, description, MessageType.INFO);
    }
}