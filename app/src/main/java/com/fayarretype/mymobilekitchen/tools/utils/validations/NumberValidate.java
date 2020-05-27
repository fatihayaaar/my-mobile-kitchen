package com.fayarretype.mymobilekitchen.tools.utils.validations;

public class NumberValidate {

    public static boolean isNumberBetween(int num, int minValue, int maxValue) {
        return num < minValue || num > maxValue;
    }
}
