package com.fayarretype.mymobilekitchen.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

public class FoodDetailActivity extends AppCompatActivity {

    private static FoodEntity mFood;
    private static CategoryEntity mCategoryEntity;
    private TextView header;
    private TextView category;
    private TextView cookingTime;
    private TextView preparationTime;
    private TextView howManyPerson;
    private TextView preparationHeader;
    private TextView preparation;
    private TextView yukleyen;
    private ImageView imageHeaderView;
    private ImageView imageOneView;
    private ImageView imageTwoView;
    private ImageView imageThreeView;
    private ImageView imageFourView;
    private LinearLayout imageLayout;
    private Bitmap[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        images = new Bitmap[5];

        header = findViewById(R.id.Baslik);
        category = findViewById(R.id.Category);
        cookingTime = findViewById(R.id.PismeSuresi);
        preparationTime = findViewById(R.id.HazirlanmaSuresi);
        howManyPerson = findViewById(R.id.KacKisilik);
        preparationHeader = findViewById(R.id.aciklamasiBaslik);
        preparation = findViewById(R.id.Aciklamasi);
        imageHeaderView = findViewById(R.id.imageHeaderView);
        imageOneView = findViewById(R.id.imageOneView);
        imageTwoView = findViewById(R.id.imageTwoView);
        imageThreeView = findViewById(R.id.imageThreeView);
        imageFourView = findViewById(R.id.imageFourView);
        imageLayout = findViewById(R.id.imageLayout);
        yukleyen = findViewById(R.id.yukleyen);

        imageOneView.setVisibility(View.GONE);
        imageTwoView.setVisibility(View.GONE);
        imageThreeView.setVisibility(View.GONE);
        imageFourView.setVisibility(View.GONE);
        imageLayout.setVisibility(View.GONE);

        loadValues();
    }

    @SuppressLint("SetTextI18n")
    private void loadValues() {
        if (mFood != null) {
            if (images[0] == null) {
                imageHeaderView.setImageResource(R.drawable.food_no_images);
            } else {
                imageHeaderView.setImageBitmap(images[0]);
            }
            header.setText(mFood.getFoodName());
            category.setText(mCategoryEntity.getCategoryName());
            yukleyen.setText("Kullanıcının Tarifi");
            cookingTime.setText("Pişme Süresi : " + mFood.getCookingTime() + " dakika");
            preparationTime.setText("Hazırlanma Süresi : " + mFood.getPreparationTime() + " dakika");
            howManyPerson.setText("Kaç Kişilik : " + mFood.getHowManyPerson());
            preparationHeader.setText("HAZIRLANIŞI");
            preparation.setText(mFood.getPreparationText());
            if (images[1] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageOneView.setVisibility(View.VISIBLE);
                imageHeaderView.setImageBitmap(images[1]);
            }
            if (images[2] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageTwoView.setVisibility(View.VISIBLE);
                imageHeaderView.setImageBitmap(images[2]);
            }
            if (images[3] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageThreeView.setVisibility(View.VISIBLE);
                imageHeaderView.setImageBitmap(images[3]);
            }
            if (images[4] != null) {
                imageFourView.setVisibility(View.VISIBLE);
                imageHeaderView.setImageBitmap(images[4]);
            }
        }
    }

    public static void setFood(FoodEntity food, CategoryEntity categoryEntity) {
        mFood = food;
        mCategoryEntity = categoryEntity;
    }
}