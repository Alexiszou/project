package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityGoodsInfoBinding;
import com.elftree.mall.fragment.GoodsInfoFragment;
import com.elftree.mall.fragment.ImageDetailFragment;
import com.elftree.mall.handler.ClickTagHandler;
import com.elftree.mall.model.Cart;
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsInfo;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.elftree.mall.utils.StringUtil;
import com.elftree.mall.views.RecyclerViewDivider;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by zouhongzhi on 2017/9/27.
 */

public class GoodsInfoActivity extends BaseActivity {

    private ActivityGoodsInfoBinding mBinding;

    private Goods mBundleGoods;

    private GoodsInfoFragment mGoodsInfoFragment;
    private ImageDetailFragment mImageDetailFragment;






    @Override
    public void initDatas(Bundle savedInstanceState) {
        mBundleGoods = (Goods)getIntent().getExtras().getSerializable("goods");
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_goods_info);
        mBinding.setClickEvent(this);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());

        mBinding.ultraViewPager.setAdapter(adapter);

    }


    private class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle bundle = null;
            switch (position){
                case 0:
                    mGoodsInfoFragment = GoodsInfoFragment.newInstance(getIntent().getExtras());
                    return mGoodsInfoFragment;
                case 1:
                    mImageDetailFragment = ImageDetailFragment.newInstance(bundle);
                    return mImageDetailFragment;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void addToShoppingCart(){
        /*if(mGoodsInfoFragment.getmSpecIdArray() == null){
            ToastUtil.showShortToast(mContext,R.string.hint_select_spec);
            return;
        }*/
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        /*user.setGoods_id(Integer.parseInt(mBundleGoods.getGoods_id()));
        user.setGoods_number(Integer.parseInt(mGoodsInfoFragment.getmQuantity()));*/
        if(mGoodsInfoFragment.getmProductId() != null) {
            user.setGoods_id(mGoodsInfoFragment.getmProductId());
        }else{
            user.setGoods_id(mBundleGoods.getGoods_id());
        }
        user.setGoods_number(mGoodsInfoFragment.getmQuantity());

        if(mGoodsInfoFragment.getmSpecIdArray() != null) {
            user.setSpec_attr(StringUtil.arrayToString(mGoodsInfoFragment.getmSpecIdArray()));
        }
        RetrofitClient.getInstance().
                createBaseApi().
                json(NetConfig.ADD_TO_CART,user.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {

                    @Override
                    public void onSuccess(String response) {

                    }
                });
    }

    private void addToCollection(){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        if(mGoodsInfoFragment.getmProductId() != null) {
            user.setGoods_id(mGoodsInfoFragment.getmProductId());
        }else{
            user.setGoods_id(mBundleGoods.getGoods_id());
        }
        RetrofitClient.getInstance()
                .createBaseApi()
                .json(NetConfig.ADD_TO_COLLECTION,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String response) {

                    }

                });
    }
    private void buyNow(){
        Bundle bundle = new Bundle();

        Cart.ListBean bean = new Cart.ListBean();
        bean.setGoods_name(mGoodsInfoFragment.getmGoodsInfo().getGoods_name());
        if(!TextUtils.isEmpty(mGoodsInfoFragment.getmProductId())) {
            bean.setGoods_id(mGoodsInfoFragment.getmProductId());
        }else{
            bean.setGoods_id(mBundleGoods.getGoods_id());
        }
        bean.setSpec_attr_value(mGoodsInfoFragment.getmSpecText());
        if(mGoodsInfoFragment.getmSpecIdArray() != null){
            bean.setSpec_attr(StringUtil.arrayToStringSort(mGoodsInfoFragment.getmSpecIdArray()));
        }

        bean.setGoods_number(mGoodsInfoFragment.getmQuantity());
        if(mGoodsInfoFragment.getmGoodsInfo().getImg() != null &&
                mGoodsInfoFragment.getmGoodsInfo().getImg().size() >0) {
            bean.setImage(mGoodsInfoFragment.getmGoodsInfo().getImg().get(0));
        }

        if(TextUtils.isEmpty(mGoodsInfoFragment.getmProductPrice())){
            bean.setShop_price(mGoodsInfoFragment.getmGoodsInfo().getShop_price());
        }else{
            bean.setShop_price(mGoodsInfoFragment.getmProductPrice());
        }
        Cart cart = new Cart();
        List<Cart.ListBean> list = new ArrayList<>();
        //for(int i=0;i<10;i++) {
            list.add(bean);
        //}
        cart.setList(list);
        bundle.putSerializable(SubmitOrderActivity.KEY_DATA,cart);
        bundle.putInt(SubmitOrderActivity.KEY_ORDER,SubmitOrderActivity.BUY_NOW_ORDER);
        CommonUtil.startActivity(mContext,SubmitOrderActivity.class,bundle);
    }
    @Override
    public void onClick(View v) {
        if(!MyApplication.getInstances().isUserLogin()){
            //未登录
            CommonUtil.startActivity(mContext,LoginActivity.class,null);
            return;
        }
        switch (v.getId()){

            case R.id.btn_service:
                //客服
                break;
            case R.id.btn_collection:
                addToCollection();
                //收藏
                break;
            case R.id.btn_shopping_cart:
                //购物车
                break;
            case R.id.btn_buy_now:
                buyNow();
                //立即购买
                break;
            case R.id.btn_add_to_shopping_cart:
                //加入购物车
                addToShoppingCart();
                break;
            default:
                break;
        }
    }
}
