package com.zhz.retrofitclient.net;

import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.utils.LogUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zouhongzhi on 2017/9/8.
 */

public class HeadersInterceptor extends BaseInterceptor {

    private Map<String, String> headers;
    public HeadersInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        LogUtil.logd(RetrofitClient.TAG,  "Okhttp url:" + builder.build().url());
        return chain.proceed(builder.build());

    }
}
