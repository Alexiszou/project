package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityAddAddressBinding;
import com.elftree.mall.databinding.ActivityAddressBinding;
import com.elftree.mall.fragment.SelectRegionDialogFragment;
import com.elftree.mall.fragment.SelectSpecDialogFragment;
import com.elftree.mall.greendao.RegionDao;
import com.elftree.mall.model.Address;
import com.elftree.mall.model.Region;
import com.elftree.mall.model.User;
import com.elftree.mall.utils.CommonUtil;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import org.w3c.dom.Entity;

import java.net.URLEncoder;

import retrofit2.Retrofit;

/**
 * Created by zouhongzhi on 2017/10/18.
 */

public class AddOrEditAddressActivity extends BaseActivity {
    public static final int TYPE_ADD = 0x0000;
    public static final int TYPE_EDIT = 0x0001;
    public static final String KEY_DATA = "data";
    public static final String KEY_TYPE = "type";

    private int mType = TYPE_ADD;
    private ActivityAddAddressBinding mBinding;
    private Address mAddress;
    private String mUrl = "";

    @Override
    public void initDatas(Bundle savedInstanceState) {
        Bundle bundle =getIntent().getExtras();
        if(bundle != null){
            mAddress = (Address)bundle.getSerializable(KEY_DATA);
            mType = bundle.getInt(KEY_TYPE);
        }else {
            mAddress = new Address();
            mType = TYPE_ADD;
        }
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);

        if(mType == TYPE_ADD){
            mBinding.setTitle(getResources().getString(R.string.add_new_address));
            mUrl = NetConfig.ADD_ADDRESS;
        }else{
            mBinding.setTitle(getResources().getString(R.string.edit_address));
            mUrl = NetConfig.EDIT_ADDRESS;
            mBinding.checkboxDefault.setVisibility(View.GONE);
            refreshLocation();
        }
        mBinding.setNext(getResources().getString(R.string.save));
        mBinding.setClickEvent(this);
        mBinding.setAddress(mAddress);

        mBinding.checkboxDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mAddress.setIf_default("1");
                }else{
                    mAddress.setIf_default("0");
                }
            }
        });
        getRegionList();

    }

    private void getRegionList(){

    }

    private void showSelectRegionDialog(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectRegionDialogFragment.KEY_ADDRESS,mAddress);
        SelectRegionDialogFragment fragment = SelectRegionDialogFragment.newInstance(bundle);
        fragment.show(getSupportFragmentManager(),"selectRegion");
        fragment.setmOnDismissListener(new SelectRegionDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(Address address) {
                mAddress.setProvince(address.getProvince());
                mAddress.setCity(address.getCity());
                mAddress.setArea(address.getArea());
                refreshLocation();
            }
        });
    }

    private void refreshLocation(){

        String province = MyApplication.getInstances().
                getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Region_id.eq(mAddress.getProvince()))
                .list().get(0).getRegion_name();
        String city = MyApplication.getInstances().
                getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Region_id.eq(mAddress.getCity()))
                .list().get(0).getRegion_name();
        String area = MyApplication.getInstances().
                getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Region_id.eq(mAddress.getArea()))
                .list().get(0).getRegion_name();
        String location = province+" "+city+" "+area;
        mBinding.edittextLocation.setText(location);
    }

    private void saveAddress() {
        mAddress.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        mAddress.setAdd_time(null);
        mAddress.setAddress(null);
        mAddress.setZip_code(null);
        try {
            String receiver = mBinding.edittextConsignee.getText().toString();
            if (TextUtils.isEmpty(receiver)) {
                ToastUtil.showShortToast(mContext, R.string.consignee_hint);
                return;
            }
            mAddress.setReceiver(URLEncoder.encode(receiver, "UTF-8"));
            String phone = mBinding.edittextConsigneePhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShortToast(mContext, R.string.consignee_phone_hint);
                return;
            } else if (!CommonUtil.isMobileNO(phone)) {
                ToastUtil.showShortToast(mContext, R.string.wrong_phone_hint);
                return;
            }
            mAddress.setMobile(phone);

            if (TextUtils.isEmpty(mAddress.getProvince())) {
                ToastUtil.showShortToast(mContext, R.string.location_hint);
                return;
            }

            String detail = mBinding.editextAddressDetail.getText().toString();
            if (TextUtils.isEmpty(detail)) {
                ToastUtil.showShortToast(mContext, R.string.address_detail_hint);
                return;
            } else if (detail.length() < getResources().getInteger(R.integer.pwd_mini_length)) {
                ToastUtil.showShortToast(mContext, R.string.address_detail_hint);
                return;
            }
            mAddress.setParticular(URLEncoder.encode(detail, "UTF-8"));
        }catch (Exception e){
            Logger.e(e.toString());
        }

        Logger.d(mAddress.toString());


        RetrofitClient.getInstance().createBaseApi()
                .json(mUrl,mAddress.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        ToastUtil.showShortToast(mContext,response.getMsg());
                        if(response.isOk()){
                            finish();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectLocation:
                showSelectRegionDialog();
                break;
            case R.id.btn_next:
                saveAddress();
                break;

        }
    }
}
