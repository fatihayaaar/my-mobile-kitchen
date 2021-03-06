package com.fayarretype.mymobilekitchen.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ImageStream;

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
    private TextView malzemeler;
    private TextView materialHeader;
    private ImageView imageHeaderView;
    private ImageView imageOneView;
    private ImageView imageTwoView;
    private ImageView imageThreeView;
    private ImageView imageFourView;
    private LinearLayout imageLayout;
    private Bitmap[] images;

    public static void setFood(FoodEntity food, CategoryEntity categoryEntity) {
        mFood = food;
        mCategoryEntity = categoryEntity;
    }

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
        malzemeler = findViewById(R.id.malzemelerTextView);
        materialHeader = findViewById(R.id.materialHeader);
        yukleyen = findViewById(R.id.yukleyen);

        imageOneView.setVisibility(View.GONE);
        imageTwoView.setVisibility(View.GONE);
        imageThreeView.setVisibility(View.GONE);
        imageFourView.setVisibility(View.GONE);
        imageLayout.setVisibility(View.GONE);

        loadValues();

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void loadValues() {
        ImageStream imStream = new ImageStream(this);
        if (mFood != null) {
            for (int i = 0; i < images.length; i++) {
                try {
                    images[i] = imStream.getImageJPG(mFood.getImage()[i].getImageID());
                } catch (Exception e) {
                    images[i] = null;
                }
            }

            if (images[0] == null) {
                imageHeaderView.setImageResource(R.drawable.food_no_images);
            } else {
                imageHeaderView.setImageBitmap(images[0]);
            }
            header.setText(mFood.getFoodName());
            category.setText(mCategoryEntity.getCategoryName());

            switch (mFood.getType()) {
                case FoodEntity.INTERNET_FOOD:
                    yukleyen.setBackgroundColor(Color.BLUE);
                    yukleyen.setText("??nternet Tarifi");
                    break;
                case FoodEntity.USER_FOOD:
                    yukleyen.setText("Kullan??c??n??n Tarifi");
            }

            if (mFood.getCookingTime().equals(FoodEntity.NULL)) {
                cookingTime.setVisibility(View.GONE);
            } else {
                cookingTime.setText("Pi??me S??resi : " + mFood.getCookingTime());
            }

            if (mFood.getPreparationTime().equals(FoodEntity.NULL)) {
                preparationTime.setVisibility(View.GONE);
            } else {
                preparationTime.setText("Haz??rlanma S??resi : " + mFood.getPreparationTime());
            }
            howManyPerson.setText("Ka?? Ki??ilik : " + mFood.getHowManyPerson());

            if (mFood.getType() == FoodEntity.USER_FOOD) {
                try {
                    DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
                    MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);

                    StringBuilder strMaterial = new StringBuilder();
                    for (MaterialByFoodEntity material : mFood.getMaterialByFoodEntities()) {
                        strMaterial.append(materialManager.getEntity(material.getMaterialID().trim()).getMaterialName());
                        strMaterial.append(", ");
                    }

                    malzemeler.setText(strMaterial);
                    dataProcessingFactory.saveChanges();
                    malzemeler.setVisibility(View.VISIBLE);
                    materialHeader.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else {
                malzemeler.setVisibility(View.GONE);
                materialHeader.setVisibility(View.GONE);
            }

            preparationHeader.setText("HAZIRLANI??I");
            preparation.setText(mFood.getPreparationText());
            if (images[1] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageOneView.setVisibility(View.VISIBLE);
                imageOneView.setImageBitmap(images[1]);
            }
            if (images[2] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageTwoView.setVisibility(View.VISIBLE);
                imageTwoView.setImageBitmap(images[2]);
            }
            if (images[3] != null) {
                imageLayout.setVisibility(View.VISIBLE);
                imageThreeView.setVisibility(View.VISIBLE);
                imageThreeView.setImageBitmap(images[3]);
            }
            if (images[4] != null) {
                imageFourView.setVisibility(View.VISIBLE);
                imageFourView.setImageBitmap(images[4]);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
