package com.elftree.mall.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.elftree.mall.R;
import com.elftree.mall.databinding.FragmentSelectSpecialBinding;
import com.elftree.mall.databinding.LayoutSpecItemBinding;
import com.elftree.mall.databinding.LayoutSpecRbtnBinding;
import com.elftree.mall.model.GoodsInfo;
import com.zhz.retrofitclient.utils.ToastUtil;

import java.util.List;

/**
 * Created by zouhongzhi on 2017/10/10.
 */

public class SelectSpecDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    public static final String KEY_DATA = "data";
    public static final String KEY_LAYOUT_ID = "layout_id";
    public static final String KEY_BR_ID = "br_id";
    public static final String KEY_SPEC_ARRAY = "spec_array";
    public static final String KEY_QUANTITY = "quantity";



    private Context mContext;
    private GoodsInfo mGoodsInfo;
    private int layoutId;
    private int brId;
    private FragmentSelectSpecialBinding mBinding;
    private LayoutInflater localInflater;
    private OnDismissListener onDismissListener;

    private String mSpecText = "";
    private String[] specIdArray;
    private String mQuantity;
    public static SelectSpecDialogFragment newInstance(Bundle bundle) {
        SelectSpecDialogFragment fragment = new SelectSpecDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    private void initDatas(){
        mGoodsInfo = (GoodsInfo) getArguments().getSerializable(KEY_DATA);
        specIdArray = getArguments().getStringArray(KEY_SPEC_ARRAY);
        mQuantity = getArguments().getString(KEY_QUANTITY);
        /*layoutId = getArguments().getInt(KEY_LAYOUT_ID);
        brId = getArguments().getInt(KEY_BR_ID);*/
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        localInflater = inflater.cloneInContext(contextThemeWrapper);
        mBinding = DataBindingUtil.inflate(localInflater, R.layout.fragment_select_special,container,false);
        initViews(inflater);
        return mBinding.getRoot();
    }

    private void initViews(LayoutInflater inflater){
        mBinding.setTitle(getResources().getString(R.string.select_spec));
        mBinding.setGoodsInfo(mGoodsInfo);
        mBinding.setClickEvent(this);
        mBinding.edittextQuantity.setText(mQuantity);

        List<GoodsInfo.GoodsSpecalBean> specList = mGoodsInfo.getGoods_specal();
        if(specList == null || specList.size()== 0){
            //没有规格
            mSpecText = getResources().getString(R.string.no_spec);
            return;
        }

        if(specIdArray == null)
            specIdArray = new String[specList.size()];
        for(int i=0;i<specList.size();i++){
            GoodsInfo.GoodsSpecalBean spec = specList.get(i);
            LayoutSpecItemBinding binding = DataBindingUtil.inflate(localInflater,
                    R.layout.layout_spec_item,
                    mBinding.specContainer,
                    true);
            binding.radioGroup.setTag(i);
            binding.setTitle(spec.getKey());

            List<GoodsInfo.GoodsSpecalBean.ListBean> beanList = spec.getList();
            for(int j=0;j<beanList.size();j++){
                GoodsInfo.GoodsSpecalBean.ListBean bean = beanList.get(j);
                LayoutSpecRbtnBinding rbtnBinding = DataBindingUtil.inflate(inflater,
                        R.layout.layout_spec_rbtn,
                        binding.radioGroup,
                        true);
                rbtnBinding.setText(bean.getSpec_name());
                rbtnBinding.getRoot().setTag(bean);
                if(specIdArray[i] == null ){
                    if(bean.isSelected()) {
                        ((RadioButton) rbtnBinding.getRoot()).setChecked(true);
                        mSpecText = bean.getSpec_name();
                        specIdArray[i] = bean.getSpec_id();
                    }
                }else{
                    if(specIdArray[i].equals(bean.getSpec_id())){
                        ((RadioButton) rbtnBinding.getRoot()).setChecked(true);
                        mSpecText = bean.getSpec_name();
                    }
                }
            }


            binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rbtn = (RadioButton)group.findViewById(checkedId);
                    GoodsInfo.GoodsSpecalBean.ListBean bean = (GoodsInfo.GoodsSpecalBean.ListBean)rbtn.getTag();
                    mSpecText = bean.getSpec_name();

                    int index = (int)group.getTag();
                    specIdArray[index] = bean.getSpec_id();
                }
            });
        }


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //ToastUtil.showShortToast(mContext,"dismiss");
        if(onDismissListener != null){

            onDismissListener.onDismiss(mSpecText,mBinding.edittextQuantity.getText().toString(),specIdArray);
        }

    }

    public void setOnDismissListener(OnDismissListener listener){
        this.onDismissListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnDismissListener{
        void onDismiss(String msg,String quantity,String[] specIdArray);
    }
}
