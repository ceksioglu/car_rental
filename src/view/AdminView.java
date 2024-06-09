package view;

import business.BrandManager;
import business.ModelManager;
import entity.Brand;
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
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;
    private JPanel panel_filter;
    private JLabel brandLabel;
    private JLabel fuelLabel;
    private JLabel gearLabel;
    private JLabel typeLabel;

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

        // Initialize the table model with column headers
        this.model_brand = new DefaultTableModel();
        this.model_brand.setColumnIdentifiers(new Object[]{"Brand ID", "Brand Name"});
        this.table_brand.setModel(model_brand);

        this.model_model = new DefaultTableModel();
        this.model_model.setColumnIdentifiers(new Object[]{"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"});
        this.table_model.setModel(model_model);

        // Disable table header reordering
        this.table_brand.getTableHeader().setReorderingAllowed(false);
        this.table_model.getTableHeader().setReorderingAllowed(false);

        loadBrandData();
        loadModelData();
        loadFilterComponents();
        loadBrandComponent();
        loadModelComponent();

        button_logout.addActionListener(e -> {
            // Handle logout logic here
            dispose();
        });
    }

    public void loadModelTable() {
        Object[] col_model = {"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"};
        // Fetch model data
        ArrayList<Object[]> modelList = modelManager.getForTable(col_model.length, modelManager.findAll());

        // Create and populate the table
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
        // Fetch brand data
        ArrayList<Object[]> brandList = brandManager.getForTable(col_brand.length);

        // Create and populate the table
        this.createTable(this.model_brand, this.table_brand, col_brand, brandList);
    }

    /**
     * Loads the model data into the table model.
     * This method fetches the model data from the ModelManager and populates the table model.
     */
    private void loadModelData() {
        filterModelData();
    }
}
