package com.elftree.mall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.elftree.mall.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zouhongzhi on 2017/9/21.
 */

public class BlankFragment extends Fragment{
    private List<String> mList;
    public BlankFragment() {
    }

    public static BlankFragment newInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setArguments(bundle);

        return  blankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    private void initDatas(){
        Logger.d("BlankFragment initDatas!!!!!!!!!");
        mList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            mList.add(String.format(Locale.CHINA,"这是第%02d页",i));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewpager = (ViewPager)view.findViewById(R.id.viewpager);
        TabLayout tab = (TabLayout)view.findViewById(R.id.tab);
        viewpager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(viewpager);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        TextView textView = (TextView) view.findViewById(R.id.pager_text);
        textView.setText(getArguments().getString("text"));
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Logger.e("isVisibleToUser:"+isVisibleToUser);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankTestFragment.newInstance(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position);
        }
    }

}
