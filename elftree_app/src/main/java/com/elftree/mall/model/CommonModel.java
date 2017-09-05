package com.elftree.mall.model;

import android.content.Context;

import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/8/26.
 */

public abstract class CommonModel {

    public static final String JSON_MEDIA_TYPE = "application/json; charset=utf-8";

    @Expose
    private long TimeStamp ;
    @Expose
    private String sign;

    public long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void genTimeStamp(){
        this.TimeStamp = TimeUtil.getCurTime();
    }

    public RequestBody genRequestBody(){
        genTimeStamp();
        Logger.d("model:"+ RetrofitCreator.getGson().toJson(this));
        return RequestBody.create(okhttp3.MediaType.parse(JSON_MEDIA_TYPE),
                RetrofitCreator.getGson().toJson(this));
    }
}
