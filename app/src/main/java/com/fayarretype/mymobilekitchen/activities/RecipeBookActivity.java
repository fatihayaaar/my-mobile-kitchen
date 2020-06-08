package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerContainer;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.pl.FoodAdapter;

import java.util.ArrayList;

public class RecipeBookActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book);
        context = this;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Yemek Kitabım");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new LoadValuesFood().start();
    }

    public void loadValuesFoodRowLayout() {
        LinearLayout relativeLayout = findViewById(R.id.recipe_book_linear_layout);
        ImageView im = findViewById(R.id.recipe_book_activity_back);
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        final ArrayList<FoodEntity> foodEntities = ((FoodManager) dataProcessingFactory
                .getManager(ManagerName.FOOD_MANAGER))
                .getEntitiesByType(FoodEntity.USER_FOOD);
        GridView foodGridView = findViewById(R.id.foodGridViewRecipes);
        if (foodEntities.isEmpty()) {
            relativeLayout.setBackgroundColor(Color.WHITE);
            im.setVisibility(View.VISIBLE);
            im.setImageResource(R.drawable.no_data);
            foodGridView.setVisibility(View.GONE);
        } else {
            relativeLayout.setBackgroundColor(Color.rgb(240, 240, 240));
            im.setVisibility(View.GONE);

            FoodAdapter foodAdapter = new FoodAdapter(this, foodEntities);
            foodGridView.setAdapter(foodAdapter);

            foodGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    IManager categoryManager = ManagerContainer.getInstance(context).getManager(ManagerName.CATEGORY_MANAGER);
                    CategoryEntity categoryEntity = (CategoryEntity) categoryManager.getEntity(String.valueOf(foodEntities.get(position).getCategoryID()));
                    RecipeBookFoodDetailActivity.setFood(foodEntities.get(position), categoryEntity);
                    Intent intent = new Intent(getBaseContext(), RecipeBookFoodDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
