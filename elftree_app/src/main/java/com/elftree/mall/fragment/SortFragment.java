package com.elftree.mall.fragment;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.CategoryActivity;
import com.elftree.mall.activity.MyApplication;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.FragmentMallBinding;
import com.elftree.mall.databinding.FragmentSortBinding;
import com.elftree.mall.model.Category;
import com.elftree.mall.model.RequestModel;
import com.elftree.mall.retrofit.RetrofitCreator;

import com.elftree.mall.utils.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.gson.GsonFactory;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/22.
 * 分类
 */

public class SortFragment extends BaseFragment {

    private FragmentSortBinding mBinding;
    private List<Category> mCategoryList;

    public static SortFragment newInstance(Bundle bundle){
        SortFragment fragment = new SortFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sort,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initViews(View view, Bundle bundle) {
        RequestModel model = new RequestModel();
        model.setParent_id("0");
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_CATEGORY_LIST,model.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isOk() && response.getData() != null) {
                            Gson gson = RetrofitCreator.getGson();
                            String jsonStr = gson.toJson(response.getData());
                            Logger.d(jsonStr);
                            //mCategoryList = new ArrayList<Category>();

                            Type type = new TypeToken<ArrayList<Category>>() {
                            }.getType();

                            mCategoryList = RetrofitCreator.getGson().fromJson(jsonStr, type);
                            refreshViews();
                        }else{
                            ToastUtil.showShortToast(mContext,response.getMsg());
                        }
                    }

                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.toString());
                        ToastUtil.showShortToast(mContext,e.getMessage());
                    }
                });
        //mBinding.textview.setText("分类");

    }



    private void refreshViews(){
        MyRecyclerAdapter<Category> adapter = new MyRecyclerAdapter<>(mContext,
                mCategoryList,
                R.layout.layout_gridview_item,
                BR.category);

        mBinding.recyclerviewSceneCategory.setLayoutManager(new GridLayoutManager(mContext,4));
        mBinding.recyclerviewSceneCategory.setAdapter(adapter);

        adapter.setmOnItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("category",mCategoryList.get(position));
                CommonUtil.startActivity(mContext,CategoryActivity.class,bundle);

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
