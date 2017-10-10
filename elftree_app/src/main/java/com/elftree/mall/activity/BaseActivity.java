package com.elftree.mall.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elftree.mall.config.NetConfig;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

/**
 * Created by zouhongzhi on 2017/9/13.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{


    public Context mContext;
    public Gson mGson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    //| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        mContext = this;
        mGson = RetrofitCreator.getGson();
        initDatas(savedInstanceState);
        initViews();
    }

    public abstract void initDatas(Bundle savedInstanceState);
    public abstract void initViews();

    public static void checkVeriCode(final Context context, User user,final OnSuccessInterface successInterface){
        RetrofitClient.getInstance().
                createBaseApi()
                .json(NetConfig.CHECK_VERIFICATION_CODE,user.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(context) {
                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.getStrCode());
                        ToastUtil.showShortToast(context,e.getMessage());
                        if(e.isSuccess()){
                            successInterface.onSuccess();
                        }
                    }

                    @Override
                    public void onNext(BaseResponse response) {

                    }
                });
    }

    public interface OnSuccessInterface{
        void onSuccess();
    }
}
