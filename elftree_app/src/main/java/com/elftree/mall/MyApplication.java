package com.elftree.mall;

import android.app.Application;

import com.elftree.mall.config.NetConfig;
import com.elftree.mall.config.SystemConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zouhongzhi on 2017/8/24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        RetrofitClient.init(this, NetConfig.BASE_URL,null);
    }
}
