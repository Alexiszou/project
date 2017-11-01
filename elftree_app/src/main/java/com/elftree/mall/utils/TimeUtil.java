package com.elftree.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zouhongzhi on 2017/8/25.
 */

public class TimeUtil {
    public static final int TIME_DIVISOR = 1000;
    public static final String DTAE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DTAE_FORMAT_WITHOUT_TIME = "yyyy.MM.dd";

    public static Long getCurTime(){
        return System.currentTimeMillis()/TIME_DIVISOR;//获取系统时间的10位的时间戳;
    }

    public static String dateFormat(long time){
        time = time*1000;
        SimpleDateFormat format = new SimpleDateFormat(DTAE_FORMAT);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String dateFormat(String time){
        long date = Long.parseLong(time);
        return dateFormat(date);
    }

    public static String dateFormatWithoutTime(long time){
        time = time*1000;
        SimpleDateFormat format = new SimpleDateFormat(DTAE_FORMAT_WITHOUT_TIME);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String dateFormatWithoutTime(String time){
        long date = Long.parseLong(time);
        return dateFormatWithoutTime(date);
    }
}
