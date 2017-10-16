package com.elftree.mall.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zouhongzhi on 2017/9/25.
 */

public class CommonUtil {

    public static void startActivity(Context context,Class<?> clazz,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(context,clazz);
        if(bundle != null)
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivity(Context context,Class<?> clazz){
        startActivity(context,clazz,null);
    }
}
