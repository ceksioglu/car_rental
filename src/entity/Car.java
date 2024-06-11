package entity;

public class Car {
    private int car_id;
    private int car_model_id;
    private int car_km;
    private Car.Color color;
    private String car_plate;
    private Model carmodel;
    private Brand carbrand;

    public enum Color {
        RED,
        GREEN,
        BLUE,
        BLACK,
        WHITE
    }

    public Car() {
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(int car_model_id) {
        this.car_model_id = car_model_id;
    }

    public int getCar_km() {
        return car_km;
    }

    public void setCar_km(int car_km) {
        this.car_km = car_km;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Model getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(Model carmodel) {
        this.carmodel = carmodel;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public Brand getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(Brand carbrand) {
        this.carbrand = carbrand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_id=" + car_id +
                ", car_model_id=" + car_model_id +
                ", car_km=" + car_km +
                ", color=" + color +
                ", car_plate='" + car_plate + '\'' +
                ", carmodel=" + carmodel +
                ", carbrand=" + carbrand +
                '}';
    }
}
