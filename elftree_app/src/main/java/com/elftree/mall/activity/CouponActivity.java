package com.elftree.mall.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityCouponBinding;
import com.elftree.mall.model.Coupon;
import com.elftree.mall.model.Region;
import com.elftree.mall.model.User;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/24.
 */

public class CouponActivity extends BaseActivity {
    public static final String KEY_COUPON = "coupon";
    public static final String KEY_TOTAL_PRICE = "total_price";
    public static final int TYPE_GET_ALL_COUPON = 0x0000;
    public static final int TYPE_GET_VALID_COUPON = 0x0001;
    public static final String KEY_TYPE = "type";


    private int mType = TYPE_GET_ALL_COUPON;
    private ActivityCouponBinding mBinding;
    private String mUrl;
    private List<Coupon> mCouponList;
    private MyRecyclerAdapter mAdapter;

    private Coupon mCurCoupon;
    private double mTotalPrice;
    @Override
    public void initDatas(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mType = bundle.getInt(KEY_TYPE, TYPE_GET_ALL_COUPON);
            mCurCoupon = (Coupon)bundle.getSerializable(KEY_COUPON);
            mTotalPrice = bundle.getDouble(KEY_TOTAL_PRICE);
        }
        if (mType == TYPE_GET_ALL_COUPON) {
            mUrl = NetConfig.GET_COUPON_LIST;
        } else if (mType == TYPE_GET_VALID_COUPON) {
            mUrl = NetConfig.GET_VALID_COUPON;
        }
        mCouponList = new ArrayList<>();
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);
        if(mType == TYPE_GET_ALL_COUPON) {
            mBinding.setTitle(getResources().getString(R.string.coupon_center));
        }else if(mType == TYPE_GET_VALID_COUPON){
            mBinding.setTitle(getResources().getString(R.string.select_coupon));
        }
        mAdapter = new MyRecyclerAdapter(mContext,mCouponList,R.layout.layout_coupon_item, BR.coupon);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));


        mAdapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, ViewDataBinding binding, int position) {
                if(mType == TYPE_GET_ALL_COUPON) {
                    receiveCoupon(position);
                }else if(mType ==TYPE_GET_VALID_COUPON){

                    if(mTotalPrice >= Double.parseDouble(mCouponList.get(position).getUsed_goods_price())) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_COUPON, mCouponList.get(position));
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        ToastUtil.showShortToast(mContext,R.string.select_invalid_coupon);
                    }
                }
            }
        });
        getRemoteDatas();
    }


    private void getRemoteDatas(){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        RetrofitClient.getInstance().createBaseApi()
                .json(mUrl,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        Type type = new TypeToken<ArrayList<Coupon>>(){}.getType();
                        List<Coupon> list = mGson.fromJson(jsonStr,type);
                        refreshViews(list);
                    }

                    /*@Override
                    public void onNext(Object o) {

                    }*/
                });
    }

    private void refreshViews(List<Coupon> list){
        mAdapter.addItem(list);
    }

    private void receiveCoupon(int index){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        user.setCoupon_id(mCouponList.get(index).getCoupon_id());
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.RECEIVE_COUPON,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        ToastUtil.showShortToast(mContext,response.getMsg());
                        if(response.isOk()){
                            getRemoteDatas();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {

    }
}
