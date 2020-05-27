package com.fayarretype.mymobilekitchen.tools.utils;

import java.util.ArrayList;

public class Convert {

    public static String[] getStringArray(ArrayList<String> arr) {
        String[] str = new String[arr.size()];

        for (int j = 0; j < arr.size(); j++)
            str[j] = arr.get(j);
        return str;
    }
}
