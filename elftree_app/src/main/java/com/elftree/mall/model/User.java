package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.elftree.mall.utils.StringUtil;
import com.elftree.mall.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import okhttp3.RequestBody;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zouhongzhi on 2017/8/25.
 */

@Entity
public class User extends CommonModel{

    @Id
    private long id;
    private String user_id;


    @Expose
    private String mobile;

    @Transient
    @Expose
    private String checkcode;

    @Expose
    private String username;

    @Transient
    @Expose
    private String password;

    @Expose
    private String nickname;

    @Generated(hash = 1712424460)
    public User(long id, String user_id, String mobile, String username,
            String nickname) {
        this.id = id;
        this.user_id = user_id;
        this.mobile = mobile;
        this.username = username;
        this.nickname = nickname;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswrod() {
        return password;
    }

    public void setPasswrod(String passwrod) {
        this.password = passwrod;
    }

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
        String str = (!TextUtils.isEmpty(checkcode)?"checkcode="+checkcode:"")
                +(!TextUtils.isEmpty(mobile)?"&mobile=" + mobile :"")
                +(!TextUtils.isEmpty(nickname)?"&nickname=" + nickname :"")
                +(!TextUtils.isEmpty(username)?"&password="+password:"")
                +(!TextUtils.isEmpty(password)?"&username="+username:"");
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", checkcode='" + checkcode + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
