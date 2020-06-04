package com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;

import java.util.ArrayList;

public interface IRepository<TEntity extends BaseEntity> {
         
    void add(TEntity entity);

    void delete(String id, Class entityClass);

    void update(TEntity entity, String id);
    
    TEntity getEntity(String id);
    
    ArrayList<TEntity> getEntities();

    void save();
}
