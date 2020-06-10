package com.fayarretype.mymobilekitchen.layers.pl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.activities.StockActivity;
import com.fayarretype.mymobilekitchen.activities.WizardFoodActivity;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialCardAdapter extends ArrayAdapter<MaterialEntity> {

    private Context context;
    private int layout;

    public MaterialCardAdapter(Context context, ArrayList<MaterialEntity> resource, @LayoutRes int layout) {
        super(context, 0, resource);
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }

        final MaterialEntity materialEntity = getItem(position);

        if (layout == R.layout.items_layout) {
            TextView textViewItemName = convertView.findViewById(R.id.item_name);
            Button buttonItemCount = convertView.findViewById(R.id.item_count);
            Button deleteItem = convertView.findViewById(R.id.delete_item_button);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Envanterden malzemeyi eksiltmek istediğinizden emin misiniz?");
                    dlgAlert.setTitle("Emin misiniz?");
                    dlgAlert.setNeutralButton("İPTAL", null);
                    dlgAlert.setNegativeButton("EKSİLT",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
                                    MaterialManager materialManager = (MaterialManager)
                                            dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
                                    materialManager.decreaseCount(materialEntity.getID());
                                    dataProcessingFactory.saveChanges();
                                    Toast.makeText(context, "Güncellendi", Toast.LENGTH_LONG).show();
                                    StockActivity.loadValues();
                                }
                            });
                    dlgAlert.setPositiveButton("TAMAMEN KALDIR",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
                                        MaterialManager materialManager = (MaterialManager)
                                                dataProcessingFactory.getManager(ManagerName.MATERIAL_MANAGER);
                                        materialManager.delete(materialEntity.getID());
                                        dataProcessingFactory.saveChanges();
                                        Toast.makeText(context, "Malzeme Kaldırıldı", Toast.LENGTH_LONG).show();
                                        StockActivity.loadValues();
                                    } catch (Exception e) {
                                        Toast.makeText(context, "Hata Oluştu", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            });

            if (materialEntity != null) {
                textViewItemName.setText(materialEntity.getMaterialName().toUpperCase());
                try {
                    buttonItemCount.setText(materialEntity.getMaterialCount().toUpperCase() + "X");
                } catch (Exception e) {
                    buttonItemCount.setText("0X");
                }
            }
        } else if (layout == R.layout.material_row_layout) {
            Button btnMaterialDelete = convertView.findViewById(R.id.delete_material_btn);
            final TextView textViewMaterialName = convertView.findViewById(R.id.material_name);
            textViewMaterialName.setText(materialEntity.getMaterialName());

            btnMaterialDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<MaterialEntity> entities = new ArrayList<>();
                    for (MaterialEntity materialEntity : WizardFoodActivity.materialEntities) {
                        if (!materialEntity.getMaterialName().equals(textViewMaterialName.getText().toString())) {
                            entities.add(materialEntity);
                        }
                    }
                    WizardFoodActivity.materialEntities = entities;
                    WizardFoodActivity.bindItemGridView();
                }
            });
        }
        return convertView;
    }
}
