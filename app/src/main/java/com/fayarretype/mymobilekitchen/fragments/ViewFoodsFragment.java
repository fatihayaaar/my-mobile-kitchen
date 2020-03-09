package com.fayarretype.mymobilekitchen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fayarretype.mymobilekitchen.R;

public class ViewFoodsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_foods, container, false);
    }

    public void loadValuesFoodRowLayout() {
        //GridView foodGridView = findViewById(R.id.foodGridView);

        //FoodAdapter foodAdapter = new FoodAdapter(this, foodEntities);
        //foodGridView.setAdapter(foodAdapter);
    }

    private class LoadValuesFood implements Runnable {

        @Override
        public void run() {
            loadValuesFoodRowLayout();
        }

        void start() {
            run();
        }
    }
}