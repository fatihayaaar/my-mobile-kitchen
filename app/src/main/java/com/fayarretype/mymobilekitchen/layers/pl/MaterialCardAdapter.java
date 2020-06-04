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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.activities.StockActivity;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import java.util.ArrayList;

public class MaterialCardAdapter extends ArrayAdapter<MaterialEntity> {

    private Context context;

    public MaterialCardAdapter(Context context, ArrayList<MaterialEntity> resource) {
        super(context, 0, resource);
        this.context = context;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_layout, parent, false);
        }
        TextView textViewItemName = convertView.findViewById(R.id.item_name);
        Button deleteItem = convertView.findViewById(R.id.delete_item_button);

        final MaterialEntity materialEntity = getItem(position);

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
        }
        return convertView;
    }
}
