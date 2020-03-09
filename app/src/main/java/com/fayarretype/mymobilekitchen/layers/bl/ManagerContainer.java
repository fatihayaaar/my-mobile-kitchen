package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

public class ManagerContainer {

    private static ManagerContainer managerContainer;
    private Context context;

    private ManagerContainer(Context context) {
        this.context = context;
    }

    public static ManagerContainer getInstance(Context context) {
        if (managerContainer == null)
            managerContainer = new ManagerContainer(context);
        return managerContainer;
    }

    public BaseManager getManager(ManagerName managerName) {
        switch (managerName) {
            case CATEGORY_MANAGER:
                return new CategoryManager(context);
            case MATERIAL_MANAGER:
                return new MaterialManager(context);
            case FOOD_MANAGER:
                return new FoodManager(context);
            default:
                return null;
        }
    }
}
