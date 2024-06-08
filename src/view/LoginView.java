package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
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
    private final UserManager userManager;


    public LoginView(){
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitialize(400,300);

        button_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.field_username,this.field_password};

            if(Helper.isFieldListEmpty(checkFieldList)){
                System.out.println("DEBUG: Enter all fields!");
                Helper.showMessage("fill");
            } else {
                User loginUser = this.userManager.findbyLogin(this.field_username.getText(),this.field_password.getText());
                if (loginUser == null){
                    Helper.showMessage("notFound");
                } else{
                    System.out.println(loginUser.toString());
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }
            }

        });
    }

}
