package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IMaterialByFoodRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;

import java.util.ArrayList;

public class MaterialByFoodRepository extends BaseRepository<MaterialByFoodEntity>
        implements IMaterialByFoodRepository<MaterialByFoodEntity> {

    public MaterialByFoodRepository(Context context) {
        super(context);
    }

    @Override
    public MaterialByFoodEntity getEntity(String id) {
        return null;
    }

    @Override
    public ArrayList<MaterialByFoodEntity> getEntitiesByFood(String ID) {
        ArrayList<BaseEntity> entities = databaseHelper.list(EntityName.MATERIAL_BY_FOOD_CLASS, SQLiteDatabaseHelper.MATERIAL_BY_FOOD_FOOD_ID, ID);

        ArrayList<MaterialByFoodEntity> materialByFoodEntities = new ArrayList<>(entities.size());

        for (int i = 0; i < entities.size(); i++)
            materialByFoodEntities.add((MaterialByFoodEntity) entities.get(i));

        return materialByFoodEntities;
    }

    @Override
    public ArrayList<MaterialByFoodEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(EntityName.MATERIAL_BY_FOOD_CLASS);

        ArrayList<MaterialByFoodEntity> materialByFoodEntities = new ArrayList<>(entities.size());

        for (int i = 0; i < entities.size(); i++)
            materialByFoodEntities.add((MaterialByFoodEntity) entities.get(i));

        return materialByFoodEntities;
    }

    public void adds(MaterialByFoodEntity entity) {
        databaseHelper.add(entity);
    }

    @Override
    public void save() {
        databaseHelper.imageSave();
    }
}
