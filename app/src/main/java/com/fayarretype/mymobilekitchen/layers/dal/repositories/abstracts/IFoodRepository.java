package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import java.util.ArrayList;

public interface IFoodRepository<TEntity extends BaseEntity> extends IRepository<TEntity> {

    ArrayList<FoodEntity> getEntitiesByType(int type);

    ArrayList<BaseEntity> getFoodByCategoryID(int categoryID);
    
    ArrayList<String> getFoodNames();
}
