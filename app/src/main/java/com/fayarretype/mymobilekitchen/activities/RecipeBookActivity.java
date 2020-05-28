package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerContainer;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.pl.FoodAdapter;

import java.util.ArrayList;

public class RecipeBookActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private GridView gridView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book);
        context = this;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Yemek Kitabım");
        setSupportActionBar(toolbar);

        new LoadValuesFood().start();
    }

    public void loadValuesFoodRowLayout() {
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        final ArrayList<FoodEntity> foodEntities = dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER).getEntities();
        GridView foodGridView = findViewById(R.id.foodGridViewRecipes);

        FoodAdapter foodAdapter = new FoodAdapter(this, foodEntities);
        foodGridView.setAdapter(foodAdapter);

        gridView = findViewById(R.id.foodGridViewRecipes);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                IManager categoryManager = ManagerContainer.getInstance(context).getManager(ManagerName.CATEGORY_MANAGER);
                CategoryEntity categoryEntity = (CategoryEntity) categoryManager.getEntity(foodEntities.get(position).getCategoryID());
                RecipeBookFoodDetailActivity.setFood(foodEntities.get(position), categoryEntity);
                Intent intent = new Intent(getBaseContext(), RecipeBookFoodDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadValuesFood().start();
    }

    private class LoadValuesFood implements Runnable {

        Thread thread;

        @Override
        public void run() {
            loadValuesFoodRowLayout();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}
