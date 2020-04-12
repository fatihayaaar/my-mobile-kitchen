package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.fayarretype.mymobilekitchen.R;

public class PhotoAddOptionsDialogBox extends DialogBox {

    protected PhotoAddOptionsDialogBox(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager, "Resim Ekle", "", R.layout.photo_add_options_dialog_box_layout);
    }
}
