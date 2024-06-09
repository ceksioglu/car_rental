package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str) {
        String msg;
        String title = switch (str) {
            case "fill" -> {
                msg = "Please fill all fields.";
                yield "Error!";
            }
            case "done" -> {
                msg = "Operation successful.";
                yield "Result";
            }
            case "notFound" -> {
                msg = "Requested data not found!";
                yield "Message";
            }
            case "error" -> {
                msg = "An error has occured.";
                yield "Error!";
            }
            default -> {
                msg = str;
                yield "Message";
            }
        };

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }
}
