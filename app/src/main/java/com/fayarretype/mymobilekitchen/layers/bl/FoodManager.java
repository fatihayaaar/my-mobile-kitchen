package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

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

        return getFoodEntities(foodEntities);
    }

    private ArrayList<FoodEntity> getFoodEntities(ArrayList<FoodEntity> foodEntities) {
        ImageStream imgStream = new ImageStream(context);
        ArrayList<FoodEntity> entities = new ArrayList<>();
        for (FoodEntity entity : foodEntities) {
            try {
                ImageEntity[] imageEntities;
                ArrayList<String> imageIds = ((ImageRepository) unitOfWork
                        .getRepository(ImageEntity.class))
                        .getFoodByImageIDs(entity.getID());
                ImageEntity entity1 = ((ImageRepository) unitOfWork
                        .getRepository(ImageEntity.class))
                        .getEntity(imageIds.get(0));
                imageEntities = new ImageEntity[5];
                imageEntities[0] = entity1;
                Bitmap image = imgStream.getImageJPG(imageEntities[0].getImageID());
                imageEntities[0].setImage(getResizedBitmap(image, 100, 100));
                try {
                    for (int i = 1; i < imageEntities.length; i++) {
                        ImageEntity entityLoc = ((ImageRepository) unitOfWork
                                .getRepository(ImageEntity.class))
                                .getEntity(imageIds.get(i));
                        imageEntities[i] = entityLoc;
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
                entity.setImage(imageEntities);
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                entities.add(entity);
            }
        }
        return entities;
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    @Override
    public ArrayList<FoodEntity> getFoodByCategoryID(int categoryID) {
        ArrayList<BaseEntity> foodEntity = ((FoodRepository) unitOfWork.getRepository(FoodEntity.class))
                .getFoodByCategoryID(categoryID);

        ArrayList<FoodEntity> entities = new ArrayList<>();
        for (BaseEntity entity : foodEntity) {
            entities.add((FoodEntity) entity);
        }
        if (foodEntity != null)
            return getFoodEntities(entities);
        return new ArrayList<>();
    }

    public ArrayList<FoodEntity> getEntitiesByType(int type) {
        return getFoodEntities(((FoodRepository) unitOfWork.getRepository(FoodEntity.class)).getEntitiesByType(type));
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
            int rnd = (int) (Math.random() * 999);
            entity[i].setID("i" + new SimpleDateFormat("yyddHHmmss").format(new Date()) + rnd);
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

