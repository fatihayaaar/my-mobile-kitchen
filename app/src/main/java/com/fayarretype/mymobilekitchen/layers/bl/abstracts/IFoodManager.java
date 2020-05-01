package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;

public interface IFoodManager<TEntity> extends IManager<TEntity> {

    TEntity getFoodByCategoryID(int categoryID);
}
