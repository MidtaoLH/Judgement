package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.NoticeModel;
import com.example.fv.judgement.app.view.ExamineListView;
import com.example.fv.judgement.app.view.NoticeListView;

import zuo.biao.library.base.BaseAdapter;

public class NoticeListAdapter extends BaseAdapter<NoticeModel, NoticeListView>
{
    public NoticeListAdapter(Activity context) {
        super(context);
    }

    @Override
    public NoticeListView createView(int position, ViewGroup parent) {
        return new NoticeListView(context, parent);
    }

    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}