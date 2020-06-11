package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;

import java.util.ArrayList;

public interface IImageRepository<TEntity extends BaseEntity> extends IRepository<TEntity> {

    ArrayList<String> getFoodByImageIDs(String foodID);
}
