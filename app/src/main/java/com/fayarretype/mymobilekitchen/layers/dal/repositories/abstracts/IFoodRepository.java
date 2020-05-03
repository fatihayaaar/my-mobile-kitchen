package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;

import java.util.ArrayList;

public interface IFoodRepository<TEntity extends BaseEntity> extends IRepository<TEntity> {

    void addWithWeb(TEntity entity);

    TEntity getFoodByCategoryID(int categoryID);
    
    ArrayList<String> getFoodNames();
}
