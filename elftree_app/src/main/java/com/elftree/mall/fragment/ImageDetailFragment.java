package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.activity.LoginActivity;
import com.elftree.mall.databinding.FragmentImageDetailBinding;
import com.elftree.mall.databinding.FragmentMineBinding;
import com.elftree.mall.utils.CommonUtil;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 图片详情
 */

public class ImageDetailFragment extends BaseFragment {

    private FragmentImageDetailBinding mBinding;

    public static ImageDetailFragment newInstance(Bundle bundle){
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_image_detail,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mBinding.textview.setText("图文详情");

    }


    @Override
    public void onClick(View v) {

    }
}
