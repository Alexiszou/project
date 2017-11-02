package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.elftree.mall.R;
import com.elftree.mall.databinding.ActivityTakeSampleBinding;
import com.elftree.mall.utils.CommonUtil;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by zouhongzhi on 2017/11/2.
 */

public class TakePhotoExample extends TakePhotoActivity implements View.OnClickListener{

    private ActivityTakeSampleBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_take_sample);
        mBinding.setClickEvent(this);
    }

    private void takePhotoByGallery(){
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        CropOptions.Builder builder=new CropOptions.Builder();
        getTakePhoto().onPickFromGalleryWithCrop(imageUri,builder.create());
        //getTakePhoto().onPickMultiple(9);
    }

    private void takePhotoByCamera(){
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        CropOptions.Builder builder=new CropOptions.Builder();
        getTakePhoto().onPickFromCaptureWithCrop(imageUri,builder.create());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Logger.d(result.getImage().getOriginalPath());
        Logger.d(result.getImage().getCompressPath());
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getOriginalPath());
        mBinding.imageview.setImageBitmap(bitmap);

        String str = CommonUtil.getImageStr(result.getImage().getOriginalPath());
        //Logger.d(str);

        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+"generate"+System.currentTimeMillis() + ".jpg");
        String path = file.getAbsolutePath();

        Logger.d(file.getPath());
        Logger.d(file.getAbsoluteFile());


        CommonUtil.generateImage(str,path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gallery:
                takePhotoByGallery();
                break;
            case R.id.btn_camera:
                takePhotoByCamera();
                break;

        }
    }
}
