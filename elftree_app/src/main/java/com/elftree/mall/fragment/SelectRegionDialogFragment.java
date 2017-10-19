package com.elftree.mall.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.elftree.mall.R;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentSelectRegionBinding;
import com.elftree.mall.greendao.RegionDao;
import com.elftree.mall.model.Address;
import com.elftree.mall.model.Region;
import com.elftree.mall.model.RegionInfo;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/9.
 */

public class SelectRegionDialogFragment extends BottomSheetDialogFragment{

    public static final int TYPE_PROVINCE = 0x0000;
    public static final int TYPE_CITY = 0x0001;
    public static final int TYPE_AREA = 0x0002;
    private int mType = TYPE_PROVINCE;
    public static final String KEY_ADDRESS = "address";
    private FragmentSelectRegionBinding mBinding;
    private Context mContext;
    private List<Region> mProvinceList;
    private List<Region> mCityList;
    private List<Region> mAreaList;

    private Address mCurAddress;


    private String mCurProvinceId="";
    private String mCurCityId="";
    private String mCurAreaId="";
    /*private String mCurProvinceName="";
    private String mCurCityName="";
    private String mCurAreaName="";*/

    private OnDismissListener mOnDismissListener;
    public static SelectRegionDialogFragment newInstance(Bundle bundle){
        SelectRegionDialogFragment fragment  = new SelectRegionDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        initDatas();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_select_region,container,false);
       initViews();
       return mBinding.getRoot();
    }


    private void initDatas(){
        if(getArguments() !=null) {
            mCurAddress = (Address) getArguments().getSerializable(KEY_ADDRESS);
        }


        if(MyApplication.getInstances().getDaoSession().getRegionDao().loadAll().size() == 0){
            getRegionInfo();
        }

        mProvinceList = MyApplication.getInstances()
                .getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Parent_id.eq("1"))
                .list();

        refreshCityDatas();
        refreshAreaDatas();


    }

    private void refreshCityDatas(){
        mCurProvinceId = "";
        if(mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getProvince())) {
            mCurProvinceId = mCurAddress.getProvince();
        }else{
            mCurProvinceId = mProvinceList.get(0).getRegion_id();

        }
        mCityList = MyApplication.getInstances()
                .getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Parent_id.eq(mCurProvinceId))
                .list();

    }

    private void refreshAreaDatas() {
        mCurCityId = "";
        if (mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getCity())) {
            mCurCityId = mCurAddress.getCity();
        } else {
            mCurCityId = mCityList.get(0).getRegion_id();

        }
        mAreaList = MyApplication.getInstances()
                .getDaoSession().getRegionDao().queryBuilder()
                .where(RegionDao.Properties.Parent_id.eq(mCurCityId))
                .list();

        if (mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getArea())) {
            mCurAreaId = mCurAddress.getArea();
        } else {
            mCurAreaId = mAreaList.get(0).getRegion_id();

        }
    }


    private void getRegionInfo(){
        User user = new User();
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_REGION_INFO,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        //Type type = new TypeToken<ArrayList<Region>>(){}.getType();
                        RegionInfo regionInfo = RetrofitCreator.getGson().fromJson(jsonStr,RegionInfo.class);
                        insertRegionInfo(regionInfo);
                    }

                });
    }

    private void insertRegionInfo(RegionInfo info){
        for(Region region :info.getProvince()){
            MyApplication.getInstances().getDaoSession().getRegionDao().insert(region);
        }
        for(Region region :info.getCity()){
            MyApplication.getInstances().getDaoSession().getRegionDao().insert(region);
        }
        for(Region region :info.getArea()){
            MyApplication.getInstances().getDaoSession().getRegionDao().insert(region);
        }
    }
    private void initViews(){
        mBinding.province.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mBinding.city.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mBinding.city.setWrapSelectorWheel(false);
        mBinding.area.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mBinding.area.setWrapSelectorWheel(false);

        refreshViews(TYPE_PROVINCE);
        refreshViews(TYPE_CITY);
        refreshViews(TYPE_AREA);

    }
    private void refreshViews(final int type){
        NumberPicker picker = null;
        List<Region> list = null;
        String curId = "";
        int curIndex = 0;

        if(type == TYPE_PROVINCE){
            picker = mBinding.province;
            list = mProvinceList;
            if(mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getProvince())){
                curId = mCurAddress.getProvince();
            }
        }else if(type == TYPE_CITY){
            picker = mBinding.city;
            list = mCityList;
            if(mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getCity())){
                curId = mCurAddress.getCity();
            }
        }else if(type == TYPE_AREA){
            picker = mBinding.area;
            list = mAreaList;
            if(mCurAddress != null && !TextUtils.isEmpty(mCurAddress.getArea())){
                curId = mCurAddress.getArea();
            }else{
                mCurAddress = new Address();
                mCurAddress.setProvince(mCurProvinceId);
                mCurAddress.setCity(mCurCityId);
                mCurAddress.setArea(mCurAreaId);

            }
        }
        picker.setDisplayedValues(null);
        picker.setMinValue(0);
        picker.setMaxValue(list.size()-1);
        picker.setWrapSelectorWheel(false);//取消循环滚动
        String[] displayValues = new String[list.size()];

        for(int i = 0;i<list.size();i++){
            displayValues[i] = list.get(i).getRegion_name();
            if (list.get(i).getRegion_id().equals(curId)) {
                curIndex = i;
            }

        }

        picker.setDisplayedValues(displayValues);
        picker.setValue(curIndex);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if(type == TYPE_PROVINCE){
                    mCurAddress.setProvince(mProvinceList.get(newVal).getRegion_id());

                    refreshCityDatas();
                    mCurAddress.setCity(mCityList.get(0).getRegion_id());
                    refreshViews(TYPE_CITY);

                    refreshAreaDatas();
                    mCurAddress.setArea(mAreaList.get(0).getRegion_id());
                    refreshViews(TYPE_AREA);
                }else if(type == TYPE_CITY){
                    mCurAddress.setCity(mCityList.get(newVal).getRegion_id());
                    refreshAreaDatas();
                    mCurAddress.setArea(mAreaList.get(0).getRegion_id());
                    refreshViews(TYPE_AREA);
                }else{
                    mCurAddress.setArea(mAreaList.get(newVal).getRegion_id());
                }

            }
        });

    }

    public OnDismissListener getmOnDismissListener() {
        return mOnDismissListener;
    }

    public void setmOnDismissListener(OnDismissListener mOnDismissListener) {
        this.mOnDismissListener = mOnDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mOnDismissListener != null){
            //Logger.d(mCurAddress.toString());
            mOnDismissListener.onDismiss(mCurAddress);
        }
    }

    public interface OnDismissListener{
        void onDismiss(Address address);
    }
}
