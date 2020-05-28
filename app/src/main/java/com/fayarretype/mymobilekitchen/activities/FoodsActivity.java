package com.fayarretype.mymobilekitchen.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.fragments.AddFoodFragment;
import com.fayarretype.mymobilekitchen.fragments.FragmentContainer;
import com.fayarretype.mymobilekitchen.fragments.FragmentName;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FoodsActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final int NORMAL_MODE = 1;
    public static final int EDIT_MODE = 2;
    private static int mMode;
    private static FoodEntity food;
    private androidx.appcompat.widget.Toolbar toolbar;

    public static int getMode() {
        return mMode;
    }

    public static void setMode(int mode) {
        mMode = mode;
    }

    public static FoodEntity getFood() {
        return food;
    }

    public static void setFood(FoodEntity food) {
        FoodsActivity.food = food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.FoodsOptionLayoutName);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (getMode() == EDIT_MODE) {
            AddFoodFragment.setMode(EDIT_MODE);
            AddFoodFragment.setMFood(food);
            bottomNavigationView.setVisibility(View.GONE);
            loadFragment(FragmentContainer.getInstance(this).getFragment(FragmentName.ADD_FOOD_FRAGMENT));
            return;
        }
        bottomNavigationView.setVisibility(View.VISIBLE);
        AddFoodFragment.setMode(NORMAL_MODE);
        loadFragment(FragmentContainer.getInstance(this).getFragment(FragmentName.VIEW_FOODS_FRAGMENT));

    }

    private void loadToolbar(@StringRes int resId) {
        toolbar.setTitle(resId);
        setSupportActionBar(toolbar);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;

        FoodsActivity.setMode(NORMAL_MODE);
        AddFoodFragment.setMode(NORMAL_MODE);

        switch (menuItem.getItemId()) {
            case R.id.nav_view_foods:
                loadToolbar(R.string.FoodsOptionLayoutName);
                fragment = FragmentContainer.getInstance(this)
                        .getFragment(FragmentName.VIEW_FOODS_FRAGMENT);
                break;
            case R.id.nav_add_food:
                loadToolbar(R.string.tabFoodsAdd);
                fragment = FragmentContainer.getInstance(this)
                        .getFragment(FragmentName.ADD_FOOD_FRAGMENT);
                break;
            default:
                fragment = null;
        }
        return loadFragment(fragment);
    }
}
