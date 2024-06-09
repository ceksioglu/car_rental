package view;

import business.BrandManager;
import business.ModelManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * The AdminView class represents the admin interface of the Rent a Car Management System.
 * It displays a welcome message for the logged-in admin user and provides a tabbed interface
 * for managing different aspects of the system, such as brands.
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
    private User user;
    private DefaultTableModel model_brand;
    private DefaultTableModel model_model;
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;

    /**
     * Constructs an AdminView instance with the specified user.
     * Initializes the user interface components and populates the brand table with data.
     *
     * @param user The logged-in user.
     */
    public AdminView(User user) {
        this.add(container);
        this.guiInitialize(500, 500);
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
        loadBrandComponent();
        loadModelComponent();

        button_logout.addActionListener(e -> {
            // Handle logout logic here
            dispose();
        });
    }

    public void loadModelData() {
        Object[] col_model = {"Model ID", "Brand ID", "Name", "Year", "Type", "Fuel", "Gear", "Brand"};
        // Fetch model data
        ArrayList<Object[]> modelList = modelManager.getForTable(col_model.length, this.modelManager.findAll());

        // Create and populate the table
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
}
