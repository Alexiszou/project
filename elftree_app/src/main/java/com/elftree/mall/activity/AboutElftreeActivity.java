package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.elftree.mall.BuildConfig;
import com.elftree.mall.R;
import com.elftree.mall.databinding.ActivityAboutElftreeBinding;

/**
 * Created by zouhongzhi on 2017/10/16.
 */

public class AboutElftreeActivity extends BaseActivity {
    private ActivityAboutElftreeBinding mBinding;
    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_elftree);
        mBinding.setTitle(getResources().getString(R.string.about_elftree));
        mBinding.setVersion(getResources().getString(R.string.version_format, BuildConfig.VERSION_NAME));
    }

    @Override
    public void onClick(View v) {

    }
}
