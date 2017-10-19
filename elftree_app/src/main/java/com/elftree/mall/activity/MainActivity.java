package com.elftree.mall.activity;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elftree.mall.BuildConfig;
import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityMainBinding;
import com.elftree.mall.databinding.LayoutMainTabItemBinding;
import com.elftree.mall.fragment.BlankFragment;
import com.elftree.mall.fragment.MallFragment;
import com.elftree.mall.fragment.MineFragment;
import com.elftree.mall.fragment.SelectRegionDialogFragment;
import com.elftree.mall.fragment.ShoppingFragment;
import com.elftree.mall.fragment.SortFragment;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.retrofit.UserService;
import com.orhanobut.logger.Logger;

import com.readystatesoftware.viewbadger.BadgeView;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity {

    public static final String TAG = MainActivity.class.getName();
    public static final String SHARE_PREFERENCES_NAME = "elftree";

    private ActivityMainBinding mBinding;
    private String[] mTabTextArray;
    private int[] mTabIconArray;

    private MallFragment mallFragment;
    private SortFragment sortFragment;
    private ShoppingFragment shoppingFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDatas(Bundle savedInstanceState){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean isFirstStart = sharedPreferences.getBoolean("isFirstStart",true);
        if(isFirstStart){
            Intent intent = new Intent(this,SplashAcitvity.class);
            startActivity(intent);
            //if(!BuildConfig.DEBUG) {
                sharedPreferences.edit().putBoolean("isFirstStart", false).commit();
            //}
        }

        mTabTextArray = getResources().getStringArray(R.array.main_tab_title_array);

        TypedArray ar = getResources().obtainTypedArray(R.array.main_tab_icon_array);
        int len = ar.length();
        mTabIconArray = new int[len];
        for (int i = 0; i < len; i++) {
            mTabIconArray[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();


    }


    @Override
    public void initViews(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setBadgeNum(mBinding.header.ibtnService,9);
        mBinding.setClickEvent(this);

        mBinding.viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //mBinding.viewpager.setOffscreenPageLimit(4);

        mBinding.tab.setupWithViewPager(mBinding.viewpager);
        mBinding.tab.clearOnTabSelectedListeners();
        mBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.viewpager.setCurrentItem(tab.getPosition(),false);
                //Logger.d("tab position:"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 0 && position != 1){
                    mBinding.header.getRoot().setVisibility(View.GONE);
                }else{
                    mBinding.header.getRoot().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        for(int i=0;i<mTabTextArray.length;i++){
            /*mBinding.tab.getTabAt(i).setText(mTabTextArray[i])
                    .setIcon(mTabIconArray[i]);*/
            LayoutMainTabItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                    R.layout.layout_main_tab_item,
                    null,
                    false);
            mBinding.tab.getTabAt(i).setCustomView(binding.getRoot());
            binding.imageview.setImageResource(mTabIconArray[i]);
            binding.textview.setText(mTabTextArray[i]);
        }

        setBadgeNum(mBinding.tab.getTabAt(0).getCustomView(),9);


    }


    private void setBadgeNum(View targetView,int num){
        BadgeView badgeView = new BadgeView(this,targetView);
        badgeView.setText(num+"");
        badgeView.setBadgeBackgroundColor(getResources().getColor(R.color.badge_back_color));
        badgeView.setTextColor(getResources().getColor(R.color.white));
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setTypeface(Typeface.DEFAULT);
        badgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.badge_view_text_size)
        );

        badgeView.show();
    }
    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    //商城
                    if(mallFragment == null)
                    mallFragment =  MallFragment.newInstance(null);
                    return mallFragment;
                case 1:
                    //分类
                    if(sortFragment == null)
                    sortFragment =  SortFragment.newInstance(null);
                    return sortFragment;
                case 2:
                    //购物车
                    if(shoppingFragment == null)
                    shoppingFragment =  ShoppingFragment.newInstance(null);
                    return shoppingFragment;
                case 3:
                    //我的
                    if(mineFragment == null)
                        mineFragment =  MineFragment.newInstance(null);
                    return mineFragment;
                default:
                    fragment =  MallFragment.newInstance(null);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabTextArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTextArray[position];
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_service:
                SelectRegionDialogFragment fragment = SelectRegionDialogFragment.newInstance(null);
                fragment.show(getSupportFragmentManager(),"selectRegion");
                break;

        }
    }
}
