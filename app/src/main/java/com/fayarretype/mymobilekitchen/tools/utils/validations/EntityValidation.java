package com.fayarretype.mymobilekitchen.tools.utils.validations;

import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

public class EntityValidation {

    public static boolean entityValidation(BaseEntity entity) {
        if (entity.getClass() == CategoryEntity.class)
            return categoryValidation((CategoryEntity) entity);
        else if (entity.getClass() == MaterialEntity.class)
            return materialValidation((MaterialEntity) entity);
        else if (entity.getClass() == FoodEntity.class)
            return foodValidation((FoodEntity) entity);
        return false;
    }

    public static boolean categoryValidation(CategoryEntity categoryEntity) {
        if (categoryEntity.getCategoryName().trim().isEmpty() || categoryEntity.getCategoryName() == null)
            return false;
        return categoryEntity.getCategoryName().length() <= 30;
    }

    public static boolean foodValidation(FoodEntity foodEntity) {
        if (foodEntity.getFoodName().trim().isEmpty() || foodEntity.getFoodName() == null)
            return false;
        if (foodEntity.getPreparationText().trim().isEmpty() || foodEntity.getPreparationText() == null)
            return false;
        if (foodEntity.getFoodName().length() > 30)
            return false;
        if (Integer.valueOf(foodEntity.getCookingTime()) < 0 || Integer.valueOf(foodEntity.getPreparationTime()) < 0
                || Integer.valueOf(foodEntity.getHowManyPerson()) < 0)
            return false;
        return foodEntity.getCategoryID() >= -1;
    }

    public static boolean materialValidation(MaterialEntity materialEntity) {
        if (materialEntity.getMaterialName().trim().isEmpty() || materialEntity.getMaterialName() == null)
            return false;
        return materialEntity.getMaterialName().length() <= 30;
    }
}
