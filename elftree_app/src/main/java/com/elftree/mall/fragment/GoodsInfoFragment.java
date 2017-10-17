package com.elftree.mall.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.activity.BaseActivity;
import com.elftree.mall.activity.GoodsCommentActivity;
import com.elftree.mall.activity.PhotoDraweeViewActivity;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.adapter.MyViewPagerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityGoodsInfoBinding;
import com.elftree.mall.databinding.FragmentGoodsInfoBinding;
import com.elftree.mall.handler.ClickTagHandler;
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsInfo;
import com.elftree.mall.utils.CommonUtil;
import com.elftree.mall.utils.StringUtil;
import com.elftree.mall.views.RecyclerViewDivider;
import com.elftree.mall.views.ViewPagerScroller;
import com.orhanobut.logger.Logger;
import com.tmall.ultraviewpager.UltraViewPager;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by zouhongzhi on 2017/9/27.
 */

public class GoodsInfoFragment extends BaseFragment {

    private FragmentGoodsInfoBinding mBinding;

    private Goods mBundleGoods;

    private GoodsInfo mGoodsInfo;
    private ArrayList<String> mImageList;
    private MyViewPagerAdapter mImageViewPagerAdapter;

    private String[] mSpecIdArray;
    private String mProductId;
    private String mQuantity = "1";
    private String mSpecText = "";
    private String mProductPrice ="";

    public String[] getmSpecIdArray() {
        return mSpecIdArray;
    }

