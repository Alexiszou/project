package com.elftree.mall.model;

import android.text.TextUtils;

import com.elftree.mall.utils.MD5Util;
import com.google.gson.annotations.Expose;
import com.orhanobut.logger.Logger;

import okhttp3.RequestBody;

/**
 * Created by zouhongzhi on 2017/10/21.
 */

public class SubmitOrder extends CommonModel {

    /*username	string	是	用户名
    goods_id	int	是	商品ID
    goods_number	int	是	购买数量
    spec_attr	string	否	规格spec_id集合, 有规格的必须传规格, 没有的可不用传, 多个规格逗号隔开, 如101,103, 而且这个规格必须属于该商品的规格
    invoice_type	int	否	发票参数，发票类型,1:增值税普通发票,2:增值税专用发票,0:不开发票
    taxpayer_num	string	否	发票参数，纳税人识别号
    header_type	int	否	发票参数，抬头类型,1:个人,2:单位
    company_name	string	否	发票参数，单位名称
    invoice_content	string	否	发票参数，发票内容,1:家具明细
    address	string	否	发票参数，注册地址
    phone	string	否	发票参数，注册电话
    bank	string	否	发票参数，开户银行
    bank_num	string	否	发票参数，银行账号
    image	string	否	发票参数，图片,一般纳税人证明, 请转换为base64然后传过来
    addr_id	int	是	收货地址ID
    order_mark	string	否	订单备注
    pay_method	int	是	支付方式：1:支付宝,2:银行汇款
    coupon_id	int	否	优惠券编号*/

    @Expose private String username;
    @Expose private String goods_id;
    @Expose private String goods_number;
    @Expose private String spec_attr;
    @Expose private String invoice_type;
    @Expose private String taxpayer_num;
    @Expose private String header_type;
    @Expose private String company_name;
    @Expose private String invoice_content;
    @Expose private String address;
    @Expose private String phone;
    @Expose private String bank_num;
    @Expose private String image;
    @Expose private String addr_id;
    @Expose private String order_mark;
    @Expose private String pay_method;
    @Expose private String coupon_id;

    public void genSign(){
        String str = (!TextUtils.isEmpty(addr_id)?"addr_id=" + addr_id :"")
                +(!TextUtils.isEmpty(address)?"&address=" + address :"")
                +(!TextUtils.isEmpty(bank_num)?"&bank_num=" + bank_num :"")
                +(!TextUtils.isEmpty(company_name)?"&company_name=" + company_name :"")
                +(!TextUtils.isEmpty(coupon_id)?"&coupon_id=" + coupon_id :"")
                +(!TextUtils.isEmpty(goods_id)?"&goods_id=" + goods_id :"")
                +(!TextUtils.isEmpty(goods_number)?"&goods_number=" + goods_number :"")
                +(!TextUtils.isEmpty(header_type)?"&header_type=" + header_type :"")
                +(!TextUtils.isEmpty(image)?"&image=" + image :"")
                +(!TextUtils.isEmpty(invoice_content)?"&invoice_content=" + invoice_content :"")
                +(!TextUtils.isEmpty(invoice_type)?"&invoice_type=" + invoice_type :"")
                +(!TextUtils.isEmpty(order_mark)?"&order_mark=" + order_mark :"")
                +(!TextUtils.isEmpty(pay_method)?"&pay_method=" + pay_method :"")
                +(!TextUtils.isEmpty(phone)?"&phone=" + phone :"")
                +(!TextUtils.isEmpty(spec_attr)?"&spec_attr=" + spec_attr :"")
                +(!TextUtils.isEmpty(taxpayer_num)?"&taxpayer_num=" + taxpayer_num :"")
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getSpec_attr() {
        return spec_attr;
    }

    public void setSpec_attr(String spec_attr) {
        this.spec_attr = spec_attr;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getTaxpayer_num() {
        return taxpayer_num;
    }

    public void setTaxpayer_num(String taxpayer_num) {
        this.taxpayer_num = taxpayer_num;
    }

    public String getHeader_type() {
        return header_type;
    }

    public void setHeader_type(String header_type) {
        this.header_type = header_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getInvoice_content() {
        return invoice_content;
    }

    public void setInvoice_content(String invoice_content) {
        this.invoice_content = invoice_content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getOrder_mark() {
        return order_mark;
    }

    public void setOrder_mark(String order_mark) {
        this.order_mark = order_mark;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }
}
