package dao;

import core.Database;
import entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDao {
    private final Connection connection;
    private final BrandDao brandDao;
    private final ModelDao modelDao;

    public CarDao() {
        this.connection = Database.getInstance();
        this.brandDao = new BrandDao();
        this.modelDao = new ModelDao();
    }

    public Car getById(int id) {
        Car obj = null;
        String query = "SELECT * FROM public.car WHERE car_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Car> selectByQuery(String query) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                cars.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public ArrayList<Car> findAll() {
        return this.selectByQuery("SELECT * FROM public.car ORDER BY car_id ASC");
    }

    public Car match(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setCar_id(rs.getInt("car_id"));
        car.setCar_model_id(rs.getInt("car_model_id"));
        car.setCar_plate(rs.getString("car_plate"));
        car.setColor(Car.Color.valueOf(rs.getString("car_color")));
        car.setCar_km(rs.getInt("car_km"));
        car.setCarmodel(this.modelDao.getById(car.getCar_model_id()));
        return car;
    }

    public boolean update(Car car) {
        String query = "UPDATE public.car SET car_model_id = ?, car_color = ?, car_km = ?, car_plate = ? WHERE car_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, car.getCar_model_id());
            ps.setString(2, car.getColor().toString());
            ps.setInt(3, car.getCar_km());
            ps.setString(4, car.getCar_plate());
            ps.setInt(5, car.getCar_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(Car car) {
        String query = "INSERT INTO public.car (car_model_id, car_color, car_km, car_plate) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, car.getCar_model_id());
            ps.setString(2, car.getColor().toString());
            ps.setInt(3, car.getCar_km());
            ps.setString(4, car.getCar_plate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.car WHERE car_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
