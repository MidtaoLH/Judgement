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

import java.util.List;

public class ExamineEditListBaseAdapter extends ArrayAdapter<MdlExamineEditDetail>
{
    private  int resourceID;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MdlExamineEditDetail detail = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView tvCaseName = view.findViewById(R.id.tvRemark);

        // 获取小布局中的组件
        // ivUserViewHead = convertView.findViewById(R.id.ivUserViewHead);
        TextView tvEmpName = convertView.findViewById(R.id.tvEmpName);
        TextView tvLeave = convertView.findViewById(R.id.tvLeave);
        TextView tvCaseDate = convertView.findViewById(R.id.tvCaseDate);
        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        TextView tvRemark = convertView.findViewById(R.id.tvRemark);

        tvCaseName.setText("23");
        return view;
    }

    public ExamineEditListBaseAdapter(Context context, int viewid, List<MdlExamineEditDetail> objects)
    {
        super(context,viewid,objects);
        resourceID = viewid;

    }
}

