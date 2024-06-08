import business.UserManager;
import core.Database;
import core.Helper;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        System.out.println("Starting application.");
        Helper.setTheme();

        Connection con = Database.getInstance();
        UserManager userManager = new UserManager();
        //LoginView loginView = new LoginView();
        AdminView adminView = new AdminView(userManager.findbyLogin("admin","sudoroot"));
        //DAO
        //Entity
        //Business
        //View



    }
}
