package view;

import core.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanel container;
    private JPanel wrapper_top;
    private JLabel label_welcome;
    private JLabel label_welcome2;
    private JPanel wrapper_bottom;
    private JTextField field_username;
    private JPasswordField field_password;
    private JButton button_login;
    private JLabel label_username;
    private JLabel label_password;

    public LoginView(){
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400,300);

        this.setLocation(Helper.getLocationPoint("x",this.getSize()), Helper.getLocationPoint("y",this.getSize()));
        this.setVisible(true);

        button_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.field_username,this.field_password};

            if(Helper.isFieldListEmpty(checkFieldList)){
                System.out.println("DEBUG: Enter all fields!");
                Helper.showMessage("fill");
            }

        });
    }

}
