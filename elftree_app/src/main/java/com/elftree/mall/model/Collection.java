package com.elftree.mall.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/13.
 */

public class Collection extends CommonModel {

    /**
     * list : [{"id":"23","goods_id":"34","username":"alexis","add_time":"1507881245","goods_name":"斯德尔・角几","img1":"/Uploads/thumb/20170704/595b424c92bfa.jpg","shop_price":"279.00","thumb_s1":"/Uploads/thumb/20170704/s_595b424c92bfa.jpg"},{"id":"22","goods_id":"35","username":"alexis","add_time":"1507881243","goods_name":"斯德尔・电视柜","img1":"/Uploads/thumb/20170704/595b42e78ee3c.jpg","shop_price":"985.00","thumb_s1":"/Uploads/thumb/20170704/s_595b42e78ee3c.jpg"},{"id":"21","goods_id":"36","username":"alexis","add_time":"1507881240","goods_name":"斯德尔・茶几","img1":"/Uploads/thumb/20170704/595b438207117.jpg","shop_price":"738.00","thumb_s1":"/Uploads/thumb/20170704/s_595b438207117.jpg"},{"id":"20","goods_id":"56","username":"alexis","add_time":"1507710598","goods_name":"斯德尔・沙发S1","img1":"/Uploads/thumb/20170816/59940d78dd17b.jpg","shop_price":"1948.00","thumb_s1":"/Uploads/thumb/20170816/s_59940d78dd17b.jpg"}]
     * page_total : 1.0
     */

    @Expose private double page_total;
    @Expose  private List<ListBean> list;

    public double getPage_total() {
        return page_total;
    }

    public void setPage_total(double page_total) {
        this.page_total = page_total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 23
         * goods_id : 34
         * username : alexis
         * add_time : 1507881245
         * goods_name : 斯德尔・角几
         * img1 : /Uploads/thumb/20170704/595b424c92bfa.jpg
         * shop_price : 279.00
         * thumb_s1 : /Uploads/thumb/20170704/s_595b424c92bfa.jpg
         */

        @Expose private String id;
        @Expose private String goods_id;
        @Expose private String username;
        @Expose private String add_time;
        @Expose private String goods_name;
        @Expose private String img1;
        @Expose private String shop_price;
        @Expose private String thumb_s1;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getThumb_s1() {
            return thumb_s1;
        }

        public void setThumb_s1(String thumb_s1) {
            this.thumb_s1 = thumb_s1;
        }
    }
}
