package com.fayarretype.mymobilekitchen.layers.pl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.dal.XMLPullParserHandler;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialAdapter extends ArrayAdapter<MaterialEntity> {

    private Context context;

    private MaterialAdapter(Context context, ArrayList<MaterialEntity> materialEntities) {
        super(context, 0, materialEntities);
        this.context = context;
    }

    public static MaterialAdapter newInstance(Context context) {
        return new MaterialAdapter(context, XMLPullParserHandler.getInstance(context).getMaterialEntities());
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

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.materials_row, parent, false);
        }

        MaterialEntity materialEntity = getItem(position);

        TextView textViewName = convertView.findViewById(R.id.materialTextView);

        if (materialEntity != null) {
            textViewName.setText(materialEntity.getMaterialName().toUpperCase());
        }

        return convertView;
    }
}
