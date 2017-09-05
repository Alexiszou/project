package com.elftree.mall.retrofit;



import java.util.LinkedHashMap;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by zouhongzhi on 2017/8/24.
 */

public interface UserService {

    /*@FormUrlEncoded
    @POST
    Observable<Object> post(@Url String url, @FieldMap LinkedHashMap<String, Object> params);*/

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST()
    Observable<Object> post(@Url String url, @Body RequestBody body);
}
