package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.XMLPullParserHandler;
import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IMaterialRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialRepository extends BaseRepository<MaterialEntity> implements IMaterialRepository<MaterialEntity> {

    public MaterialRepository(Context context) {
        super(context);
    }

    @Override
    public MaterialEntity getEntity(int id) {
        return (MaterialEntity) (databaseHelper.list(MaterialEntity.class,
                SQLiteDatabaseHelper.MATERIAL_AREA_ID, String.valueOf(id)).get(0));
    }

    @Override
    public ArrayList<MaterialEntity> getEntities() {
        ArrayList<BaseEntity> entities = databaseHelper.list(MaterialEntity.class);

        ArrayList<MaterialEntity> materialEntities = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            materialEntities.add((MaterialEntity) entities.get(i));

        return materialEntities;
    }

    @Override
    public ArrayList<String> getMaterialNames() {
        ArrayList<MaterialEntity> entities = XMLPullParserHandler.getInstance(context).getMaterialEntities();

        ArrayList<String> materialNames = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++)
            materialNames.add(entities.get(i).getMaterialName());

        return materialNames;
    }

    @Override
    public void save() {
        databaseHelper.materialSave();
    }
}
