package com.elftree.mall.fragment;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.CategoryActivity;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.adapter.MyExpandableAdapter;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentMallBinding;
import com.elftree.mall.databinding.FragmentSortBinding;
import com.elftree.mall.model.Category;
import com.elftree.mall.model.RequestModel;
import com.elftree.mall.model.Series;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;

import com.elftree.mall.utils.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.gson.GsonFactory;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 分类
 */

public class SortFragment extends BaseFragment {

    private FragmentSortBinding mBinding;
    private SeriesFragment mSeriesFragment;
    private SeriesFragment mSpaceFragment;
    private SeriesFragment mCategoryFragment;
    private SeriesFragment mCurFragment;

   // private String mUrl;

    public static SortFragment newInstance(Bundle bundle){
        SortFragment fragment = new SortFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {
        Bundle bundle = new Bundle();
        bundle.putInt(SeriesFragment.KEY_TYPE,SeriesFragment.TYPE_SERIES);
        mSeriesFragment = SeriesFragment.newInstance(bundle);
        mCurFragment = mSeriesFragment;

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sort,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        refreshViews();
        mBinding.rbtnGruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_series:
                        if(mSeriesFragment == null){
                            Bundle bundle = new Bundle();
                            bundle.putInt(SeriesFragment.KEY_TYPE,SeriesFragment.TYPE_SERIES);
                            mSeriesFragment = SeriesFragment.newInstance(bundle);
                        }
                        mCurFragment = mSeriesFragment;
                        break;
                    case R.id.rbtn_space:
                        if(mSpaceFragment == null){
                            Bundle bundle = new Bundle();
                            bundle.putInt(SeriesFragment.KEY_TYPE,SeriesFragment.TYPE_SPACE);
                            mSpaceFragment = SeriesFragment.newInstance(bundle);
                        }
                        mCurFragment = mSpaceFragment;
                        break;
                    case R.id.rbtn_category:
                        if(mCategoryFragment == null){
                            Bundle bundle = new Bundle();
                            bundle.putInt(SeriesFragment.KEY_TYPE,SeriesFragment.TYPE_CATEGORY);
                            mCategoryFragment = SeriesFragment.newInstance(bundle);
                        }
                        mCurFragment = mCategoryFragment;
                        break;
                }
                refreshViews();
            }
        });



    }





    private void refreshViews(){

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.container,mCurFragment);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onClick(View v) {

    }
}
