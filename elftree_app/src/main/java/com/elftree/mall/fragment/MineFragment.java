package com.elftree.mall.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.activity.AboutElftreeActivity;
import com.elftree.mall.activity.CollectionActivity;
import com.elftree.mall.activity.LoginActivity;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.activity.MyCouponActivity;
import com.elftree.mall.activity.MyOrderActivity;
import com.elftree.mall.databinding.FragmentMineBinding;
import com.elftree.mall.databinding.FragmentSortBinding;
import com.elftree.mall.model.CommonModel;
import com.elftree.mall.model.User;
import com.elftree.mall.utils.CommonUtil;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 我的
 */

public class MineFragment extends BaseFragment {

    private FragmentMineBinding mBinding;

    public static MineFragment newInstance(Bundle bundle){
        MineFragment fragment = new MineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        if(!MyApplication.getInstances().isUserLogin()){
            User user = MyApplication.getInstances().getCurUser();
            mBinding.name.setText(user.getUsername());
        }else{
            mBinding.name.setText(MyApplication.getInstances().getCurUser().getUsername());
        }
        mBinding.setClickEvent(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.user_container:
                break;
            case R.id.order:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyOrderActivity.KEY_TYPE,MyOrderActivity.TYPE_ALL);
                    CommonUtil.startActivity(mContext, MyOrderActivity.class,bundle);
                }
                break;
            case R.id.waitPay:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyOrderActivity.KEY_TYPE,MyOrderActivity.TYPE_PAY);
                    CommonUtil.startActivity(mContext, MyOrderActivity.class,bundle);
                }
                break;
            case R.id.waitSend:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyOrderActivity.KEY_TYPE,MyOrderActivity.TYPE_SEND);
                    CommonUtil.startActivity(mContext, MyOrderActivity.class,bundle);
                }
                break;
            case R.id.waitReceive:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyOrderActivity.KEY_TYPE,MyOrderActivity.TYPE_RECEIVE);
                    CommonUtil.startActivity(mContext, MyOrderActivity.class,bundle);
                }
                break;
            case R.id.waitEvaluate:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(MyOrderActivity.KEY_TYPE,MyOrderActivity.TYPE_EVALUATE);
                    CommonUtil.startActivity(mContext, MyOrderActivity.class,bundle);
                }
                break;
            case R.id.coupon:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else{
                    CommonUtil.startActivity(mContext,MyCouponActivity.class);
                }
                break;
            case R.id.collection:
                if(!MyApplication.getInstances().isUserLogin()){
                    CommonUtil.startActivity(mContext,LoginActivity.class);
                }else {
                    CommonUtil.startActivity(mContext, CollectionActivity.class);
                }
                break;
            case R.id.share:
                break;
            case R.id.about:
                CommonUtil.startActivity(mContext, AboutElftreeActivity.class);
                break;
            case R.id.setting:
                break;
        }
    }
}
