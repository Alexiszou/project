package com.elftree.mall.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/16.
 */

public class GoodsComment extends CommonModel {


    /**
     * list : [{"comment_id":"102","order_id":"134","goods_id":"56","spec_attr":"","username":"ADC123","level":"5","content":"沙发很漂亮，物流帮忙送到家里。安装速度一小会就好了，试着坐了下 感觉好舒服。点个赞。","add_time":"1504513840","status":"1","header_img":"","img":[]},{"comment_id":"72","order_id":"122","goods_id":"56","spec_attr":"","username":"QWE123","level":"5","content":"简约沙发，性价比还可以","add_time":"1503470248","status":"1","header_img":"","img":[]},{"comment_id":"54","order_id":"119","goods_id":"56","spec_attr":"","username":"阳光明媚不抵你笑颜","level":"5","content":"两人坐小沙发，质量很好，软软的。","add_time":"1503365020","status":"1","header_img":"","img":[]}]
     * page_total : 1.0
     */

    @Expose
    private double page_total;
    @Expose private List<ListBean> list;

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
         * comment_id : 102
         * order_id : 134
         * goods_id : 56
         * spec_attr :
         * username : ADC123
         * level : 5
         * content : 沙发很漂亮，物流帮忙送到家里。安装速度一小会就好了，试着坐了下 感觉好舒服。点个赞。
         * add_time : 1504513840
         * status : 1
         * header_img :
         * img : []
         */

        @Expose private String comment_id;
        @Expose private String order_id;
        @Expose private String goods_id;
        @Expose private String spec_attr;
        @Expose private String username;
        @Expose private String level;
        @Expose private String content;
        @Expose private String add_time;
        @Expose private String status;
        @Expose private String header_img;
        @Expose private List<String> img;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getSpec_attr() {
            return spec_attr;
        }

        public void setSpec_attr(String spec_attr) {
            this.spec_attr = spec_attr;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHeader_img() {
            return header_img;
        }

        public void setHeader_img(String header_img) {
            this.header_img = header_img;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
