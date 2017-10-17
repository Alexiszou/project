package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.databinding.ActivitySubmitOrderBinding;
import com.elftree.mall.model.Cart;

/**
 * Created by zouhongzhi on 2017/10/16.
 * 提交订单
 */

public class SubmitOrderActivity extends BaseActivity {

    public static final String KEY_ORDER = "order";
    public static final String KEY_DATA = "data";
    public static final int BUY_NOW_ORDER = 0x0000;
    public static final int CART_ORDER = 0x0001;
    private int mOrderType = BUY_NOW_ORDER;
    private ActivitySubmitOrderBinding mBinding;
    private MyRecyclerAdapter mAdapter;
    private Cart mCart;

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mCart = (Cart)getIntent().getExtras().getSerializable(KEY_DATA);
        mOrderType = getIntent().getExtras().getInt(KEY_ORDER,BUY_NOW_ORDER);
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_submit_order);
        mBinding.setTitle("提交订单");
        mBinding.knowFreight.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

        mAdapter = new MyRecyclerAdapter(mContext,mCart.getList(),R.layout.layout_submit_order_item, BR.goods);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setNestedScrollingEnabled(false);

        //获取收货地址


        //mBinding.totalPay.setText(Html.fromHtml(getString(R.string.total_pay_format,"1688")));
    }

    @Override
    public void onClick(View v) {

    }
}
