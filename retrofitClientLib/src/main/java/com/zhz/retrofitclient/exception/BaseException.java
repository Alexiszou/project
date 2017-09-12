package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/7.
 */

public class BaseException extends Exception {

    private Throwable mCause;
    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        mCause = cause;
    }

    public BaseException(Throwable cause) {
        super(cause);
        mCause = cause;
    }

    public BaseException(String message) {
        super(message);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return (mCause != null ? mCause.toString()+"\n":"")+super.toString();
    }
}
