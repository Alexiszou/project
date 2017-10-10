package com.elftree.mall.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/10.
 */

public class MyBottomSheetDialogFragment<T> extends BottomSheetDialogFragment {
    public static final String KEY_DATA = "data";
    public static final String KEY_LAYOUT_ID = "layout_id";
    public static final String KEY_BR_ID = "br_id";



    private Context mContext;
    private T mDatas;
    private int layoutId;
    private int brId;
    public static MyBottomSheetDialogFragment newInstance(Bundle bundle) {
        MyBottomSheetDialogFragment fragment = new MyBottomSheetDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    private void initDatas(){
        mDatas = (T)getArguments().getSerializable(KEY_DATA);
        layoutId = getArguments().getInt(KEY_LAYOUT_ID);
        brId = getArguments().getInt(KEY_BR_ID);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding mBinding = DataBindingUtil.inflate(inflater,layoutId,container,false);
        mBinding.setVariable(brId,mDatas);
        mBinding.executePendingBindings();
        return mBinding.getRoot();
    }
}
