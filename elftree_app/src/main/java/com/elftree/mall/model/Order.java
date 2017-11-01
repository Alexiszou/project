package com.elftree.mall.model;

import android.content.Context;

import com.elftree.mall.R;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/30.
 */

public class Order extends CommonModel {

    /**
     * list : [{"order_id":"86","order_sn":"2017081111064149565051","username":"qq123456","total_amount":"601.00","goods_amount":"1.00","total_num":"1","transport_fee":"0.00","freight_fee":"600.00","install_fee":"0.00","other_fee":"0.00","reduce_fee":"0.00","coupon_fee":"0.00","my_coupon_id":"0","order_status":"1","pay_status":"1","shipping_status":"1","confirm_status":"1","shipping_time":null,"add_time":"1502420801","pay_time":null,"confirm_time":null,"pay_method":"1","trade_no":"","order_mark":"","province":"6","city":"77","area":"707","address":"广东,深圳,南山区","particular":"沿山路22号 火炬创业大厦 5楼 精灵树","mobile":"15818730739","email":"","zip_code":"0","receiver":"杨威","from":"1","invoice_header":"","invoice_type":"1","if_reduce":"2","admin_mark":"","invoice_id":"0","is_real":"2","format_total_amount":"601.00","format_add_time":"2017-08-11 11:06:41","order_goods":[{"id":"209","order_id":"86","goods_id":"63","goods_name":"精灵树 补邮费专拍","product_id":"0","spec_attr":"","spec_attr_value":"","product_sn":"2AFC7278-BD76-CB51-5D43-F163AE0B86D7","goods_price":"1.00","number":"1","if_comment":"1","if_share_img":"1","if_tc":"2","tc_serialize":"","format_goods_price":"1.00","img":"http://www.elftree.cn/Uploads/thumb/20170811/s_598d1e5ea7326.jpg"}]}]
     * page_total : 2
     */

    @Expose private int page_total;
    @Expose private List<ListBean> list;

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
         * order_id : 86
         * order_sn : 2017081111064149565051
         * username : qq123456
         * total_amount : 601.00
         * goods_amount : 1.00
         * total_num : 1
         * transport_fee : 0.00
         * freight_fee : 600.00
         * install_fee : 0.00
         * other_fee : 0.00
         * reduce_fee : 0.00
         * coupon_fee : 0.00
         * my_coupon_id : 0
         * order_status : 1
         * pay_status : 1
         * shipping_status : 1
         * confirm_status : 1
         * shipping_time : null
         * add_time : 1502420801
         * pay_time : null
         * confirm_time : null
         * pay_method : 1
         * trade_no :
         * order_mark :
         * province : 6
         * city : 77
         * area : 707
         * address : 广东,深圳,南山区
         * particular : 沿山路22号 火炬创业大厦 5楼 精灵树
         * mobile : 15818730739
         * email :
         * zip_code : 0
         * receiver : 杨威
         * from : 1
         * invoice_header :
         * invoice_type : 1
         * if_reduce : 2
         * admin_mark :
         * invoice_id : 0
         * is_real : 2
         * format_total_amount : 601.00
         * format_add_time : 2017-08-11 11:06:41
         * order_goods : [{"id":"209","order_id":"86","goods_id":"63","goods_name":"精灵树 补邮费专拍","product_id":"0","spec_attr":"","spec_attr_value":"","product_sn":"2AFC7278-BD76-CB51-5D43-F163AE0B86D7","goods_price":"1.00","number":"1","if_comment":"1","if_share_img":"1","if_tc":"2","tc_serialize":"","format_goods_price":"1.00","img":"http://www.elftree.cn/Uploads/thumb/20170811/s_598d1e5ea7326.jpg"}]
         */

        @Expose private String order_id;
        @Expose private String order_sn;
        @Expose private String username;
        @Expose private String total_amount;
        @Expose private String goods_amount;
        @Expose private String total_num;
        @Expose private String transport_fee;
        @Expose private String freight_fee;
        @Expose private String install_fee;
        @Expose private String other_fee;
        @Expose private String reduce_fee;
        @Expose private String coupon_fee;
        @Expose private String my_coupon_id;
        @Expose private String order_status;
        @Expose private String pay_status;
        @Expose private String shipping_status;
        @Expose private String confirm_status;
        @Expose private Object shipping_time;
        @Expose private String add_time;
        @Expose private Object pay_time;
        @Expose private Object confirm_time;
        @Expose private String pay_method;
        @Expose private String trade_no;
        @Expose private String order_mark;
        @Expose private String province;
        @Expose private String city;
        @Expose private String area;
        @Expose private String address;
        @Expose private String particular;
        @Expose private String mobile;
        @Expose private String email;
        @Expose private String zip_code;
        @Expose private String receiver;
        @Expose private String from;
        @Expose private String invoice_header;
        @Expose private String invoice_type;
        @Expose private String if_reduce;
        @Expose private String admin_mark;
        @Expose private String invoice_id;
        @Expose private String is_real;
        @Expose private String format_total_amount;
        @Expose private String format_add_time;
        @Expose private List<OrderGoodsBean> order_goods;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getTransport_fee() {
            return transport_fee;
        }

        public void setTransport_fee(String transport_fee) {
            this.transport_fee = transport_fee;
        }

