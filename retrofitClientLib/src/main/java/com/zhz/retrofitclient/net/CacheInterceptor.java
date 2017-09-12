package com.zhz.retrofitclient.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zhz.retrofitclient.R;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.utils.LogUtil;
import com.zhz.retrofitclient.utils.NetworkUtil;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by zouhongzhi on 2017/9/7.
 */
public class CacheInterceptor implements Interceptor {

    private Context context;
    //set cahe times is 3 days
    int maxStale = 60 * 60 * 24 * 3;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 60;
            String cacheControl = request.cacheControl().toString();
            LogUtil.logd(RetrofitClient.TAG, "60s load cahe" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            /*((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShortToast(context, R.string.no_network);
                }
            });*/
            LogUtil.logd(RetrofitClient.TAG, " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
