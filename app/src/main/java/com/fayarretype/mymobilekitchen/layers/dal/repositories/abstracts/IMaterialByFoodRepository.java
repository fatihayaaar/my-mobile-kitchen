package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;

import java.util.ArrayList;

public interface IMaterialByFoodRepository<TEntity extends BaseEntity> extends IRepository<TEntity> {

    ArrayList<MaterialByFoodEntity> getEntitiesByFood(String ID);
}
