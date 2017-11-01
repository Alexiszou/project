package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.activity.MyOrderActivity;
import com.elftree.mall.activity.OrderDetailActivity;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentMyCouponBinding;
import com.elftree.mall.databinding.FragmentMyOrderBinding;
import com.elftree.mall.model.Collection;
import com.elftree.mall.model.Coupon;
import com.elftree.mall.model.Order;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/24.
 */

public class MyOrderFragment extends BaseFragment {
    public static final String TYPE_ALL = "all";//全部
    public static final String TYPE_PAY = "pay";//待付款
    public static final String TYPE_SHIP = "ship";//待收货
    public static final String TYPE_CONFIRM = "confirm";//待收货
    public static final String TYPE_COMPLETE = "complete";//完成
    public static final String KEY_TYPE = "type";
    private String mType = TYPE_ALL;
    private FragmentMyOrderBinding mBinding;
    private Order mOrderData;
    private List<Order.ListBean> mOrderList;
    private MyRecyclerAdapter mAdapter;
    private String mUrl = "";
    private int mCurPage = 1;
    private int mTotalPage;
    private int lastVisibleItem;
    private boolean isLoadCompleted;


    public static MyOrderFragment newInstance(Bundle bundle) {

        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void initDatas() {
        mOrderList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle != null){
            mType = bundle.getString(KEY_TYPE);
        }
        mUrl = NetConfig.GET_ORDER_LIST;

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_order,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mAdapter = new MyRecyclerAdapter(mContext,mOrderList,R.layout.layout_my_order_item, BR.orderBean,true);
        final LinearLayoutManager lm = new LinearLayoutManager(mContext);
        mBinding.recyclerview.setLayoutManager(lm);
        mBinding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 ==mAdapter.getItemCount()
                        && !checkLoadCompleted()) {
                    //加载更多
                    mAdapter.changeLoadStatus(MyRecyclerAdapter.LOADING_MORE);
                    ++mCurPage;
                    getRemoteDatas(false);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =lm.findLastVisibleItemPosition();
            }
        });
        mBinding.recyclerview.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, ViewDataBinding binding, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(OrderDetailActivity.KEY_DATA,mOrderList.get(position));
                CommonUtil.startActivityForResult(MyOrderFragment.this, OrderDetailActivity.class,bundle,0);
            }
        });
        getRemoteDatas(true);
    }

    private void getRemoteDatas(final boolean clearOldDatas){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        user.setType(mType);
        user.setPage(mCurPage+"");
        RetrofitClient.getInstance().createBaseApi()
                .json(mUrl,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        mOrderData = RetrofitCreator.getGson().fromJson(jsonStr,Order.class);
                        if(mTotalPage == 0) {
                            mTotalPage = mOrderData.getPage_total();
                        }
                        refreshViews(mOrderData.getList(),clearOldDatas);
                    }
                });
    }

    private void refreshViews(List<Order.ListBean> list,boolean clearOldDatas){

        mAdapter.changeLoadStatus(MyRecyclerAdapter.PULLUP_LOAD_MORE);
        if(list == null || list.size() == 0){
            showNullContainer(true,getResources().getString(R.string.null_order_hint));
            return;
        }
        if(clearOldDatas){
            mAdapter.addItem(list);
        }else {
            mAdapter.addMoreItem(list);
        }
        if(checkLoadCompleted()){
            return;
        }
    }

    private void showNullContainer(boolean show,String hint){
        if(show){
            mBinding.nullContainer.setVisibility(View.VISIBLE);
            mBinding.textviewHint.setText(hint);
        }else{
            mBinding.nullContainer.setVisibility(View.GONE);
        }
    }
    private boolean checkLoadCompleted(){
        if(mCurPage >= mTotalPage){
            isLoadCompleted = true;
            mAdapter.changeLoadStatus(MyRecyclerAdapter.NO_MORE_DATA);
        }
        return isLoadCompleted;
    }
    @Override
    public void onClick(View v) {

    }
}
