package com.example.fv.judgement.app.activity.ExamineEdit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.activity.Leave.LeaveEdit;
import com.example.fv.judgement.app.activity.MyExamineList.MainTabActivity;
import com.example.fv.judgement.app.adapter.ExamineEditListAdapter;
import com.example.fv.judgement.app.adapter.ExamineEditListBaseAdapter;
import com.example.fv.judgement.app.adapter.ImageListAdapter;
import com.example.fv.judgement.app.adapter.ProductListAdapter;
import com.example.fv.judgement.app.application.FullyGridLayoutManager;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ExamineModel;
import com.example.fv.judgement.app.model.LeaveModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.MdlExamineEditDetail;
import com.example.fv.judgement.app.model.MdlExamineEditHead;
import com.example.fv.judgement.app.model.MdlExamineEditImagePath;
import com.example.fv.judgement.app.model.MdlServiceReturn;
import com.example.fv.judgement.app.model.Product;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.CacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.ui.BottomMenuWindow;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.TextClearSuit;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;
import static zuo.biao.library.util.CommonUtil.showShortToast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;
import io.reactivex.Observer;

public class ExamineEdit extends AppCompatActivity  implements View.OnClickListener {
        public static final String TAG = "ExamineEdit";

    private static final int REQUEST_TO_DATE_PICKER = 33;
    private static final int REQUEST_TO_DATE_PICKEREND = 34;
    private int[] selectedDate = new int[]{1971, 0, 1};

    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private int maxSelectNum =0;// GlobalVariableApplication.maxImageSelectNum;
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private String taskid;
    private String adId;
    private String strDataType; // 1 请假 3 外出 13 出差

    public static Intent createIntent(Context context,String strDataType, String taskid) {
        Intent intent = new Intent(context, ExamineEdit.class);
        intent.putExtra("strDataType", strDataType);
        intent.putExtra("taskid", taskid);
        return intent;
     }
    private Button btnyes, btnno;

    private List<MdlExamineEditDetail> products;
    private ListView lvProduct;
    private String edittype; // 同意1 驳回2

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

        //给本画面传递参数
        Intent intent = getIntent();
        strDataType =intent.getStringExtra("strDataType");
        taskid =intent.getStringExtra("taskid");
        switch (strDataType) {
            case "请假":
                strDataType = "1";
                break;
            case "外出":
                strDataType = "3";
                break;
            case "出差":
                strDataType = "13";
                break;
        }
        //获取本机id
        adId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
//
//        //设置列表 布局样式
        this.lvProduct = findViewById(R.id.ExamineList);
//
//       //判断来源    获取  数据    设置 数据
//        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();

        //初始化图片选择控件
        initViewImage();
        initData();

        btnyes = findViewById(R.id.btnyes);
        btnno = findViewById(R.id.btnno);
        //注册监听器
        btnyes.setOnClickListener(this);
        btnno.setOnClickListener(this);

        adapter.setOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(ExamineEdit.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ExamineEdit.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ExamineEdit.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

    }
    public ImageView ivUserViewHead; //创建画面所需控件
    public TextView tvEmpName;
    public TextView tvGroupName;
    public TextView tvExamineDate;
    public TextView tvStatus;

    public TextView tvPlace;
    public TextView tvDate;
    public TextView tvRemark;
    public TextView tvCount;

