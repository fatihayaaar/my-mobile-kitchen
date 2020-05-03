package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IImageRepository;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;

import java.util.ArrayList;

public class ImageRepository extends BaseRepository<ImageEntity> implements IImageRepository<ImageEntity> {

    public ImageRepository(Context context) {
        super(context);
    }

    @Override
    public ArrayList<String> getFoodByImageIDs(int foodID) {
        return null;
    }

    @Override
    public ImageEntity getEntity(int id) {
        return (ImageEntity) (databaseHelper.list(EntityName.IMAGE_ENTITY_CLASS,
                SQLiteDatabaseHelper.IMAGES_AREA_ID, String.valueOf(id)).get(0));
    }

    @Override
    public ArrayList<ImageEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(EntityName.IMAGE_ENTITY_CLASS);

        ArrayList<ImageEntity> imagesEntities = new ArrayList<>(entities.size());

        for (int i = 0; i < entities.size(); i++)
            imagesEntities.add(entities.get(i).getID(), (ImageEntity) entities.get(i));

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
