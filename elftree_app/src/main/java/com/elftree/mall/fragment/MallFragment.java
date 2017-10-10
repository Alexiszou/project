package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.databinding.FragmentMallBinding;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 商城
 */

public class MallFragment extends BaseFragment {

    private FragmentMallBinding mBinding;
    private List<String> mTabTitleList;
    public static MallFragment newInstance(Bundle bundle){
        MallFragment fragment = new MallFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {
        mTabTitleList = Arrays.asList(getResources().getStringArray(R.array.mall_fragment_tab_title_array));
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mall,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mBinding.viewpager.setAdapter(new MyAdapter(getChildFragmentManager()));
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
            bundle.putString("title",mTabTitleList.get(position));
            return MallSortFragment.newInstance(bundle);
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
