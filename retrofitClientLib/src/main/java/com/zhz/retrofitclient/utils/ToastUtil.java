package com.zhz.retrofitclient.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zouhongzhi on 2017/9/11.
 */

public class ToastUtil {

    public static void showShortToast(Context conext, String msg){
        Toast.makeText(conext,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }


    public static void showShortToast(Context conext, int strId){
        Toast.makeText(conext,strId,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,int strId){
        Toast.makeText(context,strId,Toast.LENGTH_LONG).show();
    }
}
