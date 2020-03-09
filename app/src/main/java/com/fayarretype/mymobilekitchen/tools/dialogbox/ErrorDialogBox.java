package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class ErrorDialogBox extends DialogBox {

    public ErrorDialogBox(Context context, FragmentManager fragmentManager, String title,
                             String preparation) {
        super(context, fragmentManager, title, preparation);
    }

    public ErrorDialogBox(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager, "Hata oluştu", "Bir hata oluştu.");
    }
}
