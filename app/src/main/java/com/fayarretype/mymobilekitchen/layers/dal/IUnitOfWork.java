package com.fayarretype.mymobilekitchen.layers.dal;

import com.fayarretype.mymobilekitchen.layers.dal.repositories.BaseRepository;

public interface IUnitOfWork {

    BaseRepository getRepository(Class entityClass);

    void complete();
}
