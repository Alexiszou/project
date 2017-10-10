package com.elftree.mall.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.elftree.mall.R;
import com.elftree.mall.config.NetConfig;

import com.elftree.mall.databinding.ActivityForgetPwdBinding;
import com.elftree.mall.handler.ClickTagHandler;
import com.elftree.mall.model.User;
import com.orhanobut.logger.Logger;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.RequestException;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.net.BaseResponse;
import com.zhz.retrofitclient.net.BaseSubscriber;
import com.zhz.retrofitclient.utils.ToastUtil;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * Created by zouhongzhi on 2017/9/15.
 */

public class RegisterAndForgetPwdActivity extends BaseActivity {


    /**
     * 获取验证码时间间隔
     */
    private static final int GET_VERIFICATION_CODE_INTERVAL = 60;

    private static final int MSG_SET_INTERVAL = 0x0000;
    private ActivityForgetPwdBinding mBinding;
    private int mType;
    @Override
    public void initDatas(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra("type",LoginActivity.TYPE_REGISTER);
    }

    @Override
    public void initViews() {
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_forget_pwd);

        if(mType == LoginActivity.TYPE_REGISTER) {
            mBinding.setTitle(getString(R.string.register));
            mBinding.setIsRegisterType(true);
        }else if(mType == LoginActivity.TYPE_FORGET){
            mBinding.setTitle(getString(R.string.reset_login_pwd));
            mBinding.setIsRegisterType(false);
        }
        mBinding.setClickEvent(this);
        //Logger.d("user agreement:"+getString(R.string.user_agreement));
        mBinding.checkboxUserAgreement.setText(Html.fromHtml(getString(R.string.know_freight),
        null,
                new ClickTagHandler(new ClickTagHandler.ClickInterface() {
                    @Override
                    public void click() {
                        Logger.d("user agreement onclick!!!!!!");
                    }
                })));
        mBinding.checkboxUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.checkboxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mBinding.edittextPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    mBinding.edittextPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    private void getVericationCode(){
        String phoneNum = mBinding.edittextPhoneNum.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNum)){
            ToastUtil.showShortToast(this,R.string.phoneNum_hint);
            return;
        }
        User user = new User();
        user.setMobile(phoneNum);
        String url = "";
        if(mType == LoginActivity.TYPE_REGISTER){
            url = NetConfig.REGISTER_GET_VERIFICATION_CODE;
        }else if(mType == LoginActivity.TYPE_FORGET){
            url = NetConfig.FORGET_PWD_GET_VERIFICATION_CODE;
        }
        RetrofitClient.getInstance().
                createBaseApi().
                json(url,user.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(this) {
                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.getStrCode());
                        ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this,e.getMessage());

                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this,response.getMsg());
                        if(response.isOk()){
                            //获取验证码成功
                            Message msg = mHandler.obtainMessage();
                            msg.what = MSG_SET_INTERVAL;
                            msg.arg1 = GET_VERIFICATION_CODE_INTERVAL;
                            mHandler.sendMessage(msg);
                        }
                    }
                });
    }
    private void confirm(){
        final String phoneNum = mBinding.edittextPhoneNum.getText().toString().trim();
        final String verificationCode = mBinding.edittextVerificationCode.getText().toString().trim();
        final String pwd = mBinding.edittextPwd.getText().toString().trim();
        final String username = mBinding.edittextUserName.getText().toString().trim();
        boolean isCheck = mBinding.checkboxUserAgreement.isChecked();

        if(TextUtils.isEmpty(phoneNum)){
            ToastUtil.showShortToast(this,R.string.phoneNum_hint);
            return;
        }
        if(TextUtils.isEmpty(verificationCode)){
            ToastUtil.showShortToast(this,R.string.verification_code_hint);
            return;
        }
        if(mType == LoginActivity.TYPE_REGISTER) {
            if (TextUtils.isEmpty(username)) {
                ToastUtil.showShortToast(this, R.string.username_hint);
                return;
            } else if (username.length() < getResources().getInteger(R.integer.username_mini_length)) {
                ToastUtil.showShortToast(this, R.string.mini_username_length_hint);
                return;
            }
        }
        if(TextUtils.isEmpty(pwd)){
            if(mType == LoginActivity.TYPE_REGISTER) {
                ToastUtil.showShortToast(this, R.string.register_pwd_hint);
            }else if(mType == LoginActivity.TYPE_FORGET){
                ToastUtil.showShortToast(this, R.string.new_pwd_hint);
            }
            return;
        }else if(pwd.length() < getResources().getInteger(R.integer.pwd_mini_length)){
            ToastUtil.showShortToast(this,R.string.mini_pwd_length_hint);
            return;
        }
        if(!isCheck){
            ToastUtil.showShortToast(this,R.string.read_user_agreement_hint);
            return;
        }
        //先验证手机验证码
        final User user = new User();
        user.setMobile(phoneNum);
        user.setCheckcode(verificationCode);
        Flowable flowable = RetrofitClient.getInstance()
                .createBaseApi()
                .json(NetConfig.CHECK_VERIFICATION_CODE,user.genRequestBody())
                .flatMap(new Function<BaseResponse,Flowable<BaseResponse>>() {
                    @Override
                    public Flowable<BaseResponse> apply(BaseResponse response) throws Exception {

                        if(response.isOk()) {
                            user.setCheckcode(null);
                            user.setPasswrod(pwd);
                            String url = "";
                            if(mType == LoginActivity.TYPE_REGISTER){
                                user.setUsername(username);
                                url = NetConfig.USER_REGISTER;
                            }else if(mType == LoginActivity.TYPE_FORGET){
                                url = NetConfig.RESET_PWD;
                            }
                            return RetrofitClient.getInstance()
                                    .createBaseApi()
                                    .json(url, user.genRequestBody());
                        }else{
                            ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this,response.getMsg());
                        }
                        return Flowable.empty();
                    }
                });
                if(flowable != Flowable.empty()) {
                    flowable.subscribe(new BaseSubscriber<BaseResponse>(this) {
                        @Override
                        public void onError(ResponeException e) {
                            Logger.e(e.getStrCode());
                            ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this, e.getMessage());

                        }

                        @Override
                        public void onNext(BaseResponse response) {
                            ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this, response.getMsg());
                            if (response.isOk()) {
                                finish();
                            }
                        }
                    });
                }
        /*checkVeriCode(this, user, new OnSuccessInterface() {
            @Override
            public void onSuccess() {
                user.setCheckcode(null);
                user.setPasswrod(pwd);
                if(mType == LoginActivity.TYPE_REGISTER){
                    user.setUsername(username);
                    resetPwdOrRegister(user,NetConfig.USER_REGISTER);
                }else if(mType == LoginActivity.TYPE_FORGET){
                    resetPwdOrRegister(user,NetConfig.RESET_PWD);
                }

            }
        });*/
    }

    private void resetPwdOrRegister(User user,String url){
        RetrofitClient.getInstance()
                .createBaseApi()
                .json(url,user.genRequestBody())
                .subscribe(new BaseSubscriber<BaseResponse>(this) {
                    @Override
                    public void onError(ResponeException e) {
                        Logger.e(e.getStrCode());
                        ToastUtil.showShortToast(RegisterAndForgetPwdActivity.this,e.getMessage());
                        if(e.isSuccess()){
                            finish();
                        }
                    }

                    @Override
                    public void onNext(BaseResponse response) {

                    }
                });
    }

    private void setInterval(int interval){
        //Logger.d("interval:"+interval);
        if(interval <= 0){
            mBinding.btnGetVerificationCode.setEnabled(true);
            mBinding.btnGetVerificationCode.setText(R.string.get_verification_code);
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        mBinding.btnGetVerificationCode.setEnabled(false);
        mBinding.btnGetVerificationCode.setText(
                getString(R.string.get_verification_code_interval,interval+""));
        Message msg = mHandler.obtainMessage();
        msg.what = MSG_SET_INTERVAL;
        msg.arg1 = --interval;
        mHandler.sendMessageDelayed(msg,1000);
    }
    private Handler mHandler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SET_INTERVAL:
                    setInterval(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_verification_code:
                getVericationCode();
                break;
            case R.id.btn_confirm:
                confirm();
                break;
            default:
                break;
        }
    }
}
