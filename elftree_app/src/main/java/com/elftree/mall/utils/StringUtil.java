package com.elftree.mall.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by zouhongzhi on 2017/8/26.
 */

public class StringUtil {

    public static String mapToString(Map map){
        return map.toString().replaceAll("\\{|\\}", "");
    }
    public static String arrayToString(String[] array){
        return Arrays.toString(array).replaceAll("\\[|\\]", "");
    }

    public static String arrayToStringSort(String[] array){
        Arrays.sort(array);
        return arrayToString(array);
    }
}
