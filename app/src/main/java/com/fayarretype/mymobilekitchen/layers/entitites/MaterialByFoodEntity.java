package com.fayarretype.mymobilekitchen.layers.entitites;

public class MaterialByFoodEntity extends BaseEntity {

    private String materialID;
    private String foodID;
    private MaterialEntity materialEntity;

    public MaterialByFoodEntity() {

    }

    public MaterialByFoodEntity(String ID) {
        super(ID);
    }

    public MaterialByFoodEntity(String ID, String materialID, String foodID) {
        super(ID);
        this.materialID = materialID;
        this.foodID = foodID;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public MaterialEntity getMaterialEntity() {
        return materialEntity;
    }

    public void setMaterialEntity(MaterialEntity materialEntity) {
        this.materialEntity = materialEntity;
        setMaterialID(materialEntity.getID());
    }
}
