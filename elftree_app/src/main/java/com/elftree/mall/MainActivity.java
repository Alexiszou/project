package com.elftree.mall;



import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elftree.mall.config.NetConfig;
import com.elftree.mall.model.User;
import com.elftree.mall.retrofit.RetrofitCreator;
import com.elftree.mall.retrofit.UserService;
import com.elftree.mall.utils.MD5Util;
import com.elftree.mall.utils.StringUtil;
import com.elftree.mall.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.Timer;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();

    }

    private void initDatas(){

    }



    private void initViews(){

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRegisterVerCode();
            }
        });

    }

    private void testRegisterVerCode(){
        String phoneNumber = editText.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,"请输入电话号码！",Toast.LENGTH_SHORT).show();
            return;
        }

        UserService service = RetrofitCreator.getUserService();
        //String url = NetConfig.REGISTER_GET_VERIFICATION_CODE;
        String url = NetConfig.CHECK_MOBILE_NUMBER;
        User user = new User();
        user.setMobile(phoneNumber);
        //Logger.d("user.genRequestBody:"+user.genRequestBody(user));
        Observable<Object> observable = service.post(url,user.genRequestBody());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object s) {
                        Logger.d("onNext:"+s.toString());
                        textView.setText(s.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("e:"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete!!!!!!!!!!!");
                    }
                });
    }
}
