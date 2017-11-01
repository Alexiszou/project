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
    private Long id;
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

    /*@Transient
    @Expose
    private int goods_id = INVALID_NUM;
    @Transient
    @Expose
    private int goods_number = INVALID_NUM;*/

    @Transient
    @Expose
    private String goods_id;
    @Transient
    @Expose
    private String goods_number;
    @Transient
    @Expose
    private String spec_attr;

    @Transient
    @Expose
    private String cart_id;

    @Transient
    @Expose
    private String page;

    @Transient
    @Expose
    private String parent_id;

    @Transient
    @Expose
    private String coupon_id;

    @Transient
    @Expose
    private String type;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSpec_attr() {
        return spec_attr;
    }

    public void setSpec_attr(String spec_attr) {
        this.spec_attr = spec_attr;
    }

    @Generated(hash = 1255709505)
    public User(Long id, String user_id, String mobile, String username,
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

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void genSign(){
        String str = (!TextUtils.isEmpty(cart_id)?"cart_id="+cart_id:"")
                +(!TextUtils.isEmpty(checkcode)?"&checkcode="+checkcode:"")
                +(!TextUtils.isEmpty(coupon_id)?"&coupon_id="+coupon_id:"")
                +(!TextUtils.isEmpty(goods_id)?"&goods_id="+goods_id:"")
                +(!TextUtils.isEmpty(goods_number)?"&goods_number="+goods_number:"")
                +(!TextUtils.isEmpty(mobile)?"&mobile=" + mobile :"")
                +(!TextUtils.isEmpty(nickname)?"&nickname=" + nickname :"")
                +(!TextUtils.isEmpty(page)?"&page="+page:"")
                +(!TextUtils.isEmpty(parent_id)?"&parent_id="+parent_id:"")
                +(!TextUtils.isEmpty(password)?"&password="+password:"")
                +(!TextUtils.isEmpty(spec_attr)?"&spec_attr="+spec_attr:"")
                +(!TextUtils.isEmpty(type)?"&type="+type:"")
                +(!TextUtils.isEmpty(username)?"&username="+username:"");
        //Logger.d("& index:"+str.indexOf("&"));
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

    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }
}
