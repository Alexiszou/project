package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public class Goods extends CommonModel {

    //goods_id: 商品ID, shop_price:商品价格, img1:商品图片, goods_name:商品名称, url:商品详情接口地址,
    @Expose
    private String goods_id = "0";

    @Expose private String shop_price;
    @Expose private String img1;
    @Expose private String goods_name;
    @Expose private String url;

    public void genSign(){
        String str = (!TextUtils.isEmpty(goods_id)?"goods_id=" + goods_id :"");
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

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
