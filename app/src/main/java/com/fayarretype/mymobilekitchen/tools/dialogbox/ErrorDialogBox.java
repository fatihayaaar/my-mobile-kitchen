package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.fayarretype.mymobilekitchen.R;

public class ErrorDialogBox extends DialogBox {

    public ErrorDialogBox(Context context, FragmentManager fragmentManager, String title,
                             String preparation) {
        super(context, fragmentManager, title, preparation, R.layout.dialog_box);
    }

    public ErrorDialogBox(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager, "Hata oluştu", "Bir hata oluştu.", R.layout.dialog_box);
    }
}
