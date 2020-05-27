package com.fayarretype.mymobilekitchen.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.fragments.FragmentContainer;
import com.fayarretype.mymobilekitchen.fragments.FragmentName;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FoodsActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        toolbar = findViewById(R.id.toolbar);
        loadToolbar(R.string.FoodsOptionLayoutName);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(FragmentContainer.getInstance(this)
                .getFragment(FragmentName.VIEW_FOODS_FRAGMENT));
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
