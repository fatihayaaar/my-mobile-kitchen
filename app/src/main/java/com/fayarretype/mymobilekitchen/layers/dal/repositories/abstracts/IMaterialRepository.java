package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public interface IMaterialRepository<TEntity extends BaseEntity> extends IRepository<MaterialEntity> {

    ArrayList<String> getMaterialNames();

    TEntity getEntitiesByMaterialName(String materialName);
}
