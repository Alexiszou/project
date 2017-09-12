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
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            LogUtil.logd(RetrofitClient.TAG, "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        LogUtil.logd(RetrofitClient.TAG, "originalResponse：" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Flowable.fromIterable(originalResponse.headers("set-cookie"))

                    .map(new Function<String, String>() {

                        @Override
                        public String apply(@NonNull String s) throws Exception {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];

                        }
                    }).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String cookie) throws Exception {
                                cookieBuffer.append(cookie).append(";");
                        }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            LogUtil.logd(RetrofitClient.TAG, "ReceivedCookiesInterceptor:" + cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}