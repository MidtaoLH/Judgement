package com.example.fv.judgement.app.activity.ExamineEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.ExamineEditListAdapter;
import com.example.fv.judgement.app.adapter.ProductListAdapter;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;

import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.CacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.TextClearSuit;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

public class ExamineEdit extends BaseActivity  {
        public static final String TAG = "UserActivity";

     public static Intent createIntent(Context context, long userId) {
         return new Intent(context, ExamineEdit.class).putExtra(INTENT_ID, userId);
     }

    private List<MdlExamineEditDetail> products;
    private ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_edit1);
       intent = getIntent();

        //功能归类分区方法，必须调用<<<<<<<<<<
//        initView();
//        initData();
//        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    @Override
    public void initView() {//必须调用

        //添加用户名片，这些方式都可<<<<<<<<<<<<<<<<<<<<<<
        //		//方式一
        //		bvlUser = findView(R.id.bvlUser);
        //		bvlUser.createView(new UserView(context, getResources()));
        //
        //		//方式二
        //		uvlUser = findView(R.id.uvlUser);

    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {//必须调用

    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private static final int REQUEST_TO_BOTTOM_MENU = 1;
    private static final int REQUEST_TO_EDIT_TEXT_INFO = 2;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

    }

    @Override
    public void finish() {
        super.finish();

    }
    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
