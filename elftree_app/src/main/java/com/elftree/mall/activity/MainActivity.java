package com.elftree.mall.activity;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
            //startActivity(intent);
            if(!BuildConfig.DEBUG) {
                sharedPreferences.edit().putBoolean("isFirstStart", false).commit();
            }
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

        mBinding.viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        mBinding.tab.setupWithViewPager(mBinding.viewpager);


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
                    fragment =  MallFragment.newInstance(null);
                    break;
                case 1:
                    //分类
                    fragment =  SortFragment.newInstance(null);
                    break;
                case 2:
                    //购物车
                    fragment =  ShoppingFragment.newInstance(null);
                    break;
                case 3:
                    //我的
                    fragment =  MineFragment.newInstance(null);
                    break;
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

    }
}