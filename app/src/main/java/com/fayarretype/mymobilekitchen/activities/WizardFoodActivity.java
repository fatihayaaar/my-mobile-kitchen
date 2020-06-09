package com.fayarretype.mymobilekitchen.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.layers.pl.MaterialCardAdapter;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

import java.util.ArrayList;

public class WizardFoodActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private GridView itemGridView;
    private ArrayList<MaterialEntity> materialEntities;
    private AutoCompleteTextView autoCompleteTextViewMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_food);

        itemGridView = findViewById(R.id.item_grid_view_food_wizard);
        autoCompleteTextViewMaterial = findViewById(R.id.materialsAddAutoCompleteTextView);
        materialEntities = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.WizardFoodOptionLayoutName);

        init();
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
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
        try {
            materialEntities.add(materialManager.getEntitiesByMaterialName(autoCompleteTextViewMaterial.getText().toString()));
        } catch (Exception e) {
            e.getStackTrace();
        }
        bindItemGridView();
    }

    public void bindItemGridView() {
        MaterialCardAdapter materialCardViewAdapter = new MaterialCardAdapter(this, materialEntities, R.layout.material_row_layout);
        itemGridView.setAdapter(materialCardViewAdapter);
    }

    public void bindToMaterialsMultiAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.materials_row, R.id.materialTextView,
                Convert.getStringArray(((MaterialManager) DataProcessingFactory.getInstance(this)
                        .getManager(ManagerName.MATERIAL_MANAGER)).getNames()));

        AutoCompleteTextView multiAutoCompleteTextView = findViewById(R.id.materialsAddAutoCompleteTextView);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setAdapter(adapter);
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
