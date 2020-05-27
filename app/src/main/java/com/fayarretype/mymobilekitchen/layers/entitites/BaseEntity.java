package com.fayarretype.mymobilekitchen.layers.entitites;

public abstract class BaseEntity {
    
    protected String id;
    
    public BaseEntity() {
        
    }
    
    public BaseEntity(String id) {
        this.id = id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getID() {
        return id;
    }
}
