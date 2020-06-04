package com.fayarretype.mymobilekitchen.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.MalzemeEkle);
        autoCompleteTextView = findViewById(R.id.item_name_edit_text);

        new LoadValuesMaterial().start();
    }

    private void loadToolbar(@StringRes int resId) {
        toolbar.setTitle(resId);
        setSupportActionBar(toolbar);
    }

    public void addButtonOnClick(View view) {
        try {
            if (validateControl()) {
                MaterialEntity materialEntity = new MaterialEntity();
                String key = new SimpleDateFormat("yyddHHmmss").format(new Date());
                materialEntity.setID(key);
                materialEntity.setMaterialName(autoCompleteTextView.getText().toString().trim().toLowerCase());

                DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
                MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
                materialManager.add(materialEntity);
                dataProcessingFactory.saveChanges();
                Toast.makeText(getApplicationContext(), "Envantere yeni malzeme eklendi", Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (Exception e) {
            Log.i("Error :", e.getMessage());
            Toast.makeText(getApplicationContext(), "Bir hata oluştu", Toast.LENGTH_LONG).show();
        }
    }

    private void bindToMaterialsAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.materials_row, R.id.materialTextView,
                Convert.getStringArray(((MaterialManager) DataProcessingFactory
                        .getInstance(this)
                        .getManager(ManagerName.MATERIAL_MANAGER))
                        .getNames()));

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
    }

    private boolean validateControl() {
        boolean c = true;
        if (autoCompleteTextView.getText().toString().trim().equals("")
                || autoCompleteTextView.getText().toString().trim() == null) {
            autoCompleteTextView.setError("Boş geçilemez");
            c = false;
        }
        return c;
    }

    private class LoadValuesMaterial implements Runnable {

        Thread thread;

        @Override
        public void run() {
            bindToMaterialsAutoCompleteTextView();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}
