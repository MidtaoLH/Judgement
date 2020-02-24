package com.example.fv.judgement.app.activity.ExamineEdit;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
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
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;
import com.example.fv.judgement.app.model.Product;
import com.example.fv.judgement.app.util.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
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

    private List<Product> products;
    private ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_edit1);
        intent = getIntent();

        String DataType =intent.getStringExtra("DataType");

        //设置列表 布局样式
        this.lvProduct = (ListView) findViewById(R.id.ExamineList);

       //判断来源    获取  数据    设置 数据
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据

        String methodName = "GetExamineEditData";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","96");
        soapObject.addProperty("taskID","438");
        soapObject.addProperty("TaskType","13");
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<ExamineModel> listExaData=new ArrayList<ExamineModel>();

        Type type = new TypeToken<List<ExamineModel>>(){}.getType();
        listExaData = new Gson().fromJson(jsonData,type);

        // 初始化产品数据
        this.products = new ArrayList<Product>();
        Product pro1 = new Product(1001,"艾静吸尘器",1200,"超静音八遍系成绩，能铣刀各个觉喽哦，不放过任何一个死角",R.drawable.pro01);
        products.add(pro1);
        Product pro2 = new Product(1002,"高级电饭锅",2388,"香喷喷的米饭由此而生",R.drawable.pro04);
        products.add(pro2);
        Product pro3 = new Product(1003,"负离子去螨器",890,"香喷喷的螨虫，你想如何处置", R.drawable.pro06);
        products.add(pro3);
        Product pro4 = new Product(1004,"多功能料理机",2359,"功能丰富，随心所欲。。。。", R.drawable.pro08);
        products.add(pro4);
        Product pro5 = new Product(1005,"多可必搅拌棒",300,"操作方便，随时随地都可以获取新鲜食物。",R.drawable.pro10);
        products.add(pro5);

        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), products,DataType);
        this.lvProduct.setAdapter(adapter);
        //功能归类分区方法，必须调用<<<<<<<<<<
//        initView();
       initData();
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
    public void initData() {
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        String methodName = "GetExamineEditData";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","96");
        soapObject.addProperty("taskID","438");
        soapObject.addProperty("TaskType","13");
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<ExamineModel> listExaData=new ArrayList<ExamineModel>();

        Type type = new TypeToken<List<ExamineModel>>(){}.getType();
        listExaData = new Gson().fromJson(jsonData,type);
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
