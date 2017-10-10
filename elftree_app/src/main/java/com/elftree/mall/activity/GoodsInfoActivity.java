package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
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
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsInfo;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.elftree.mall.views.RecyclerViewDivider;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/27.
 */

public class GoodsInfoActivity extends BaseActivity {

    private ActivityGoodsInfoBinding mBinding;

    private Goods mBundleGoods;




    @Override
    public void initDatas(Bundle savedInstanceState) {
        mBundleGoods = (Goods)getIntent().getExtras().getSerializable("goods");
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_goods_info);

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
                    fragment = GoodsInfoFragment.newInstance(getIntent().getExtras());
                    break;
                case 1:
                    fragment = ImageDetailFragment.newInstance(bundle);
                    break;
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

    @Override
    public void onClick(View v) {

    }
}
