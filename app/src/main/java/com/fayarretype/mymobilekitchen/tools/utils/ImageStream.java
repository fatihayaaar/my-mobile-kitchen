package com.fayarretype.mymobilekitchen.tools.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
public class ImageStream {

    private final String PATH = Environment.getExternalStorageDirectory().toString();
    private Context context;
    private Bitmap pictureBitmap;
    private OutputStream fOut;
    private Integer key;
    private File file;

    public ImageStream(Context context) {
        setContext(context);

        fOut = null;
    }

    public void saveImage() {
        key = (int)(Math.random() * ((999999999 - 1) + 1)) + 1;
        file = new File(PATH, "food_image" + key + ".jpg");
        try {
            setfOut(new FileOutputStream(getFile()));
            getPictureBitmap().compress(Bitmap.CompressFormat.JPEG, 85, getfOut());
            getfOut().flush();
            getfOut().close();

            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    getFile().getAbsolutePath(), getFile().getName(), getFile().getName());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public Bitmap getBitmap(int key) {
        return BitmapFactory.decodeFile(PATH + "/food_image" + key + ".jpg");
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

    public OutputStream getfOut() {
        return fOut;
    }

    public void setfOut(OutputStream fOut) {
        this.fOut = fOut;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
