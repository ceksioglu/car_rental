package view;

import core.Database;
import entity.User;

import javax.swing.*;
import java.sql.Connection;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel panel_top;
    private JTabbedPane tab_menu;
    private JButton button_logout;
    private JPanel panel_brand;
    private JScrollPane scroll_brand;
    private JTable table_brand;
    private User user;


    public AdminView(User user) {
        this.add(container);
        this.guiInitialize(1000,500);
        this.user = user;

        if (this.user == null){
            dispose();
        }
        this.label_welcome.setText("Welcome :" + this.user.getUsername());
    }
}
