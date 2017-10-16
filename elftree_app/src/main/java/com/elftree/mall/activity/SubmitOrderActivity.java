package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.databinding.ActivitySubmitOrderBinding;

/**
 * Created by zouhongzhi on 2017/10/16.
 * 提交订单
 */

public class SubmitOrderActivity extends BaseActivity {

    private ActivitySubmitOrderBinding mBinding;
    private MyRecyclerAdapter mAdapter;

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_submit_order);
        mBinding.setTitle("提交订单");
        mBinding.knowFreight.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    @Override
    public void onClick(View v) {

    }
}
