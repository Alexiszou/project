package com.elftree.mall.utils;

import java.util.Map;

/**
 * Created by zouhongzhi on 2017/8/26.
 */

public class StringUtil {

    public static String mapToString(Map map){
        return map.toString().replaceAll("\\{|\\}", "");
    }
}
