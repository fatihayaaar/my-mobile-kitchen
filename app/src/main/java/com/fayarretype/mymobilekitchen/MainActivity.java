package com.fayarretype.mymobilekitchen;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.activities.AboutActivity;
import com.fayarretype.mymobilekitchen.activities.FoodsActivity;
import com.fayarretype.mymobilekitchen.activities.ProfileActivity;
import com.fayarretype.mymobilekitchen.activities.RecipeBookActivity;
import com.fayarretype.mymobilekitchen.activities.StockActivity;
import com.fayarretype.mymobilekitchen.activities.WizardFoodActivity;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetData(this, getCurrentFocus()).execute();
    }

    private void loadCardView() {
        androidx.cardview.widget.CardView foodsCardView = findViewById(R.id.foodsCardView);
        foodsCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FoodsActivity.class);
                FoodsActivity.setMode(FoodsActivity.NORMAL_MODE);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView wizardFoodOptions = findViewById(R.id.wizard_food_activity);
        wizardFoodOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WizardFoodActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView profileOptions = findViewById(R.id.profileOptionsCardView);
        profileOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView stockOptions = findViewById(R.id.stockOptions);
        stockOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StockActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView recipeBookOptions = findViewById(R.id.recipeBooksCardView);
        recipeBookOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RecipeBookActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView aboutOptions = findViewById(R.id.aboutActivityCardView);
        aboutOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        private final String URL = "";
        private Context context;
        private View view;
        private ArrayList<FoodEntity> entities;
        private ArrayList<String> foodNames;
        private ArrayList<String> preparationTexts;
        private ArrayList<String> cookingTimes;
        private ArrayList<String> preparationTimes;
        private ArrayList<String> howManyPersons;
        private ArrayList<String> imgURLs;

        public GetData(Context context, View view) {
            this.context = context;
            this.view = view;
            entities = new ArrayList<>();
            foodNames = new ArrayList<>();
            preparationTexts = new ArrayList<>();
            cookingTimes = new ArrayList<>();
            preparationTimes = new ArrayList<>();
            howManyPersons = new ArrayList<>();
            imgURLs = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void mVoid) {
            super.onPostExecute(mVoid);

            for (int i = 0; i < foodNames.size(); i++) {
                FoodEntity foodEntity = new FoodEntity();
                foodEntity.setFoodName(foodNames.get(i));
                foodEntity.setPreparationText(preparationTexts.get(i));
                foodEntity.setCookingTime(cookingTimes.get(i));
                foodEntity.setPreparationTime(preparationTimes.get(i));
                foodEntity.setHowManyPerson(howManyPersons.get(i));
                foodEntity.setCategoryID(1);
                entities.add(foodEntity);
            }

            DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
            for (FoodEntity entity : entities) {
                FoodManager foodManager = (FoodManager) dataProcessingFactory
                        .getManager(ManagerName.FOOD_MANAGER);
                foodManager.add(entity);
            }
            dataProcessingFactory.saveChanges();
            loadCardView();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(URL).timeout(30 * 1000).get();
                Elements foodName = doc.select("");
                Elements preparationText = doc.select("");
                Elements cookingTime = doc.select("");
                Elements preparationTime = doc.select("");
                Elements howManyPerson = doc.select("");
                Elements imgURL = doc.select("");

                for (int i = 0; i < foodName.size(); i++) {
                    foodNames.add(foodName.get(i).text());
                    preparationTexts.add(preparationText.get(i).text());
                    cookingTimes.add(cookingTime.get(i).text());
                    preparationTimes.add(preparationTime.get(i).text());
                    howManyPersons.add(howManyPerson.get(i).text());
                    imgURLs.add(imgURL.get(i).text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
