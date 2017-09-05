package com.elftree.mall.utils;

/**
 * Created by zouhongzhi on 2017/8/25.
 */

public class TimeUtil {
    public static final int TIME_DIVISOR = 1000;

    public static Long getCurTime(){
        return System.currentTimeMillis()/TIME_DIVISOR;//获取系统时间的10位的时间戳;
    }
}
