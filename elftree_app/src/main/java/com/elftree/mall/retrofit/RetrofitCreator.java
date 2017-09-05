package com.elftree.mall.retrofit;

import com.elftree.mall.config.NetConfig;
import com.elftree.mall.config.SystemConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zouhongzhi on 2017/8/24.
 */

public class RetrofitCreator {



    private static class RetrofitHolder{
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(NetConfig.BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder {
        private static final int TIME_OUT = 20;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //private static final ArrayList<Interceptor> INTERCEPTORS = Zm.getConfiguration(ConfigKeys.INTERCEPTOR);

        public static OkHttpClient.Builder addInterceptor() {
            /*if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }*/
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class ServiceHolder {
        private static final UserService USER_SERVICE = RetrofitHolder.RETROFIT.create(UserService.class);
    }


    public static UserService getUserService(){
        return ServiceHolder.USER_SERVICE;
    }

    private static final class GsonHolder{
        private static final Gson mGson = new GsonBuilder().setVersion(SystemConfig.APP_VENRSION)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public static Gson getGson(){
        return GsonHolder.mGson;
    }
}
