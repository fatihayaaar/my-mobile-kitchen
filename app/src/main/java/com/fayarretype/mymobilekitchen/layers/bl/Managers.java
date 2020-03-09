package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManagers;

public class Managers implements IManagers<BaseManager> {

    private static Managers managers;
    private CategoryManager categoryManager;
    private MaterialManager materialManager;
    private FoodManager foodManager;

    private Managers(Context context) {
        ManagerContainer managerContainer = ManagerContainer.getInstance(context);

        categoryManager = (CategoryManager) managerContainer.getManager(ManagerName.CATEGORY_MANAGER);
        materialManager = (MaterialManager) managerContainer.getManager(ManagerName.MATERIAL_MANAGER);
        foodManager = (FoodManager) managerContainer.getManager(ManagerName.FOOD_MANAGER);
    }

    public static Managers getInstance(Context context) {
        if (managers == null)
            managers = new Managers(context);
        return managers;
    }

    @Override
    public BaseManager getManager(ManagerName managerName) {
        switch (managerName) {
            case CATEGORY_MANAGER:
                return categoryManager;
            case MATERIAL_MANAGER:
                return materialManager;
            case FOOD_MANAGER:
                return foodManager;
            default:
                return null;
        }
    }

    @Override
    public void saveChanges() {
        foodManager.unitOfWork.complete();
    }
}
