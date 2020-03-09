package com.fayarretype.mymobilekitchen.tools.validations;

public class TextValidate {

    private static final String CHARS = "-*/+.:;_?!'^%&()=|[]}{½<>£/'";

    public static boolean isFormatValidate(String text) {
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < CHARS.length(); j++) {
                if (text.charAt(i) == CHARS.charAt(j))
                    return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String text) {
        return text.equals("");
    }

    public static boolean isRequestedLengthRange(String text, int minNum, int maxNum) {
        return text.length() >= minNum && text.length() <= maxNum;
    }

    public static boolean isRequestedLengthRange(String text, int num) {
        return text.length() <= num;
    }
}
