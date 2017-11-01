package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.databinding.ActivityOrderDetailBinding;
import com.elftree.mall.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/31.
 */

public class OrderDetailActivity extends BaseActivity {
    public static final String KEY_DATA = "data";
    private ActivityOrderDetailBinding mBinding;
    private Order.ListBean mOrderBean;
    private List<Order.ListBean.OrderGoodsBean> mOrderGoodsList;

    private MyRecyclerAdapter mAdapter;

    @Override
    public void initDatas(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mOrderBean = (Order.ListBean)bundle.getSerializable(KEY_DATA);
            mOrderGoodsList = mOrderBean.getOrder_goods();
        }
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        mBinding.setTitle(getResources().getString(R.string.order_detail));
        mBinding.setOrderBean(mOrderBean);

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(mContext,
                mOrderGoodsList,
                R.layout.layout_my_order_item_goods,
                BR.orderGoods);
        mBinding.recyclerview.setAdapter(adapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {

    }
}
