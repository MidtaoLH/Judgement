package com.example.fv.judgement.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;
import com.example.fv.judgement.app.view.ExamineEditListView;

import java.util.List;

import zuo.biao.library.base.BaseAdapter;

public class ExamineEditListAdapter extends BaseAdapter<MdlExamineEditDetail, ExamineEditListView>
{
    public ExamineEditListAdapter(Activity context) {
        super(context);
    }
    @Override
    public ExamineEditListView createView(int position, ViewGroup parent) {
        return new ExamineEditListView(context, parent);
    }
    @Override
    //获取列表id的方法
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}

