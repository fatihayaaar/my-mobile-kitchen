package com.fayarretype.mymobilekitchen.tools.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageStream {

    private final String PATH = Environment.getExternalStorageDirectory().toString();
    private Context context;
    private Bitmap pictureBitmap;
    private OutputStream fOut;
    private String key;
    private File file;

    public ImageStream(Context context) {
        setContext(context);

        fOut = null;
    }

    public void saveImage() {
        key = new SimpleDateFormat("yyddHHmmss").format(new Date());
        file = new File(PATH, "food_image" + key + ".jpg");
        try {
            setfOut(new FileOutputStream(getFile()));
            getPictureBitmap().compress(Bitmap.CompressFormat.JPEG, 85, getfOut());

            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    getFile().getAbsolutePath(), getFile().getName(), getFile().getName());

            getfOut().flush();
            getfOut().close();
            Log.i("Başarılı", "Başarılı");

        } catch (Exception e) {
            Log.i("hata oluştu", "bir hata meydana geldi -" + e.getMessage());
            e.getStackTrace();
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

    public OutputStream getfOut() {
        return fOut;
    }

    public void setfOut(OutputStream fOut) {
        this.fOut = fOut;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
