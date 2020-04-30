package com.fayarretype.mymobilekitchen.layers.dal.databasehelper;

import java.util.ArrayList;

public interface IDatabaseHelper<T> {

    void add(T entity);

    void delete(int id, Class entity);

    void update(T entity, int id);

    ArrayList<T> list(Class entity);

    ArrayList<T> list(Class entity, String selectionValue, String selectionValue2);

    void connect();

    void disconnect();

    void categorySave();

    void materialSave();

    void foodSave();

    void imageSave();
}
