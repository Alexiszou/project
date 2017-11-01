package com.elftree.mall.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import com.elftree.mall.R;
import com.elftree.mall.databinding.LayoutExpandableParentItemBinding;
import com.elftree.mall.model.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouhongzhi on 2017/11/1.
 */

public class MyExpandableAdapter<T extends Series> extends BaseExpandableListAdapter {

    private Context mContext;
    private List<T> mGroupList;
    private LayoutInflater mInflater;
    private int layoutId;
    private int brId;

    public MyExpandableAdapter(Context context,List<T> mGroupList){
        this(context,mGroupList,0,0);
    }

    public MyExpandableAdapter(Context context,List<T> mGroupList, int layoutId, int brId){
        mContext = context;
        this.mGroupList = mGroupList;
        this.layoutId = layoutId;
        this.brId = brId;
        initDatas();
    }

    private void initDatas(){
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {

        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mGroupList.get(groupPosition).getChildList() != null){
            return mGroupList.get(groupPosition).getChildList().size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //View view = LayoutInflater.from(mContext).inflate(R.layout.layout_expandable_parent_item, null);
        LayoutExpandableParentItemBinding binding = DataBindingUtil.inflate(mInflater,R.layout.layout_expandable_parent_item,parent,false);
        binding.setSeries(mGroupList.get(groupPosition));
        return binding.getRoot();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    //添加数据
    /*public void addItem(List<T> newDatas) {

        mGroupList.clear();

        this.addMoreItem(newDatas);
    }

    public void addMoreItem(List<T> newDatas) {
        mGroupList.addAll(newDatas);

        notifyDataSetChanged();
    }*/
}
