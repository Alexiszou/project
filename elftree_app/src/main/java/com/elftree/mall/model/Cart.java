package com.elftree.mall.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/12.
 */

public class Cart extends CommonModel{

    /**
     * list : [{"cart_id":"1025","goods_id":"56","goods_name":"斯德尔・沙发S1","spec_attr":"","spec_attr_value":"","goods_number":"4","image":"/Uploads/thumb/20170816/s_59940d78dd17b.jpg"}]
     * page_total : 1.0
     */

    @Expose
    private int page_total;
    @Expose
    private List<ListBean> list;

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends CommonModel{
        /**
         * cart_id : 1025
         * goods_id : 56
         * goods_name : 斯德尔・沙发S1
         * spec_attr :
         * spec_attr_value :
         * goods_number : 4
         * image : /Uploads/thumb/20170816/s_59940d78dd17b.jpg
         */

        @Expose private String cart_id;
        @Expose private String goods_id;
        @Expose private String goods_name;
        @Expose private String spec_attr;
        @Expose private String spec_attr_value;
        @Expose private String goods_number;
        @Expose private String image;
        @Expose private String shop_price;

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getSpec_attr() {
            return spec_attr;
        }

        public void setSpec_attr(String spec_attr) {
            this.spec_attr = spec_attr;
        }

        public String getSpec_attr_value() {
            return spec_attr_value;
        }

        public void setSpec_attr_value(String spec_attr_value) {
            /*if(TextUtils.isEmpty(spec_attr_value)){
                this.spec_attr_value = "无规格";
                return;
            }*/
            this.spec_attr_value = spec_attr_value;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "cart_id='" + cart_id + '\'' +
                    ", goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", spec_attr='" + spec_attr + '\'' +
                    ", spec_attr_value='" + spec_attr_value + '\'' +
                    ", goods_number='" + goods_number + '\'' +
                    ", image='" + image + '\'' +
                    ", shop_price='" + shop_price + '\'' +
                    '}';
        }
    }
}
