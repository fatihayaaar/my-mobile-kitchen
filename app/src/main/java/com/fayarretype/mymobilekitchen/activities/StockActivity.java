package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.layers.pl.MaterialCardAdapter;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    private static Context context;
    private static View view;
    private androidx.appcompat.widget.Toolbar toolbar;

    public static void loadValues() {
        new LoadValuesItemStatic(context, view).start();
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
    }

    public void addItemButtonOnClick(View view) {
        Intent intent = new Intent(getBaseContext(), AddItemActivity.class);
        startActivity(intent);
    }

    public void loadValuesItems() {
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        ArrayList<MaterialEntity> materialEntities = dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER).getEntities();
        GridView itemGridView = findViewById(R.id.item_grid_view);

        MaterialCardAdapter materialCardViewAdapter = new MaterialCardAdapter(this, materialEntities);
        itemGridView.setAdapter(materialCardViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadValuesItem().start();
    }

    private static class LoadValuesItemStatic implements Runnable {

        Thread thread;
        Context context;
        View view;

        public LoadValuesItemStatic(Context context, View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        public void run() {
            DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
            ArrayList<MaterialEntity> materialEntities = dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER).getEntities();
            GridView itemGridView = view.findViewById(R.id.item_grid_view);

            MaterialCardAdapter materialCardViewAdapter = new MaterialCardAdapter(context, materialEntities);
            itemGridView.setAdapter(materialCardViewAdapter);
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }

    private class LoadValuesItem implements Runnable {

        Thread thread;

        @Override
        public void run() {
            loadValuesItems();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}
