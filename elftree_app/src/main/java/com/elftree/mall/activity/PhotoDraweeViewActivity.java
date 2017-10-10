package com.elftree.mall.activity;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.adapter.MyRecyclerAdapter;
import com.elftree.mall.adapter.MyViewPagerAdapter;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityGoodsInfoBinding;
import com.elftree.mall.databinding.ActivityPhotoDraweeViewBinding;
import com.elftree.mall.model.Goods;
import com.elftree.mall.model.GoodsInfo;
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

public class PhotoDraweeViewActivity extends BaseActivity {

    private ActivityPhotoDraweeViewBinding mBinding;

    private List<String> mImageList;
    private int mCurPosition;
    private MyViewPagerAdapter mImageViewPagerAdapter;


    @Override
    public void initDatas(Bundle savedInstanceState) {
        mImageList = getIntent().getExtras().getStringArrayList("imageList");
        mCurPosition = getIntent().getExtras().getInt("position");
        Logger.d("mImageList:"+mImageList.toString());
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo_drawee_view);
        mImageViewPagerAdapter = new MyViewPagerAdapter(mContext,
                mImageList,
                R.layout.layout_photoview_viewpager,
                BR.photoUri);
        mBinding.viewpager.setAdapter(mImageViewPagerAdapter);
        mBinding.viewpager.setInfiniteLoop(true);
        setViewPagerIndicator(this,mBinding.viewpager);
    }

    public static final void setViewPagerIndicator(Context context, UltraViewPager viewpager){
        //initialize built-in indicator
        viewpager.initIndicator();
        //set style of indicators
        viewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(context.getResources().getColor(R.color.colorPrimary))
                .setNormalColor(context.getResources().getColor(R.color.white))
                .setRadius(context.getResources().getDimensionPixelOffset(R.dimen.circle_indicator_radius))
                //set the alignment
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                .setMargin(0,0,0,10)
                //construct built-in indicator, and add it to  UltraViewPager
                .build();
    }
    @BindingAdapter("android:photoUri")
    public static void setPhotoUri(PhotoDraweeView view, String uri) {
        view.setPhotoUri(Uri.parse(uri));
    }

    @Override
    public void onClick(View v) {

    }
}
