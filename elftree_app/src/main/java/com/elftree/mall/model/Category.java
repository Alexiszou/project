package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public class Category extends CommonModel {

    @Expose
    private String cat_id ;

    @Expose private String style_id;
    @Expose private String brand_id;

    @Expose private String cat_name;
    @Expose private String icon;

    @Expose private String page = "1";

    public void genSign(){
        String str = (!TextUtils.isEmpty(brand_id)?"brand_id=" + brand_id :"")
                +(!TextUtils.isEmpty(cat_id)?"cat_id=" + cat_id :"")
                +(!TextUtils.isEmpty(page)?"&page=" + page :"")
                +(!TextUtils.isEmpty(style_id)?"&style_id=" + style_id :"");

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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
