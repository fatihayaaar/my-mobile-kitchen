package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import java.util.ArrayList;

public interface IManager<TEntity> {

    boolean add(TEntity entity);

    boolean update(TEntity entity, String ID);

    void delete(String ID);

    TEntity getEntity(String ID);

    ArrayList<TEntity> getEntities();
}
