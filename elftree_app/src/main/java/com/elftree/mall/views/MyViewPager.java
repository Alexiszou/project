package com.elftree.mall.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;


/**
 * Created by zouhongzhi on 2017/10/11.
 */

public class MyViewPager extends ViewPager {
    private boolean isPagingEnabled = false;
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }




    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        //return super.executeKeyEvent(event);
        return false;
    }
}
