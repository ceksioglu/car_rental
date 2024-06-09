package business;

import core.Helper;
import dao.BrandDao;
import entity.Brand;

import java.util.ArrayList;

/**
 * The BrandManager class provides methods for managing brand data.
 * It interacts with the BrandDao to perform database operations and handles business logic.
 */
public class BrandManager {
    private final BrandDao brandDao;

    /**
     * Constructs a BrandManager instance and initializes the BrandDao.
     */
    public BrandManager() {
        this.brandDao = new BrandDao();
    }

    /**
     * Retrieves all brands.
     *
     * @return An ArrayList of Brand objects.
     */
    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    /**
     * Retrieves all brands formatted for table display.
     *
     * @param size The size of the row object array.
     * @return An ArrayList of Object arrays representing brand data.
     */
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);
        }
        return brandRowList;
    }

    /**
     * Saves a new brand to the database.
     * If the brand ID is not zero, it displays an error message.
     *
     * @param brand The brand to be saved.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Helper.showMessage("error");
            return false;
        }
        return this.brandDao.save(brand);
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id The ID of the brand.
     * @return A Brand object if found, null otherwise.
     */
    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }

    /**
     * Updates an existing brand in the database.
     * If the brand is not found, it displays a not found message.
     *
     * @param brand The brand to be updated.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean update(Brand brand) {
        if (this.getById(brand.getId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.brandDao.update(brand);
    }

    /**
     * Deletes a brand by its ID.
     * If the brand is not found, it displays a not found message.
     *
     * @param id The ID of the brand to be deleted.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " No Brand with this ID.");
            return false;
        }
        return this.brandDao.delete(id);
    }
}
