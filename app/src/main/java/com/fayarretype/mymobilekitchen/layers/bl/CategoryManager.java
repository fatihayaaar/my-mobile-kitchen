package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.ICategoryManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;

import java.util.ArrayList;

public class CategoryManager extends BaseManager<CategoryEntity> implements ICategoryManager<CategoryEntity> {

    public CategoryManager(Context context) {
        super(context);
    }

    @Override
    public void delete(String ID) {
        unitOfWork.getRepository(CategoryEntity.class).delete(ID, CategoryEntity.class);
    }

    @Override
    public CategoryEntity getEntity(int ID) {
        CategoryEntity entity = (CategoryEntity) unitOfWork.getRepository(CategoryEntity.class).getEntity(ID);
        if (entity != null)
            return entity;
        return new CategoryEntity("-1");
    }

    @Override
    public ArrayList<CategoryEntity> getEntities() {
        return unitOfWork.getRepository(CategoryEntity.class).getEntities();
    }
}
