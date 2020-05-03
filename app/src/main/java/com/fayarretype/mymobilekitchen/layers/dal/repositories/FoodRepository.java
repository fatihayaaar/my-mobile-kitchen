package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IFoodRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import java.util.ArrayList;

public class FoodRepository extends BaseRepository<FoodEntity> implements IFoodRepository<FoodEntity> {

    public FoodRepository(Context context) {
        super(context);
    }

    @Override
    public FoodEntity getEntity(int id) {
        return (FoodEntity) (databaseHelper.list(FoodEntity.class,
                SQLiteDatabaseHelper.FOOD_AREA_ID, String.valueOf(id))).get(0);
    }

    @Override
    public void add(FoodEntity entity) {
        entity.setType(FoodEntity.USER_FOOD);
        databaseHelper.add(entity);
    }

    @Override
    public void addWithWeb(FoodEntity entity) {
        entity.setType(FoodEntity.INTERNET_FOOD);
        databaseHelper.add(entity);
    }

    @Override
    public ArrayList<FoodEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(FoodEntity.class);

        ArrayList<FoodEntity> foodEntities = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            foodEntities.add(entities.get(i).getID(), (FoodEntity) entities.get(i));

        return foodEntities;
    }

    @Override
    public FoodEntity getFoodByCategoryID(int categoryID) {
        return (FoodEntity) (databaseHelper.list(FoodEntity.class,
                SQLiteDatabaseHelper.FOOD_AREA_CATEGORY_ID, String.valueOf(categoryID)).get(0));
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
