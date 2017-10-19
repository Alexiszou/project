package com.elftree.mall.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivitySubmitOrderBinding;
import com.elftree.mall.model.Address;
import com.elftree.mall.model.Cart;
import com.elftree.mall.model.User;
import com.elftree.mall.utils.CommonUtil;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/16.
 * 提交订单
 */

public class SubmitOrderActivity extends BaseActivity {

    public static final int REQUEST_ADDRESS_LSIT = 0x0000;
    public static final int REQUEST_ADD_ADDRESS = 0x0001;

    public static final String KEY_ORDER = "order";
    public static final String KEY_DATA = "data";
    public static final int BUY_NOW_ORDER = 0x0000;
    public static final int CART_ORDER = 0x0001;
    private int mOrderType = BUY_NOW_ORDER;
    private ActivitySubmitOrderBinding mBinding;
    private MyRecyclerAdapter mAdapter;
    private Cart mCart;
    private List<Address> mAddressList;
    private Address mSelectAddress;

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
        mBinding.setClickEvent(this);

        mAdapter = new MyRecyclerAdapter(mContext,mCart.getList(),R.layout.layout_submit_order_item, BR.goods);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setNestedScrollingEnabled(false);

        //获取收货地址
        getAddress();

        //mBinding.totalPay.setText(Html.fromHtml(getString(R.string.total_pay_format,"1688")));
    }

    private void getAddress(){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());

        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_ADDRESS_LIST,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        Type type = new TypeToken<ArrayList<Address>>() {}.getType();
                        mAddressList = mGson.fromJson(jsonStr,type);
                        refreshAddress();
                    }

                });
    }

    private void refreshAddress(){
        if(mAddressList != null && mAddressList.size()>0){
            mBinding.nullAddressContainer.setVisibility(View.GONE);
            mBinding.addressContainer.setVisibility(View.VISIBLE);
            for(Address address:mAddressList){
                if(address.isDefault()){
                    mBinding.setAddress(address);
                    mSelectAddress = address;
                }
            }
        }else{
            mBinding.nullAddressContainer.setVisibility(View.VISIBLE);
            mBinding.addressContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.null_address_container:
                CommonUtil.startActivity(mContext,AddOrEditAddressActivity.class);
                break;
            case R.id.address_container:
                CommonUtil.startActivityForResult(this,AddressActivity.class,REQUEST_ADDRESS_LSIT);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取收货地址
        //getAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_ADDRESS_LSIT){
            //从地址列表返回
            if(resultCode == RESULT_OK){
                mSelectAddress = (Address)data.getExtras().getSerializable(AddressActivity.KEY_ADDRESS);
                mBinding.setAddress(mSelectAddress);
            }
        }
    }
}
