package com.fayarretype.mymobilekitchen.fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

public class FragmentContainer {
    private AddFoodFragment addFoodFragment;
    private ViewFoodsFragment viewFoodsFragment;
    private static FragmentContainer fragmentContainer;

    private FragmentContainer(Context context) {
        addFoodFragment = new AddFoodFragment(context);
        viewFoodsFragment = new ViewFoodsFragment();
    }

    public static FragmentContainer getInstance(Context context) {
        if (fragmentContainer == null) {
            fragmentContainer = new FragmentContainer(context);
        }
        return fragmentContainer;
    }

    public Fragment getFragment(FragmentName fragmentName) {
        switch (fragmentName) {
            case ADD_FOOD_FRAGMENT:
                return addFoodFragment;
            case VIEW_FOODS_FRAGMENT:
                return viewFoodsFragment;
            default:
                return null;
        }
    }
}
