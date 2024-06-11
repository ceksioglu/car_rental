package view;

import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import entity.Brand;
import entity.Car;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The AdminView class represents the admin interface of the Rent a Car Management System.
 * It displays a welcome message for the logged-in admin user and provides a tabbed interface
 * for managing different aspects of the system, such as brands and models.
 * The class extends the Layout class, which provides common GUI initialization functionality.
 */
public class AdminView extends Layout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel panel_top;
    private JTabbedPane tab_menu;
    private JButton button_logout;
    private JPanel panel_brand;
    private JScrollPane scroll_brand;
    private JTable table_brand;
    private JPanel panel_model;
    private JScrollPane scroll_model;
    private JTable table_model;
    private JComboBox<Brand> combo_filter_brand;
    private JComboBox<Model.Type> combo_filter_type;
    private JComboBox<Model.Fuel> combo_filter_fuel;
    private JComboBox<Model.Gear> combo_filter_gear;
    private User user;
    private DefaultTableModel model_brand;
    private DefaultTableModel model_model;
    private DefaultTableModel model_car;
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;
    private JPopupMenu carMenu;
    private JPanel panel_filter;
    private JLabel brandLabel;
    private JLabel fuelLabel;
    private JLabel gearLabel;
    private JLabel typeLabel;
    private JPanel panel_car;
    private JScrollPane scroll_car;
    private JTable table_car;

    /**
     * Constructs an AdminView instance with the specified user.
     * Initializes the user interface components and populates the brand table with data.
     *
     * @param user The logged-in user.
     */
    public AdminView(User user) {
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText("Welcome: " + this.user.getUsername());

        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();

        // Initialize the table model with column headers
        this.model_brand = new DefaultTableModel();
        this.model_brand.setColumnIdentifiers(new Object[]{"Brand ID", "Brand Name"});
        this.table_brand.setModel(model_brand);

        this.model_model = new DefaultTableModel();
        this.model_model.setColumnIdentifiers(new Object[]{"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"});
        this.table_model.setModel(model_model);

        this.model_car = new DefaultTableModel();
        this.model_car.setColumnIdentifiers(new Object[]{"ID","Brand","Plate","Color","KM","Year","Type","Fuel Type","Gear"});
        this.table_car.setModel(model_car);



        // Disable table header reordering
        this.table_brand.getTableHeader().setReorderingAllowed(false);
        this.table_model.getTableHeader().setReorderingAllowed(false);
        this.table_car.getTableHeader().setReorderingAllowed(false);

        loadBrandData();
        loadModelData(); // Load all models initially
        loadCarData();

        loadFilterComponents();
        loadBrandComponent();
        loadModelComponent();
        loadCarComponent();



        button_logout.addActionListener(e -> {
            // Handle logout logic here
            dispose();
        });
    }

    /**
     * Loads all model data into the table model.
     * This method fetches all model data from the ModelManager and populates the table model.
     */
    private void loadModelData() {
        Object[] col_model = {"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"};
        ArrayList<Object[]> modelList = modelManager.getForTable(col_model.length, modelManager.findAll());
        this.createTable(this.model_model, this.table_model, col_model, modelList);
    }

    /**
     * Initializes the filter components for models.
     */
    private void loadFilterComponents() {
        // Add "None" option and populate combo boxes with enum values
        combo_filter_brand.addItem(null); // Add "None" option
        for (Brand brand : brandManager.findAll()) {
            combo_filter_brand.addItem(brand);
        }

        combo_filter_type.addItem(null); // Add "None" option
        for (Model.Type type : Model.Type.values()) {
            combo_filter_type.addItem(type);
        }

        combo_filter_fuel.addItem(null); // Add "None" option
        for (Model.Fuel fuel : Model.Fuel.values()) {
            combo_filter_fuel.addItem(fuel);
        }

        combo_filter_gear.addItem(null); // Add "None" option
        for (Model.Gear gear : Model.Gear.values()) {
            combo_filter_gear.addItem(gear);
        }

        combo_filter_brand.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    setText("None");
                } else {
                    setText(((Brand) value).getName());
                }
                return this;
            }
        });

        combo_filter_type.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    setText("None");
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });

        combo_filter_fuel.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    setText("None");
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });

        combo_filter_gear.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) {
                    setText("None");
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });

        ActionListener filterActionListener = e -> filterModelData();
        combo_filter_brand.addActionListener(filterActionListener);
        combo_filter_type.addActionListener(filterActionListener);
        combo_filter_fuel.addActionListener(filterActionListener);
        combo_filter_gear.addActionListener(filterActionListener);
    }

    /**
     * Filters the model data based on selected filters.
     */
    private void filterModelData() {
        Brand selectedBrand = (Brand) combo_filter_brand.getSelectedItem();
        Model.Type selectedType = (Model.Type) combo_filter_type.getSelectedItem();
        Model.Fuel selectedFuel = (Model.Fuel) combo_filter_fuel.getSelectedItem();
        Model.Gear selectedGear = (Model.Gear) combo_filter_gear.getSelectedItem();

        ArrayList<Model> models = modelManager.findAll();
        ArrayList<Model> filteredModels = (ArrayList<Model>) models.stream()
                .filter(model -> (selectedBrand == null || model.getBrandId() == selectedBrand.getId()))
                .filter(model -> (selectedType == null || model.getType() == selectedType))
                .filter(model -> (selectedFuel == null || model.getFuel() == selectedFuel))
                .filter(model -> (selectedGear == null || model.getGear() == selectedGear))
                .collect(Collectors.toList());

        Object[] col_model = {"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"};
        ArrayList<Object[]> modelList = modelManager.getForTable(col_model.length, filteredModels);
        this.createTable(this.model_model, this.table_model, col_model, modelList);
    }

    /**
     * Initializes the brand components including the table and its context menu.
     */
    private void loadBrandComponent() {
        this.brandMenu = new JPopupMenu();

        this.table_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = table_brand.rowAtPoint(e.getPoint());
                table_brand.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.brandMenu.add("Create").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandData();
                }
            });
        });

        this.brandMenu.add("Update").addActionListener(e -> {
            int selectedRow = table_brand.getSelectedRow();
            if (selectedRow != -1) {
                int selectBrandId = getTableSelectedRow(table_brand, 0);
                BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
                brandView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadBrandData();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select a brand to update.", "Update Brand", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.brandMenu.add("Delete").addActionListener(e -> {
            int selectedRow = table_brand.getSelectedRow();
            if (selectedRow != -1) {
                int selectBrandId = getTableSelectedRow(table_brand, 0);
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this brand?", "Delete Brand", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (this.brandManager.delete(selectBrandId)) {
                        loadBrandData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a brand to delete.", "Delete Brand", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.table_brand.setComponentPopupMenu(brandMenu);
    }

    /**
     * Initializes the model components including the table and its context menu.
     */
    private void loadModelComponent() {
        this.modelMenu = new JPopupMenu();

        this.table_model.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = table_model.rowAtPoint(e.getPoint());
                table_model.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.modelMenu.add("Create").addActionListener(e -> {
            ModelView modelView = new ModelView(null);
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelData();
                }
            });
        });

        this.modelMenu.add("Update").addActionListener(e -> {
            int selectedRow = table_model.getSelectedRow();
            if (selectedRow != -1) {
                int selectModelId = getTableSelectedRow(table_model, 0);
                ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
                modelView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadModelData();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select a model to update.", "Update Model", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.modelMenu.add("Delete").addActionListener(e -> {
            int selectedRow = table_model.getSelectedRow();
            if (selectedRow != -1) {
                int selectModelId = getTableSelectedRow(table_model, 0);
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this model?", "Delete Model", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (this.modelManager.delete(selectModelId)) {
                        loadModelData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a model to delete.", "Delete Model", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.table_model.setComponentPopupMenu(modelMenu);
    }

    /**
     * Loads the brand data into the table model.
     * This method fetches the brand data from the BrandManager and populates the table model.
     */
    private void loadBrandData() {
        Object[] col_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = brandManager.getForTable(col_brand.length);
        this.createTable(this.model_brand, this.table_brand, col_brand, brandList);
    }

    private void loadCarData() {
        Object[] col_car = {"ID","Brand","Model","Plate","Color","KM","Year","Type","Fuel Type"};
        ArrayList<Object[]> carList = this.carManager.getForTable(10, this.carManager.findAll());
        createTable(this.model_car,this.table_car,col_car,carList);
    }

    private void loadCarComponent() {
        this.carMenu = new JPopupMenu();

        this.table_car.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = table_car.rowAtPoint(e.getPoint());
                table_car.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.carMenu.add("Create").addActionListener(e -> {
            CarView carView = new CarView(null);  // Pass null for new car entry
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarData();
                }
            });
        });

        this.carMenu.add("Update").addActionListener(e -> {
            int selectedRow = table_car.getSelectedRow();
            if (selectedRow != -1) {
                int selectCarId = getTableSelectedRow(table_car, 0);
                CarView carView = new CarView(this.carManager.getById(selectCarId));
                carView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadCarData();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select a car to update.", "Update Car", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.carMenu.add("Delete").addActionListener(e -> {
            int selectedRow = table_car.getSelectedRow();
            if (selectedRow != -1) {
                int selectCarId = getTableSelectedRow(table_car, 0);
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this car?", "Delete Car", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (this.carManager.delete(selectCarId)) {
                        loadCarData();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a car to delete.", "Delete Car", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.table_car.setComponentPopupMenu(carMenu);
    }

}
