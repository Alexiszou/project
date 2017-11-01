package com.elftree.mall.config;

/**
 * Created by zouhongzhi on 2017/8/24.
 */

public class NetConfig {

    public static final String BASE_URL = "http://www.elftree.cn/";

    public static final String REGISTER_GET_VERIFICATION_CODE = "/Api/Sms/register";//用户注册，获取注册验证码短信接口

    public static final String CHECK_MOBILE_NUMBER = "/Api/User/mobileCheck";//手机注册验证手机合法性, 检查手机号码是否存在

    public static final String CHECK_VERIFICATION_CODE = "/Api/User/checkCode";//验证手机验证码的合法性, 通用

    public static final String USER_REGISTER = "/Api/User/register";//用户注册接口

    public static final String USER_LOGIN = "/Api/User/login";//用户登录接口

    public static final String FORGET_PWD_GET_VERIFICATION_CODE = "/Api/Sms/smsPwdForget";//找回密码，获取手机验证码接口

    public static final String RESET_PWD = "/Api/User/forgetPassword";//用户找回密码, 重置密码接口

    public static final String RESET_USER_INFO = "/Api/User/editUser";//修改昵称, 修改密码接口

    public static final String GET_CATEGORY_LIST = "/Api/Cate/cateList";//商品分类列表接口

    public static final String GET_GOODS_LIST = "/Api/Goods/goodsList";//获取商品列表信息

    public static final String GET_GOODS_INFO = "/Api/Goods/goodsInfo";//获取商品详情

    public static final String ADD_TO_CART = "/Api/Cart/cartAdd";//加入购物车,已有购物车中数量增减也可以使用该接口

    public static final String ADD_TO_COLLECTION = "/Api/Collect/collectAdd";//添加商品到收藏列表

    public static final String GET_CART_LIST = "/Api/Cart/cartGoodsList";//获取购物车中商品列表

    public static final String DELETE_CART_GOODS = "/Api/Cart/cartDel";//移除购物车中数据

    public static final String GET_COLLECTION_LIST = "/Api/Collect/collectList";//我的收藏, 商品收藏

    public static final String DELETE_COLLECTION_GOODS = "/Api/Collect/collectDel";//删除收藏

    public static final String GET_GOODS_COMMENT_LIST = "/Api/Comment/commentList";//商品的评价列表

    public static final String GET_ADDRESS_LIST = "/Api/Address/addressList";//收货地址列表

    public static final String GET_REGION_LIST = "/Api/Address/regionList";//获取地区信息

    public static final String GET_REGION_INFO = "/Api/Address/regionInfo";//获取所有地区信息

    public static final String ADD_ADDRESS = "/Api/Address/addressAdd";//添加收货地址

    public static final String EDIT_ADDRESS = "/Api/Address/addressEdit";//编辑收货地址

    public static final String DELETE_ADDRESS = "/Api/Address/addressDel";//删除收货地址

    public static final String BUY_NOW = "/Api/Order/buy/";//通过立即购买生成订单

    public static final String ORDER_BUY = "/Api/Order/orderAdd";//添加订单, 通过购物车添加

    public static final String GET_COUPON_LIST = "/Api/Coupon/couponList";//获取可领取的优惠券列表

    public static final String RECEIVE_COUPON= "/Api/Coupon/getCoupon";//领取优惠券

    public static final String GET_VALID_COUPON= "/Api/Coupon/myCouponNotUsed";//获取可使用的优惠券

    public static final String GET_USED__COUPON= "/Api/Coupon/myCouponUsed";//获取已使用的优惠券

    public static final String GET_INVALID_COUPON= "/Api/Coupon/myCouponExpired";//获取已过期的优惠券

    public static final String GET_ORDER_LIST= "/Api/Order/orderList";//我的订单列表









    public static final String KEY_TIME_STAMP = "TimeStamp";
    public static final String KEY_SIGN = "sign";
    public static final String KEY_MOBILE_NUMBER = "mobile";
    public static final String KEY_CHECK_CODE = "checkcode";


}
