package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.databinding.FragmentMallSortBinding;
import com.elftree.mall.databinding.FragmentSortBinding;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 商城分类
 */

public class MallSortFragment extends BaseFragment {

    private FragmentMallSortBinding mBinding;

    public static MallSortFragment newInstance(Bundle bundle){
        MallSortFragment fragment = new MallSortFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mall_sort,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {

        mBinding.textview.setText(getArguments().getString("title"));
    }


    @Override
    public void onClick(View v) {

    }
}
