package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import java.util.ArrayList;

public interface IFoodManager<TEntity> extends IManager<TEntity> {

    ArrayList<FoodEntity> getFoodByCategoryID(int categoryID);
}
