package com.elftree.mall.activity;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.elftree.mall.BuildConfig;
import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.config.SystemConfig;
import com.elftree.mall.greendao.DaoMaster;
import com.elftree.mall.greendao.DaoSession;
import com.elftree.mall.greendao.MyDevOpenHelper;
import com.elftree.mall.model.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.utils.ToastUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zouhongzhi on 2017/8/24.
 */

public class MyApplication extends Application {
    private static final String DATABASE_NAME = "elftree.db";
    private MyDevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;



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

        instances = this;
        setDatabase();
        Fresco.initialize(this);
    }

    public static MyApplication getInstances(){
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new MyDevOpenHelper(this, DATABASE_NAME, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


    public User getCurUser(){
        if(mDaoSession.getUserDao().loadAll().size() == 0){
            ToastUtil.showShortToast(this, R.string.hint_no_login);
            return new User();
        }else{
            return mDaoSession.getUserDao().loadAll().get(0);
        }
    }
    public boolean isUserLogin(){
        if(mDaoSession.getUserDao().loadAll().size() != 0){
            return true;
        }
        return false;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
