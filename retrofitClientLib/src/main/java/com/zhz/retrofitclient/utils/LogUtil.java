package com.zhz.retrofitclient.utils;

import android.util.Log;

import com.zhz.retrofitclient.BuildConfig;

/**
 * Created by zouhongzhi on 2017/9/9.
 */

public class LogUtil {
    private static boolean isPrint = BuildConfig.DEBUG;

    public static void logd(String tag,String msg){
        if(isPrint){
            Log.d(tag,msg);
        }
    }

    public static void loge(String tag,String msg){
        Log.e(tag,msg);
    }

}
