package com.zhz.retrofitclient.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.utils.LogUtil;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String language) {
        super();
        this.context = context;
        this.lang = language;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            LogUtil.logd(RetrofitClient.TAG, "Addchain == null");
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Flowable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        if (cookie.contains("lang=ch")){
                            cookie = cookie.replace("lang=ch","lang="+lang);
                        }
                        if (cookie.contains("lang=en")){
                            cookie = cookie.replace("lang=en","lang="+lang);
                        }
                        LogUtil.logd(RetrofitClient.TAG, "AddCookiesInterceptor: "+cookie);
                        builder.addHeader("cookie", cookie);
                    }

                });
        return chain.proceed(builder.build());
    }
}