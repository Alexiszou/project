package com.elftree.mall.model;

import com.google.gson.annotations.Expose;

/**
 * Created by zouhongzhi on 2017/10/24.
 */

public class Coupon extends CommonModel {

    /**
     * coupon_id : 16
     * coupon_name : 双节4
     * face_value : 100
     * used_goods_price : 9000.00
     * limit_get_num : 1
     * coupon_num : 50
     * received : 5
     * begin_time : 1506009600
     * end_time : 1510329599
     * description : 满9000可用
     * coupon_img :
     * add_time : 1505957770
     * add_user : admin
     * status : 1
     * if_delete : 1
     * goods_type : 1
     * goods_id_str :
     */
    @Expose private String username;
    @Expose private String coupon_id;
    @Expose private String coupon_name;
    @Expose private String face_value;
    @Expose private String used_goods_price;
    @Expose private String limit_get_num;
    @Expose private String coupon_num;
    @Expose private String received;
    @Expose private String begin_time;
    @Expose private String end_time;
    @Expose private String description;
    @Expose private String coupon_img;
    @Expose private String add_time;
    @Expose private String add_user;
    @Expose private String status;
    @Expose private String if_delete;
    @Expose private String goods_type;
    @Expose private String goods_id_str;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getFace_value() {
        return face_value;
    }

    public void setFace_value(String face_value) {
        this.face_value = face_value;
    }

    public String getUsed_goods_price() {
        return used_goods_price;
    }

    public void setUsed_goods_price(String used_goods_price) {
        this.used_goods_price = used_goods_price;
    }

    public String getLimit_get_num() {
        return limit_get_num;
    }

    public void setLimit_get_num(String limit_get_num) {
        this.limit_get_num = limit_get_num;
    }

    public String getCoupon_num() {
        return coupon_num;
    }

    public void setCoupon_num(String coupon_num) {
        this.coupon_num = coupon_num;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAdd_user() {
        return add_user;
    }

    public void setAdd_user(String add_user) {
        this.add_user = add_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIf_delete() {
        return if_delete;
    }

    public void setIf_delete(String if_delete) {
        this.if_delete = if_delete;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_id_str() {
        return goods_id_str;
    }

    public void setGoods_id_str(String goods_id_str) {
        this.goods_id_str = goods_id_str;
    }

    public boolean isEnabled(){
        if(status.equals("1")){
            return true;
        }
        return false;
    }
}
