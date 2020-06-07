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
    public void delete(String ID) {
        unitOfWork.getRepository(MaterialEntity.class).delete(ID, MaterialEntity.class);
    }

    @Override
    public MaterialEntity getEntity(String ID) {
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

    public void increaseCount(String id) {
        MaterialEntity materialEntity = (MaterialEntity) unitOfWork.getRepository(MaterialEntity.class).getEntity(id);
        materialEntity.setMaterialCount(String.valueOf(Integer.valueOf(materialEntity.getMaterialCount()) + 1));
        unitOfWork.getRepository(MaterialEntity.class).update(materialEntity, id);
    }

    public void decreaseCount(String id) {
        MaterialEntity materialEntity = (MaterialEntity) unitOfWork.getRepository(MaterialEntity.class).getEntity(id);
        if (Integer.valueOf(materialEntity.getMaterialCount()) <= 1) {
            unitOfWork.getRepository(MaterialEntity.class).delete(id, MaterialEntity.class);
        } else {
            materialEntity.setMaterialCount(String.valueOf(Integer.valueOf(materialEntity.getMaterialCount()) - 1));
            unitOfWork.getRepository(MaterialEntity.class).update(materialEntity, id);
        }
    }
}
