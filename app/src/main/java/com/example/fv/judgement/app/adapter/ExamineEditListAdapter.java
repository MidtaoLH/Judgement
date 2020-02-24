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

public class ExamineEditListAdapter extends ArrayAdapter<MdlExamineEditDetail>
{
    private  int resourceID;

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MdlExamineEditDetail detail = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView tvCaseName = view.findViewById(R.id.tvCaseType);
        tvCaseName.setText("23");
        return view;
    }

    public ExamineEditListAdapter(Context context, int viewid, List<MdlExamineEditDetail> objects)
    {
        super(context,viewid,objects);
        resourceID = viewid;

    }
}

