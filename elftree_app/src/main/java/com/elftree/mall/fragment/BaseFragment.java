package com.elftree.mall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.R;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.google.gson.Gson;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public Context mContext;
    public Gson mGson;

    /*public static BaseFragment newInstance(Bundle bundle){
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mGson = RetrofitCreator.getGson();
        initDatas();
    }

    public abstract void initDatas();
    public abstract View createView(LayoutInflater inflater,ViewGroup container,Bundle bundle);
    public abstract void initViews(View view,Bundle bundle);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return createView(localInflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view,savedInstanceState);
    }
}
