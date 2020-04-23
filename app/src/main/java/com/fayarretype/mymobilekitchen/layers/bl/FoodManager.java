package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IFoodManager;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.FoodRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import java.util.ArrayList;

public class FoodManager extends BaseManager<FoodEntity> implements IFoodManager<FoodEntity> {

    public FoodManager(Context context) {
        super(context);
    }

    @Override
    public void delete(int ID) {
        unitOfWork.getRepository(FoodEntity.class).delete(ID, FoodEntity.class);
    }

    @Override
    public FoodEntity getEntity(int ID) {
        FoodEntity entity = (FoodEntity) unitOfWork.getRepository(FoodEntity.class).getEntity(ID);
        if (entity != null)
            return entity;
        return new FoodEntity(-1);
    }

    @Override
    public ArrayList<FoodEntity> getEntities() {
        return unitOfWork.getRepository(FoodEntity.class).getEntities();
    }

    @Override
    public FoodEntity getFoodByCategoryID(int categoryID) {
        FoodEntity foodEntity = ((FoodRepository) unitOfWork.getRepository(FoodEntity.class)).getFoodByCategoryID(categoryID);
        if (foodEntity != null)
            return foodEntity;
        return new FoodEntity(-1);
    }

    @Override
    public void uploadImages() {

    }
}
