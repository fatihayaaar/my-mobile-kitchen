package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;
import android.util.Log;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IImageRepository;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ImageStream;

import java.util.ArrayList;

public class ImageRepository extends BaseRepository<ImageEntity> implements IImageRepository<ImageEntity> {

    public ImageRepository(Context context) {
        super(context);
    }

    @Override
    public ImageEntity getEntity(int id) {
        return null;
    }

    @Override
    public ArrayList<String> getFoodByImageIDs(int foodID) {
        return null;
    }

    public ImageEntity[] getEntity(String id) {
        ArrayList<BaseEntity> entities = databaseHelper.list(EntityName.IMAGE_ENTITY_CLASS,
                SQLiteDatabaseHelper.IMAGES_AREA_FOOD_ID, id);
        ImageEntity[] imageEntity = new ImageEntity[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            imageEntity[i] = (ImageEntity) entities.get(i);
            ImageStream imgStream = new ImageStream(context);
            imageEntity[i].setImage(imgStream.getBitmap(((ImageEntity) entities.get(i)).getImageID()));
        }
        return imageEntity;
    }

    @Override
    public ArrayList<ImageEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(EntityName.IMAGE_ENTITY_CLASS);

        ArrayList<ImageEntity> imagesEntities = new ArrayList<>(entities.size());

        for (int i = 0; i < entities.size(); i++)
            imagesEntities.add((ImageEntity) entities.get(i));

        return imagesEntities;
    }

    public void adds(ImageEntity entity) {
        databaseHelper.add(entity);
    }

    @Override
    public void save() {
        databaseHelper.imageSave();
    }
}
