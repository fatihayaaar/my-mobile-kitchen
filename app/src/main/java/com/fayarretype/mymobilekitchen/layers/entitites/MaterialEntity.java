package com.fayarretype.mymobilekitchen.layers.entitites;

public class MaterialEntity extends BaseEntity {

    public static final int MATERIAL_NO_STOCK = 0;
    public static final int MATERIAL_YES_STOCK = 1;
    private String materialName;
    private String materialCount;
    private String isItInStock;

    public MaterialEntity() {

    }

    public MaterialEntity(String id) {
        super(id);
    }

    public MaterialEntity(String id, String materialName, String materialCount, String isItInStock) {
        super(id);
        this.materialName = materialName;
        this.setMaterialCount(materialCount);
        this.setIsItInStock(isItInStock);
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

    public String getIsItInStock() {
        return isItInStock;
    }

    public void setIsItInStock(String isItInStock) {
        this.isItInStock = isItInStock;
    }
}
