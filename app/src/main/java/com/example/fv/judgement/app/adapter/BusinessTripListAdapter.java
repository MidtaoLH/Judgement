package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.fv.judgement.app.model.BusinessTripListModel;
import com.example.fv.judgement.app.view.BusinessTripListView;

import zuo.biao.library.base.BaseAdapter;

public class BusinessTripListAdapter extends BaseAdapter<BusinessTripListModel, BusinessTripListView>
{
    public BusinessTripListAdapter(Activity context) {
        super(context);
    }

    @Override
    public BusinessTripListView createView(int position, ViewGroup parent) {
        return new BusinessTripListView(context, parent);
    }

    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}