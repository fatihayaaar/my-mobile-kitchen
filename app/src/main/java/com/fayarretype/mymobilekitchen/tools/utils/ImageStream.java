package com.fayarretype.mymobilekitchen.tools.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageStream {
    private Context context;
    private String key;

    @SuppressLint("SdCardPath")
    public ImageStream(Context context) {
        setContext(context);
    }

    @SuppressLint("SimpleDateFormat")
    public void saveImage(Bitmap b) {
        int rnd = (int) (Math.random() * 999);
        this.key = new SimpleDateFormat("yyddHHmmss").format(new Date()) + rnd;
        String fileName = "image-" + key + ".jpg";
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    public Bitmap getImageJPG(String key) {
        Bitmap bitmap = null;
        String fileName = "image-" + key + ".jpg";
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
