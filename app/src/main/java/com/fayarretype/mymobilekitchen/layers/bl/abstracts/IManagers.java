package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;

public interface IManagers<TEntity> {

    TEntity getManager(ManagerName managerName);

    void saveChanges();
}
