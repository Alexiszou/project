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
import android.widget.TextView;

import com.elftree.mall.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zouhongzhi on 2017/9/21.
 */

public class BlankTestFragment extends Fragment{
    private List<String> mList;
    public BlankTestFragment() {
    }

    public static BlankTestFragment newInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        BlankTestFragment blankFragment = new BlankTestFragment();
        blankFragment.setArguments(bundle);

        return  blankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    private void initDatas(){
        Logger.d("BlankTestFragment initDatas!!!!!!!!!");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.pager_text);
        textView.setText(getArguments().getString("text"));
    }




}
