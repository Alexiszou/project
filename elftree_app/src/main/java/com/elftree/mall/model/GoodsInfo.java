package com.elftree.mall.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/27.
 */

public class GoodsInfo extends CommonModel{


    /**
     * goods_sn : E31177D4-BE55-7D53-347B-C76487909198
     * goods_name : 纳维亚・二居室套餐
     * sell_num : 23
     * goods_number : 100
     * weight : 0.00
     * volume : 0.00
     * market_price : 15998.00
     * shop_price : 7999.00
     * goods_desc_mobile :
     * spec_id : 99
     * goods_desc :
     * img : ["http://www.elftree.cn/Uploads/thumb/20170721/s_5971b4cf01033.jpg","http://www.elftree.cn/Uploads/thumb/20170721/s_5971b4cf1ba8a.jpg","http://www.elftree.cn/Uploads/thumb/20170721/s_5971b4cf355d3.jpg","http://www.elftree.cn/Uploads/thumb/20170721/s_5971b4cf4faa5.jpg","http://www.elftree.cn/Uploads/thumb/20170721/s_5971b4cf6b04e.jpg"]
     * product_id : 333
     * products : [{"product_id":"333","goods_id":"59","product_number":"100","product_price":"7999.00","spec_attr":"101","spec_attr_value":"","product_sn":"","if_default":"1","img":["test"]},{"product_id":"334","goods_id":"59","product_number":"100","product_price":"7999.00","spec_attr":"100","spec_attr_value":"","product_sn":"","if_default":"0","img":["test"]}]
     * goods_specal : [{"key":"搭配","list":[{"spec_name":"客厅+卧室+餐厅+书房","spec_id":"100","sort":"1","is_group":"0","group_id":"99","status":"1","is_selected":0},{"spec_name":"客厅+主卧+次卧+餐厅","spec_id":"101","sort":"2","is_group":"0","group_id":"99","status":"1","is_selected":1}]}]
     */

    @Expose
    private String goods_sn;
    @Expose
    private String goods_name;
    @Expose
    private String sell_num;
    @Expose
    private String goods_number;
    @Expose
    private String weight;
    @Expose
    private String volume;
    @Expose
    private String market_price;
    @Expose
    private String shop_price;
    @Expose
    private String goods_desc_mobile;
    @Expose
    private String spec_id;
    @Expose
    private String goods_desc;
    @Expose
    private String product_id;
    @Expose
    private List<String> img;
    @Expose
    private List<ProductsBean> products;
    @Expose
    private List<GoodsSpecalBean> goods_specal;

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getGoods_desc_mobile() {
        return goods_desc_mobile;
    }

    public void setGoods_desc_mobile(String goods_desc_mobile) {
        this.goods_desc_mobile = goods_desc_mobile;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public List<GoodsSpecalBean> getGoods_specal() {
        return goods_specal;
    }

    public void setGoods_specal(List<GoodsSpecalBean> goods_specal) {
        this.goods_specal = goods_specal;
    }

    public static class ProductsBean {
        /**
         * product_id : 333
         * goods_id : 59
         * product_number : 100
         * product_price : 7999.00
         * spec_attr : 101
         * spec_attr_value :
         * product_sn :
         * if_default : 1
         * img : ["test"]
         */
        @Expose
        private String product_id;
        @Expose private String goods_id;
        @Expose private String product_number;
        @Expose private String product_price;
        @Expose private String spec_attr;
        @Expose private String spec_attr_value;
        @Expose private String product_sn;
        @Expose private String if_default;
        @Expose private List<String> img;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getProduct_number() {
            return product_number;
        }

        public void setProduct_number(String product_number) {
            this.product_number = product_number;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
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
            this.spec_attr_value = spec_attr_value;
        }

        public String getProduct_sn() {
            return product_sn;
        }

        public void setProduct_sn(String product_sn) {
            this.product_sn = product_sn;
        }

        public String getIf_default() {
            return if_default;
        }

        public void setIf_default(String if_default) {
            this.if_default = if_default;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }

    public static class GoodsSpecalBean {
        /**
         * key : 搭配
         * list : [{"spec_name":"客厅+卧室+餐厅+书房","spec_id":"100","sort":"1","is_group":"0","group_id":"99","status":"1","is_selected":0},{"spec_name":"客厅+主卧+次卧+餐厅","spec_id":"101","sort":"2","is_group":"0","group_id":"99","status":"1","is_selected":1}]
         */

        @Expose private String key;
        @Expose private List<ListBean> list;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * spec_name : 客厅+卧室+餐厅+书房
             * spec_id : 100
             * sort : 1
             * is_group : 0
             * group_id : 99
             * status : 1
             * is_selected : 0
             */

            @Expose private String spec_name;
            @Expose private String spec_id;
            @Expose private String sort;
            @Expose private String is_group;
            @Expose private String group_id;
            @Expose private String status;
            @Expose private int is_selected;

            public boolean isSelected(){
                return (is_selected == 1 ? true : false);
            }
            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getIs_group() {
                return is_group;
            }

            public void setIs_group(String is_group) {
                this.is_group = is_group;
            }

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getIs_selected() {
                return is_selected;
            }

            public void setIs_selected(int is_selected) {
                this.is_selected = is_selected;
            }
        }
    }
}
