package com.fayarretype.mymobilekitchen.tools.dialogbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.fragments.AlertDialogBoxFragment;

public abstract class DialogBox {

    private FragmentManager fragmentManager;
    private AlertDialogBoxFragment alertDialogBoxFragment;
    private String title;
    private String preparation;
    private Context context;
    private View view;

    protected DialogBox(Context context, FragmentManager fragmentManager, String title,
                        String preparation) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.title = title;
        this.preparation = preparation;
    }

    private void componentLoadValues() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_box, null, true);

        TextView textViewTitle = view.findViewById(R.id.dialogBoxTitleTextView);
        TextView textViewPreparation = view.findViewById(R.id.dialogBoxPreparationTextView);
        Button closeButton = view.findViewById(R.id.closeButton);

        textViewTitle.setText(title);
        textViewPreparation.setText(preparation);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBoxFragment.destroyed();
            }
        });
    }

    public void show() {
        componentLoadValues();
        alertDialogBoxFragment = new AlertDialogBoxFragment(view);
        alertDialogBoxFragment.show(fragmentManager, "Message");
    }

    public void destroyed() {
        alertDialogBoxFragment.destroyed();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public AlertDialogBoxFragment getAlertDialogBoxFragment() {
        return alertDialogBoxFragment;
    }

    public void setAlertDialogBoxFragment(AlertDialogBoxFragment alertDialogBoxFragment) {
        this.alertDialogBoxFragment = alertDialogBoxFragment;
    }
}