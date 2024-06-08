package dao;

import core.Database;
import entity.Brand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection connection;

    public BrandDao(){
        this.connection = Database.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> userArrayList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                userArrayList.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userArrayList;
    }

    public Brand match(ResultSet rs){
        Brand obj = new Brand();
        try {
            obj.setId(rs.getInt("brand_id"));
            obj.setName(rs.getString("brand_name"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
}
