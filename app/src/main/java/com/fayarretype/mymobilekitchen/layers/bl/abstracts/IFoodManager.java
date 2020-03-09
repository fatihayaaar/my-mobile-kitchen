package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

public interface IFoodManager<TEntity> extends IManager<TEntity> {

    TEntity getFoodByCategoryID(int categoryID);
}
