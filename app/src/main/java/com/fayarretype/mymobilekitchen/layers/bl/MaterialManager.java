package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IMaterialManager;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.MaterialRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialManager extends BaseManager<MaterialEntity> implements IMaterialManager<MaterialEntity> {

    public MaterialManager(Context context) {
        super(context);
    }

    @Override
    public void delete(int ID) {
        unitOfWork.getRepository(MaterialEntity.class).delete(ID, MaterialEntity.class);
    }

    @Override
    public MaterialEntity getEntity(int ID) {
        MaterialEntity entity = (MaterialEntity) unitOfWork.getRepository(MaterialEntity.class).getEntity(ID);
        if (entity != null)
            return entity;
        return new MaterialEntity("-1");
    }

    @Override
    public ArrayList<MaterialEntity> getEntities() {
        return unitOfWork.getRepository(MaterialEntity.class).getEntities();
    }

    @Override
    public ArrayList<String> getNames() {
        return ((MaterialRepository) unitOfWork.getRepository(MaterialEntity.class)).getMaterialNames();
    }
}
