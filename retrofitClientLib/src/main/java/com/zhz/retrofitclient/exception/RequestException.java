package com.zhz.retrofitclient.exception;

/**
 * Created by zouhongzhi on 2017/9/12.
 */

public class RequestException extends BaseException {
    public enum RequestError {
        SUCCESS("SUCCESS","请求成功"),
        TIME_ERROR("TIME_ERROR","请求已过期"),
        SIGN_ERROR("SIGN_ERROR","签名错误"),
        PARAMS_ERROR("PARAMS_ERROR","参数错误"),
        SIGN_EMPTY("SIGN_EMPTY","签名不能为空");
        private String code;
        private String message;
        private RequestError(String code,String msg){
            this.code = code;
            this.message = msg;
        }

        public static RequestError getInstance(String code){
            for(RequestError error : RequestError.values()){
                if(code.equals(error.getCode())){
                    return error;
                }
            }
            return null;
        }
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


    private String code;
    private String message;
    private Throwable mCause;


    public RequestException(RequestError error){

        super(error.getMessage());
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    public RequestException(String code,String msg){

        super(msg);
        this.message = msg;
        this.code = code;
    }
    public RequestException(RequestError error,Throwable e){

        super(error.getMessage(),e);
        this.message = error.getMessage();
        this.code = error.getCode();
        this.mCause = e;
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
