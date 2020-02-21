package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.fv.judgement.app.activity.WayCheck.WayCheck_view;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.view.ExamineListView;

import zuo.biao.library.base.BaseAdapter;
import zuo.biao.library.model.Entry;

public class WayCheckAdapter extends BaseAdapter<WayCheckModel, WayCheck_view> {

    public WayCheckAdapter(Activity context) {
        super(context);
    }

    @Override
    public WayCheck_view createView(int position, ViewGroup parent) {
        return new WayCheck_view(context, parent);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


}


