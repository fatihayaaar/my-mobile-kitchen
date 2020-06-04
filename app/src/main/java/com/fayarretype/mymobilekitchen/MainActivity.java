package com.fayarretype.mymobilekitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.activities.AboutActivity;
import com.fayarretype.mymobilekitchen.activities.FoodsActivity;
import com.fayarretype.mymobilekitchen.activities.ProfileActivity;
import com.fayarretype.mymobilekitchen.activities.RecipeBookActivity;
import com.fayarretype.mymobilekitchen.activities.StockActivity;

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
*/
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
}
