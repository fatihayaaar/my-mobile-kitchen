package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.pl.MaterialCardAdapter;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    private static Context context;
    private static View view;
    private androidx.appcompat.widget.Toolbar toolbar;

    public static void loadValues() {
        new LoadValuesItem().start();
    }

    private static void LoadValuesItems() {
        RelativeLayout relativeLayout = view.findViewById(R.id.stock_activity_relative_layout);
        ImageView im = view.findViewById(R.id.stock_activity_back);
        GridView itemGridView = view.findViewById(R.id.item_grid_view);
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
        ArrayList materialEntities = dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER).getEntities();
        if (materialEntities.isEmpty()) {
            relativeLayout.setBackgroundColor(Color.WHITE);
            im.setVisibility(View.VISIBLE);
            im.setImageResource(R.drawable.no_data);
            itemGridView.setVisibility(View.GONE);
        } else {
            relativeLayout.setBackgroundColor(Color.rgb(240, 240, 240));
            im.setVisibility(View.GONE);

            MaterialCardAdapter materialCardViewAdapter = new MaterialCardAdapter(context, materialEntities);
            itemGridView.setAdapter(materialCardViewAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        view = getWindow().getDecorView().getRootView();
        context = this;

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.StockOptionLayoutName);

        new LoadValuesItem().start();
    }

    private void loadToolbar(@StringRes int resId) {
        toolbar.setTitle(resId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void addItemButtonOnClick(View view) {
        Intent intent = new Intent(getBaseContext(), AddItemActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadValuesItem().start();
    }

    private static class LoadValuesItem implements Runnable {

        Thread thread;

        @Override
        public void run() {
            LoadValuesItems();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}
