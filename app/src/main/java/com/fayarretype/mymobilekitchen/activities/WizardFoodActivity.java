package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerContainer;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.layers.pl.FoodAdapter;
import com.fayarretype.mymobilekitchen.layers.pl.MaterialCardAdapter;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

import java.util.ArrayList;

public class WizardFoodActivity extends AppCompatActivity {

    public static ArrayList<MaterialEntity> materialEntities;
    private static Context context;
    private static GridView itemGridView;
    private androidx.appcompat.widget.Toolbar toolbar;
    private static GridView foodGridView;
    private AutoCompleteTextView autoCompleteTextViewMaterial;

    public static void bindItemGridView() {
        MaterialCardAdapter materialCardViewAdapter = new MaterialCardAdapter(context, materialEntities, R.layout.material_row_layout);
        itemGridView.setAdapter(materialCardViewAdapter);
        itemGridView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_food);

        context = this;

        foodGridView = findViewById(R.id.food_grid_view_food_wizard);
        itemGridView = findViewById(R.id.item_grid_view_food_wizard);
        autoCompleteTextViewMaterial = findViewById(R.id.materialsAddAutoCompleteTextView);
        materialEntities = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.WizardFoodOptionLayoutName);

        init();

        new LoadValuesFood().start();
    }

    public static void loadValuesFoodRowLayout() {
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
        MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
        ArrayList<MaterialEntity> materialEntities = materialManager.getEntitiesMaterialByStock(MaterialEntity.MATERIAL_YES_STOCK);
        if (!WizardFoodActivity.materialEntities.isEmpty()) {
            materialEntities = WizardFoodActivity.materialEntities;
        }
        MaterialEntity[] entities = new MaterialEntity[materialEntities.size()];
        for (int i = 0; i < entities.length; i++) {
            entities[i] = materialEntities.get(i);
        }
        final ArrayList<FoodEntity> foodEntities = ((FoodManager) dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER)).getEntitiesByStock(entities);
        FoodAdapter foodAdapter = new FoodAdapter(context, foodEntities);
        foodGridView.setAdapter(foodAdapter);

        foodGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                IManager categoryManager = ManagerContainer.getInstance(context).getManager(ManagerName.CATEGORY_MANAGER);
                CategoryEntity categoryEntity = (CategoryEntity) categoryManager.getEntity(String.valueOf(foodEntities.get(position).getCategoryID()));
                FoodDetailActivity.setFood(foodEntities.get(position), categoryEntity);
                Intent intent = new Intent(context, FoodDetailActivity.class);
                context.startActivity(intent);
            }
        });

        foodGridView.setVisibility(View.VISIBLE);
        dataProcessingFactory.saveChanges();
    }

    public void init() {
        LoadValuesMaterial loadValuesMaterial = new LoadValuesMaterial();
        loadValuesMaterial.start();
    }

    private void loadToolbar(@StringRes int resId) {
        toolbar.setTitle(resId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addItemBtnOnClick(View view) {
        boolean isMaterial = false;
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
        try {
            for (MaterialEntity entity : materialEntities) {
                if (entity.getMaterialName().equals(autoCompleteTextViewMaterial.getText().toString().trim().toLowerCase())) {
                    isMaterial = true;
                }
            }
            if (!isMaterial) {
                materialEntities.add(materialManager.getEntitiesByMaterialName(autoCompleteTextViewMaterial.getText().toString()));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        bindItemGridView();
        new LoadValuesFood().start();
    }

    public void bindToMaterialsMultiAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.materials_row, R.id.materialTextView,
                Convert.getStringArray(((MaterialManager) DataProcessingFactory.getInstance(this)
                        .getManager(ManagerName.MATERIAL_MANAGER)).getNames()));

        AutoCompleteTextView multiAutoCompleteTextView = findViewById(R.id.materialsAddAutoCompleteTextView);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setAdapter(adapter);
    }

    public class LoadValuesFood implements Runnable {

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

    private class LoadValuesMaterial implements Runnable {

        Thread thread;

        @Override
        public void run() {
            bindToMaterialsMultiAutoCompleteTextView();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}
