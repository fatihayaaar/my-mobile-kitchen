package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IRepository;

public class RepositoryContainer {

    private static RepositoryContainer repositoryContainer;
    private Context context;

    private RepositoryContainer(Context context) {
        this.context = context;
    }

    public static RepositoryContainer getInstance(Context context) {
        if (repositoryContainer == null)
            repositoryContainer = new RepositoryContainer(context);
        return repositoryContainer;
    }

    public IRepository getRepository(RepositoryName repositoryName) {
        switch (repositoryName) {
            case CATEGORY:
                return new CategoryRepository(context);
            case MATERIAL:
                return new MaterialRepository(context);
            case FOOD:
                return new FoodRepository(context);
            case IMAGE:
                return new ImageRepository(context);
            case MATERIAL_BY_FOOD:
                return new MaterialByFoodRepository(context);
            default:
                return null;
        }
    }
}
