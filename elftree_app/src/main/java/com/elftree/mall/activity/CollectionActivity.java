package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityCollectionBinding;
import com.elftree.mall.databinding.LayoutCartItemBinding;
import com.elftree.mall.databinding.LayoutCollectionItemBinding;
import com.elftree.mall.model.Cart;
import com.elftree.mall.model.Collection;
import com.elftree.mall.model.Goods;
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

/**
 * Created by zouhongzhi on 2017/10/13.
 */

public class CollectionActivity extends BaseActivity {

    private ActivityCollectionBinding mBinding;
    private int mCurPage = CategoryActivity.START_PAGE_INDEX;
    private double mTotalPage;
    private boolean isLoadCompleted;
    private MyRecyclerAdapter mAdapter;
    private List<Collection.ListBean> mCollectionBeanList;
    private Collection mCollectionData;
    private int lastVisibleItem;
    private boolean isSelectAllChecked;


    @Override
    public void initDatas(Bundle savedInstanceState) {
        mCollectionBeanList = new ArrayList<>();
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
        mBinding.setTitle(getResources().getString(R.string.my_collection));
        mBinding.setNext(getResources().getString(R.string.edit));
        mBinding.setClickEvent(this);
        mBinding.refreshlayout.setEnabled(false);

        if(!MyApplication.getInstances().isUserLogin()){
            //未登录
            showNullContainer(true,getResources().getString(R.string.no_login_hint));
            return;
        }
        mAdapter = new MyRecyclerAdapter<>(mContext,
                mCollectionBeanList,
                R.layout.layout_collection_item,
                BR.goods,
                true);
        mBinding.recyclerview.setAdapter(mAdapter);
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

        mAdapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, ViewDataBinding binding, int position) {

                LayoutCollectionItemBinding dataBinding = ((LayoutCollectionItemBinding)binding);

                if(mAdapter.getMode() == MyRecyclerAdapter.MODE_EDIT) {
                    //编辑模式
                    if ((boolean) (mAdapter.getCheckStates().get(position))) {
                        dataBinding.checkbox.setChecked(false);
                        mAdapter.getCheckStates().set(position, false);
                    } else {
                        dataBinding.checkbox.setChecked(true);
                        mAdapter.getCheckStates().set(position, true);
                    }
                    isSelectAllChecked = false;
                    mBinding.checkboxSelectAll.setChecked(false);
                }else if(mAdapter.getMode() == MyRecyclerAdapter.MODE_NORMAL){
                    //普通模式
                    Bundle bundle = new Bundle();
                    Goods goods = new Goods();
                    goods.setGoods_id(mCollectionBeanList.get(position).getGoods_id());
                    bundle.putSerializable("goods",goods);
                    CommonUtil.startActivity(mContext,GoodsInfoActivity.class,bundle);
                }

            }
        });
        getRemoteDatas(true);
    }

    private void getRemoteDatas(final boolean clearOldDatas){
        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        user.setPage(mCurPage+"");
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_COLLECTION_LIST,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        mCollectionData = RetrofitCreator.getGson().fromJson(jsonStr,Collection.class);
                        if(mTotalPage == 0) {
                            mTotalPage = mCollectionData.getPage_total();
                        }
                        refreshViews(mCollectionData.getList(),clearOldDatas);
                    }


                });
    }


    private void refreshViews(List<Collection.ListBean> list,boolean clearOldDatas){
        isSelectAllChecked = false;
        mBinding.checkboxSelectAll.setChecked(false);
        mAdapter.changeLoadStatus(MyRecyclerAdapter.PULLUP_LOAD_MORE);
        if(list == null || list.size() == 0){
            showNullContainer(true,getResources().getString(R.string.null_collection));
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
    private boolean checkLoadCompleted(){
        if(mCurPage >= mTotalPage){
            isLoadCompleted = true;
            mAdapter.changeLoadStatus(MyRecyclerAdapter.NO_MORE_DATA);
        }
        return isLoadCompleted;
    }
    private void showNullContainer(boolean show,String hint){
        if(show){
            mBinding.nullContainer.setVisibility(View.VISIBLE);
            mBinding.textviewHint.setText(hint);
            mBinding.header.btnNext.setVisibility(View.GONE);
            mAdapter.setMode(MyRecyclerAdapter.MODE_NORMAL);
        }else{
            mBinding.nullContainer.setVisibility(View.GONE);
            mBinding.header.btnNext.setVisibility(View.VISIBLE);
        }
    }

    private void setEditMode(){
        //Logger.d("next onclick!!!!!!!"+mAdapter.getMode());
        if(mAdapter.getMode() == MyRecyclerAdapter.MODE_NORMAL) {

            mAdapter.setMode(MyRecyclerAdapter.MODE_EDIT);
            mBinding.header.btnNext.setText(R.string.cancel);
            mBinding.container.setVisibility(View.VISIBLE);
        }else if(mAdapter.getMode() == MyRecyclerAdapter.MODE_EDIT){
            mAdapter.setMode(MyRecyclerAdapter.MODE_NORMAL);
            mBinding.header.btnNext.setText(R.string.edit);
            mAdapter.refreshAllCheckStates(false);
            mBinding.checkboxSelectAll.setChecked(false);
            isSelectAllChecked = false;
            mBinding.container.setVisibility(View.GONE);
        }
    }
    private void selectAllCheckChange(){
        if(isSelectAllChecked){
            mBinding.checkboxSelectAll.setChecked(false);
            isSelectAllChecked = false;
        }else{
            mBinding.checkboxSelectAll.setChecked(true);
            isSelectAllChecked = true;
        }
        mAdapter.refreshAllCheckStates(isSelectAllChecked);

    }


    private SparseBooleanArray mDeleteArray;
    private void deleteCollectionGoods(){
        mDeleteArray = new SparseBooleanArray();
        String goodsId = "";
        for(int i=0;i<mAdapter.getCheckStates().size();i++){
            if((boolean)mAdapter.getCheckStates().get(i)){
                mDeleteArray.put(i,(boolean)mAdapter.getCheckStates().get(i));
                goodsId += mCollectionBeanList.get(i).getGoods_id()+",";
            }
        }
        //Logger.d("delete goodsId:"+goodsId);
        if(mDeleteArray.size() == 0){
            ToastUtil.showShortToast(mContext,R.string.no_select_collection);
            return;
        }

        User user = new User();
        user.setUsername(MyApplication.getInstances().getCurUser().getUsername());
        user.setGoods_id(goodsId);
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.DELETE_COLLECTION_GOODS,user.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        //super.onNext(response);
                        //删除成功
                        deleteItem();
                    }
                });


    }

    private void deleteItem(){
        //Logger.d("deleteItem size:"+mDeleteArray.size());
        for(int i=mDeleteArray.size()-1;i>=0;i--) {
            int key = mDeleteArray.keyAt(i);
            //Logger.d("key:" + key);
            mAdapter.deleteItem(key);

        }
        if (mCollectionBeanList.size() == 0) {
            showNullContainer(true, getResources().getString(R.string.null_collection));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                //Logger.d("next onclick!!!!!!!");
                setEditMode();
                break;
            case R.id.checkbox_select_all:
                selectAllCheckChange();
                break;
            case R.id.btn_delete:
                deleteCollectionGoods();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if(mAdapter.getMode() == MyRecyclerAdapter.MODE_EDIT){
            setEditMode();
        }else{
            super.onBackPressed();
        }
    }
}
