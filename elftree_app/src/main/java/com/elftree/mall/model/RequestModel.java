package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public class RequestModel extends CommonModel {

    @Expose
    private String parent_id = "0";

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void genSign(){
        String str = (!TextUtils.isEmpty(parent_id)?"parent_id=" + parent_id :"");
                /*+(!TextUtils.isEmpty(mobile)?"&mobile=" + mobile :"")
                +(!TextUtils.isEmpty(nickname)?"&nickname=" + nickname :"")
                +(!TextUtils.isEmpty(username)?"&password="+password:"")
                +(!TextUtils.isEmpty(password)?"&username="+username:"");*/
        Logger.d("& index:"+str.indexOf("&"));
        if(str.startsWith("&")){
            str = str.replaceFirst("&","");
        }
        Logger.d("genSign str:"+str);
        setSign(MD5Util.str2MD5Offset(str));
    }

    @Override
    public RequestBody genRequestBody() {
        genSign();
        return super.genRequestBody();
    }
}
