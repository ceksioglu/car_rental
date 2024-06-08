package dao;

import core.Database;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;


    public UserDao() {
        this.con = Database.getInstance();
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userArrayList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()) {
                userArrayList.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userArrayList;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";

        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return obj;
    }

    public User match(ResultSet rs){
        User obj = new User();
        try {
            obj.setId(rs.getInt("user_id"));
            obj.setUsername(rs.getString("user_name"));
            obj.setPassword(rs.getString("user_password"));
            obj.setRole(rs.getString("user_role"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

}
