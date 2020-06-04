package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.ICategoryRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;

import java.util.ArrayList;

public class CategoryRepository extends BaseRepository<CategoryEntity> implements ICategoryRepository<CategoryEntity> {

    public CategoryRepository(Context context) {
        super(context);
    }

    @Override
    public CategoryEntity getEntity(String id) {
        return (CategoryEntity) (databaseHelper.list(CategoryEntity.class,
                SQLiteDatabaseHelper.CATEGORY_AREA_ID, String.valueOf(id)).get(0));
    }

    @Override
    public ArrayList<CategoryEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(CategoryEntity.class);

        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            categoryEntities.add((CategoryEntity) entities.get(i));

        return categoryEntities;
    }

    @Override
    public ArrayList<String> getCategoryNames() {
        ArrayList<BaseEntity> entities = databaseHelper.list(CategoryEntity.class);

        ArrayList<String> categoryNames = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            categoryNames.add(((CategoryEntity) entities.get(i)).getCategoryName());

        return categoryNames;
    }

    @Override
    public void save() {
        databaseHelper.categorySave();
    }
}
