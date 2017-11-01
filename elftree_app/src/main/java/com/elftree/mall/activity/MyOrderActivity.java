package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityMyCouponBinding;
import com.elftree.mall.databinding.ActivityMyOrderBinding;
import com.elftree.mall.fragment.MyCouponFragment;
import com.elftree.mall.fragment.MyOrderFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/30.
 */

public class MyOrderActivity extends BaseActivity {
    public static final int TYPE_ALL = 0x0000;
    public static final int TYPE_PAY = 0x0001;
    public static final int TYPE_SEND = 0x0002;
    public static final int TYPE_RECEIVE = 0x0003;
    public static final int TYPE_EVALUATE = 0x0004;
    public static final String KEY_TYPE = "type";
    private int mType = TYPE_ALL;
    private ActivityMyOrderBinding mBinding;
    private List<String> mTabTitleList;
    @Override
    public void initDatas(Bundle savedInstanceState) {
        mTabTitleList = Arrays.asList(getResources().getStringArray(R.array.my_order_tab_title_array));
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            mType = bundle.getInt(KEY_TYPE);
        }
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_order);
        mBinding.setTitle(getResources().getString(R.string.my_order));

        mBinding.viewpager.setAdapter(new MyOrderActivity.MyAdapter(getSupportFragmentManager()));
        mBinding.tab.setupWithViewPager(mBinding.viewpager);
        mBinding.viewpager.setOffscreenPageLimit(5);
        mBinding.viewpager.setCurrentItem(mType);
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position){
                case 0:
                    bundle.putString(MyOrderFragment.KEY_TYPE, MyOrderFragment.TYPE_ALL);
                    break;
                case 1:
                    bundle.putString(MyOrderFragment.KEY_TYPE, MyOrderFragment.TYPE_PAY);
                    break;
                case 2:
                    bundle.putString(MyOrderFragment.KEY_TYPE, MyOrderFragment.TYPE_SHIP);
                    break;
                case 3:
                    bundle.putString(MyOrderFragment.KEY_TYPE, MyOrderFragment.TYPE_CONFIRM);
                    break;
                case 4:
                    bundle.putString(MyOrderFragment.KEY_TYPE, MyOrderFragment.TYPE_COMPLETE);
                    break;
            }

            return MyOrderFragment.newInstance(bundle);
        }

        @Override
        public int getCount() {
            return mTabTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitleList.get(position);
        }
    }
}
