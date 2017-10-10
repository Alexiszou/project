package com.elftree.mall.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elftree.mall.R;
import com.elftree.mall.databinding.LayoutFooterBinding;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public class MyRecyclerAdapter<T> extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 0x0000;
    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0x0000;
    //正在加载中
    public static final int  LOADING_MORE=0x0001;

    //加载完成
    public static final int NO_MORE_DATA = 0x0002;
    //上拉加载更多状态-默认为0
    private int load_more_status = PULLUP_LOAD_MORE;

    private Context mContext;
    private List<T> mDatas;

    private int layoutId;

    private int brId;

    private MyOnItemClickListener mOnItemClickListener;
    private boolean showFooter = false;

    public MyRecyclerAdapter(Context context,List<T> mDatas, int layoutId, int brId) {
        this(context,mDatas,layoutId,brId,false);
    }

    public MyRecyclerAdapter(Context context,List<T> mDatas, int layoutId, int brId,boolean showFooter) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
        this.showFooter = showFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType ==  TYPE_ITEM) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    layoutId,
                    parent,
                    false);
            MyViewHolder viewHolder = new MyViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        }else if(viewType == TYPE_FOOTER){
            LayoutFooterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.layout_footer,
                    parent,
                    false);
            FooterViewHolder viewHolder = new FooterViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyRecyclerAdapter.MyViewHolder) {
            ((MyRecyclerAdapter.MyViewHolder)holder).getBinding().setVariable(brId, mDatas.get(position));
            ((MyRecyclerAdapter.MyViewHolder)holder).getBinding().executePendingBindings();
        }else if(holder instanceof MyRecyclerAdapter.FooterViewHolder){
            Logger.d("load_more_status:"+load_more_status);
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    ((MyRecyclerAdapter.FooterViewHolder)holder).getBinding().setText(
                            mContext.getResources().getString(R.string.pullup_load_more)
                    );
                    break;
                case LOADING_MORE:
                    ((MyRecyclerAdapter.FooterViewHolder)holder).getBinding().setText(
                            mContext.getResources().getString(R.string.loading_more)
                    );
                    break;
                case NO_MORE_DATA:
                    ((MyRecyclerAdapter.FooterViewHolder)holder).getBinding().setText(
                            mContext.getResources().getString(R.string.no_more_data)
                    );
                    break;
                default:
                    ((MyRecyclerAdapter.FooterViewHolder)holder).getBinding().setText(
                            mContext.getResources().getString(R.string.pullup_load_more)
                    );
                    break;

            }

        }
    }


    @Override
    public int getItemCount() {
        if(showFooter) {
            return mDatas.size() + 1;
        }else {
            return mDatas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(showFooter) {
            if (position+1  == getItemCount()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ITEM;
    }

    public MyOnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(MyOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null){
                        mOnItemClickListener.onItemClickListener(v,getAdapterPosition());
                    }
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        private LayoutFooterBinding mBinding;

        public LayoutFooterBinding getBinding() {
            return mBinding;
        }

        public void setBinding(LayoutFooterBinding binding) {
            this.mBinding = binding;
        }

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    /*public boolean isHeader(int position) {
        return position == 0;
    }*/

    public boolean isFooter(int position) {
        if(showFooter) {
            return position+1 == getItemCount();
        }else{
            return false;
        }
    }


    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeLoadStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }


    //添加数据
    public void addItem(List<T> newDatas) {

        mDatas.clear();
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<T> newDatas) {
        mDatas.addAll(newDatas);
        //Logger.d("mDatas:"+mDatas.size());
        notifyDataSetChanged();
    }
    public interface MyOnItemClickListener{
        void onItemClickListener(View view,int position);
    }
}
