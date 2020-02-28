package com.example.fv.judgement.app.activity.ApplyEdit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.ImageListAdapter;
import com.example.fv.judgement.app.adapter.applytasklook.ApplyTaskLookAdapter;
import com.example.fv.judgement.app.application.FullyGridLayoutManager;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.ApplyTaskLook.ApplyTaskLook;
import com.example.fv.judgement.app.model.ApplyTaskLook.ApplyTaskLookImage;
import com.example.fv.judgement.app.model.ApplyTaskLook.ApplyTaskLookList;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.permissions.RxPermissions;
import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.util.CommonUtil;

public class ApplyTaskLookActivity extends AppCompatActivity {
    public static final String TAG = "ApplyTaskLookActivity";

    //头部
    public ImageView ivUserViewHead; //创建画面所需控件
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
    private String code;
    private String adId;
    private String strpagetype;
    //页面传递参数 end

    public static Intent createIntent(Context context, String pagetype, String code) {
        Intent intent = new Intent(context, ApplyTaskLookActivity.class);
        intent.putExtra("strpagetype", pagetype);
        intent.putExtra("code", code);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applytask_list);

        //给本画面传递参数
        Intent intent = getIntent();
        strpagetype =intent.getStringExtra("strpagetype");
        code =intent.getStringExtra("code");

        //初始化图片选择控件
        initViewImage();

        //设置列表 布局样式
        this.lvProduct = findViewById(R.id.ExamineList);

        //加载服务器数据  并给画面赋值
        initDataHead();
        initDataImage();
        initDataTaskList();
        //加载服务器数据  并给画面赋值end

        //图片点击注册
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
                            PictureSelector.create(ApplyTaskLookActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ApplyTaskLookActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ApplyTaskLookActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
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
                    PictureSelector.create(ApplyTaskLookActivity.this)
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

    //获取服务器数据
    public void initDataHead() {
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        String methodName = "GetProcInfo";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","75");
        soapObject.addProperty("processInstanceID",code);
        soapObject.addProperty("pageType",strpagetype);
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<ApplyTaskLook> listData=new ArrayList<ApplyTaskLook>();
        Type type = new TypeToken<List<ApplyTaskLook>>(){}.getType();
        listData = new Gson().fromJson(jsonData,type);
        initViewHead(listData.get(0));
        String end = "";
    }
    //获取服务器数据
    public void initDataImage() {
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        String methodName = "GetProcFile";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","75");
        soapObject.addProperty("processInstanceID",code);
        soapObject.addProperty("pageType",strpagetype);
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<ApplyTaskLookImage> listData=new ArrayList<ApplyTaskLookImage>();

        Type typeImage = new TypeToken<List<ApplyTaskLookImage>>(){}.getType();
        listData = new Gson().fromJson(jsonData,typeImage);
        for (ApplyTaskLookImage bean : listData) {
            if (bean != null) {
                LocalMedia localMedia = new LocalMedia();
                String url = GlobalVariableApplication.IMAGE_FUJIAN_URL + bean.getAttachFilePath();
                localMedia.setPath(url);
                selectList.add(localMedia);//添加图片
            }
        }
        String end = "";
    }
    //获取服务器数据
    public void initDataTaskList() {
        LoginUserModel userModel = GlobalInformationApplication.getInstance().getCurrentUser();

        //获取服务器数据
        String methodName = "GetProcTaskInstance";
        SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
        soapObject.addProperty("userID","75");
        soapObject.addProperty("processInstanceID",code);
        soapObject.addProperty("pageType",strpagetype);
        soapObject.addProperty("iosid","00000000-0000-0000-0000-000000000000");
        HttpRequest httpres= new HttpRequest();
        String jsonData = httpres.httpWebService_GetString(methodName,soapObject);
        List<ApplyTaskLookList> listData=new ArrayList<ApplyTaskLookList>();
        Type type = new TypeToken<List<ApplyTaskLookList>>(){}.getType();
        listData = new Gson().fromJson(jsonData,type);
        initViewList(listData);

        String end = "";
    }
    //头部赋值
    public void initViewHead(ApplyTaskLook Item) {//必须调用

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
        String strPhoto = String.format(GlobalVariableApplication.SERVICE_PHOTO_URL,Item.getApplyMan());
        Glide.with(ApplyTaskLookActivity.this).asBitmap().load(strPhoto).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                ivUserViewHead.setImageBitmap(CommonUtil.toRoundCorner(bitmap, bitmap.getWidth()/2));
            }
        });
        tvEmpName.setText(Item.getApplyManName());
        tvGroupName.setText(Item.getApplyGroupName());
        tvExamineDate.setText("申请时间: " + Item.getApplyDate());
        tvStatus.setText(Item.getProcStatus());

        if(Item.getDocumentName().equals("请假"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="请假时长: ";
            strDatetxt = "请假时间: ";
            strRemarktxt = "请假事由: ";
        }
        else if(Item.getDocumentName().equals("外出"))
        {
            tvPlace.setVisibility(View.GONE);
            strDateCounttxt="外出时长: ";
            strDatetxt = "外出时间: ";
            strRemarktxt = "外出事由: ";
        }
        else if(Item.getDocumentName().equals("出差"))
        {
            strDateCounttxt="出差天数: ";
            strDatetxt = "出差时间: ";
            strRemarktxt = "出差事由: ";
            tvPlace.setText(strDateCounttxt+Item.getCCAddress());
        }
        tvDate.setText(strDatetxt+Item.getStrattime() + "～" +Item.getEndtime() );
        tvCount.setText(strDateCounttxt+Item.getApplyAmount());
        tvRemark.setText(strRemarktxt + Item.getProcDescribe());
    }
    //图片赋值
    public void initViewImage()
    {
        themeId = R.style.picture_default_style;
        recyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ApplyTaskLookActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new ImageListAdapter(ApplyTaskLookActivity.this, onAddPicClickListener);
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
                    PictureFileUtils.deleteCacheDirFile(ApplyTaskLookActivity.this);
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
    //承认列表赋值
    public void initViewList(List<ApplyTaskLookList> listTaskList)
    {
        ApplyTaskLookAdapter adapter = new ApplyTaskLookAdapter(getApplicationContext(), listTaskList);
        this.lvProduct.setAdapter(adapter);
    }
}
