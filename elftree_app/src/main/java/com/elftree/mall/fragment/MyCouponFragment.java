package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.databinding.FragmentMyCouponBinding;
import com.elftree.mall.model.Coupon;
import com.elftree.mall.model.User;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/24.
 */

public class MyCouponFragment extends BaseFragment {
    public static final String KEY_URL = "url";
    private FragmentMyCouponBinding mBinding;
    private List<Coupon> mCouponList;
    private MyRecyclerAdapter mAdapter;
    private String mUrl = "";

    public static MyCouponFragment newInstance(Bundle bundle) {

        MyCouponFragment fragment = new MyCouponFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void initDatas() {
        mCouponList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle != null){
            mUrl = bundle.getString(KEY_URL);
        }
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_coupon,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mAdapter = new MyRecyclerAdapter(mContext,mCouponList,R.layout.layout_coupon_item, BR.coupon);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerview.setAdapter(mAdapter);
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
                    public void onNext(Ba o) {

                    }*/
                });
    }

    private void refreshViews(List<Coupon> list){
        mAdapter.addItem(list);
    }
    @Override
    public void onClick(View v) {

    }
}
