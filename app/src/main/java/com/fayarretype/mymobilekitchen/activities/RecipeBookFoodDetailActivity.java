package com.fayarretype.mymobilekitchen.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ImageStream;

public class RecipeBookFoodDetailActivity extends AppCompatActivity {

    private static FoodEntity mFood;
    private static CategoryEntity mCategoryEntity;
    private TextView header;
    private TextView category;
    private TextView cookingTime;
    private TextView preparationTime;
    private TextView howManyPerson;
    private TextView preparationHeader;
    private TextView preparation;
    private TextView malzemeler;
    private TextView materialHeader;
    private ImageView imageHeaderView;
    private ImageView imageOneView;
    private ImageView imageTwoView;
    private ImageView imageThreeView;
    private ImageView imageFourView;
    private LinearLayout imageLayout;
    private Bitmap[] images;
    private Context context;

    public static void setFood(FoodEntity food, CategoryEntity categoryEntity) {
        mFood = food;
        mCategoryEntity = categoryEntity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_food_detail);

        context = this;
        images = new Bitmap[5];

        header = findViewById(R.id.BaslikDetail);
        category = findViewById(R.id.CategoryDetail);
        cookingTime = findViewById(R.id.PismeSuresiDetail);
        preparationTime = findViewById(R.id.HazirlanmaSuresiDetail);
        howManyPerson = findViewById(R.id.KacKisilikDetail);
        preparationHeader = findViewById(R.id.aciklamasiBaslikDetail);
        preparation = findViewById(R.id.AciklamasiDetail);
        imageHeaderView = findViewById(R.id.imageHeaderViewDetail);
        imageOneView = findViewById(R.id.imageOneViewDetail);
        imageTwoView = findViewById(R.id.imageTwoViewDetail);
        imageThreeView = findViewById(R.id.imageThreeViewDetail);
        imageFourView = findViewById(R.id.imageFourViewDetail);
        malzemeler = findViewById(R.id.malzemelerTextView);
        materialHeader = findViewById(R.id.materialHeader);
        imageLayout = findViewById(R.id.imageLayoutDetail);

        imageOneView.setVisibility(View.GONE);
        imageTwoView.setVisibility(View.GONE);
        imageThreeView.setVisibility(View.GONE);
        imageFourView.setVisibility(View.GONE);
        imageLayout.setVisibility(View.GONE);

        loadValues();

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(header.getText().toString());
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

            cookingTime.setText("Pişme Süresi : " + mFood.getCookingTime() + " dakika");
            preparationTime.setText("Hazırlanma Süresi : " + mFood.getPreparationTime() + " dakika");
            howManyPerson.setText("Kaç Kişilik : " + mFood.getHowManyPerson());
            preparationHeader.setText("HAZIRLANIŞI");
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

    public void deleteButtonOnClick(View view) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Yemeği silmek istediğinizden emin misiniz?");
        dlgAlert.setTitle("Emin misiniz?");
        dlgAlert.setNegativeButton("İPTAL", null);
        dlgAlert.setPositiveButton("SİL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FoodManager foodManager = (FoodManager) DataProcessingFactory
                                .getInstance(context)
                                .getManager(ManagerName.FOOD_MANAGER);
                        foodManager.delete(mFood.getID());
                        DataProcessingFactory.getInstance(context).saveChanges();
                        finish();
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void editButtonOnClick(View View) {
        Intent intent = new Intent(getBaseContext(), FoodsActivity.class);
        FoodsActivity.setFood(mFood);
        FoodsActivity.setMode(FoodsActivity.EDIT_MODE);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadValues();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
