package com.elftree.mall.utils;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;

import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouhongzhi on 2017/9/25.
 */

public class CommonUtil {

    public static final String ENCODE_TYPE = "UTF-8";

    public static void startActivity(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> clazz) {
        startActivity(context, clazz, null);
    }

    public static void startActivityForResult(Activity context, Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity context, Class<?> clazz, int requestCode) {
        startActivityForResult(context, clazz, null, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> clazz, int requestCode) {
        startActivityForResult(fragment, clazz, null, requestCode);
    }

    /**
     * 判断手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobiles);
        b = m.matches();
        return b;
    }


    public static String getMobileNum(String content) {
        String temp = "";
        Pattern p = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
        Matcher m = p.matcher(content);
        while (m.find()) {
            temp = m.group();
        }
        return temp;
    }

    public static String encode(String str) {
        /*try{
            str = URLEncoder.encode(str,ENCODE_TYPE);
        }catch (Exception e){
            Logger.e("encode utf-8 error:"+e.toString());
        }*/
        return str;
    }

    public static String encodeUTF(String str) {
        try {
            str = URLEncoder.encode(str, ENCODE_TYPE);
        } catch (Exception e) {
            Logger.e("encode utf-8 error:" + e.toString());
        }
        return str;
    }

    public static String decodeUTF(String str) {
        try {
            str = URLDecoder.decode(str, ENCODE_TYPE);
        } catch (Exception e) {
            Logger.e("decode utf-8 error:" + e.toString());
        }
        return str;
    }


    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) return false;

        try {
            // 解密
            byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
