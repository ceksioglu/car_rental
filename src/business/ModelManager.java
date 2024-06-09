package business;

import core.Helper;
import dao.ModelDao;
import entity.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao;

    public ModelManager() {
        this.modelDao = new ModelDao();
    }

    public ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelRowList = new ArrayList<>();
        for (Model model : modelList) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = model.getId();
            rowObject[i++] = model.getBrandId();
            rowObject[i++] = model.getName();
            rowObject[i++] = model.getYear();
            rowObject[i++] = model.getType();
            rowObject[i++] = model.getFuel();
            rowObject[i++] = model.getGear();
            rowObject[i++] = model.getBrand().getName();
            modelRowList.add(rowObject);
        }
        return modelRowList;
    }

    public boolean save(Model model) {
        if (model.getId() != 0) {
            Helper.showMessage("error");
        }
        return this.modelDao.save(model);
    }

    public Model getById(int id) {
        return this.modelDao.getById(id);
    }

    public boolean update(Model model) {
        if (this.getById(model.getId()) == null) {
            Helper.showMessage("notFound");
        }
        return this.modelDao.update(model);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " No item with this ID.");
            return false;
        }
        return this.modelDao.delete(id);
    }
}
