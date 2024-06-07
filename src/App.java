import core.Database;
import core.Helper;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        System.out.println("Starting application.");
        Helper.setTheme();

        Connection con = Database.getInstance();
        LoginView loginView = new LoginView();

        //DAO
        //Entity
        //Business
        //View



    }
}
