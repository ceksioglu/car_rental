package view;

import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.Helper;
import entity.Brand;
import entity.Car;
import entity.Model;
import entity.Brand;

import javax.swing.*;

public class CarView extends Layout{
    private JPanel container;
    private JLabel label_head;
    private JLabel label_brand;
    private JComboBox combo_brand;
    private JLabel label_model;
    private JComboBox combo_model;
    private JLabel label_color;
    private JComboBox combo_color;
    private JTextField field_km;
    private JLabel label_km;
    private JTextField field_plate;
    private JButton button_car_save;
    private Car car;
    private CarManager carManager;
    private ModelManager modelManager;
    private BrandManager brandManager;

    public CarView(Car car) {
        this.car = car;
        this.carManager = new CarManager();
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(600,600);

        this.combo_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));

        this.combo_brand.setModel(new DefaultComboBoxModel<>(this.brandManager.findAll().toArray(new Brand[0])));


        for (Model model : this.modelManager.findAll()){
            this.combo_model.addItem(model.getComboItem());
        }

        if (this.car.getCar_id() != 0){
            Model.ComboItem selectedItem = car.getCarmodel().getComboItem();
            this.combo_model.getModel().setSelectedItem(selectedItem);
            this.combo_color.getModel().setSelectedItem(car.getColor());
            this.field_plate.setText(car.getCar_plate());
            this.field_km.setText(Integer.toString(car.getCar_km()));
        }

        this.button_car_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{field_km,field_plate})) {
                Helper.showMessage("fill");
            } else {
                boolean result;
                Model.ComboItem selectedModel = (Model.ComboItem) this.combo_model.getSelectedItem();
                this.car.setCar_model_id(selectedModel.getKey());
                this.car.setColor((Car.Color) combo_color.getSelectedItem());
                this.car.setCar_plate(this.field_plate.getText());
                this.car.setCar_km(Integer.parseInt(this.field_km.getText()));
                if (this.car.getCar_id() != 0){
                    result = this.carManager.update(this.car);
                } else {
                    result = this.carManager.save(this.car);
                }
                if (result) {
                    Helper.showMessage("done");
                    dispose();
                    } else {
                    Helper.showMessage("error");
                }
            }
        });




    }
}
