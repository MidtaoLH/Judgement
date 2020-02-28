package com.example.fv.judgement.app.adapter.applytasklook;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ApplyTaskLook.ApplyTaskLookList;

import java.util.List;

public class ApplyTaskLookAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyTaskLookList> products;

    public ApplyTaskLookAdapter(Context context, List<ApplyTaskLookList> products) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
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

    public ImageView ivUserViewHeadList;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 获取小布局
        View view;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.examine_edit_list, null);
        }
        else
        {
            view = convertView;
        }
        // 获取小布局中的组件
        ivUserViewHeadList = view.findViewById(R.id.ivUserViewHeadList);
        ImageView ivSelectImage = view.findViewById(R.id.iv_select);
        TextView tvEmpName = view.findViewById(R.id.tvEmpName);
        TextView tvLeave = view.findViewById(R.id.tvLeave);
        TextView tvCaseDate = view.findViewById(R.id.tvCaseDate);
        TextView tvGroupName = view.findViewById(R.id.tvGroupName);
        TextView tvRemark = view.findViewById(R.id.tvRemark);

        // 给组件设置数据
        ApplyTaskLookList product = products.get(position);

        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,product.getUserCode());
        //  initLoadImage(Glide.with(context), context, strPhoto, ivUserViewHeadList);

        Glide.with(context).load(strPhoto)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(ivUserViewHeadList);
        //是否选择
        if(product.getTaskAuditeStatusNM().equals("待承认") || product.getTaskAuditeStatusNM().equals("未回览")
                || product.getTaskAuditeStatusNM().equals("予定中"))
        {
            ivSelectImage.setImageResource(R.drawable.unselect);
        }
        else if(product.getTaskAuditeStatusNM().equals("已驳回"))
        {
            ivSelectImage.setImageResource(R.drawable.orderselect);
        }
        else
        {
            ivSelectImage.setImageResource(R.drawable.finish2);
        }

        tvEmpName.setText(product.getTaskEmpCName());
        tvLeave.setText(product.getTaskNodeLevelNM());
        tvCaseDate.setText(product.getTaskAuditeDate());
        tvGroupName.setText(product.getTaskEmpGroupName());
        tvRemark.setText(product.getRemark());

        // 返回小布局视图
        return view;
    }

}
