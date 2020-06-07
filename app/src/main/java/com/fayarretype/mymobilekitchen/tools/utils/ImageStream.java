package com.fayarretype.mymobilekitchen.tools.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageStream {

    private Context context;
    private Bitmap pictureBitmap;
    private String key;

    public ImageStream(Context context) {
        setContext(context);
    }

    public void saveImage() {
        key = new SimpleDateFormat("yyddHHmmss").format(new Date());
        String fileName = "food_image" + key + ".jpg";
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            pictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
        }
    }

    public Bitmap getBitmap(String key) {
        return pictureBitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getPictureBitmap() {
        return pictureBitmap;
    }

    public void setPictureBitmap(Bitmap pictureBitmap) {
        this.pictureBitmap = pictureBitmap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
