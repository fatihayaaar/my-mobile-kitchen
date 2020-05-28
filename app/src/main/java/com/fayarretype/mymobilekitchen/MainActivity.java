package com.fayarretype.mymobilekitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.activities.FoodsActivity;
import com.fayarretype.mymobilekitchen.activities.RecipeBookActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.cardview.widget.CardView foodsCardView = findViewById(R.id.foodsCardView);
        foodsCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FoodsActivity.class);
                FoodsActivity.setMode(FoodsActivity.NORMAL_MODE);
                startActivity(intent);
            }
        });
/*
        LinearLayout wizardFoodOptionsLayout = findViewById(R.id.wizardFoodOptionsLayout);
        wizardFoodOptionsLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WizardFoodActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout profileOptionsLayout = findViewById(R.id.profileOptionsLayout);
        profileOptionsLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout stockOptionsLayout = findViewById(R.id.stockOptionsLayout);
        stockOptionsLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StockActivity.class);
                startActivity(intent);
            }
        });
*/
        androidx.cardview.widget.CardView recipeBookOptions = findViewById(R.id.recipeBooksCardView);
        recipeBookOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RecipeBookActivity.class);
                startActivity(intent);
            }
        });
/*
        LinearLayout aboutOptionsLayout = findViewById(R.id.aboutOptionsLayout);
        aboutOptionsLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
            }
        });/*/
    }
}
