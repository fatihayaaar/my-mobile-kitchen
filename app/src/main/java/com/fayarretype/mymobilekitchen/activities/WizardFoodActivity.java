package com.fayarretype.mymobilekitchen.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

public class WizardFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_food);

        getSupportActionBar().show();
        getSupportActionBar().setTitle(R.string.WizardFoodOptionLayoutName);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        init();
    }

    public void init() {
        LoadValuesMaterial loadValuesMaterial = new LoadValuesMaterial();
        loadValuesMaterial.start();
    }

    public void bindToMaterialsMultiAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.materials_row, R.id.materialTextView,
                Convert.getStringArray(((MaterialManager) DataProcessingFactory.getInstance(this)
                        .getManager(ManagerName.MATERIAL_MANAGER)).getNames()));

        MultiAutoCompleteTextView multiAutoCompleteTextView = findViewById(R.id.materialsAddMultiAutoCompleteTextView);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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
