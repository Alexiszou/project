package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.elftree.mall.utils.StringUtil;
import com.elftree.mall.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/8/25.
 */

public class User extends CommonModel{

    @Expose
    private String mobile;
    @Expose
    private String checkcode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }


    public void genSign(){
        String str = (!TextUtils.isEmpty(mobile)?"mobile=" + mobile :"")+

                (!TextUtils.isEmpty(checkcode)?"&checkcode="+checkcode:"");
        setSign(MD5Util.str2MD5Offset(str));
    }

    @Override
    public RequestBody genRequestBody() {
        genSign();
        return super.genRequestBody();
    }


}
