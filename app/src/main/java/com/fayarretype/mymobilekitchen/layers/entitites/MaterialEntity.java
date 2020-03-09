package com.fayarretype.mymobilekitchen.layers.entitites;

public class MaterialEntity extends BaseEntity {

    private String materialName;
    
    public MaterialEntity() {
        
    }
    
    public MaterialEntity(int id) {
        super(id);
    }

    public MaterialEntity(int id, String materialName) {
        super(id);
        this.materialName = materialName;
    }
    
    public MaterialEntity(String materialName) {
        this.materialName = materialName;
    }
    
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    
    public String getMaterialName() {
        return materialName;
    }
}
