package com.example.fv.judgement.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.fv.judgement.R;
import com.example.fv.judgement.app.model.Product;

import java.util.List;

/**
 * Created by Arvin on 2017/1/13.
 */

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 获取小布局
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.examine_edit_list, null);
        }

        // 获取小布局中的组件
//        ImageView ivProImage = convertView.findViewById(R.id.iv_pro_image);
//        TextView tvProId = convertView.findViewById(R.id.tv_pro_id);
//        TextView tvProName = convertView.findViewById(R.id.tv_pro_name);
//        TextView tvProPrice = convertView.findViewById(R.id.tv_pro_price);
        TextView tvProDesc = convertView.findViewById(R.id.tvCaseName);

        // 给组件设置数据
        Product product = products.get(position);
//        ivProImage.setImageResource(product.getImage());
//        tvProId.setText(product.getPid()+"");
//        tvProName.setText(product.getName());
//        tvProPrice.setText("￥" + product.getPrice());
        tvProDesc.setText(product.getDesc());

        // 返回小布局视图
        return convertView;
    }
}
