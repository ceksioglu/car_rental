package business;

import core.Helper;
import dao.CarDao;
import entity.Car;

import java.util.ArrayList;

public class CarManager {
    private final CarDao carDao;

    public CarManager() {
        this.carDao = new CarDao();
    }

    public Car getById(int id) {
        return this.carDao.getById(id);
    }

    public ArrayList<Car> findAll() {
        return this.carDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> cars) {
        ArrayList<Object[]> carList = new ArrayList<>();
        for (Car obj : cars) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getCar_id();
            if (obj.getCarmodel() != null && obj.getCarmodel().getBrand() != null) {
                rowObject[i++] = obj.getCarmodel().getBrand().getName();
                rowObject[i++] = obj.getCarmodel().getName();
                rowObject[i++] = obj.getCar_plate();
                rowObject[i++] = obj.getColor();
                rowObject[i++] = obj.getCar_km();
                rowObject[i++] = obj.getCarmodel().getYear();
                rowObject[i++] = obj.getCarmodel().getType();
                rowObject[i++] = obj.getCarmodel().getFuel();
                rowObject[i++] = obj.getCarmodel().getGear();
            } else {
                rowObject[i++] = "N/A";
                rowObject[i++] = "N/A";
                rowObject[i++] = obj.getCar_plate();
                rowObject[i++] = obj.getColor();
                rowObject[i++] = obj.getCar_km();
                rowObject[i++] = "N/A";
                rowObject[i++] = "N/A";
                rowObject[i++] = "N/A";
                rowObject[i++] = "N/A";
            }
            carList.add(rowObject);
        }
        return carList;
    }

    public boolean save(Car car) {
        if (this.getById(car.getCar_id()) != null) {
            Helper.showMessage("error");
            return false;
        }
        return this.carDao.save(car);
    }

    public boolean update(Car car) {
        if (this.getById(car.getCar_id()) == null) {
            Helper.showMessage(car.getCar_id() + " ID not found!");
            return false;
        }
        return this.carDao.update(car);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " ID not found!");
            return false;
        }
        return this.carDao.delete(id);
    }
}
