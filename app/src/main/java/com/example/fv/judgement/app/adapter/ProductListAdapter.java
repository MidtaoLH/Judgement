package com.example.fv.judgement.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;
import com.example.fv.judgement.app.model.Product;

import java.util.List;

import zuo.biao.library.util.CommonUtil;

/**
 * Created by Arvin on 2017/1/13.
 */

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<MdlExamineEditDetail> products;
    private String dataType;

    public ProductListAdapter(Context context, List<MdlExamineEditDetail> products,String DataType) {
        this.context = context;
        this.products = products;
        this.dataType = DataType;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ImageView ivUserViewHead;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 获取小布局
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.examine_edit_list, null);
        }

        // 获取小布局中的组件
        ivUserViewHead = convertView.findViewById(R.id.ivUserViewHead);
        TextView tvEmpName = convertView.findViewById(R.id.tvEmpName);
        TextView tvLeave = convertView.findViewById(R.id.tvLeave);
        TextView tvCaseDate = convertView.findViewById(R.id.tvCaseDate);
        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        TextView tvRemark = convertView.findViewById(R.id.tvRemark);

        // 给组件设置数据
        MdlExamineEditDetail product = products.get(position);
        //格式化头像地址
        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,product.getU_LoginName());
        Glide.with(context).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
        tvEmpName.setText(product.getName());
        tvLeave.setText(product.getLevelname());
        tvCaseDate.setText(product.getTaskDate());
        tvGroupName.setText(product.getGroupname());
        tvRemark.setText(product.getRemark());

        // 返回小布局视图
        return convertView;
    }
}
