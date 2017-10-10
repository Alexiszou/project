package com.elftree.mall.handler;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import org.xml.sax.XMLReader;

/**
 * Created by zouhongzhi on 2017/9/18.
 */

public class ClickTagHandler implements Html.TagHandler {
    private int sIndex = 0;
    private  int eIndex=0;
    private ClickInterface clickInterface;
    public ClickTagHandler(ClickInterface clickInterface){
        this.clickInterface = clickInterface;
    }
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("click")) {
            if (opening) {
                sIndex=output.length();
            }else {
                eIndex=output.length();
                output.setSpan(new MySpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private class MySpan extends ClickableSpan implements View.OnClickListener {
        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub
            //具体代码，可以是跳转页面，可以是弹出对话框，下面是跳转页面
           clickInterface.click();
        }
    }

    public interface ClickInterface{
        void click();
    }
}
