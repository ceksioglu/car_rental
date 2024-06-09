package dao;

import core.Database;
import entity.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The BrandDao class provides methods for interacting with the brand data in the database.
 */
public class BrandDao {
    private final Connection connection;

    /**
     * Constructs a BrandDao instance and initializes the database connection.
     */
    public BrandDao() {
        this.connection = Database.getInstance();
    }

    /**
     * Retrieves all brands from the database.
     *
     * @return An ArrayList of Brand objects.
     */
    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandArrayList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                brandArrayList.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return brandArrayList;
    }

    /**
     * Saves a new brand to the database.
     *
     * @param brand The brand to be saved.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean save(Brand brand) {
        String query = "INSERT INTO public.brand (brand_name) VALUES (?) ";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, brand.getName());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing brand in the database.
     *
     * @param brand The brand to be updated.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean update(Brand brand) {
        String query = "UPDATE public.brand SET brand_name = ? WHERE brand_id = ? ";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, brand.getName());
            ps.setInt(2, brand.getId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id The ID of the brand.
     * @return A Brand object if found, null otherwise.
     */
    public Brand getById(int id) {
        Brand obj = null;
        String query = "SELECT * FROM public.brand WHERE brand_id = ? ";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return obj;
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param id The ID of the brand to be deleted.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean delete(int id) {
        String query = "DELETE FROM public.brand WHERE brand_id = ? ";
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
     * Maps a ResultSet row to a Brand object.
     *
     * @param rs The ResultSet object.
     * @return A Brand object.
     */
    public Brand match(ResultSet rs) {
        Brand obj = new Brand();
        try {
            obj.setId(rs.getInt("brand_id"));
            obj.setName(rs.getString("brand_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
