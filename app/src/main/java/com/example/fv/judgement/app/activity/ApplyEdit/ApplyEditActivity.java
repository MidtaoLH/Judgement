package com.example.fv.judgement.app.activity.ApplyEdit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.adapter.ImageListAdapter;
import com.example.fv.judgement.app.adapter.applytasklook.ApplyTaskEditAdapter;
import com.example.fv.judgement.app.application.FullyGridLayoutManager;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ApplyTaskLook.MdlApplyEditImagePath;
import com.example.fv.judgement.app.model.ApplyTaskLook.MdlApplyTaskEdit;
import com.example.fv.judgement.app.model.ApplyTaskLook.MdlApplyTaskEditList;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.util.CommonUtil;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;

public class ApplyEditActivity extends BaseActivity  implements View.OnClickListener {
    public static final String TAG = "ApplyEditActivity";
    private String userID, tasktype,iosid;

    //头部
    public ImageView ivUserViewHead; //创建画面所需控件
    public TextView tvTitle;
    public TextView tvEmpName;
    public TextView tvGroupName;
    public TextView tvExamineDate;
    public TextView tvStatus;

    public TextView tvPlace;
    public TextView tvDate;
    public TextView tvRemark;
    public TextView tvCount;
    private  String strDateCounttxt="";
    private  String strDatetxt = "";
    private  String strRemarktxt = "";
    private  String strStatus = "";
    //头部end

    //图片列表
    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum =0;// GlobalVariableApplication.maxImageSelectNum;
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private static final int REQUEST_TO_DATE_PICKER = 33;
    private static final int REQUEST_TO_DATE_PICKEREND = 34;
    private int[] selectedDate = new int[]{1971, 0, 1};
    //图片列表 end

    //列表
    private ListView lvProduct;
    //列表 end

    //页面传递参数
    private String FID,awardID_FK,processInstanceID,title,ProcessApplyCode;
    //页面传递参数 end

    private Button btnyes, btnno;

