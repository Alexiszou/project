package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/7.
 */

public class UninitException extends BaseException {

    private static final String MSG = "Retrofit Client is not init!";
    public UninitException(){
        super(MSG);
    }

    public UninitException(String msg){
        super(msg);
    }
    public UninitException(Throwable cause) {
        super(MSG,cause);
    }
}
