package com.fayarretype.mymobilekitchen.layers.bl;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.dal.UnitOfWork;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.tools.utils.validations.EntityValidation;

import java.util.ArrayList;

public abstract class BaseManager<TEntity> implements IManager<TEntity> {

    protected UnitOfWork unitOfWork;

    public BaseManager(Context context) {
        unitOfWork = UnitOfWork.getInstance(context);
    }

    @Override
    public boolean add(TEntity entity) {
            unitOfWork.getRepository(entity.getClass()).add((BaseEntity) entity);
            return true;
    }

    @Override
    public boolean update(TEntity entity, int ID) {
        if (EntityValidation.entityValidation((CategoryEntity) entity)) {
            unitOfWork.getRepository(entity.getClass()).update((BaseEntity) entity, ID);
            return true;
        }
        return false;
    }

    @Override
    public abstract void delete(int ID);

    @Override
    public abstract TEntity getEntity(int ID);

    @Override
    public abstract ArrayList<TEntity> getEntities();
}
