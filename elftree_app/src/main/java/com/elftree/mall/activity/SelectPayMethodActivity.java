package com.elftree.mall.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.elftree.mall.R;
import com.elftree.mall.databinding.ActivitySelectPayMethodBinding;

/**
 * Created by zouhongzhi on 2017/10/23.
 */

public class SelectPayMethodActivity extends BaseActivity {

    private ActivitySelectPayMethodBinding mBinding;
    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_pay_method);
        mBinding.setTitle(getResources().getString(R.string.select_pay_method));
        mBinding.setClickEvent(this);
    }


    private void payByZhi(){
        //支付宝支付
    }


    private void payByWechat(){
        //微信支付

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhifubao:
                payByZhi();
                break;
            case R.id.wechat:
                payByWechat();
                break;
        }
    }
}
