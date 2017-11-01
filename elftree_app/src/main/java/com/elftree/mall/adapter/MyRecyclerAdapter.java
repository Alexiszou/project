package com.elftree.mall.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.MyCouponActivity;
import com.elftree.mall.databinding.LayoutAddressItemBinding;
import com.elftree.mall.databinding.LayoutCartItemBinding;
import com.elftree.mall.databinding.LayoutCollectionItemBinding;
import com.elftree.mall.databinding.LayoutCouponItemBinding;
import com.elftree.mall.databinding.LayoutFooterBinding;
import com.elftree.mall.databinding.LayoutGoodsCommentItemBinding;
import com.elftree.mall.databinding.LayoutMyOrderItemBinding;
import com.elftree.mall.databinding.LayoutMyOrderItemGoodsBinding;
import com.elftree.mall.fragment.MyCouponFragment;
import com.elftree.mall.model.Coupon;
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsComment;
import com.elftree.mall.model.Order;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/9/22.
 */

public class MyRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NUM_PER_PAGE = 10;//每页10条数据
    private static final int TYPE_ITEM = 0x0000;
    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    public static final int MODE_NORMAL = 0x0000;
    public static final int MODE_EDIT = 0x0001;
    private int mMode = MODE_NORMAL;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0x0000;
    //正在加载中
    public static final int LOADING_MORE = 0x0001;

    //加载完成
    public static final int NO_MORE_DATA = 0x0002;
    //上拉加载更多状态-默认为1
    private int load_more_status = LOADING_MORE;

    private Context mContext;
    private List<T> mDatas;

    private int layoutId;

    private int brId;

    private MyOnItemClickListener mOnItemClickListener;
    private boolean showFooter = false;
    private List<Boolean> checkStates;

    public MyRecyclerAdapter(Context context, List<T> mDatas, int layoutId, int brId) {
        this(context, mDatas, layoutId, brId, false);
    }

    public MyRecyclerAdapter(Context context, List<T> mDatas, int layoutId, int brId, boolean showFooter) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
        this.showFooter = showFooter;
        initData();
    }

    //    初始化
    private void initData() {

        checkStates = new ArrayList<>();

    }

    public void refreshAllCheckStates(boolean check) {
        for (int i = 0; i < mDatas.size(); i++) {
            checkStates.set(i, check);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    layoutId,
                    parent,
                    false);
            MyViewHolder viewHolder = new MyViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyRecyclerAdapter.MyViewHolder) {
            final ViewDataBinding binding = ((MyRecyclerAdapter.MyViewHolder) holder).getBinding();
            binding.setVariable(brId, mDatas.get(position));
            binding.executePendingBindings();

            if (binding instanceof LayoutCartItemBinding) {
                if (checkStates.size() != 0) {
                    ((LayoutCartItemBinding) binding).checkbox.setChecked(checkStates.get(position));

                    ((LayoutCartItemBinding) binding).checkbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClickListener(v, binding, position);
                            }
                        }
                    });
                    ((LayoutCartItemBinding) binding).btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClickListener(v, binding, position);
                            }
                        }
                    });
                }
            } else if (binding instanceof LayoutCollectionItemBinding) {

                if (checkStates.size() != 0) {
                    ((LayoutCollectionItemBinding) binding).checkbox.setChecked(checkStates.get(position));
                    if (mMode == MODE_EDIT) {
                        ((LayoutCollectionItemBinding) binding).checkbox.setVisibility(View.VISIBLE);
                        ((LayoutCollectionItemBinding) binding).imageviewMore.setVisibility(View.GONE);
                        ((LayoutCollectionItemBinding) binding).checkbox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mOnItemClickListener != null) {
                                    mOnItemClickListener.onItemClickListener(v, binding, position);
                                }
                            }
                        });
                    } else if (mMode == MODE_NORMAL) {
                        ((LayoutCollectionItemBinding) binding).checkbox.setVisibility(View.GONE);
                        ((LayoutCollectionItemBinding) binding).imageviewMore.setVisibility(View.VISIBLE);
                    }

                }
            } else if (binding instanceof LayoutGoodsCommentItemBinding) {
                LayoutGoodsCommentItemBinding dataBinding = (LayoutGoodsCommentItemBinding) binding;
                GoodsComment.ListBean comment = (GoodsComment.ListBean) (mDatas.get(position));
                if (comment.getImg() != null && comment.getImg().size() > 0) {
                    MyRecyclerAdapter adapter = new MyRecyclerAdapter(mContext,
                            comment.getImg(),
                            R.layout.layout_comment_item_image,
                            BR.imageUrl);
                    dataBinding.recyclerviewCommentImage.setAdapter(adapter);
                    dataBinding.recyclerviewCommentImage.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.HORIZONTAL, false));
                }

            }else if(binding instanceof LayoutAddressItemBinding){
                //我的收货地址
                LayoutAddressItemBinding dataBinding = (LayoutAddressItemBinding)binding;
                dataBinding.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClickListener(v, binding, position);
                        }
                    }
                });
                dataBinding.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClickListener(v, binding, position);
                        }
                    }
                });
                dataBinding.rbtnDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClickListener(v, binding, position);
                        }
                    }
                });
            }else if(binding instanceof LayoutCouponItemBinding){
                //优惠券
                LayoutCouponItemBinding dataBinding = (LayoutCouponItemBinding)binding;
                //if(mContext instanceof MyCouponActivity){
                    dataBinding.receive.setVisibility(View.GONE);
                //}
                dataBinding.getRoot().setEnabled(((Coupon)mDatas.get(position)).isEnabled());
            }else if(binding instanceof LayoutMyOrderItemBinding){
                //我的订单
                LayoutMyOrderItemBinding dataBinding = (LayoutMyOrderItemBinding)binding;
                Order.ListBean data = (Order.ListBean)mDatas.get(position);
                if (data.getOrder_goods() != null && data.getOrder_goods().size() > 0) {

                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    for(Order.ListBean.OrderGoodsBean bean : data.getOrder_goods()) {

                        LayoutMyOrderItemGoodsBinding goodsBinding = DataBindingUtil.inflate(inflater,
                                R.layout.layout_my_order_item_goods,
                                dataBinding.goodsContainer,
                                true);
                        goodsBinding.setOrderGoods(bean);
                    }

                }
                dataBinding.type.setText(data.getOrderStatus(mContext));
                dataBinding.total.setText(
                        mContext.getResources().getString
                                (R.string.order_total_format,
                                        data.getTotal_num(),
                                        data.getFormat_total_amount(),
                                        data.getFreight_fee()));
            }

        } else if (holder instanceof MyRecyclerAdapter.FooterViewHolder) {
            Logger.d("load_more_status:" + load_more_status);
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    ((MyRecyclerAdapter.FooterViewHolder) holder).getBinding().setText(
                            mContext.getResources().getString(R.string.pullup_load_more)
                    );
                    break;
                case LOADING_MORE:
                    ((MyRecyclerAdapter.FooterViewHolder) holder).getBinding().setText(
                            mContext.getResources().getString(R.string.loading_more)
                    );
                    break;
                case NO_MORE_DATA:
                    ((MyRecyclerAdapter.FooterViewHolder) holder).getBinding().setText(
                            mContext.getResources().getString(R.string.no_more_data)
                    );
                    break;
                default:
                    ((MyRecyclerAdapter.FooterViewHolder) holder).getBinding().setText(
                            mContext.getResources().getString(R.string.pullup_load_more)
                    );
                    break;

            }

        }
    }


    @Override
    public int getItemCount() {
        if (showFooter) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showFooter) {
            if (position + 1 == getItemCount()) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setEnabled(false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClickListener(v, binding, getAdapterPosition());
                    }
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

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
        if (showFooter) {
            return position + 1 == getItemCount();
        } else {
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
     *
     * @param status
     */
    public void changeLoadStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    public List<Boolean> getCheckStates() {
        return checkStates;
    }

    public void setCheckStates(List<Boolean> checkStates) {
        this.checkStates = checkStates;
    }

    //添加数据
    public void addItem(List<T> newDatas) {

        mDatas.clear();
        checkStates.clear();
        this.addMoreItem(newDatas);
    }

    public void addMoreItem(List<T> newDatas) {
        mDatas.addAll(newDatas);
        for (int i = 0; i < mDatas.size(); i++) {
            //checkStates.set(mDatas.size()+i,false);
            checkStates.add(false);

        }
        notifyDataSetChanged();
    }

    //删除数据
    public void deleteItem(int poistion) {
        checkStates.remove(poistion);
        mDatas.remove(poistion);
        notifyItemRemoved(poistion);

        notifyItemRangeChanged(0, mDatas.size());

        /*mDatas.remove(poistion);
        checkStates.delete(poistion);
        notifyDataSetChanged();*/
    }

    //批量删除数据
    /*public void deleteItem(){
        Boolean bool = new Boolean();
        bool.
        checkStates.
    }*/

    public int getMode() {
        return mMode;
    }

    public void setMode(int mode) {
        this.mMode = mode;
        notifyDataSetChanged();
    }

    public interface MyOnItemClickListener {
        void onItemClickListener(View view, ViewDataBinding binding, int position);
    }

}
