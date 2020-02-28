package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.fv.judgement.app.model.AgentListModel;
import com.example.fv.judgement.app.view.AgentListView;


import zuo.biao.library.base.BaseAdapter;

public class AgentListAdapter extends BaseAdapter<AgentListModel,AgentListView>
{
    public AgentListAdapter(Activity context) {
        super(context);
    }

    @Override
    public AgentListView createView(int position, ViewGroup parent) {
        return new AgentListView(context, parent);
    }

    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}
