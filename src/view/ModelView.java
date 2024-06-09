package view;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entity.Brand;
import entity.Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ModelView class represents the interface for adding and editing car models.
 * It provides fields for entering model details and a combo box for selecting the associated brand.
 */
public class ModelView extends Layout {
    private JPanel container;
    private JLabel label_model;
    private JLabel label_model_name;
    private JTextField field_model_name;
    private JLabel label_model_year;
    private JTextField field_model_year;
    private JLabel label_model_type;
    private JComboBox<Model.Type> combo_model_type;
    private JLabel label_model_fuel;
    private JComboBox<Model.Fuel> combo_model_fuel;
    private JLabel label_model_gear;
    private JComboBox<Model.Gear> combo_model_gear;
    private JLabel label_model_brand;
    private JComboBox<Brand> combo_model_brand;
    private JButton button_model_save;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;

    /**
     * Constructs a ModelView instance for adding or editing a car model.
     *
     * @param model The model to be edited, or null for adding a new model.
     */
    public ModelView(Model model) {
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(400, 500);
        this.model = model;

        // Populate combo boxes with enum values
        for (Model.Type type : Model.Type.values()) {
            combo_model_type.addItem(type);
        }

        for (Model.Fuel fuel : Model.Fuel.values()) {
            combo_model_fuel.addItem(fuel);
        }

        for (Model.Gear gear : Model.Gear.values()) {
            combo_model_gear.addItem(gear);
        }

        // Populate the brand combo box with available brands, sorted alphabetically
        ArrayList<Brand> brands = brandManager.findAll();
        Collections.sort(brands, Comparator.comparing(Brand::getName));
        for (Brand brand : brands) {
            combo_model_brand.addItem(brand);
        }

        if (this.model != null) {
            // Set fields with existing model data for editing
            field_model_name.setText(this.model.getName());
            field_model_year.setText(this.model.getYear());
            combo_model_type.setSelectedItem(this.model.getType());
            combo_model_fuel.setSelectedItem(this.model.getFuel());
            combo_model_gear.setSelectedItem(this.model.getGear());
            combo_model_brand.setSelectedItem(this.model.getBrand());
        }

        button_model_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.field_model_name) || Helper.isFieldEmpty(this.field_model_year)) {
                Helper.showMessage("fill");
            } else {
                Brand brand = (Brand) combo_model_brand.getSelectedItem();
                Model.Type type = (Model.Type) combo_model_type.getSelectedItem();
                Model.Fuel fuel = (Model.Fuel) combo_model_fuel.getSelectedItem();
                Model.Gear gear = (Model.Gear) combo_model_gear.getSelectedItem();

                boolean result = true;
                if (this.model == null) {
                    result = modelManager.save(new Model(field_model_name.getText(), field_model_year.getText(), type, fuel, gear, brand));
                } else {
                    model.setName(field_model_name.getText());
                    model.setYear(field_model_year.getText());
                    model.setType(type);
                    model.setFuel(fuel);
                    model.setGear(gear);
                    model.setBrand(brand);
                    result = modelManager.update(model);
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
