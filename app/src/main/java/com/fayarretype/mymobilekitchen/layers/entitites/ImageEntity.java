package com.fayarretype.mymobilekitchen.layers.entitites;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageEntity extends BaseEntity {

    private String foodID;
    private String ImageID;
    private Bitmap image;

    public ImageEntity() {

    }

    public ImageEntity(String ID) {
        super(ID);
    }

    public ImageEntity(String ID, String foodID, String imageID) {
        this(ID);
        setFoodID(foodID);
        setImageID(imageID);
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getImageID() {
        return ImageID;
    }

    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
