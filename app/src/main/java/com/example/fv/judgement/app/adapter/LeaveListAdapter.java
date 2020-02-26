package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import com.example.fv.judgement.app.model.LeaveListModel;
import com.example.fv.judgement.app.view.LeaveListView;

import zuo.biao.library.base.BaseAdapter;

public class LeaveListAdapter extends BaseAdapter<LeaveListModel, LeaveListView>
{
    public LeaveListAdapter(Activity context) {
        super(context);
    }

    @Override
    public LeaveListView createView(int position, ViewGroup parent) {
        return new LeaveListView(context, parent);
    }

    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}

