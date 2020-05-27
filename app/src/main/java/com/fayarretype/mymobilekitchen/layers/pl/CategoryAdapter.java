package com.fayarretype.mymobilekitchen.layers.pl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.dal.XMLPullParserHandler;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.tools.utils.Images;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoryEntity> {

    private CategoryAdapter(Context context, ArrayList<CategoryEntity> categoryList) {
        super(context, 0, categoryList);
    }

    public static CategoryAdapter newInstance(Context context) {
        ArrayList<CategoryEntity> entities = XMLPullParserHandler.getInstance(context)
                .getCategoryEntities();
        entities.add(new CategoryEntity("-1", "diğer"));
        entities.add(new CategoryEntity("-1", "kategoriler"));

        return new CategoryAdapter(context, entities);
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
        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.category_spinner_row, parent, false);

        CategoryEntity categoryEntity = getItem(position);

        ImageView imageViewIcon = convertView.findViewById(R.id.categoryImageView);
        TextView textViewName = convertView.findViewById(R.id.categoryTextView);

        if (categoryEntity != null) {
            imageViewIcon.setImageResource(Images.CategoryImages.NULL
                    .getCategoryImages(categoryEntity.getCategoryImageNo()).getValue());
            textViewName.setText(categoryEntity.getCategoryName().toUpperCase());
        }

        return convertView;
    }
}