    public static Intent createIntent(Context context,String FID,  String awardID_FK, String processInstanceID,  String ProcessApplyCode) {
        Intent intent = new Intent(context, ApplyEditActivity.class);
        intent.putExtra("awardID_FK", awardID_FK);
        intent.putExtra("processInstanceID", processInstanceID);
        intent.putExtra("ProcessApplyCode", ProcessApplyCode);
        intent.putExtra("FID", FID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_edit);

        //设置列表 布局样式
        this.lvProduct = findViewById(R.id.ApplyEditList);

        //给本画面传递参数
        Intent intent = getIntent();
        awardID_FK =intent.getStringExtra("awardID_FK");
        processInstanceID =intent.getStringExtra("processInstanceID");
        title =intent.getStringExtra("title");
        ProcessApplyCode =intent.getStringExtra("ProcessApplyCode");
        FID =intent.getStringExtra("FID");

        //初始化图片选择控件
        initViewImage();

        //必须实现的方法
        initView();
        initData();
//        initEvent();
        btnyes = findViewById(R.id.btnyes);
        btnno = findViewById(R.id.btnno);
        //注册监听器
        btnyes.setOnClickListener(this);
        btnno.setOnClickListener(this);

//        //必须实现的方法end
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
                            PictureSelector.create(ApplyEditActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ApplyEditActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ApplyEditActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }
    //图片赋值
    public void initViewImage()
    {
        themeId = R.style.picture_default_style;
        recyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ApplyEditActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ImageListAdapter(ApplyEditActivity.this, onAddPicClickListener);
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
                    PictureFileUtils.deleteCacheDirFile(ApplyEditActivity.this);
                } else {
//                    Toast.makeText(ApplyTaskLookActivity.this,
//                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
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
                    PictureSelector.create(ApplyEditActivity.this)
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

                    }
                }
                break;
            case REQUEST_TO_DATE_PICKEREND:
                if (data != null) {

                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {
                      ;
                    }
                }
                break;
            default:
                break;
        }
    }
    public void initView()
    {

    }

    //toubu
    public void initViewHead(MdlApplyTaskEdit Item) {//必须调用

        String strName = "";
        String strGroupName = "";
        String strApplyDate = "";
        String strRemark = "";

        ivUserViewHead = findViewById(R.id.ivUserViewHead);// findView(R.id.ivUserViewHead, this);
        tvEmpName = findViewById(R.id.tvEmpName);//findView(R.id.tvCaseName);
        tvGroupName = findViewById(R.id.tvGroupName);
        tvExamineDate = findViewById(R.id.tvApplyDate);

        tvPlace = findViewById(R.id.tvPlace);
        tvDate = findViewById(R.id.tvDate);
        tvCount = findViewById(R.id.tvCount);
        tvRemark = findViewById(R.id.tvRemark);
        tvStatus = findViewById(R.id.tvStatus);

         strName = Item.getEmpName();
         strGroupName = Item.getG_CName();
         strApplyDate = Item.getApplyDate();
         strRemark = "";
         strStatus = Item.getProcessStutasTxt();

        //格式化头像地址
        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,Item.getU_LoginName());
        Glide.with(ApplyEditActivity.this).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
        tvEmpName.setText(strName);
        tvGroupName.setText(strGroupName);
        tvExamineDate.setText("申请时间: " + strApplyDate);
        tvStatus.setText(strStatus);

        if(tasktype.equals("请假"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="请假时长: ";
            strDatetxt = "请假时间: ";
            strRemarktxt = "请假事由: ";
        }
        else if(tasktype.equals("外出"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="外出时长: ";
            strDatetxt = "外出时间: ";
            strRemarktxt = "外出事由: ";
        }
        else if(tasktype.equals("出差"))
        {
            strDateCounttxt="出差天数: ";
            strDatetxt = "出差时间: ";
            strRemarktxt = "出差事由: ";
        }
        tvDate.setText(strDatetxt+Item.getPlanStartTime() + "-" +Item.getPlanStartTime() );
        tvCount.setText(strDateCounttxt+Item.getTimePlanNum());
        tvRemark.setText(strRemarktxt + Item.getCaseName());
    }

    //承认列表适配器
    public void initViewList(List<MdlApplyTaskEditList> listTaskList)
    {
          ApplyTaskEditAdapter adapter = new ApplyTaskEditAdapter(getApplicationContext(), listTaskList);
          this.lvProduct.setAdapter(adapter);
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //获取数据
    public void initData() {

        //方法名
        String methodName = "";
        String methodpara = "";
        String TableName = "";
        String FIDName = "";
        String FID = "";

        if(ProcessApplyCode.indexOf("QJ") != -1)
        {
            tasktype = "请假";
            methodName = "GetLeaveDataByID";
            methodpara = "LeaveID";
            TableName = "AD_LeaveAnnex";
            FIDName = "LeaveID_FK";
        }
        else if(ProcessApplyCode.indexOf("WC") != -1)
        {
            tasktype = "外出";
            methodName = "GetGoOutDataByID";
            methodpara = "EvectionID";
            TableName = "AD_EvectionAnnex";
            FIDName = "EvectionID_FK";
        }
        else
        {
            methodName = "GetBusinessTripDataByID";
            tasktype = "出差";
            methodpara = "BusinessTripID";
            TableName = "AD_BusinessTripAnnex";
            FIDName = "BusinessTripID_FK";
        }

        //登录用户数据
        LoginUserModel model = GlobalInformationApplication.getInstance().getCurrentUser();
        userID = model.getId();
        iosid =model.getAdId();

        //获取服务器数据
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID",userID);
        soapObject.addProperty(methodpara,awardID_FK);
        soapObject.addProperty("ProcessInstanceID",processInstanceID);
        soapObject.addProperty("iosid",iosid);
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        if (jsonData.equals(GlobalVariableApplication.UnLoginFlag))
        {
            showShortToast(GlobalVariableApplication.UnLoginFlag);
            Intent intent = new Intent();
            intent.setClass(this.context, MainLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(jsonData.length()>0) {
            //解析字符串
            String strHead = getInsideString(jsonData, "{\"Table\":", ",\"Table1\":");
            String strTaskList  = getInsideString(jsonData, ",\"Table1\":", ",\"Table2\":");
      //      String strImageList = getInsideString(jsonData, ",\"Table2\":", "}");

            List<MdlApplyTaskEdit> listHead = new ArrayList<MdlApplyTaskEdit>();
            List<MdlApplyEditImagePath> listImageList = new ArrayList<MdlApplyEditImagePath>();
            List<MdlApplyTaskEditList> listTaskList = new ArrayList<MdlApplyTaskEditList>();

            //头部数据
            Type typeHead = new TypeToken<List<MdlApplyTaskEdit>>() {
            }.getType();
            listHead = new Gson().fromJson(strHead, typeHead);

            //承认任务
            Type typeTask = new TypeToken<List<MdlApplyTaskEditList>>() {
            }.getType();
            listTaskList = new Gson().fromJson(strTaskList, typeTask);
            if(listHead.size()>0)
            {
                initViewHead(listHead.get(0));

                if( tasktype.equals("请假"))
                {
                    FID = listHead.get(0).getLeaveID();
                }
                //头部数据不为空获取列表数据
                methodName = "GetImagePathByIDAndroid";
                SoapObject soapObjectImage = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
                soapObjectImage.addProperty("TableName",TableName);
                soapObjectImage.addProperty("FIDName",FIDName);
                soapObjectImage.addProperty("FID",48);

                HttpRequest httpresImage= new HttpRequest();
                String jsonDataImage = httpresImage.httpWebService_GetString(methodName,soapObjectImage);
                //头部数据
                Type typeHeadImage = new TypeToken<List<MdlApplyEditImagePath>>() {
                }.getType();
                listImageList = new Gson().fromJson(jsonDataImage, typeHeadImage);

                for (MdlApplyEditImagePath bean : listImageList) {
                    if (bean != null) {
                        LocalMedia localMedia = new LocalMedia();
                        String url = GlobalVariableApplication.IMAGE_FUJIAN_URL + bean.getAnnexPath();
                        localMedia.setPath(url);
                        selectList.add(localMedia);//添加图片
                    }
                }
            }
            if(listTaskList.size()>0)
            {
                initViewList(listTaskList);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.btnyes:
                title = "1";
                editservice(view);
                break;
            case R.id.btnno:
                title = "2";
                editservice(view);
                break;
            default:
                break;
        }
    }

    private void editservice(View view) {
        //已驳回不写理由。 直接跳到明细编辑画面，点保存生成下一版本
        if(title.equals("1") && strStatus.equals("已驳回"))
        {
            //关闭当前活动 跳转其他活动
            if(tasktype.equals("请假"))
            {
                toActivity(LeaveEdit.createIntent(context, awardID_FK,processInstanceID,ProcessApplyCode
                        ,"3","getdata"));
            }
            else if(tasktype.equals("外出"))
            {
                toActivity(LeaveEdit.createIntent(context, awardID_FK,processInstanceID,ProcessApplyCode
                        ,"3","getdata"));
            }
            else if(tasktype.equals("出差"))
            {
                toActivity(LeaveEdit.createIntent(context, awardID_FK,processInstanceID,ProcessApplyCode
                        ,"3","getdata"));
            }
        }
        else {
//            Intent it = new Intent();
//            it = LeaveEdit.createIntent(context, awardID_FK, processInstanceID, ProcessApplyCode
//                    , "3", "getdata");
//            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            //     startActivity(it);
//            toActivity(it);

            final EditText inputServer = new EditText(this);
            final AlertDialog dialog = new AlertDialog.Builder(ApplyEditActivity.this)
                    .setTitle("请输入理由")
                    .setView(inputServer)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
             //这里必须要先调show()方法，后面的getButton才有效
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(inputServer.getText())) {
                        showShortToast("请输入理由");
                        return;
                    }
                    dialog.dismiss();
                    //关闭当前活动 跳转其他活动
                    if(tasktype.equals("请假"))
                    {
                        Intent it = new Intent();
                        it = LeaveEdit.createIntent(context, FID, processInstanceID, ProcessApplyCode
                                , "3", "getdata");
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //     startActivity(it);
                        toActivity(it);
                    }
                    else if(tasktype.equals("外出"))
                    {
                        toActivity(LeaveEdit.createIntent(context, awardID_FK,processInstanceID,ProcessApplyCode
                                ,"3","getdata"));
                    }
                    else if(tasktype.equals("出差"))
                    {
                        toActivity(LeaveEdit.createIntent(context, awardID_FK,processInstanceID,ProcessApplyCode
                                ,"3","getdata"));
                    }
                }
            });
        }

//        EditText editText1 = findViewById (R.id.etxtRemark);
//        String strRemark=editText1.getText().toString();
//
//        //获取服务器数据
//        String methodName = "TaskInstanceEdit";
//        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
//        soapObject.addProperty("userID","96");
//        soapObject.addProperty("taskInstanceID","238");
//        soapObject.addProperty("Remark",strRemark);
//        soapObject.addProperty("operate",title);
//        soapObject.addProperty("operatr","96");
//        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
//        HttpRequest httpres= new HttpRequest();
//        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);

        //如果返回不为0 弹出错误提示

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

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}

