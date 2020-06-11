package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IFoodManager;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.FoodRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.ImageRepository;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.MaterialByFoodRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ImageStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodManager extends BaseManager<FoodEntity> implements IFoodManager<FoodEntity> {

    private String foodID;
    private Context context;

    public FoodManager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void delete(String ID) {
        unitOfWork.getRepository(FoodEntity.class).delete(ID, FoodEntity.class);
    }

    @Override
    public FoodEntity getEntity(String ID) {
        FoodEntity entity = (FoodEntity) unitOfWork.getRepository(FoodEntity.class).getEntity(ID);
        if (entity != null)
            return entity;
        return new FoodEntity("-1");
    }

    @Override
    public ArrayList<FoodEntity> getEntities() {
        ArrayList<FoodEntity> foodEntities = ((FoodRepository) unitOfWork
                .getRepository(FoodEntity.class))
                .getEntities();

        ArrayList<FoodEntity> entities = new ArrayList<>();
        for (FoodEntity entity : foodEntities) {
            try {
                ArrayList<String> imageIds = ((ImageRepository) unitOfWork
                        .getRepository(ImageEntity.class))
                        .getFoodByImageIDs(entity.getID());
                ImageEntity entity1 = ((ImageRepository) unitOfWork
                        .getRepository(ImageEntity.class))
                        .getEntity(imageIds.get(0));

                ImageEntity[] imageEntities = new ImageEntity[1];
                imageEntities[0] = entity1;
                entity.setImage(imageEntities);
                entities.add(entity);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return entities;
    }

    @Override
    public ArrayList<BaseEntity> getFoodByCategoryID(int categoryID) {
        ArrayList<BaseEntity> foodEntity = ((FoodRepository) unitOfWork.getRepository(FoodEntity.class))
                .getFoodByCategoryID(categoryID);
        if (foodEntity != null)
            return foodEntity;
        return new ArrayList<>();
    }

    public ArrayList<FoodEntity> getEntitiesByType(int type) {
        return ((FoodRepository) unitOfWork.getRepository(FoodEntity.class)).getEntitiesByType(type);
    }

    @Override
    public boolean add(FoodEntity entity) {
        foodID = entity.getID();
        unitOfWork.getRepository(entity.getClass()).add(entity);
        addMaterials(entity.getMaterialByFoodEntities());
        uploadImages(entity.getImage());
        return true;
    }

    private void uploadImages(ImageEntity[] entity) {
        for (int i = 0; i < entity.length; i++) {
            ImageStream imageStream = new ImageStream(context);
            imageStream.setPictureBitmap(entity[i].getImage());
            imageStream.saveImage();
            entity[i].setID("i" + new SimpleDateFormat("yyddHHmmss").format(new Date()));
            entity[i].setFoodID(foodID);
            entity[i].setImageID(imageStream.getKey());
            ((ImageRepository) unitOfWork.getRepository(ImageEntity.class)).adds(entity[i]);
        }
    }

    private void addMaterials(MaterialByFoodEntity[] materialByFoodEntities) {
        try {
            for (int i = 0; i < materialByFoodEntities.length; i++) {
                materialByFoodEntities[i] = new MaterialByFoodEntity();
                materialByFoodEntities[i].setFoodID(foodID);
                ((MaterialByFoodRepository) unitOfWork.getRepository(MaterialByFoodEntity.class))
                        .adds(materialByFoodEntities[i]);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}

