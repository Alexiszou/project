package com.zhz.retrofitclient.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.utils.LogUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;


/**
 *
 */
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static Exception handleException(Throwable e) {
        LogUtil.logd(RetrofitClient.TAG,"handleException e:"+e.toString());
        ResponeException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = new ResponeException(ResponeException.Error.HTTP_ERROR, e);
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeException(ResponeException.Error.PARSE_ERROR,e);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeException(ResponeException.Error.NETWORD_ERROR,e);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeException(ResponeException.Error.SSL_ERROR,e);
            return ex;
        } else if (e instanceof ConnectTimeoutException){
            ex = new ResponeException(ResponeException.Error.TIMEOUT_ERROR,e);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponeException(ResponeException.Error.TIMEOUT_ERROR,e);
            return ex;
        } else if(e instanceof SuccessWithoutDataException){
            return (SuccessWithoutDataException) e;
        } else if(e instanceof RequestException){
            return (RequestException) e;
        } else if(e instanceof ResponeException){
            return (ResponeException) e;
        } else{
            ex = new ResponeException(ResponeException.Error.UNKNOWN,e);
            return ex;
        }
    }


}