    public void setmSpecIdArray(String[] mSpecIdArray) {
        this.mSpecIdArray = mSpecIdArray;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public GoodsInfo getmGoodsInfo() {
        return mGoodsInfo;
    }

    public String getmSpecText() {
        return mSpecText;
    }

    public void setmSpecText(String mSpecText) {
        this.mSpecText = mSpecText;
    }

    public void setmGoodsInfo(GoodsInfo mGoodsInfo) {
        this.mGoodsInfo = mGoodsInfo;
    }


    public String getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public static GoodsInfoFragment newInstance(Bundle bundle){
        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(bundle);
        return fragment;

    }
    @Override
    public void initDatas() {
        mBundleGoods = (Goods)getArguments().getSerializable("goods");
    }
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_goods_info,container,false);
        return mBinding.getRoot();
    }
    @Override
    public void initViews(View view, Bundle bundle) {
        mBinding.setTitle(getString(R.string.goods_info));
        mBinding.setClickEvent(this);
        mImageList = new ArrayList<>();
        mImageViewPagerAdapter = new MyViewPagerAdapter(mContext,
                mImageList,
                R.layout.layout_image_viewpager,
                BR.imageUrl);
        mBinding.viewpager.setAdapter(mImageViewPagerAdapter);
        mBinding.viewpager.setInfiniteLoop(true);
        mBinding.viewpager.setAutoScroll(3000);

        ViewPagerScroller pagerScroller = new ViewPagerScroller(mContext);
        pagerScroller.setScrollDuration(1000);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(mBinding.viewpager.getViewPager());

        mImageViewPagerAdapter.setItemClickListener(new MyRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClickListener(View view,ViewDataBinding binding, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imageList",mImageList);
                bundle.putInt("position",position);
                CommonUtil.startActivity(mContext,PhotoDraweeViewActivity.class,bundle);
            }
        });
        PhotoDraweeViewActivity.setViewPagerIndicator(mContext,mBinding.viewpager);
        getRemoteData();
    }

    private void getRemoteData(){
        Goods goods = new Goods();
        goods.setGoods_id(mBundleGoods.getGoods_id());
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.GET_GOODS_INFO,goods.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(mContext) {
                    /*@Override
                    public void onError(ResponeException e) {
                        Logger.e(e.toString());
                        ToastUtil.showShortToast(mContext,e.getMessage());
                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isOk() && response.getData() != null) {
                            String jsonStr = mGson.toJson(response.getData());
                            Logger.d(jsonStr);
                            mGoodsInfo = mGson.fromJson(jsonStr,GoodsInfo.class);
                            refreshViews();
                        }else{
                            ToastUtil.showShortToast(mContext,response.getMsg());
                        }
                    }*/

                    @Override
                    public void onSuccess(String jsonStr) {
                        /*String jsonStr = mGson.toJson(response.getData());*/
                        Logger.d(jsonStr);
                        mGoodsInfo = mGson.fromJson(jsonStr,GoodsInfo.class);
                        refreshViews();
                    }
                });
    }

    private void refreshViews(){
        Logger.d(mGoodsInfo.getImg());

        mImageList.clear();
        mImageList.addAll(mGoodsInfo.getImg());

        mBinding.viewpager.getViewPager().getAdapter().notifyDataSetChanged();

        mBinding.setGoodsInfo(mGoodsInfo);
        //添加删除线
        mBinding.textviewMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        List<GoodsInfo.GoodsSpecalBean> list = mGoodsInfo.getGoods_specal();
        if(list == null || list.size() == 0){
            mBinding.btnSelectSpecification.setText(
                    Html.fromHtml(getString(R.string.specification_format,
                            getString(R.string.no_spec),
                            mQuantity)));
        }else{
            //List<GoodsInfo.GoodsSpecalBean.ListBean> beanList = list.get(0).getList();
            mSpecIdArray = new String[list.size()];
            int index = 0;
            for(GoodsInfo.GoodsSpecalBean specBean : list) {
                for (GoodsInfo.GoodsSpecalBean.ListBean bean : specBean.getList()) {
                    if (bean.isSelected()) {
                        mSpecText = bean.getSpec_name();
                        mBinding.btnSelectSpecification.setText(
                                Html.fromHtml(getString(R.string.specification_format,
                                        bean.getSpec_name(),
                                        mQuantity)));
                        mSpecIdArray[index] = bean.getSpec_id();
                        index++;
                    }
                }
            }
            refreshProductId();

        }



        mBinding.btnFreight.setText(Html.fromHtml(
                getString(R.string.freight_format, getString(R.string.know_freight)),
                null,
                new ClickTagHandler(new ClickTagHandler.ClickInterface() {
                    @Override
                    public void click() {
                        Logger.d("know freight onclick!!!!!!");
                    }
                })));
        //mBinding.btnFreight.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.btnFreight.setOnClickListener(this);


        //评论区
        /*MyRecyclerAdapter mCommentAdapter = new MyRecyclerAdapter(mContext,
                mImageList,
                R.layout.layout_comment_item,
                BR.comment);
        mBinding.recyclerviewComment.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerviewComment.setAdapter(mCommentAdapter);
        mBinding.recyclerviewComment.addItemDecoration(
                new RecyclerViewDivider(mContext,
                        LinearLayoutManager.HORIZONTAL));*/
    }


    private void refreshProductId(){
        List<GoodsInfo.ProductsBean> productList = mGoodsInfo.getProducts();
        if(productList != null && productList.size()>0) {
            for (GoodsInfo.ProductsBean product : productList) {
                if (product.getSpec_attr().equals(StringUtil.arrayToStringSort(mSpecIdArray))) {
                    mProductId = product.getProduct_id();
                    mProductPrice = product.getProduct_price();
                }
            }
        }
    }
    private void showSelectSpecDialog(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(SelectSpecDialogFragment.KEY_DATA,mGoodsInfo);
        bundle.putStringArray(SelectSpecDialogFragment.KEY_SPEC_ARRAY,mSpecIdArray);
        bundle.putString(SelectSpecDialogFragment.KEY_QUANTITY,mQuantity);
        SelectSpecDialogFragment fragment = SelectSpecDialogFragment.newInstance(bundle);
        fragment.show(getChildFragmentManager(),"select_spec");
        fragment.setOnDismissListener(new SelectSpecDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(String msg,String quantity,String[] specIdArray) {
                mSpecText = msg;
                mBinding.btnSelectSpecification.setText(
                        Html.fromHtml(getString(R.string.specification_format,
                                msg,
                                quantity)));
                mSpecIdArray = specIdArray;
                mQuantity = quantity;
                refreshProductId();
            }
        });


    }

    private void startGoodsCommentActivity(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(GoodsCommentActivity.KEY_GOODS_ID,mBundleGoods.getGoods_id());
        CommonUtil.startActivity(mContext,GoodsCommentActivity.class,bundle);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_select_specification:
                showSelectSpecDialog();
                break;
            case R.id.btn_freight:
                break;
            case R.id.user_rating:
                startGoodsCommentActivity();
                break;
            default:
                break;
        }
    }
}
