package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;

public class BrandView extends Layout {
    private JPanel container;
    private JLabel label_brand;
    private JLabel label_brand_name;
    private JTextField field_brand_name;
    private JButton button_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BrandView(Brand brand){
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(400, 500);
        this.brand = brand;

        if(brand != null){
            this.field_brand_name.setText(brand.getName());
        }



        button_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.field_brand_name)){
                Helper.showMessage("fill");
            } else {
                boolean result = true;
                if (this.brand == null) {
                    result = this.brandManager.save(new Brand(field_brand_name.getText()));
                }else{
                    this.brand.setName(field_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }
                if (result){
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}
