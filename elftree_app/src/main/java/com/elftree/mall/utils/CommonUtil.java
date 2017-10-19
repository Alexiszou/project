package com.elftree.mall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouhongzhi on 2017/9/25.
 */

public class CommonUtil {

    public static final String ENCODE_TYPE = "UTF-8";
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

    public static void startActivityForResult(Activity context, Class<?> clazz, Bundle bundle, int requestCode){
        Intent intent = new Intent();
        intent.setClass(context,clazz);
        if(bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent,requestCode);
    }
    public static void startActivityForResult(Activity context, Class<?> clazz,int requestCode){
        startActivityForResult(context,clazz,null,requestCode);
    }
    /**
     * 判断手机号是否正确
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobiles);
        b = m.matches();
        return b;
    }


    public static String getMobileNum(String content){
        String temp = "";
        Pattern p = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
        Matcher m = p.matcher(content);
        while (m.find()){
            temp = m.group();
        }
        return temp;
    }

    public static String encode(String str){
        try{
            str = URLEncoder.encode(str,ENCODE_TYPE);
        }catch (Exception e){
            Logger.e("encode utf-8 error:"+e.toString());
        }
        return str;
    }

    public static String decode(String str){
        try{
            str = URLDecoder.decode(str,ENCODE_TYPE);
        }catch (Exception e){
            Logger.e("decode utf-8 error:"+e.toString());
        }
        return str;
    }

}
