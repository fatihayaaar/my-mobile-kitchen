package com.fayarretype.mymobilekitchen.layers.entitites;

public abstract class BaseEntity {
    
    protected int id;
    
    public BaseEntity() {
        
    }
    
    public BaseEntity(int id) {
        this.id = id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
}
