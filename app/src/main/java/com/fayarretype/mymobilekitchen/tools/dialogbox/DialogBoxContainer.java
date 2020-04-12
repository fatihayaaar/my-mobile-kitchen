package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class DialogBoxContainer {

    private static DialogBoxContainer dialogBoxContainer;
    private Context context;
    private FragmentManager fragmentManager;

    private DialogBoxContainer(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public static DialogBoxContainer getInstance(Context context, FragmentManager fragmentManager) {
        if (dialogBoxContainer == null)
            dialogBoxContainer = new DialogBoxContainer(context, fragmentManager);
        return dialogBoxContainer;
    }

    public DialogBox getDialogBox(DialogBoxName dialogBoxName) {
        switch (dialogBoxName) {
            case ERROR_DIALOG_BOX:
                return new ErrorDialogBox(context, fragmentManager);
            case INTERNET_CONNECTION_ERROR_DIALOG_BOX:
                return new InternetConnectionErrorDialogBox(context, fragmentManager);
            case PHOTO_ADD_OPTIONS_DIALOG_BOX:
                return new PhotoAddOptionsDialogBox(context, fragmentManager);
            default:
                return null;
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
