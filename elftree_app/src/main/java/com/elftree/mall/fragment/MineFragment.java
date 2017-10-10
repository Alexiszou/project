package com.elftree.mall.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.activity.LoginActivity;
import com.elftree.mall.databinding.FragmentMineBinding;
import com.elftree.mall.databinding.FragmentSortBinding;
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
        mBinding.textview.setText("我的");
        mBinding.textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtil.startActivity(mContext,LoginActivity.class,new Bundle());

            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