        public String getFreight_fee() {
            return freight_fee;
        }

        public void setFreight_fee(String freight_fee) {
            this.freight_fee = freight_fee;
        }

        public String getInstall_fee() {
            return install_fee;
        }

        public void setInstall_fee(String install_fee) {
            this.install_fee = install_fee;
        }

        public String getOther_fee() {
            return other_fee;
        }

        public void setOther_fee(String other_fee) {
            this.other_fee = other_fee;
        }

        public String getReduce_fee() {
            return reduce_fee;
        }

        public void setReduce_fee(String reduce_fee) {
            this.reduce_fee = reduce_fee;
        }

        public String getCoupon_fee() {
            return coupon_fee;
        }

        public void setCoupon_fee(String coupon_fee) {
            this.coupon_fee = coupon_fee;
        }

        public String getMy_coupon_id() {
            return my_coupon_id;
        }

        public void setMy_coupon_id(String my_coupon_id) {
            this.my_coupon_id = my_coupon_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(String shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getConfirm_status() {
            return confirm_status;
        }

        public void setConfirm_status(String confirm_status) {
            this.confirm_status = confirm_status;
        }

        public Object getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(Object shipping_time) {
            this.shipping_time = shipping_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public Object getPay_time() {
            return pay_time;
        }

        public void setPay_time(Object pay_time) {
            this.pay_time = pay_time;
        }

        public Object getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(Object confirm_time) {
            this.confirm_time = confirm_time;
        }

        public String getPay_method() {
            return pay_method;
        }

        public void setPay_method(String pay_method) {
            this.pay_method = pay_method;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getOrder_mark() {
            return order_mark;
        }

        public void setOrder_mark(String order_mark) {
            this.order_mark = order_mark;
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

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getInvoice_header() {
            return invoice_header;
        }

        public void setInvoice_header(String invoice_header) {
            this.invoice_header = invoice_header;
        }

        public String getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(String invoice_type) {
            this.invoice_type = invoice_type;
        }

        public String getIf_reduce() {
            return if_reduce;
        }

        public void setIf_reduce(String if_reduce) {
            this.if_reduce = if_reduce;
        }

        public String getAdmin_mark() {
            return admin_mark;
        }

        public void setAdmin_mark(String admin_mark) {
            this.admin_mark = admin_mark;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }

        public String getFormat_total_amount() {
            return format_total_amount;
        }

        public void setFormat_total_amount(String format_total_amount) {
            this.format_total_amount = format_total_amount;
        }

        public String getFormat_add_time() {
            return format_add_time;
        }

        public void setFormat_add_time(String format_add_time) {
            this.format_add_time = format_add_time;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }


        public String getOrderStatus(Context context){
            String str = "";
            if(order_status.equals("1")){
                //正常
                if(pay_status.equals("1")){
                    str = context.getString(R.string.wait_for_pay);
                }else if(shipping_status.equals("1")){
                    str = context.getString(R.string.wait_for_send);
                }else if(confirm_status.equals("1")){
                    str = context.getString(R.string.wait_for_receive);
                }else{
                    str = context.getString(R.string.wait_for_evaluate);
                }
            }else if(order_status.equals("2")){
                //取消
                str = context.getString(R.string.canceled);
            }else if(order_status.equals("3")){
                //删除
                str = context.getString(R.string.deleted);
            }
            return str;
        }
        public static class OrderGoodsBean extends CommonModel{
            /**
             * id : 209
             * order_id : 86
             * goods_id : 63
             * goods_name : 精灵树 补邮费专拍
             * product_id : 0
             * spec_attr :
             * spec_attr_value :
             * product_sn : 2AFC7278-BD76-CB51-5D43-F163AE0B86D7
             * goods_price : 1.00
             * number : 1
             * if_comment : 1
             * if_share_img : 1
             * if_tc : 2
             * tc_serialize :
             * format_goods_price : 1.00
             * img : http://www.elftree.cn/Uploads/thumb/20170811/s_598d1e5ea7326.jpg
             */

            @Expose private String id;
            @Expose private String order_id;
            @Expose private String goods_id;
            @Expose private String goods_name;
            @Expose private String product_id;
            @Expose private String spec_attr;
            @Expose private String spec_attr_value;
            @Expose private String product_sn;
            @Expose private String goods_price;
            @Expose private String number;
            @Expose private String if_comment;
            @Expose private String if_share_img;
            @Expose private String if_tc;
            @Expose private String tc_serialize;
            @Expose private String format_goods_price;
            @Expose private String img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
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

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getIf_comment() {
                return if_comment;
            }

            public void setIf_comment(String if_comment) {
                this.if_comment = if_comment;
            }

            public String getIf_share_img() {
                return if_share_img;
            }

            public void setIf_share_img(String if_share_img) {
                this.if_share_img = if_share_img;
            }

            public String getIf_tc() {
                return if_tc;
            }

            public void setIf_tc(String if_tc) {
                this.if_tc = if_tc;
            }

            public String getTc_serialize() {
                return tc_serialize;
            }

            public void setTc_serialize(String tc_serialize) {
                this.tc_serialize = tc_serialize;
            }

            public String getFormat_goods_price() {
                return format_goods_price;
            }

            public void setFormat_goods_price(String format_goods_price) {
                this.format_goods_price = format_goods_price;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
