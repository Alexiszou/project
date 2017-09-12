package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/12.
 */

public class ResponeException extends BaseException {

    private int code;
    private String strCode;
    private String message;
    private Throwable mCause;
    public enum Error {
        UNKNOWN(1000,"未知错误"),
        PARSE_ERROR(1001,"解析错误"),
        NETWORD_ERROR(1002,"连接失败"),
        HTTP_ERROR(1003,"网络错误"),
        SSL_ERROR(1004,"证书验证失败"),
        TIMEOUT_ERROR(1005,"连接超时");
        private int code;
        private String message;
        private Error(int code,String msg){
            this.code = code;
            this.message = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    public ResponeException(Error error,Throwable e){

        super(error.getMessage(),e);
        this.message = error.getMessage();
        this.code = error.getCode();
        this.mCause = e;
    }

    public ResponeException(String msg,Throwable e){

        super(msg,e);
        this.message = msg;
        this.mCause = e;
    }

    public ResponeException(String code,String msg){

        super(msg);
        this.message = msg;
        this.strCode = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
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
