package NePlanProject;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put("control", new Color(64, 64, 64));
                UIManager.put("info", new Color(64, 64, 64));
                UIManager.put("nimbusBase", new Color(82, 82, 82));
                UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
                UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
                UIManager.put("nimbusFocus", new Color(110, 109, 112));
                UIManager.put("nimbusLightBackground", new Color(82, 82, 82));
                UIManager.put("nimbusOrange", new Color(191, 98, 4));
                UIManager.put("nimbusRed", new Color(169, 46, 34));
                UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
                UIManager.put("background", new Color(255, 255, 255));
                UIManager.put("nimbusSelection", new Color(150, 150, 150));
                UIManager.put("SelectionBackground", new Color(150, 150, 150));
                UIManager.put("text", new Color(255, 255, 255));
                UIManager.put("TextArea.background", new Color(255, 255, 255));
                UIManager.put("Table.textForeground", new Color(255, 255, 255));
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (javax.swing.UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new MainFrame();
            }
        });


    }
}
