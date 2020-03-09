package com.fayarretype.mymobilekitchen.layers.dal;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.BaseRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.CategoryRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.EntityName;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.FoodRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.MaterialRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.RepositoryContainer;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.RepositoryName;

public final class UnitOfWork implements IUnitOfWork {

    private static UnitOfWork unitOfWork;
    private Context context;
    private CategoryRepository categoryRepository;
    private MaterialRepository materialRepository;
    private FoodRepository foodRepository;

    private UnitOfWork(Context context) {
        RepositoryContainer repositoryContainer = RepositoryContainer.getInstance(context);

        categoryRepository = (CategoryRepository) repositoryContainer.getRepository(RepositoryName.CATEGORY);
        materialRepository = (MaterialRepository) repositoryContainer.getRepository(RepositoryName.MATERIAL);
        foodRepository = (FoodRepository) repositoryContainer.getRepository(RepositoryName.FOOD);

        this.context = context;
    }

    public static UnitOfWork getInstance(Context context) {
        if (unitOfWork == null)
            unitOfWork = new UnitOfWork(context);
        return unitOfWork;
    }

    @Override
    public BaseRepository getRepository(Class entityClass) {
        if (entityClass.getName().equals(EntityName.CATEGORY_ENTITY_CLASS.getName()))
            return categoryRepository;
        else if (entityClass.getName().equals(EntityName.MATERIAL_ENTITY_CLASS.getName()))
            return materialRepository;
        else if (entityClass.getName().equals(EntityName.FOOD_ENTITY_CLASS.getName()))
            return foodRepository;
        else
            return null;
    }

    @Override
    public void complete() {
        categoryRepository.save();
        materialRepository.save();
        foodRepository.save();

        SQLiteDatabaseHelper.getInstance(context).finish();
    }
}
