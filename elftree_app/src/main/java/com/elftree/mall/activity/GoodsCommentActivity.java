package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityGoodsCommentBinding;
import com.elftree.mall.databinding.LayoutCollectionItemBinding;
import com.elftree.mall.model.Collection;
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsComment;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.net.BaseSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/16.
 * 商品评价列表
 */

public class GoodsCommentActivity extends BaseActivity {
    public static final String KEY_GOODS_ID = "goods_id";
    private String mBundleGoodsId;
    private ActivityGoodsCommentBinding mBinding;
    private MyRecyclerAdapter mAdapter;
    private int mCurPage = CategoryActivity.START_PAGE_INDEX;
    private double mTotalPage;
    private int lastVisibleItem;
    private List<GoodsComment.ListBean> mGoodsCommentList;
    private boolean isLoadCompleted;
    private GoodsComment mGoodsComment;

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mBundleGoodsId = getIntent().getExtras().getString(KEY_GOODS_ID);
        mGoodsCommentList = new ArrayList<>();
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_goods_comment);
        mBinding.setTitle(getResources().getString(R.string.comment));
        mBinding.refreshlayout.setEnabled(false);

        mAdapter = new MyRecyclerAdapter<>(mContext,
                mGoodsCommentList,
                R.layout.layout_goods_comment_item,
                BR.comment,
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

                /*Bundle bundle = new Bundle();
                Goods goods = new Goods();
                goods.setGoods_id(mCollectionBeanList.get(position).getGoods_id());
                bundle.putSerializable("goods",goods);
                CommonUtil.startActivity(mContext,GoodsInfoActivity.class,bundle);*/

            }
        });
        getRemoteDatas(true);
    }

    private void getRemoteDatas(final boolean clearOldDatas){
        Goods goods = new Goods();
        goods.setGoods_id(mBundleGoodsId);
        goods.setPage(mCurPage+"");
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_GOODS_COMMENT_LIST,goods.genRequestBody())
                .subscribe(new BaseSubscriber(mContext) {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Logger.d(jsonStr);
                        mGoodsComment = RetrofitCreator.getGson().fromJson(jsonStr,GoodsComment.class);
                        if(mTotalPage == 0) {
                            mTotalPage = mGoodsComment.getPage_total();
                        }
                        refreshViews(mGoodsComment.getList(),clearOldDatas);
                    }


                });
    }
    private void refreshViews(List<GoodsComment.ListBean> list,boolean clearOldDatas){

        mAdapter.changeLoadStatus(MyRecyclerAdapter.PULLUP_LOAD_MORE);
        if(list == null || list.size() == 0){
            showNullContainer(true,getResources().getString(R.string.null_goods_comment));
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
            mBinding.header.btnNext.setVisibility(View.VISIBLE);
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
