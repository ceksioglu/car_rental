package entity;

public class Model {
    private int id;
    private int brandId;
    private String name;
    private String year;
    private Type type;
    private Fuel fuel;
    private Gear gear;
    private Brand brand;

    public enum Fuel {
        GASOLINE,
        LPG,
        ELECTRIC,
        DIESEL
    }

    public enum Gear {
        MANUAL,
        AUTO
    }

    public enum Type {
        SEDAN,
        HATCHBACK
    }

    // Default constructor
    public Model() {
    }

    // Constructor for creating a new model
    public Model(int brandId, String name, String year, Type type, Fuel fuel, Gear gear) {
        this.brandId = brandId;
        this.name = name;
        this.year = year;
        this.type = type;
        this.fuel = fuel;
        this.gear = gear;
    }

    // Constructor for updating an existing model
    public Model(int id, int brandId, String name, String year, Type type, Fuel fuel, Gear gear) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.year = year;
        this.type = type;
        this.fuel = fuel;
        this.gear = gear;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
