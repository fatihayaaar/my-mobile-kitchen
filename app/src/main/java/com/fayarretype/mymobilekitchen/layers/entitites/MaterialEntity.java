package com.fayarretype.mymobilekitchen.layers.entitites;

public class MaterialEntity extends BaseEntity {

    private String materialName;
    private String materialCount;

    public MaterialEntity() {

    }

    public MaterialEntity(String id) {
        super(id);
    }

    public MaterialEntity(String id, String materialName, String materialCount) {
        super(id);
        this.materialName = materialName;
        this.setMaterialCount(materialCount);
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(String materialCount) {
        this.materialCount = materialCount;
    }
}
