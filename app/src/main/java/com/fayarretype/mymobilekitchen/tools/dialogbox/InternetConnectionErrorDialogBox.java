package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class InternetConnectionErrorDialogBox extends ErrorDialogBox {

    public InternetConnectionErrorDialogBox(Context context, FragmentManager fragmentManager) {
        super(context, fragmentManager, "Bağlantı hatası",
                "İnternet bağlantınızı kontrol ediniz.");
    }
}
