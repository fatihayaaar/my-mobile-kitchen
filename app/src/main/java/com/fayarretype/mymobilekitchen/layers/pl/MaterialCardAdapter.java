package com.fayarretype.mymobilekitchen.layers.pl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialCardAdapter extends ArrayAdapter<MaterialEntity> {

    private Context context;

    public MaterialCardAdapter(Context context, ArrayList<MaterialEntity> resource) {
        super(context, 0, resource);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_layout, parent, false);
        }
        TextView textViewItemName = convertView.findViewById(R.id.item_name);

        MaterialEntity materialEntity = getItem(position);

        if (materialEntity != null) {
            textViewItemName.setText(materialEntity.getMaterialName());
        }
        return convertView;
    }
}
