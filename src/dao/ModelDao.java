package dao;

import core.Database;
import entity.Brand;
import entity.Model;

import java.sql.*;
import java.util.ArrayList;

/**
 * The ModelDao class provides methods for interacting with the model data in the database.
 */
public class ModelDao {
    private final Connection connection;

    /**
     * Constructs a ModelDao instance and initializes the database connection.
     */
    public ModelDao() {
        this.connection = Database.getInstance();
    }

    /**
     * Retrieves all models from the database.
     *
     * @return An ArrayList of Model objects.
     */
    public ArrayList<Model> findAll() {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        String sql = "SELECT * FROM public.model";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                modelArrayList.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelArrayList;
    }

    /**
     * Saves a new model to the database.
     *
     * @param model The model to be saved.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean save(Model model) {
        String query = "INSERT INTO public.model (model_brand_id, model_name, model_type, model_year, model_fuel, model_gear) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, model.getBrand_id());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().name());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().name());
            ps.setString(6, model.getGear().name());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing model in the database.
     *
     * @param model The model to be updated.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean update(Model model) {
        String query = "UPDATE public.model SET model_brand_id = ?, model_name = ?, model_type = ?, model_year = ?, model_fuel = ?, model_gear = ? WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, model.getBrand_id());
            ps.setString(2, model.getName());
            ps.setString(3, model.getType().name());
            ps.setString(4, model.getYear());
            ps.setString(5, model.getFuel().name());
            ps.setString(6, model.getGear().name());
            ps.setInt(7, model.getId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a model by its ID.
     *
     * @param id The ID of the model to be deleted.
     * @return true if the operation was successful, false otherwise.
     */
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

    /**
     * Retrieves a model by its ID.
     *
     * @param id The ID of the model.
     * @return A Model object if found, null otherwise.
     */
    public Model getById(int id) {
        Model model = null;
        String query = "SELECT * FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                model = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return model;
    }

    /**
     * Maps a ResultSet row to a Model object.
     *
     * @param rs The ResultSet object.
     * @return A Model object.
     */
    public Model match(ResultSet rs) {
        Model model = new Model();
        try {
            model.setId(rs.getInt("model_id"));
            model.setBrand_id(rs.getInt("model_brand_id"));
            model.setName(rs.getString("model_name"));
            model.setType(Model.Type.valueOf(rs.getString("model_type")));
            model.setYear(rs.getString("model_year"));
            model.setFuel(Model.Fuel.valueOf(rs.getString("model_fuel")));
            model.setGear(Model.Gear.valueOf(rs.getString("model_gear")));
            // Fetch and set the Brand object
            BrandDao brandDao = new BrandDao();
            Brand brand = brandDao.getById(rs.getInt("model_brand_id"));
            model.setBrand(brand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }
}
