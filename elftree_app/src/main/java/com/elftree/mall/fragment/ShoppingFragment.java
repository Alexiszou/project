package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.databinding.FragmentShoppingBinding;
import com.elftree.mall.databinding.FragmentSortBinding;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 购物车
 */

public class ShoppingFragment extends BaseFragment {

    private FragmentShoppingBinding mBinding;

    public static ShoppingFragment newInstance(Bundle bundle){
        ShoppingFragment fragment = new ShoppingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shopping,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mBinding.textview.setText("购物车");
    }


    @Override
    public void onClick(View v) {

    }
}
