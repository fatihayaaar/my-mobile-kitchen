package com.fayarretype.mymobilekitchen.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.dal.XMLPullParserHandler;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    private Context context;
    private androidx.appcompat.widget.Toolbar toolbar;
    private AutoCompleteTextView autoCompleteTextView;
    private MaterialEntity materialEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        context = this;
        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.MalzemeEkle);
        autoCompleteTextView = findViewById(R.id.item_name_edit_text);

        new LoadValuesMaterial().start();
    }

    private void loadToolbar(@StringRes int resId) {
        toolbar.setTitle(resId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void addButtonOnClick(View view) {
        try {
            Boolean isAddItem = true;
            Boolean isNewAddItem = true;
            if (validateControl()) {
                materialEntity = new MaterialEntity();
                String key = new SimpleDateFormat("yyddHHmmss").format(new Date());
                materialEntity.setID(key);
                materialEntity.setMaterialName(autoCompleteTextView.getText().toString().trim().toLowerCase());
                materialEntity.setMaterialCount("1");

                DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
                MaterialManager materialManager = (MaterialManager) dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);

                ArrayList<MaterialEntity> entities = materialManager.getEntities();
                for (MaterialEntity entity : entities) {
                    if (materialEntity.getMaterialName().trim().toLowerCase().equals(entity.getMaterialName().trim().toLowerCase())) {
                        materialEntity.setID(entity.getID());
                        isAddItem = false;
                    }
                }
                if (isAddItem) {
                    materialManager.add(materialEntity);
                    XMLPullParserHandler xmlPullParserHandler = XMLPullParserHandler.getInstance(this);
                    ArrayList<MaterialEntity> materialEntities = xmlPullParserHandler.getMaterialEntities();
                    for (MaterialEntity entity : materialEntities) {
                        if (materialEntity.getMaterialName().trim().toLowerCase().equals(entity.getMaterialName().trim().toLowerCase())) {
                            isNewAddItem = false;
                        }
                    }
                    if (isNewAddItem) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                        dlgAlert.setMessage("Yeni bir malzeme oluşturmak ister misin?");
                        dlgAlert.setTitle("Bu malzeme yok!");
                        dlgAlert.setNeutralButton("İPTAL", null);
                        dlgAlert.setNegativeButton("OLUŞTUR",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        XMLPullParserHandler xmlPullParserHandler = XMLPullParserHandler.getInstance(context);
                                        xmlPullParserHandler.setMaterialEntityXMLData(materialEntity);
                                        finish();
                                    }
                                });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        dataProcessingFactory.saveChanges();
                    }

                } else {
                    materialManager.increaseCount(materialEntity.getID());
                    dataProcessingFactory.saveChanges();
                    Toast.makeText(getApplicationContext(), "Başarılı", Toast.LENGTH_LONG).show();
                    finish();
                }
                if (!isNewAddItem) {
                    dataProcessingFactory.saveChanges();
                    Toast.makeText(getApplicationContext(), "Başarılı", Toast.LENGTH_LONG).show();
                    finish();
                }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
