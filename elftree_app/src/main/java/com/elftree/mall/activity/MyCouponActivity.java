package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityCouponBinding;
import com.elftree.mall.databinding.ActivityMyCouponBinding;
import com.elftree.mall.fragment.MallFragment;
import com.elftree.mall.fragment.MallSortFragment;
import com.elftree.mall.fragment.MyCouponFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/24.
 */

public class MyCouponActivity extends BaseActivity {
    private ActivityMyCouponBinding mBinding;
    private List<String> mTabTitleList;
    @Override
    public void initDatas(Bundle savedInstanceState) {
        mTabTitleList = Arrays.asList(getResources().getStringArray(R.array.my_coupon_tab_title_array));
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_coupon);
        mBinding.setTitle(getResources().getString(R.string.my_coupon));

        mBinding.viewpager.setAdapter(new MyCouponActivity.MyAdapter(getSupportFragmentManager()));
        mBinding.tab.setupWithViewPager(mBinding.viewpager);
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
                    bundle.putString(MyCouponFragment.KEY_URL, NetConfig.GET_VALID_COUPON);
                    break;
                case 1:
                    bundle.putString(MyCouponFragment.KEY_URL, NetConfig.GET_USED__COUPON);
                    break;
                case 2:
                    bundle.putString(MyCouponFragment.KEY_URL, NetConfig.GET_INVALID_COUPON);
                    break;
            }

            return MyCouponFragment.newInstance(bundle);
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
