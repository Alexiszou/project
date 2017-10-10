package com.zhz.retrofitclient.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zouhongzhi on 2017/9/19.
 */

public class GsonFactory {

    private GsonFactory(){

    }
    public static Gson create(){
        return GsonHolder.INSTANCE;
    }

    private static class GsonHolder{
        private static final Gson INSTANCE = new GsonBuilder().create();
    }
}
