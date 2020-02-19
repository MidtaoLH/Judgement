package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.view.ExamineListView;
import zuo.biao.library.base.BaseAdapter;

public class ExamineListAdapter extends BaseAdapter<ExamineModel, ExamineListView>
{
    public ExamineListAdapter(Activity context) {
        super(context);
    }

    @Override
    public ExamineListView createView(int position, ViewGroup parent) {
        return new ExamineListView(context, parent);
    }

    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}

