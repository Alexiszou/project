package com.elftree.mall.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.CategoryActivity;
import com.elftree.mall.activity.CouponActivity;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.activity.SubmitOrderActivity;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentShoppingBinding;
import com.elftree.mall.databinding.FragmentSortBinding;
import com.elftree.mall.databinding.LayoutCartItemBinding;
import com.elftree.mall.model.Cart;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 购物车
 */

public class ShoppingFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener{

    public static final int REQUEST_SUBMIT = 0x0000;
    private FragmentShoppingBinding mBinding;
    private User mCurUser;
    private int mCurPage = CategoryActivity.START_PAGE_INDEX;
    private int mTotalPage;
    private boolean isLoadCompleted = false;
    private int lastVisibleItem;

    private Cart mCartData;
    private List<Cart.ListBean> mCartGoodsList;
    private MyRecyclerAdapter mAdapter;

    private double mTotalPrice;

    public static ShoppingFragment newInstance(Bundle bundle){
        ShoppingFragment fragment = new ShoppingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {
        mCurUser = new User();
        mCartGoodsList = new ArrayList<>();
        Logger.d("shopping fragment init!!!");
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shopping,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        mBinding.setTitle(getString(R.string.shopping_cart));
        //mBinding.setCheckListener(this);
        mBinding.setClickEvent(this);

        if(!MyApplication.getInstances().isUserLogin()){
            //未登录
            showNullContainer(true,getResources().getString(R.string.no_login_hint));
            return;
        }
        mAdapter = new MyRecyclerAdapter<>(mContext,
                mCartGoodsList,
                R.layout.layout_cart_item,
                BR.goods,
                true);
        mAdapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view,ViewDataBinding binding, int position) {

                LayoutCartItemBinding dataBinding = ((LayoutCartItemBinding)binding);
                if(view.getId() == dataBinding.btnDelete.getId()){
                    //ToastUtil.showShortToast(mContext,"delete:"+position);
                    deleteCartGoods(dataBinding,position);
                    return;
                }
                if((boolean)(mAdapter.getCheckStates().get(position))) {
                    dataBinding.checkbox.setChecked(false);
                    mAdapter.getCheckStates().set(position,false);
                    mTotalPrice -= Double.parseDouble(dataBinding.textviewPrice.getText().toString().replace("¥",""));
                }else{
                    dataBinding.checkbox.setChecked(true);
                    mAdapter.getCheckStates().set(position,true);
                    mTotalPrice += Double.parseDouble(dataBinding.textviewPrice.getText().toString().replace("¥",""));
                }
                isSelectAllChecked = false;
                mBinding.checkboxSelectAll.setChecked(false);
                mBinding.textviewTotalAmount.setText(mTotalPrice+"");
            }
        });
        mBinding.recyclerview.setAdapter(mAdapter);
        final LinearLayoutManager lm = new LinearLayoutManager(mContext);
        mBinding.recyclerview.setLayoutManager(lm);


        mBinding.refreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mBinding.refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetStates();
                getRemoteDatas(true);
            }
        });

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


        getRemoteDatas(true);

    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(mCartGoodsList.size() == 0){
            getRemoteDatas(true);
        }
    }*/

    private void getRemoteDatas(final boolean clearOldDatas){
        mCurUser.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        mCurUser.setPage(mCurPage+"");
        RetrofitClient.getInstance()
                .createBaseApi()
                .json(NetConfig.GET_CART_LIST,mCurUser.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                       mCartData = RetrofitCreator.getGson().fromJson(jsonStr,Cart.class);
                        if(mTotalPage == 0) {
                            mTotalPage = mCartData.getPage_total();
                        }

                        refreshViews(mCartData.getList(),clearOldDatas);
                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        mBinding.refreshlayout.setRefreshing(false);
                        super.onNext(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mBinding.refreshlayout.setRefreshing(false);
                    }
                });

    }

    private void refreshViews(List<Cart.ListBean> list,boolean clearOldDatas){
        //mAdapter.setCurPageCheckStates(mCurPage,false);
        isSelectAllChecked = false;
        mBinding.checkboxSelectAll.setChecked(false);
        mAdapter.changeLoadStatus(MyRecyclerAdapter.PULLUP_LOAD_MORE);
        if(list == null || list.size() == 0){
            showNullContainer(true,getResources().getString(R.string.null_shopping_cart));
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
            mBinding.btnGetCoupon.setVisibility(View.GONE);
            mBinding.textviewHint.setText(hint);
        }else{
            mBinding.nullContainer.setVisibility(View.GONE);
            mBinding.btnGetCoupon.setVisibility(View.VISIBLE);
        }
    }

    private void deleteCartGoods(final LayoutCartItemBinding binding,final int position){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        user.setCart_id(mCartGoodsList.get(position).getCart_id());
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.DELETE_CART_GOODS,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        //super.onNext(response);
                        //删除成功
                        if(((boolean)mAdapter.getCheckStates().get(position))) {
                            mTotalPrice -= Double.parseDouble(binding.textviewPrice.getText().toString().replace("¥", ""));
                            mBinding.textviewTotalAmount.setText(mTotalPrice + "");
                        }
                        mAdapter.deleteItem(position);
                        Logger.d("mCartGoodsList.size()"+mCartGoodsList.size());
                        if(mCartGoodsList.size() == 0){
                            showNullContainer(true,getResources().getString(R.string.null_shopping_cart));
                        }
                    }
                });
    }
    private void resetStates(){
        mCurPage = CategoryActivity.START_PAGE_INDEX;
        mTotalPage = 0;
        isLoadCompleted = false;
        isSelectAllChecked = false;
        mBinding.checkboxSelectAll.setChecked(false);
        mTotalPrice = 0;
        mBinding.textviewTotalAmount.setText(mTotalPrice+"");
        mAdapter.getCheckStates().clear();
        mAdapter.notifyDataSetChanged();
    }

    /*private void setSelectAllChecked(boolean checked){
        isSelectAllChecked = checked;
        mBinding.checkboxSelectAll.setChecked(checked);
    }*/
    private boolean checkLoadCompleted(){
        if(mCurPage >= mTotalPage){
            isLoadCompleted = true;
            mAdapter.changeLoadStatus(MyRecyclerAdapter.NO_MORE_DATA);
        }
        return isLoadCompleted;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkbox_select_all:
                selectAllCheckChange();
                break;
            case R.id.btn_settlement:
                settlement();
                break;
            case R.id.btn_get_coupon:
                CommonUtil.startActivity(mContext, CouponActivity.class);
                break;
            default:
                break;
        }
    }

    private boolean isSelectAllChecked = false;
    private void selectAllCheckChange(){
        if(isSelectAllChecked){
            mBinding.checkboxSelectAll.setChecked(false);
            isSelectAllChecked = false;
        }else{
            mBinding.checkboxSelectAll.setChecked(true);
            isSelectAllChecked = true;
        }
        mAdapter.refreshAllCheckStates(isSelectAllChecked);

        mTotalPrice = 0;
        if(!isSelectAllChecked){
            mBinding.textviewTotalAmount.setText(mTotalPrice+"");
        }else{
            for(int i=0;i<mCartGoodsList.size();i++){
                Cart.ListBean bean = mCartGoodsList.get(i);
                mTotalPrice += Double.parseDouble(bean.getGoods_number())*
                        Double.parseDouble(bean.getShop_price());
            }

        }
        mBinding.textviewTotalAmount.setText(mTotalPrice+"");
    }

    private void settlement(){
        Cart cart = new Cart();
        List<Cart.ListBean> list = new ArrayList<>();
        for(int i=0;i<mAdapter.getCheckStates().size();i++){
            if((boolean)mAdapter.getCheckStates().get(i)){
                Cart.ListBean bean = mCartGoodsList.get(i);
                //mCartGoodsList.get(i).setSpec_attr_value(mCartGoodsList.get(i).getSpec_attr_value()+"x"+mCartGoodsList.get(i).getGoods_number());
                /*if(TextUtils.isEmpty(bean.getSpec_attr_value())){
                    bean.setSpec_attr_value(getResources().getString(R.string.no_spec));
                }*/
                list.add(bean);
            }
        }
        if(list != null && list.size()>0){
            cart.setList(list);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SubmitOrderActivity.KEY_DATA,cart);
            bundle.putInt(SubmitOrderActivity.KEY_ORDER,SubmitOrderActivity.CART_ORDER);
            CommonUtil.startActivityForResult(this,SubmitOrderActivity.class,bundle,REQUEST_SUBMIT);
        }else{
            ToastUtil.showShortToast(mContext,R.string.no_buy_goods);
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("requestCode:"+requestCode);
        //Logger.d("resultCode:"+resultCode);
        if(requestCode == REQUEST_SUBMIT){
            if(resultCode == Activity.RESULT_OK){
                getRemoteDatas(true);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
