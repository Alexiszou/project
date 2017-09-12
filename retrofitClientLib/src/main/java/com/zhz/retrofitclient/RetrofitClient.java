package com.zhz.retrofitclient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhz.retrofitclient.exception.ApiServiceNullException;
import com.zhz.retrofitclient.exception.CreateHttpCacheException;
import com.zhz.retrofitclient.exception.ExceptionHandle;
import com.zhz.retrofitclient.exception.RequestException;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.exception.SuccessWithoutDataException;
import com.zhz.retrofitclient.exception.UninitException;
import com.zhz.retrofitclient.net.AddCookiesInterceptor;
import com.zhz.retrofitclient.net.BaseApiService;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.CacheInterceptor;
import com.zhz.retrofitclient.net.HeadersInterceptor;
import com.zhz.retrofitclient.net.ReceivedCookiesInterceptor;
import com.zhz.retrofitclient.utils.LogUtil;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zouhongzhi on 2017/9/7.
 */

public class RetrofitClient {

    public static final String TAG = RetrofitClient.class.getName();
    private static final int DEFAULT_TIMEOUT = 20;
    private static Context mContext;
    private static String baseUrl;
    private static Map<String,String> httpHeaders;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private BaseApiService mBaseApiService;
    private Cache httpCache = null;
    private File httpCacheDirectory;
    private static final long HTTP_CACHE_MAX_SIZE = 10*1024*1024;
    private static final String HTTP_CACHE_DIRECTORY = "retrofitClientCache";



    private static class SingletonHolder{
        private static RetrofitClient INSTANCE ;
        static {
            try {
                INSTANCE = new RetrofitClient();
            } catch (UninitException e){
                LogUtil.loge(TAG,e.toString());
            } catch (CreateHttpCacheException e){
                LogUtil.loge(TAG,e.toString());
            }
        }

    }
    public static void init(Context context,String url,Map<String,String> headers){
        mContext = context;
        baseUrl = url;
        httpHeaders = headers;
    }

    public static RetrofitClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() throws UninitException , CreateHttpCacheException{
        if(mContext == null){
            //没有初始化init
            throw new UninitException();
        }

        if(httpCacheDirectory == null){
            httpCacheDirectory = new File(mContext.getCacheDir(),HTTP_CACHE_DIRECTORY);
        }
        try {
            if (httpCache == null) {
                httpCache = new Cache(httpCacheDirectory, HTTP_CACHE_MAX_SIZE);
            }
        }catch (Exception e){
            throw new CreateHttpCacheException(e);
        }

        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.cookieJar(new NovateCookieManger(context))
                .cache(httpCache)
                .addInterceptor(new HeadersInterceptor(httpHeaders))
                .addInterceptor(new CacheInterceptor(mContext))
                .addInterceptor(new AddCookiesInterceptor(mContext, "ch"))
                .addInterceptor(new ReceivedCookiesInterceptor(mContext))
                .addNetworkInterceptor(new CacheInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }



    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        try {
            mBaseApiService = create(BaseApiService.class);
        }catch (ApiServiceNullException e){
            LogUtil.loge(RetrofitClient.TAG,e.toString());
        }
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) throws ApiServiceNullException{
        if (service == null) {
            throw new ApiServiceNullException();
        }
        return mRetrofit.create(service);
    }

    public Flowable json(String url, RequestBody jsonStr) {

        return mBaseApiService.json(url, jsonStr)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    FlowableTransformer schedulersTransformer() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public <T> FlowableTransformer<BaseResponse<T>, T> transformer() {

        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }

           /* @Override
            public Object call(Object observable) {
                return observable.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }*/
        };
    }


    private static class HttpResponseFunc<T> implements Function<Throwable, Flowable<T>> {
        @Override
        public Flowable<T> apply(@NonNull Throwable t) throws Exception {
            return Flowable.error(ExceptionHandle.handleException(t));
        }
    }

    private class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(@NonNull BaseResponse<T> response) throws Exception {
            if (!response.isOk() || response.getData() == null) {
                //throw new RequestException(response.getCode(),response.getMsg());
                throw new ResponeException(response.getCode(),response.getMsg());
            } else if(response.getData() == null){
                //throw new SuccessWithoutDataException(RequestException.RequestError.getInstance(response.getCode()));
            }
            return response.getData();
        }
    }
}
