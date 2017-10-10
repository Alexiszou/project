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




    public static final String KEY_TIME_STAMP = "TimeStamp";
    public static final String KEY_SIGN = "sign";
    public static final String KEY_MOBILE_NUMBER = "mobile";
    public static final String KEY_CHECK_CODE = "checkcode";


}
