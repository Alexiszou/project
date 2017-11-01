package com.zhz.retrofitclient.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhz.retrofitclient.R;
import com.zhz.retrofitclient.RetrofitClient;
import com.zhz.retrofitclient.exception.ExceptionHandle;
import com.zhz.retrofitclient.exception.RequestException;
import com.zhz.retrofitclient.exception.ResponeException;
import com.zhz.retrofitclient.exception.SuccessWithoutDataException;
import com.zhz.retrofitclient.gson.GsonFactory;
import com.zhz.retrofitclient.utils.LogUtil;
import com.zhz.retrofitclient.utils.NetworkUtil;
import com.zhz.retrofitclient.utils.ToastUtil;

import io.reactivex.subscribers.DisposableSubscriber;


/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T extends BaseResponse> extends DisposableSubscriber<T> {

    private static final String TAG = BaseSubscriber.class.getSimpleName();
    private Context context;
    private ProgressDialog progress;

    public BaseSubscriber(Context context) {
        this.context = context;
        progress = new ProgressDialog(context);
        progress.setMessage(context.getString(R.string.loading));
    }


    @Override
    public void onError(Throwable e) {
        //LogUtil.loge(RetrofitClient.TAG, "onError:"+e.toString());
        // todo error somthing
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
        if (!NetworkUtil.isNetworkAvailable(context)) {
            return;
        }
        if(e instanceof ResponeException){
            onError((ResponeException)e);
        } else if(e instanceof SuccessWithoutDataException){
            //onSuccessWithoutData((SuccessWithoutDataException)e);
        } else if(e instanceof RequestException){
            onError(new ResponeException(((RequestException)e).getMessage(),e));
        } else {
            onError(new ResponeException(ResponeException.Error.UNKNOWN,e));
        }
    }

    @Override
    public void onComplete() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
        LogUtil.logd(RetrofitClient.TAG,"http request is completed!");
    }


   @Override
    public void onStart() {
        LogUtil.logd(RetrofitClient.TAG,"http request is starting!");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
       if (progress != null){
           if (progress.isShowing()) {
               progress.dismiss();
           }
           //progress.show();
       }
        if (!NetworkUtil.isNetworkAvailable(context)) {
            ToastUtil.showShortToast(context,R.string.no_network);
            onComplete();
            return;
        }

       super.onStart();
    }


    @Override
    public void onNext(T response) {
        if(response.isOk() && response.getData() != null) {
            Gson gson = GsonFactory.create();
            String jsonStr = gson.toJson(response.getData());
            //LogUtil.logd(TAG,jsonStr);
            onSuccess(jsonStr);
        }else{
            ToastUtil.showShortToast(context,response.getMsg());
        }
    }

    //public abstract void onSuccessWithoutData(SuccessWithoutDataException e);
    public void onError(ResponeException e){
        LogUtil.loge(TAG,e.toString());
        ToastUtil.showShortToast(context,e.getMessage());
    }

    public abstract void onSuccess(String jsonStr);




}
