package com.fayarretype.mymobilekitchen.tools.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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
        int rnd = (int) (Math.random() * 999);
        this.key = new SimpleDateFormat("yyddHHmmss").format(new Date()) + rnd;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/imageMb");
        myDir.mkdirs();
        String fileName = "image-" + key + ".jpg";
        File file = new File(myDir, fileName);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImageJPG(String key) {
        String root = Environment.getExternalStorageDirectory().toString() + "/imageMb";
        String fileName = root + "/image-" + key + ".jpg";
        File file = new File(fileName);
        try {
            FileInputStream in = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
