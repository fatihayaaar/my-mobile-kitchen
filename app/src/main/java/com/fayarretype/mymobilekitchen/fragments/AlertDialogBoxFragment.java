package com.fayarretype.mymobilekitchen.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AlertDialogBoxFragment extends AppCompatDialogFragment {

    private View view;

    public AlertDialogBoxFragment(View view) {
        this.view = view;
        if (this.view != null) {
            if (this.view.getParent() != null) {
                ((ViewGroup) this.view.getParent()).removeAllViews();
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    public void destroyed() {
        onDestroyView();
    }
}
