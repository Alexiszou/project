package com.elftree.mall.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.elftree.mall.BR;
import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;
import com.elftree.mall.databinding.ActivityLoginBinding;
import com.elftree.mall.model.User;

import com.elftree.mall.retrofit.RetrofitCreator;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.gson.GsonFactory;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.io.InputStreamReader;

import io.reactivex.functions.Function;


/**
 * Created by zouhongzhi on 2017/9/13.
 */

public class LoginActivity extends BaseActivity {

    public static final int TYPE_REGISTER = 0x0000;
    public static final int TYPE_FORGET = 0x0001;

    private EditText edittext_phone_number;
    private EditText edittext_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_login);

        binding.setTitle(getString(R.string.login));
        //binding.setNext("next");
        binding.setClickEvent(this);

        edittext_phone_number = binding.edittextPhoneNum;

        edittext_pwd = binding.edittextPwd;


    }

    private void login(){
        String phoneNum = edittext_phone_number.getText().toString().trim();
        String pwd = edittext_pwd.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNum)){
            ToastUtil.showShortToast(this,R.string.phoneNum_or_username_hint);
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtil.showShortToast(this,R.string.pwd_hint);
            return;
        }
        User user = new User();
        user.setUsername(phoneNum);
        user.setPasswrod(pwd);
        RetrofitClient.getInstance().createBaseApi()
                .json(NetConfig.USER_LOGIN,user.genRequestBody())

                .subscribe(new BaseSubscriber<BaseResponse>(this) {
                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.getStrCode());
                        ToastUtil.showShortToast(LoginActivity.this,e.getMessage());

                    }

                    @Override
                    public void onNext(BaseResponse response) {

                        ToastUtil.showShortToast(LoginActivity.this,response.getMsg());
                        if(response.isOk() && response.getData() != null){
                            Gson gson = RetrofitCreator.getGson();
                            User user = gson.fromJson(gson.toJson(response.getData()),User.class);
                            Logger.d("onNext:"+user.toString());
                            MyApplication.getInstances().getDaoSession().getUserDao().deleteAll();
                            MyApplication.getInstances().getDaoSession().getUserDao().insert(user);
                            Logger.d("user dao:"+MyApplication.getInstances()
                                    .getDaoSession().getUserDao().loadAll().get(0).toString());
                            finish();
                        }
                    }

                    @Override
                    public void onSuccess(String response) {

                    }
                });

    }

    private void startRegisterOrForgetPwd(int type){
        Intent intent = new Intent(LoginActivity.this,RegisterAndForgetPwdActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_forget_pwd:
                startRegisterOrForgetPwd(TYPE_FORGET);
                break;
            case R.id.btn_register:
                startRegisterOrForgetPwd(TYPE_REGISTER);
                break;
            case R.id.btn_wechat:
                break;
            case R.id.btn_qq:
                break;
            case R.id.btn_weibo:
                break;
            default:
                break;
        }
    }
}
