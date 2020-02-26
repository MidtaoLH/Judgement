package com.example.fv.judgement.app.activity.ExamineEdit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.ExamineEditListAdapter;
import com.example.fv.judgement.app.adapter.ProductListAdapter;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;
import com.example.fv.judgement.app.model.MdlExamineEditHead;
import com.example.fv.judgement.app.model.MdlExamineEditImagePath;
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

public class ExamineEdit extends AppCompatActivity  {
        public static final String TAG = "ExamineEdit";

     public static Intent createIntent(Context context, long userId) {
         return new Intent(context, ExamineEdit.class).putExtra("strDataType", userId);
     }

    private List<MdlExamineEditDetail> products;
    private ListView lvProduct;
    private String adId;
    private String strDataType; // 1 请假 3 外出 13 出差

    private  String strDateCounttxt="";
    private  String strDatetxt = "";
    private  String strRemarktxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_edit1);

        //获取本机id
        adId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        //设置列表 布局样式
        this.lvProduct = (ListView) findViewById(R.id.ExamineList);

       //判断来源    获取  数据    设置 数据
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //给本画面传递参数
        Intent intent = getIntent();
        strDataType =intent.getStringExtra("DataType");
        strDataType = "1";

        //获取服务器数据
        //功能归类分区方法，必须调用<<<<<<<<<<
//        initView();
        initData();
//        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        // 初始化产品数据
//        this.products = new ArrayList<Product>();
//        Product pro1 = new Product(1001,"艾静吸尘器",1200,"超静音八遍系成绩，能铣刀各个觉喽哦，不放过任何一个死角",R.drawable.pro01);
//        products.add(pro1);
//        Product pro2 = new Product(1002,"高级电饭锅",2388,"香喷喷的米饭由此而生",R.drawable.pro04);
//        products.add(pro2);
//        Product pro3 = new Product(1003,"负离子去螨器",890,"香喷喷的螨虫，你想如何处置", R.drawable.pro06);
//        products.add(pro3);
//        Product pro4 = new Product(1004,"多功能料理机",2359,"功能丰富，随心所欲。。。。", R.drawable.pro08);
//        products.add(pro4);
//        Product pro5 = new Product(1005,"多可必搅拌棒",300,"操作方便，随时随地都可以获取新鲜食物。",R.drawable.pro10);
//        products.add(pro5);

    }
    public ImageView ivUserViewHead; //创建画面所需控件
    public TextView tvEmpName;
    public TextView tvGroupName;
    public TextView tvExamineDate;

    public TextView tvPlace;
    public TextView tvDate;
    public TextView tvRemark;
    public TextView tvCount;

    //toubu
    public void initViewHead(MdlExamineEditHead Item) {//必须调用

        ivUserViewHead = findViewById(R.id.ivUserViewHead);// findView(R.id.ivUserViewHead, this);
        tvEmpName = findViewById(R.id.tvEmpName);//findView(R.id.tvCaseName);
        tvGroupName = findViewById(R.id.tvGroupName);
        tvExamineDate = findViewById(R.id.tvApplyDate);

        tvPlace = findViewById(R.id.tvPlace);
        tvDate = findViewById(R.id.tvDate);
        tvCount = findViewById(R.id.tvCount);
        tvRemark = findViewById(R.id.tvRemark);

        //格式化头像地址
        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,Item.getU_LoginName());
        Glide.with(ExamineEdit.this).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
        tvEmpName.setText(Item.getEmpCName());
        tvGroupName.setText(Item.getGroupname());
        tvExamineDate.setText("申请时间: " + Item.getExamineDate());

        if(strDataType.equals("1"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="请假时长: ";
            strDatetxt = "请假时间: ";
            strRemarktxt = "请假事由: ";
        }
        else if(strDataType.equals("3"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="外出时长: ";
            strDatetxt = "外出时间: ";
            strRemarktxt = "外出事由: ";
        }
        else if(strDataType.equals("13"))
        {
            strDateCounttxt="出差天数: ";
            strDatetxt = "出差时间: ";
            strRemarktxt = "出差事由: ";
        }
        tvDate.setText(strDatetxt+Item.getBeignDate() + "-" +Item.getEndDate() );
        tvCount.setText(strDateCounttxt+Item.getNumcount());
        tvRemark.setText(strRemarktxt + Item.getDescribe());
        //添加用户名片，这些方式都可<<<<<<<<<<<<<<<<<<<<<<
        //		//方式一
        //		bvlUser = findView(R.id.bvlUser);
        //		bvlUser.createView(new UserView(context, getResources()));
        //
        //		//方式二
        //		uvlUser = findView(R.id.uvlUser);

    }

    public void initViewList(List<MdlExamineEditDetail> listTaskList)
    {
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), listTaskList,strDataType);
        this.lvProduct.setAdapter(adapter);
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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

        //解析字符串
        String strHead= getInsideString(jsonData,"{\"Table\":",",\"Table1\":");
        String strImageList= getInsideString(jsonData,",\"Table1\":",",\"Table2\":");
        String strTaskList= getInsideString(jsonData,",\"Table2\":",",\"Table3\":");

        List<MdlExamineEditHead> listHead=new ArrayList<MdlExamineEditHead>();
        List<MdlExamineEditImagePath> listImageList=new ArrayList<MdlExamineEditImagePath>();
        List<MdlExamineEditDetail> listTaskList=new ArrayList<MdlExamineEditDetail>();

        //头部数据
        Type typeHead = new TypeToken<List<MdlExamineEditHead>>(){}.getType();
        listHead = new Gson().fromJson(strHead,typeHead);

        //图片地址
        Type typeImage = new TypeToken<List<MdlExamineEditImagePath>>(){}.getType();
        listImageList = new Gson().fromJson(strImageList,typeImage);

        //承认任务
        Type typeTask = new TypeToken<List<MdlExamineEditDetail>>(){}.getType();
        listTaskList = new Gson().fromJson(strTaskList,typeTask);

        initViewHead(listHead.get(0));

        initViewList(listTaskList);

    }
    /**
     * 截取两字符之间的字符串，str 和 start不能为null或""
     */
    public   String  getInsideString(String  str, String strStart, String strEnd ) {
        if ( str.indexOf(strStart) < 0 ){
            return "";
        }
        if ( str.indexOf(strEnd) < 0 ){
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
