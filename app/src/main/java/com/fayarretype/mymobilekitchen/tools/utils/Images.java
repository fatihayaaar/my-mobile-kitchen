package com.fayarretype.mymobilekitchen.tools.utils;

import com.fayarretype.mymobilekitchen.R;

public class Images {

    public enum CategoryImages {

        NULL(0, 0),
        APPETIZER(1, R.drawable.appetizer),
        BABY_FOOD(2, R.drawable.baby_food),
        BAGEL(3, R.drawable.bagel),
        BEVERAGE(4, R.drawable.beverage),
        BREAKFAST(5, R.drawable.breakfast),
        CAKE(6, R.drawable.cake),
        CAKE_P(7, R.drawable.cake_p),
        CHICKEN(8, R.drawable.chicken),
        COOKIE(9, R.drawable.cookie),
        DIET(10, R.drawable.diet),
        DOUGHNUT(11, R.drawable.doughnut),
        FISH(12, R.drawable.fish),
        HAMBURGER(13, R.drawable.hamburger),
        ICE_CREAM(14, R.drawable.ice_cream),
        JAM(15, R.drawable.jam),
        MEAT_BALL(16, R.drawable.meatball),
        OLIVE_OIL(17, R.drawable.olive_oil),
        PICKLES(18, R.drawable.pickles),
        PIE(19, R.drawable.pie),
        PIZZA(20, R.drawable.pizza),
        RICE(21, R.drawable.rice),
        SALAD(22, R.drawable.salad),
        SANDWICH(23, R.drawable.sandwich),
        SNACK(24, R.drawable.snack),
        SOUP(25, R.drawable.soup),
        SPAGHETTI(26, R.drawable.spaghetti),
        STEAK(27, R.drawable.steak),
        TURKEY_MAP(28, R.drawable.turkey_map),
        VEGAN(29, R.drawable.vegan),
        VEGETABLES(30, R.drawable.vegetables),
        VEGETARIAN(31, R.drawable.vegetarian),
        WORLD(32, R.drawable.world);

        private int id;
        private int value;

        CategoryImages(int id, int value) {
            this.id = id;
            this.value = value;
        }

        public CategoryImages getCategoryImages(int id) {
            for (CategoryImages type : CategoryImages.values()) {
                if (id == type.getId())
                    return type;
            }
            return NULL;
        }

        public int getId() {
            return id;
        }

        public int getValue() {
            return value;
        }
    }
}
