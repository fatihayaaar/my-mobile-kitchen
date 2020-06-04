package com.fayarretype.mymobilekitchen.layers.dal.repositories;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.dal.databasehelper.SQLiteDatabaseHelper;
import com.fayarretype.mymobilekitchen.layers.dal.repositories.abstracts.IRepository;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;

import java.util.ArrayList;

public abstract class BaseRepository<TEntity extends BaseEntity> implements IRepository<TEntity> {

    protected SQLiteDatabaseHelper databaseHelper;
    protected Context context;

    public BaseRepository(Context context) {
        databaseHelper = SQLiteDatabaseHelper.getInstance(context);
        this.context = context;
    }

    @Override
    public void add(TEntity entity) {
        databaseHelper.add(entity);
    }

    @Override
    public void delete(String id, Class entity) {
        databaseHelper.delete(id, entity);
    }

    @Override
    public void update(TEntity entity, String id) {
        databaseHelper.update(entity, id);
    }

    @Override
    public abstract TEntity getEntity(String id);

    @Override
    public abstract ArrayList<TEntity> getEntities();

    @Override
    public abstract void save();
}
