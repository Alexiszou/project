package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/10/18.
 */

public class Address extends CommonModel {


    /**
     * addr_id : 12
     * province : 6
     * city : 77
     * area : 707
     * address : 广东,深圳,南山区
     * particular : 沿山路22号 火炬创业大厦 5楼 精灵树
     * mobile : 15818730739
     * email :
     * zip_code :
     * username : qq123456
     * receiver : 杨威
     * add_time : 1500256215
     * if_default : 1
     */

    @Expose private String addr_id;
    @Expose private String province;
    @Expose private String city;
    @Expose private String area;
    @Expose private String address;
    @Expose private String particular;
    @Expose private String mobile;
    @Expose private String email;
    @Expose private String zip_code;
    @Expose private String username;
    @Expose private String receiver;
    @Expose private String add_time;
    @Expose private String if_default = "0";


    public void genSign(){
        String str = (!TextUtils.isEmpty(addr_id)?"addr_id=" + addr_id :"")+
                (!TextUtils.isEmpty(area)?"area=" + area :"")
                +(!TextUtils.isEmpty(city)?"&city=" + city :"")
                +(!TextUtils.isEmpty(if_default)?"&if_default=" + if_default :"")
                +(!TextUtils.isEmpty(mobile)?"&mobile=" + mobile :"")
                +(!TextUtils.isEmpty(particular)?"&particular=" + particular :"")
                +(!TextUtils.isEmpty(province)?"&province=" + province :"")
                +(!TextUtils.isEmpty(receiver)?"&receiver=" + receiver :"")
                +(!TextUtils.isEmpty(username)?"&username=" + username :"");

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

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIf_default() {
        return if_default;
    }

    public void setIf_default(String if_default) {
        this.if_default = if_default;
    }

    public boolean isDefault(){
        return if_default.equals("1") ? true:false;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addr_id='" + addr_id + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", particular='" + particular + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", username='" + username + '\'' +
                ", receiver='" + receiver + '\'' +
                ", add_time='" + add_time + '\'' +
                ", if_default='" + if_default + '\'' +
                '}';
    }
}
