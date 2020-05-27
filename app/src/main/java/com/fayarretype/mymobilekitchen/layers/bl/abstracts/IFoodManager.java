package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;

import java.util.ArrayList;

public interface IFoodManager<TEntity> extends IManager<TEntity> {

    ArrayList<BaseEntity> getFoodByCategoryID(int categoryID);
}
