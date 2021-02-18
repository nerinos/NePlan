package NePlanProject;

import model.Database;
import model.EventClass;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.Date;

public class BackgroundCheck extends Thread {
    Date alarmTime;
    String alarmName;
    String alarmDescription;
    Date alarmTime2;
    Database db;

    public BackgroundCheck(Date alarmTime, String alarmName, String alarmDescription, Database db) {
        super(alarmName);
        alarmTime.setSeconds(0);
        this.alarmTime = alarmTime;
    //    System.out.println(alarmTime);
        alarmTime2 = new Date(alarmTime.getTime() - 30 * 60000);
        alarmTime2.setSeconds(0);
    //    System.out.println(alarmTime2);
        this.alarmName = alarmName;
        //      System.out.println(alarmName);
        this.alarmDescription = alarmDescription;
        //      System.out.println(alarmDescription);
        this.db = db;
        start();
    }

    public void run() {
        try {
            while (alarmTime2.after(new Date())) {
                if (!isInterrupted()) {
                    Thread.sleep(500);
                } else {
                    throw new InterruptedException();
                }
            }
            if (SystemTray.isSupported()) {
                Notification.Notification("30 minutes left before the event: \""+ alarmName + "\"", alarmDescription);
            } else {
                System.err.println("System tray not supported!");
            }
            while (alarmTime.after(new Date())) {
                if (!isInterrupted()) {
                    Thread.sleep(500);
                } else {
                    throw new InterruptedException();
                }
            }
            if (SystemTray.isSupported()) {
                Notification.Notification(alarmName, alarmDescription);
            } else {
                System.err.println("System tray not supported!");
            }
            EventClass nearestEvent = db.getNearestEvent();
            if (nearestEvent != null) {
                Thread t = new BackgroundCheck(nearestEvent.getDate(), nearestEvent.getName(), nearestEvent.getDescription(), db);
                MainFrame.SetThread(t);
            } else {
                Thread t = new Thread();
                MainFrame.SetThread(t);
            }
        } catch (AWTException | MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("thread was interrupted");
        }
    }
}
