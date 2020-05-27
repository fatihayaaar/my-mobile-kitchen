package com.fayarretype.mymobilekitchen.layers.entitites;

public class MaterialEntity extends BaseEntity {

    private String materialName;
    
    public MaterialEntity() {
        
    }
    
    public MaterialEntity(String id) {
        super(id);
    }

    public MaterialEntity(String id, String materialName) {
        super(id);
        this.materialName = materialName;
    }
    
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    
    public String getMaterialName() {
        return materialName;
    }
}
