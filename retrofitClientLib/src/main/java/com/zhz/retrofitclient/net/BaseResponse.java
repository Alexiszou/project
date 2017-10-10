package com.zhz.retrofitclient.net;


import com.google.gson.annotations.Expose;
import com.zhz.retrofitclient.exception.RequestException;

/**
 * 网络返回基类 支持泛型
 */
public class BaseResponse<T> {

    @Expose
    private String code;
    @Expose
    private String msg;

    @Expose
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isOk() {
        return code.equals(RequestException.RequestError.SUCCESS.getCode());
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