    public void initViewImage()
    {
        themeId = R.style.picture_default_style;
        recyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ExamineEdit.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ImageListAdapter(ExamineEdit.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(ExamineEdit.this);
                } else {
                    Toast.makeText(ExamineEdit.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }
    //图片控件)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private ImageListAdapter.onAddPicClickListener onAddPicClickListener = new ImageListAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            try {
                boolean mode = true;// cb_mode.isChecked();
                if (mode) {
                    // 进入相册 以下是例子：不需要的api可以不写
                    PictureSelector.create(ExamineEdit.this)
                            .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                            .theme(themeId)// 主题样式设置 具体参考 values/styles
                            .maxSelectNum(maxSelectNum)// 最大图片选择数量
                            .minSelectNum(1)// 最小选择数量
                            .imageSpanCount(4)// 每行显示个数
                            .selectionMode(true ?
                                    PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                            .previewImage(true)// 是否可预览图片
                            .previewVideo(true)// 是否可预览视频
                            .enablePreviewAudio(false)// 是否预览音频
                            .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                            .isCamera(true)// 是否显示拍照按钮
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            //.setOutputCameraPath("/Chinayie/App")// 自定义拍照保存路径
                            .compress(true)// 是否压缩
                            .compressMode(compressMode)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .isGif(true)// 是否显示gif图片
                            .openClickSound(true)// 是否开启点击声音
                            .selectionMedia(selectList)// 是否传入已选图片
                            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                            //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                            //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                            //.videoQuality()// 视频录制质量 0 or 1
                            //.videoSecond()//显示多少秒以内的视频
                            //.recordVideoSecond()//录制视频秒数 默认60秒
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
            } catch (Exception e) {
                MyLog.writeLogtoFile("错误", "LeaveEdit", "onAddPicClick", e.toString(), "0");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0x002&&resultCode==0x001){
         //   typetag.setText(data.getStringExtra("name"));//当LoginActivity finish后，就会调用这里，data为值
        }
        else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回两种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
        switch (requestCode) {
            case REQUEST_TO_DATE_PICKER:
                if (data != null) {
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                        selectedDate = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedDate[i] = list.get(i);
                        }
//                        startdatetag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
//                        selectedDate[1]=selectedDate[1]+1;
//                        selectedStaraDate= selectedDate;
                    }
                }
                break;
            case REQUEST_TO_DATE_PICKEREND:
                if (data != null) {

                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                        selectedDate = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedDate[i] = list.get(i);
                        }
//                        endDateTag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
//                        selectedDate[1]=selectedDate[1]+1;
//                        selectedEndDate= selectedDate;
                    }
                }
                break;
            default:
                break;
        }
    }

    public void initView()
    {
        edittype = "";
    }

    //头部赋值
    public void initViewHead(MdlExamineEditHead Item) {//必须调用

        ivUserViewHead = findViewById(R.id.ivUserViewHead);// findView(R.id.ivUserViewHead, this);
        tvEmpName = findViewById(R.id.tvEmpName);//findView(R.id.tvCaseName);
        tvGroupName = findViewById(R.id.tvGroupName);
        tvExamineDate = findViewById(R.id.tvApplyDate);

        tvPlace = findViewById(R.id.tvPlace);
        tvDate = findViewById(R.id.tvDate);
        tvCount = findViewById(R.id.tvCount);
        tvRemark = findViewById(R.id.tvRemark);
        tvStatus = findViewById(R.id.tvStatus);
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
        tvStatus.setText(Item.getStatusTxt());

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
        tvDate.setText(strDatetxt+Item.getBeignDate() + "～" +Item.getEndDate() );
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

    //承认列表赋值
    public void initViewList(List<MdlExamineEditDetail> listTaskList)
    {
     //   ExamineEditListBaseAdapter examineEditListBaseAdapter = new ExamineEditListBaseAdapter(ExamineEdit.this,R.layout.examine_edit_list,listTaskList);
//
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), listTaskList,strDataType);
          this.lvProduct.setAdapter(adapter);
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //获取服务器数据
    public void initData() {
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        String methodName = "GetExamineEditData";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","75");
        soapObject.addProperty("taskID",taskid);
        soapObject.addProperty("TaskType",strDataType);
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
        for (MdlExamineEditImagePath bean : listImageList) {
            if (bean != null) {
                LocalMedia localMedia = new LocalMedia();
                String url = GlobalVariableApplication.IMAGE_FUJIAN_URL + bean.getAnnexPath();
                localMedia.setPath(url);
                selectList.add(localMedia);//添加图片
            }
        }
        //承认任务
        Type typeTask = new TypeToken<List<MdlExamineEditDetail>>(){}.getType();
        listTaskList = new Gson().fromJson(strTaskList,typeTask);

        initViewHead(listHead.get(0));

        initViewList(listTaskList);
    }

    //驳回 承认
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnyes:
                edittype = "1";
                editservice(view);
                break;
            case R.id.btnno:
                edittype = "2";
                editservice(view);
                break;
            default:
                break;
        }
    }

    private void editservice(View view) {
        EditText editText1 = findViewById (R.id.etxtRemark);
        String strRemark=editText1.getText().toString();

        if(edittype.equals("2") && "".equals(strRemark))
        {
            showShortToast(ExamineEdit.this,"请输入驳回理由");
            return;
        }
        //获取服务器数据
        String methodName = "TaskInstanceEdit";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","96");
        soapObject.addProperty("taskInstanceID","238");
        soapObject.addProperty("Remark",strRemark);
        soapObject.addProperty("operate",edittype);
        soapObject.addProperty("operatr","96");
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);

        List<MdlServiceReturn> returnResult=new ArrayList<MdlServiceReturn>();

        Type typeHead = new TypeToken<List<MdlServiceReturn>>(){}.getType();
        returnResult = new Gson().fromJson(jsonData,typeHead);

        if(returnResult.size()>0)
        {
            MdlServiceReturn item = new MdlServiceReturn();
            item = returnResult.get(0);

            if(item.getResuelt().equals("0"))
            {
                if(item.getResuelt().equals("1"))
                {
                    showShortToast(ExamineEdit.this,"同意成功");
                }
                else if(item.getResuelt().equals("2"))
                {
                    showShortToast(ExamineEdit.this,"驳回成功");
                }
                Intent intent = new Intent(ExamineEdit.this, MainTabActivity.class);
                startActivity(intent);
            }
        }
        //如果返回为0 则跳转画面
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//
//    }

    @Override
    public void finish() {
        super.finish();

    }
    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
