package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/12.
 */

public class SuccessWithoutDataException extends BaseException {

    private String code;
    private String message;
    private Throwable mCause;


    public SuccessWithoutDataException(RequestException.RequestError error){

        super(error.getMessage());
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getmCause() {
        return mCause;
    }

    public void setmCause(Throwable mCause) {
        this.mCause = mCause;
    }
}
