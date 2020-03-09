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
import com.fayarretype.mymobilekitchen.layers.bl.ManagerContainer;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<FoodEntity> {
    private Context context;

    public FoodAdapter(Context context, ArrayList<FoodEntity> foodList) {
        super(context, 0, foodList);
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

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_row_layout, parent, false);
        }

        FoodEntity foodEntity = getItem(position);

        IManager categoryManager = ManagerContainer.getInstance(context).getManager(ManagerName.CATEGORY_MANAGER);
        CategoryEntity categoryEntity = (CategoryEntity) categoryManager.getEntity(foodEntity.getCategoryID());

        ImageView imageViewIcon = convertView.findViewById(R.id.foodImageView);
        TextView textViewName = convertView.findViewById(R.id.foodNameTextView);
        TextView textViewCategory = convertView.findViewById(R.id.foodCategoryTextView);
        TextView textViewPreparationOfText = convertView.findViewById(R.id.foodPreparationOfTextView);
        TextView textViewCookingTime = convertView.findViewById(R.id.cookingTimeTextView);
        TextView textViewPreparationTime = convertView.findViewById(R.id.preparationTimeTextView);
        TextView textViewForHowManyPerson = convertView.findViewById(R.id.forHowManyPersonTextView);

        if (foodEntity != null) {
            //imageViewIcon.setImageResource(foodEntity.getImage());
            textViewName.setText(foodEntity.getFoodName().toUpperCase());
            textViewCategory.setText(categoryEntity.getCategoryName().toUpperCase());
            textViewPreparationOfText.setText(((foodEntity.getPreparationText().length() <= 50)
                    ? foodEntity.getPreparationText() : foodEntity.getPreparationText().substring(0, 50)) + "...");
            textViewCookingTime.setText("PİŞMESİ: " + foodEntity.getCookingTime().toUpperCase());
            textViewPreparationTime.setText("HAZIRLANMASI: " + foodEntity.getPreparationTime().toUpperCase());
            textViewForHowManyPerson.setText(foodEntity.getHowManyPerson().toUpperCase());
        }

        return convertView;
    }
}
