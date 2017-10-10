package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.JsonConfig;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityCategoryBinding;
import com.elftree.mall.model.Category;
import com.elftree.mall.model.Goods;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.utils.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zouhongzhi on 2017/9/25.
 */

public class CategoryActivity extends BaseActivity {
    private ActivityCategoryBinding mBinding;

    public static final int START_PAGE_INDEX = 1;
    private List<Goods> mGoodsList;
    private Category mCategory;
    private int mCurPage = START_PAGE_INDEX;
    private int mTotalPage;

    private int lastVisibleItem;
    private MyRecyclerAdapter mAdapter;
    private boolean isLoadCompleted = false;

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mCategory = (Category)getIntent().getExtras().getSerializable("category");
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        mBinding.setTitle(mCategory.getCat_name());
        mGoodsList = new ArrayList<>();
        mAdapter = new MyRecyclerAdapter<>(mContext,
                mGoodsList,
                R.layout.layout_gridview_item_large,
                BR.goods,
                true);

        //初始化布局管理器
        final GridLayoutManager lm = new GridLayoutManager(this,2);

        /*
        *设置SpanSizeLookup，它将决定view会横跨多少列。这个方法是为RecyclerView添加Header和Footer的关键。
        *当判断position指向的View为Header或者Footer时候，返回总列数（ lm.getSpanCount()）,即可让其独占一行。
        */
        lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isFooter(position) ? lm.getSpanCount() : 1;
            }
        });
        mBinding.recyclerview.setLayoutManager(lm);
        mBinding.recyclerview.setAdapter(mAdapter);


        mBinding.recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods",mGoodsList.get(position));
                CommonUtil.startActivity(mContext,GoodsInfoActivity.class,bundle);

            }
        });
        mBinding.refreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mBinding.refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetStates();
                getRemoteDatas(true);
            }
        });
        getRemoteDatas(false);

    }


    private void resetStates(){
        mCurPage = START_PAGE_INDEX;
        mTotalPage = 0;
        isLoadCompleted = false;
    }

    private boolean checkLoadCompleted(){
        if(mCurPage >= mTotalPage){
            isLoadCompleted = true;
            mAdapter.changeLoadStatus(MyRecyclerAdapter.NO_MORE_DATA);
        }
        return isLoadCompleted;
    }

    private void getRemoteDatas(final boolean clearOldDatas){
        Category category = new Category();
        category.setCat_id(mCategory.getCat_id());
        category.setPage(mCurPage);
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_GOODS_LIST,category.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {
                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.toString());
                        ToastUtil.showShortToast(mContext,e.getMessage());
                        mBinding.refreshlayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        mBinding.refreshlayout.setRefreshing(false);
                        if(response.isOk() && response.getData() != null) {
                            Gson gson = RetrofitCreator.getGson();
                            String jsonStr = gson.toJson(response.getData());

                            Logger.d(jsonStr);
                            JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
                            if(mTotalPage == 0)
                                mTotalPage = jsonObject.getAsJsonPrimitive(JsonConfig.KEY_PAGE_TOTAL).getAsInt();
                            Logger.d("page_total:"+mTotalPage);
                            //Logger.d("page_total:"+jsonObject.getAsJsonPrimitive("page_total").getAsInt());

                            Type type = new TypeToken<List<Goods>>() {
                            }.getType();

                            List<Goods> list= RetrofitCreator.getGson().fromJson(
                                    jsonObject.getAsJsonArray(JsonConfig.KEY_LIST),
                                    type);

                            refreshViews(list,clearOldDatas);
                        }else{
                            ToastUtil.showShortToast(mContext,response.getMsg());
                        }
                    }
                });
    }


    private void refreshViews(List<Goods> list,boolean clearOldDatas){

        mAdapter.changeLoadStatus(MyRecyclerAdapter.PULLUP_LOAD_MORE);
        if(list == null || list.size() == 0){
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

    @Override
    public void onClick(View v) {

    }
}
