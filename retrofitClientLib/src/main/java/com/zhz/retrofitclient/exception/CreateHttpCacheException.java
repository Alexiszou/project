package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/7.
 */

public class CreateHttpCacheException extends BaseException {

    private static final String MSG = "Create http cache directory failed!";

    public CreateHttpCacheException(){
        super(MSG);
    }

    public CreateHttpCacheException(String msg){
        super(msg);
    }

    public CreateHttpCacheException(Exception e){
        super(MSG,e);
    }
}
