package business;

import core.Helper;
import dao.ModelDao;
import entity.Model;

import java.util.ArrayList;

/**
 * The ModelManager class provides methods for managing model data.
 * It interacts with the ModelDao to perform database operations and handles business logic.
 */
public class ModelManager {
    private final ModelDao modelDao;

    /**
     * Constructs a ModelManager instance and initializes the ModelDao.
     */
    public ModelManager() {
        this.modelDao = new ModelDao();
    }

    /**
     * Retrieves all models.
     *
     * @return An ArrayList of Model objects.
     */
    public ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }

    /**
     * Retrieves all models formatted for table display.
     *
     * @param size The size of the row object array.
     * @param modelList The list of Model objects to be formatted.
     * @return An ArrayList of Object arrays representing model data.
     */
    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelRowList = new ArrayList<>();
        for (Model model : modelList) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = model.getId();
            rowObject[i++] = model.getBrand().getName(); // Set brand name for display
            rowObject[i++] = model.getName();
            rowObject[i++] = model.getType().name();
            rowObject[i++] = model.getYear();
            rowObject[i++] = model.getFuel().name();
            rowObject[i++] = model.getGear().name();
            modelRowList.add(rowObject);
        }
        return modelRowList;
    }

    /**
     * Saves a new model to the database.
     * If the model ID is not zero, it displays an error message.
     *
     * @param model The model to be saved.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean save(Model model) {
        if (model.getId() != 0) {
            Helper.showMessage("error");
            return false;
        }
        return this.modelDao.save(model);
    }

    /**
     * Retrieves a model by its ID.
     *
     * @param id The ID of the model.
     * @return A Model object if found, null otherwise.
     */
    public Model getById(int id) {
        return this.modelDao.getById(id);
    }

    /**
     * Updates an existing model in the database.
     * If the model is not found, it displays a not found message.
     *
     * @param model The model to be updated.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean update(Model model) {
        if (this.getById(model.getId()) == null) {
            Helper.showMessage("notFound");
            return false;
        }
        return this.modelDao.update(model);
    }

    /**
     * Deletes a model by its ID.
     * If the model is not found, it displays a not found message.
     *
     * @param id The ID of the model to be deleted.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " No Model with this ID.");
            return false;
        }
        return this.modelDao.delete(id);
    }
}
