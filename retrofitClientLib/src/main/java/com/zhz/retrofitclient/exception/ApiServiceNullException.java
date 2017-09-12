package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/7.
 */

public class ApiServiceNullException extends BaseException {

    private static final String MSG = "Api service is null!";

    public ApiServiceNullException(){
        super(MSG);
    }

    public ApiServiceNullException(String msg){
        super(msg);
    }

    public ApiServiceNullException(Exception e){
        super(MSG,e);
    }
}
