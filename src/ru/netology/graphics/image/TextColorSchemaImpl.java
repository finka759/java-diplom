package ru.netology.graphics.image;

import java.util.*;

public class TextColorSchemaImpl implements TextColorSchema {
    static char[] charArr = {'#', '$', '@', '%', '*', '+', '-', '\''};
    static Map<Integer, Character> lHashMap = getMap(charArr);

    static Map<Integer, Character> getMap(char[] charArr) {
        Map<Integer, Character> lHashMap = new LinkedHashMap<>();
        for (int i = charArr.length - 1; i >= 0; i--) {
            Integer border = Math.toIntExact(Math.round(255.0 / (double) charArr.length * (i)));
            lHashMap.put(border, charArr[i]);
        }
        return lHashMap;
    }

    @Override
    public char convert(int color) {
        for (int i : lHashMap.keySet()) {
            if (color >= i) {
                return lHashMap.get(i);
            }
        }
        return 0;
    }
}
