package dao;

import core.Database;
import entity.Brand;
import entity.Model;

import java.sql.*;
import java.util.ArrayList;

public class ModelDao {
    private final Connection connection;

    public ModelDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Model> findAll() {
        ArrayList<Model> modelList = new ArrayList<>();
        String sql = "SELECT * FROM public.model";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                Model model = this.match(rs);
                model.setBrand(getBrandById(model.getBrandId()));
                modelList.add(model);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelList;
    }

    public boolean save(Model model) {
        String query = "INSERT INTO public.model (model_brand_id, model_name, model_year, model_type, model_fuel, model_gear) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, model.getBrandId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getYear());
            ps.setString(4, model.getType().name());
            ps.setString(5, model.getFuel().name());
            ps.setString(6, model.getGear().name());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Model model) {
        String query = "UPDATE public.model SET model_brand_id = ?, model_name = ?, model_year = ?, model_type = ?, model_fuel = ?, model_gear = ? WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, model.getBrandId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getYear());
            ps.setString(4, model.getType().name());
            ps.setString(5, model.getFuel().name());
            ps.setString(6, model.getGear().name());
            ps.setInt(7, model.getId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Model getById(int id) {
        Model obj = null;
        String query = "SELECT * FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
                obj.setBrand(getBrandById(obj.getBrandId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return obj;
    }

    private Model match(ResultSet rs) {
        Model obj = new Model();
        try {
            obj.setId(rs.getInt("model_id"));
            obj.setBrandId(rs.getInt("model_brand_id"));
            obj.setName(rs.getString("model_name"));
            obj.setYear(rs.getString("model_year"));
            obj.setType(Model.Type.valueOf(rs.getString("model_type")));
            obj.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
            obj.setGear(Model.Gear.valueOf(rs.getString("model_gear")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private Brand getBrandById(int brandId) {
        Brand brand = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, brandId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brand = new Brand();
                brand.setId(rs.getInt("brand_id"));
                brand.setName(rs.getString("brand_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brand;
    }
}
