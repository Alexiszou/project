package com.elftree.mall.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityAddressBinding;
import com.elftree.mall.databinding.LayoutAddressItemBinding;
import com.elftree.mall.model.Address;
import com.elftree.mall.model.User;
import com.elftree.mall.utils.CommonUtil;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/18.
 */

public class AddressActivity extends BaseActivity {
    public static final String KEY_ADDRESS = "address";
    private ActivityAddressBinding mBinding;
    private MyRecyclerAdapter mAdapter;
    private List<Address> mAddressList;

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mAddressList = new ArrayList<>();
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        mBinding.setTitle(getResources().getString(R.string.my_address));
        mBinding.setClickEvent(this);
        mAdapter = new MyRecyclerAdapter(mContext,mAddressList,R.layout.layout_address_item, BR.address);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerview.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, ViewDataBinding binding, int position) {
                LayoutAddressItemBinding dataBinding = (LayoutAddressItemBinding)binding;
                if(view.getId() == dataBinding.edit.getId()){
                    //编辑地址
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AddOrEditAddressActivity.KEY_DATA,mAddressList.get(position));
                    bundle.putInt(AddOrEditAddressActivity.KEY_TYPE,AddOrEditAddressActivity.TYPE_EDIT);
                    CommonUtil.startActivity(mContext,AddOrEditAddressActivity.class,bundle);
                }else if(view.getId() == dataBinding.delete.getId()){
                    //删除地址
                    deleteAddress(position);
                }
                else{
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ADDRESS,mAddressList.get(position));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }

    private void getRemoteDatas(){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());

        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_ADDRESS_LIST,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        Type type = new TypeToken<ArrayList<Address>>(){}.getType();
                        List<Address> list = mGson.fromJson(jsonStr,type);
                        refreshViews(list);
                    }

                });
    }

    private void refreshViews(List<Address> list){
        mAdapter.addItem(list);
    }

    private void deleteAddress(int index){
        Address address = new Address();
        address.setUsername(mAddressList.get(index).getUsername());
        address.setAddr_id(mAddressList.get(index).getAddr_id());
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.DELETE_ADDRESS,address.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        ToastUtil.showShortToast(mContext,response.getMsg());
                        if(response.isOk()){
                            getRemoteDatas();
                        }
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getRemoteDatas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_address:
                CommonUtil.startActivity(mContext,AddOrEditAddressActivity.class);
                break;
        }
    }


}
