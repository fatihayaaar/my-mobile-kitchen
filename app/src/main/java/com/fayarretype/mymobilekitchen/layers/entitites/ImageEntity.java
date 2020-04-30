package com.fayarretype.mymobilekitchen.layers.entitites;

public class ImageEntity extends BaseEntity {

    private int foodID;
    private int ImageID;

    public ImageEntity() {

    }

    public ImageEntity(int ID) {
        super(ID);
    }

    public ImageEntity(int ID, int foodID, int imageID) {
        this(ID);
        setFoodID(foodID);
        setImageID(imageID);
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }
}
