package com.fayarretype.mymobilekitchen.layers.entitites;

import android.graphics.Bitmap;

public class ImageEntity extends BaseEntity {

    private int foodID;
    private int ImageID;
    private Bitmap image;

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
