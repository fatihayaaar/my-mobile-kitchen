package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IFoodManager;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.FoodRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.ImageRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ImageStream;

import java.util.ArrayList;

public class FoodManager extends BaseManager<FoodEntity> implements IFoodManager<FoodEntity> {

    private int foodID;
    private Context context;

    public FoodManager(Context context) {
        super(context);
        this.context = context;
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
        FoodEntity foodEntity = ((FoodRepository) unitOfWork.getRepository(FoodEntity.class))
                .getFoodByCategoryID(categoryID);
        if (foodEntity != null)
            return foodEntity;
        return new FoodEntity(-1);
    }

    @Override
    public boolean add(FoodEntity entity) {
        foodID = entity.getID();
        unitOfWork.getRepository(entity.getClass()).add(entity);
        uploadImages(entity.getImage());
        return true;
    }

    private void uploadImages(ImageEntity[] entity) {
        ImageStream imageStream = new ImageStream(context);
        for (int i = 0; i < entity.length; i++) {
            entity[i] = new ImageEntity();
            imageStream.setPictureBitmap(entity[i].getImage());
            imageStream.saveImage();
            entity[i].setFoodID(foodID);
            entity[i].setImageID(imageStream.getKey());
            ((ImageRepository) unitOfWork.getRepository(ImageEntity.class)).adds(entity[i]);
        }
    }
}

