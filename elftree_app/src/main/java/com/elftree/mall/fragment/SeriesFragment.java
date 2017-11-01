package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.elftree.mall.R;
import com.elftree.mall.adapter.MyExpandableAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentSeriesBinding;
import com.elftree.mall.model.Series;
import com.elftree.mall.model.User;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zouhongzhi on 2017/9/21.
 */

public class SeriesFragment extends BaseFragment{

    public static final String KEY_URL = "url";
    private String mUrl = "";
    private FragmentSeriesBinding mBinding;
    private List<Series> mSeriesList;
    private MyExpandableAdapter mExpandableAdapter;
    public static SeriesFragment newInstance(Bundle bundle) {
        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {
        mUrl = getArguments().getString(KEY_URL, NetConfig.GET_SERIES_LIST);
        mSeriesList = new ArrayList<>();
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_series,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mExpandableAdapter = new MyExpandableAdapter(mContext,mSeriesList);
        mBinding.expandList.setAdapter(mExpandableAdapter);
        mBinding.expandList.setGroupIndicator(null);
        mBinding.expandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        getRemoteDatas();
    }
    private void getRemoteDatas(){

        User user = new User();
        RetrofitClient.getInstance().createBaseApi()
                .json(mUrl,user.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {


                    @Override
                    public void onSuccess(String response) {
                        Logger.d(response);
                        Type type = new TypeToken<ArrayList<Series>>(){}.getType();
                        List<Series> list = mGson.fromJson(response,type);
                        refreshViews(list);
                    }
                });
    }


    private void refreshViews(List<Series> list){
        /*mSeriesList.clear();
        mSeriesList.addAll(list);
        mExpandableAdapter.notifyDataSetChanged();*/

        mExpandableAdapter = new MyExpandableAdapter(mContext,list);
        mBinding.expandList.setAdapter(mExpandableAdapter);
    }
    @Override
    public void onClick(View v) {

    }
}
