package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import java.util.ArrayList;

public interface IManager<TEntity> {

    boolean add(TEntity entity);

    boolean update(TEntity entity, int ID);

    void delete(String ID);

    TEntity getEntity(int ID);

    ArrayList<TEntity> getEntities();
}
