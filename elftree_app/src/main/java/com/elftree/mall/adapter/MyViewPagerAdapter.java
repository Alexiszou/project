package com.elftree.mall.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/30.
 */

public class MyViewPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mDatas;
    private int layoutId;
    private int brId;
    private LayoutInflater mInflater;
    private ViewDataBinding mBinding;
    private MyRecyclerAdapter.MyOnItemClickListener mOnItemClickListener;



    public MyViewPagerAdapter(Context context,List<T> mDatas, int layoutId, int brId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mBinding = DataBindingUtil.inflate(mInflater,layoutId,container,false);
        mBinding.setVariable(brId, mDatas.get(position));
        mBinding.executePendingBindings();

        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClickListener(v,mBinding,position);
                }
            }
        });
        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

   @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public MyRecyclerAdapter.MyOnItemClickListener getItemClickListener() {
        return mOnItemClickListener;
    }

    public void setItemClickListener(MyRecyclerAdapter.MyOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    /*@Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(mDatas != null && mDatas.size() != 0) {
            mBinding.setVariable(brId, mDatas.get(position));
            mBinding.executePendingBindings();
        }
    }*/


    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    public void addMoreItem(List<T> newDatas) {
        mDatas.addAll(newDatas);
        //Logger.d("mDatas:"+mDatas.size());
        notifyDataSetChanged();
    }

    //添加数据
    public void addItem(List<T> newDatas) {

        mDatas.clear();
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
}
