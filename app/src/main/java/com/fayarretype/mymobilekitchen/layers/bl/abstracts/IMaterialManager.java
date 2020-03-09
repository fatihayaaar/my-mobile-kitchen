package com.fayarretype.mymobilekitchen.layers.bl.abstracts;

import java.util.ArrayList;

public interface IMaterialManager<TEntity> extends IManager<TEntity> {

    ArrayList<String> getNames();
}
