package com.elftree.mall.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/11/1.
 */

public class Series extends CommonModel {

    /**
     * style_id : 2
     * style_name : 纳维亚系列
     * style_desc :
     * thumb_s : http://www.elftree.cn/Uploads/style/20171016/s_59e4557fd7530.jpg
     */

    @Expose
    @SerializedName(value = "style_id", alternate = "brand_id")
    private String style_id;

    @Expose
    @SerializedName(value = "style_name", alternate = "brand_name")
    private String style_name;

    @Expose
    @SerializedName(value = "style_desc", alternate = "brand_desc")
    private String style_desc;

    @Expose private String thumb_s;

    @Expose private List<String> childList;

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getStyle_desc() {
        return style_desc;
    }

    public void setStyle_desc(String style_desc) {
        this.style_desc = style_desc;
    }

    public String getThumb_s() {
        return thumb_s;
    }

    public void setThumb_s(String thumb_s) {
        this.thumb_s = thumb_s;
    }

    public List<String> getChildList() {
        return childList;
    }

    public void setChildList(List<String> childList) {
        this.childList = childList;
    }
}
