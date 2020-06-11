package com.fayarretype.mymobilekitchen.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.activities.FoodDetailActivity;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerContainer;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.abstracts.IManager;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.pl.CategoryAdapter;
import com.fayarretype.mymobilekitchen.layers.pl.FoodAdapter;

import java.util.ArrayList;

public class ViewFoodsFragment extends Fragment {

    private View view;
    private Context context;
    private Spinner categoryAddSpinner;

    public ViewFoodsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_foods, container, false);
        categoryAddSpinner = view.findViewById(R.id.categorySpinner);
        categoryAddSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (categoryAddSpinner.getSelectedItemPosition() + 1 != 34) {
                    new LoadValuesFoodByCategory().start(categoryAddSpinner.getSelectedItemPosition() + 1);
                } else {
                    new LoadValuesFood().start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        new LoadValuesFood().start();
        new LoadValuesCategory().start();
        return view;
    }

    private void bindToCategorySpinner() {
        CategoryAdapter categoryAdapter = CategoryAdapter.newInstance(context);
        categoryAddSpinner.setAdapter(categoryAdapter);
        categoryAddSpinner.setSelection(categoryAdapter.getCount() - 1);
    }

    public void loadValuesFoodRowLayoutByCategory(int categoryID) {
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(getContext());
        final ArrayList<BaseEntity> foodEntities = ((FoodManager) dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER)).getFoodByCategoryID(categoryID);
        GridView foodGridView = view.findViewById(R.id.foodGridView);

        ArrayList<FoodEntity> foods = new ArrayList<>();
        for (BaseEntity food : foodEntities) {
            foods.add((FoodEntity) food);
        }

        FoodAdapter foodAdapter = new FoodAdapter(context, foods);
        foodGridView.setAdapter(foodAdapter);
    }

    public void loadValuesFoodRowLayout() {
        LinearLayout relativeLayout = view.findViewById(R.id.view_foods_linear_layout);
        ImageView im = view.findViewById(R.id.view_foods_back);
        GridView foodGridView = view.findViewById(R.id.foodGridView);
        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(getContext());
        final ArrayList<FoodEntity> foodEntities = ((FoodManager) dataProcessingFactory
                .getManager(ManagerName.FOOD_MANAGER))
                .getEntities();
        if (foodEntities.isEmpty()) {
            relativeLayout.setBackgroundColor(Color.WHITE);
            im.setVisibility(View.VISIBLE);
            im.setImageResource(R.drawable.no_data);
            foodGridView.setVisibility(View.GONE);
        } else {
            relativeLayout.setBackgroundColor(Color.rgb(240, 240, 240));
            im.setVisibility(View.GONE);

            FoodAdapter foodAdapter = new FoodAdapter(context, foodEntities);
            foodGridView.setAdapter(foodAdapter);

            foodGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    IManager categoryManager = ManagerContainer.getInstance(context).getManager(ManagerName.CATEGORY_MANAGER);
                    CategoryEntity categoryEntity = (CategoryEntity) categoryManager.getEntity(String.valueOf(foodEntities.get(position).getCategoryID()));
                    FoodDetailActivity.setFood(foodEntities.get(position), categoryEntity);
                    Intent intent = new Intent(getActivity().getBaseContext(), FoodDetailActivity.class);
                    startActivity(intent);
                }
            });

            foodGridView.setVisibility(View.VISIBLE);
        }
    }

    private class LoadValuesCategory implements Runnable {

        Thread thread;

        @Override
        public void run() {
            bindToCategorySpinner();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }

    private class LoadValuesFood implements Runnable {

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

    private class LoadValuesFoodByCategory implements Runnable {

        Thread thread;
        int categoryID;

        @Override
        public void run() {
            loadValuesFoodRowLayoutByCategory(categoryID);
        }

        void start(int categoryID) {
            this.categoryID = categoryID;
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}