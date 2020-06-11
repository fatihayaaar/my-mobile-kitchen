package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IFoodRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;

import java.util.ArrayList;

public class FoodRepository extends BaseRepository<FoodEntity> implements IFoodRepository<FoodEntity> {

    public FoodRepository(Context context) {
        super(context);
    }

    @Override
    public FoodEntity getEntity(String id) {
        return (FoodEntity) (databaseHelper.list(FoodEntity.class,
                SQLiteDatabaseHelper.FOOD_AREA_ID, String.valueOf(id))).get(0);
    }

    @Override
    public void add(FoodEntity entity) {
        databaseHelper.add(entity);
    }

    @Override
    public void delete(String id, Class entity) {
        try {
            ArrayList<BaseEntity> foodEntities = databaseHelper.list(FoodEntity.class,
                    SQLiteDatabaseHelper.FOOD_AREA_ID, id);
            for (BaseEntity baseEntity : foodEntities) {
                ImageEntity entity1 = (ImageEntity) databaseHelper.list(ImageEntity.class,
                        SQLiteDatabaseHelper.IMAGES_AREA_FOOD_ID,
                        baseEntity.getID()).get(0);
                databaseHelper.delete(entity1.getID(), ImageEntity.class);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        databaseHelper.delete(id, entity);
    }

    @Override
    public ArrayList<FoodEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(FoodEntity.class);

        ArrayList<FoodEntity> foodEntities = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            foodEntities.add((FoodEntity) entities.get(i));
        }

        return foodEntities;
    }

    @Override
    public ArrayList<FoodEntity> getEntitiesByType(int type) {
        ArrayList<BaseEntity> entities = databaseHelper.list(FoodEntity.class,
                SQLiteDatabaseHelper.FOOD_AREA_TYPE, String.valueOf(type));

        ArrayList<FoodEntity> foodEntities = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            foodEntities.add((FoodEntity) entities.get(i));

        return foodEntities;

    }

    @Override
    public ArrayList<BaseEntity> getFoodByCategoryID(int categoryID) {
        return (databaseHelper.list(FoodEntity.class,
                SQLiteDatabaseHelper.FOOD_AREA_CATEGORY_ID, String.valueOf(categoryID)));
    }

    @Override
    public ArrayList<String> getFoodNames() {
        ArrayList<BaseEntity> entities = databaseHelper.list(FoodEntity.class);

        ArrayList<String> foodNames = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            foodNames.add(((FoodEntity) entities.get(i)).getFoodName());

        return foodNames;
    }

    @Override
    public void save() {
        databaseHelper.foodSave();
    }
}
