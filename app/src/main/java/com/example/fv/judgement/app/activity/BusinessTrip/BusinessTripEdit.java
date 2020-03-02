package com.example.fv.judgement.app.activity.BusinessTrip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.adapter.GridImageAdapter;
import com.example.fv.judgement.app.application.FullyGridLayoutManager;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalMethodApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.BusinessTripDetailModel;
import com.example.fv.judgement.app.model.LeaveStatusModel;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.example.fv.judgement.app.util.UploadImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.DatePickerWindow;
import zuo.biao.library.util.StringUtil;
import zuo.biao.library.util.TimeUtil;

public class BusinessTripEdit extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.class.getSimpleName();
    private String  businessTripID="0", userID, groupid, empID, processInstanceID="0", iosid
            , empname, pagetype,ApplyCode,userHour,proCelReson,vtype,operateType;
    private TextView title,lblTitle, lblbalance, lblstartdate, lblenddate, lblCause,lblduration,typetag,startdatetag,endDateTag;
    private EditText txtcause,txtduration;
    private Button btnpath, btnsave, btnsubmit;

    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelectNum = GlobalVariableApplication.maxImageSelectNum;
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();

    private static final int REQUEST_TO_DATE_PICKER = 33;
    private static final int REQUEST_TO_DATE_PICKEREND = 34;
    private int[] selectedDate = new int[]{1971, 0, 1};
    private int[] selectedStaraDate,selectedEndDate;

    //出差地点
    private LinearLayout busnessTripPlace1,busnessTripPlace2,busnessTripPlace3,busnessTripPlace4,busnessTripPlace5;
    private EditText txtbusnessTripPlace1,txtbusnessTripPlace2,txtbusnessTripPlace3,txtbusnessTripPlace4,txtbusnessTripPlace5;
    private TextView lblplace1,lblplace2,lblplace3,lblplace4,lblplace5;
    private ImageView btnadd1,btnadd2,btnadd3,btnadd4,btnadd5;
    private ImageView btndelete1,btndelete2,btndelete3,btndelete4,btndelete5;

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private Intent intent;
    public static Intent createIntent(Context context, String businessTripID, String processInstanceID, String pagetype) {
        return new Intent(context, com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.class) .
                putExtra("businessTripID", businessTripID).
                putExtra("processInstanceID", processInstanceID).
                putExtra("pagetype", pagetype);
    }

    public static Intent createIntent(Context context,String pagetype) {
        return new Intent(context, BusinessTripEdit.class) .
                putExtra("pagetype", pagetype);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesstrip_edit);
        initImage();
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        pagetype = StringUtil.get(getIntent().getStringExtra("pagetype"));
        if(pagetype.equals("2") || pagetype.equals("3"))
        {
            businessTripID = StringUtil.get(getIntent().getStringExtra("businessTripID"));
            processInstanceID = StringUtil.get(getIntent().getStringExtra("processInstanceID"));
            operateType="2";
            initData();
        }
        else
        {
            pagetype="1";
        }
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        GlobalMethodApplication.TxtNumber(txtduration);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
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
                            PictureSelector.create(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }
    public void initImage() {
        themeId = R.style.picture_default_style;
        recyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this);
                } else {
                    Toast.makeText(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this,
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
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initView() {
        LoginUserModel model = GlobalInformationApplication.getInstance().getCurrentUser();
        userID = model.getId();
        empID = model.getEmpID();
        groupid = model.getGroupid();
        empname = model.getGroupName();
        iosid =model.getAdId();
        userHour = model.getUserHour();

        intent = getIntent();

        lblstartdate = (TextView) findViewById(R.id.lblstartdate);
        lblenddate = (TextView) findViewById(R.id.lblenddate);
        lblCause = (TextView) findViewById(R.id.lblCause);
        txtduration = (EditText) findViewById(R.id.txtduration);
        txtcause = (EditText) findViewById(R.id.txtcause);
        btnpath = (Button) findViewById(R.id.btnpath);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        lblduration=(TextView) findViewById(R.id.lblduration);
        startdatetag = (TextView) findViewById(R.id.startdatetag);
        endDateTag = (TextView) findViewById(R.id.endDateTag);
        txtcause = (EditText) findViewById(R.id.txtcause);
        title=(TextView) findViewById(R.id.title);

        //出差地点
        busnessTripPlace1=(LinearLayout)findView(R.id.busnessTripPlace1);
        busnessTripPlace2=(LinearLayout)findView(R.id.busnessTripPlace2);
        busnessTripPlace3=(LinearLayout)findView(R.id.busnessTripPlace3);
        busnessTripPlace4=(LinearLayout)findView(R.id.busnessTripPlace4);
        busnessTripPlace5=(LinearLayout)findView(R.id.busnessTripPlace5);
        lblplace1=(TextView)findView(R.id.lblplace1);
        lblplace2=(TextView)findView(R.id.lblplace2);
        lblplace3=(TextView)findView(R.id.lblplace3);
        lblplace4=(TextView)findView(R.id.lblplace4);
        lblplace5=(TextView)findView(R.id.lblplace5);
        txtbusnessTripPlace1=(EditText)findView(R.id.txtbusnessTripPlace1);
        txtbusnessTripPlace2=(EditText)findView(R.id.txtbusnessTripPlace2);
        txtbusnessTripPlace3=(EditText)findView(R.id.txtbusnessTripPlace3);
        txtbusnessTripPlace4=(EditText)findView(R.id.txtbusnessTripPlace4);
        txtbusnessTripPlace5=(EditText)findView(R.id.txtbusnessTripPlace5);
        btnadd1=(ImageView)findView(R.id.btnadd1);
        btnadd2=(ImageView)findView(R.id.btnadd2);
        btnadd3=(ImageView)findView(R.id.btnadd3);
        btnadd4=(ImageView)findView(R.id.btnadd4);
        btnadd5=(ImageView)findView(R.id.btnadd5);
        btndelete1=(ImageView)findView(R.id.btndelete1);
        btndelete2=(ImageView)findView(R.id.btndelete2);
        btndelete3=(ImageView)findView(R.id.btndelete3);
        btndelete4=(ImageView)findView(R.id.btndelete4);
        btndelete5=(ImageView)findView(R.id.btndelete5);
        busnessTripPlace2.setVisibility(View.GONE);
        busnessTripPlace3.setVisibility(View.GONE);
        busnessTripPlace4.setVisibility(View.GONE);
        busnessTripPlace5.setVisibility(View.GONE);
        String strPlace =  " <font color='#FF0000'>*</font> 出差地点";
        lblplace1.setText(Html.fromHtml(strPlace));
        lblplace2.setText(Html.fromHtml(strPlace));
        lblplace3.setText(Html.fromHtml(strPlace));
        lblplace4.setText(Html.fromHtml(strPlace));
        lblplace5.setText(Html.fromHtml(strPlace));
        btnadd1.setOnClickListener(this);
        btnadd2.setOnClickListener(this);
        btnadd3.setOnClickListener(this);
        btnadd4.setOnClickListener(this);
        btndelete1.setOnClickListener(this);
        btndelete2.setOnClickListener(this);
        btndelete3.setOnClickListener(this);
        btndelete4.setOnClickListener(this);

        //title.setText(GlobalVariableApplication.editLeave);
        String str =  " <font color='#FF0000'>*</font> 出发日期";
        lblstartdate.setText(Html.fromHtml(str));
        str = " <font color='#FF0000'>*</font> 返回日期";
        lblenddate.setText(Html.fromHtml(str));
        str = " <font color='#FF0000'>*</font> 出差事由";
        lblCause.setText(Html.fromHtml(str));
        str = " <font color='#FF0000'>*</font> 出差天数";
        lblduration.setText(Html.fromHtml(str));
        str = "请输入";
        lblduration.setHint(str);
        str = "请输入出差事由";
        txtcause.setHint(str);

        btnpath.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
    }
    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initData() {
        //必须在onCreate方法内调用

        String jsonString = GetInfo();
        //拆分 头表、图片、出差地点
        String[] parts = jsonString.split("❀");
        String jsondetail=parts[0];//头信息
        String jsonphoto=parts[2];//图片
        String jsonplace=parts[1];//出差地点

        List<BusinessTripDetailModel> LUDetail = new ArrayList<BusinessTripDetailModel>();
        List<BusinessTripDetailModel> LUPhoto = new ArrayList<BusinessTripDetailModel>();
        List<BusinessTripDetailModel> LUPlace = new ArrayList<BusinessTripDetailModel>();

        //json转为实体
        Type type1 = new TypeToken<List<BusinessTripDetailModel>>() {
        }.getType();
        LUDetail = new Gson().fromJson(jsondetail, type1);
        LUPhoto = new Gson().fromJson(jsonphoto, type1);
        LUPlace = new Gson().fromJson(jsonplace, type1);

        //json转为实体
        //Type BusinessTripDetailModel = new TypeToken<List<BusinessTripDetailModel>>() { }.getType();

        String str = GlobalMethodApplication.getMyDate(LUDetail.get(0).getBusinessTripStartTime());
        startdatetag.setText(str);
        selectedStaraDate=GlobalMethodApplication.StringToInt(str);

        str =  GlobalMethodApplication.getMyDate(LUDetail.get(0).getBusinessTripEndTime());
        endDateTag.setText(GlobalMethodApplication.getMyDate(str));
        selectedEndDate=GlobalMethodApplication.StringToInt(str);

        str = LUDetail.get(0).getBusinessTripNum();
        txtduration.setText(str);
        str = LUDetail.get(0).getBusinessTripReason();
        txtcause.setText(str);

        //出差地点
        for(int i=0;i<LUPlace.size();i++) {
            String strPlace = LUPlace.get(i).getBusinessTripPlace();
            if(i==0){
                busnessTripPlace1.setVisibility(View.VISIBLE);
                txtbusnessTripPlace1.setText(strPlace);
            }
            if(i==1){
                busnessTripPlace2.setVisibility(View.VISIBLE);
                txtbusnessTripPlace2.setText(strPlace);
            }
            if(i==2){
                busnessTripPlace3.setVisibility(View.VISIBLE);
                txtbusnessTripPlace3.setText(strPlace);
            }
            if(i==3){
                busnessTripPlace4.setVisibility(View.VISIBLE);
                txtbusnessTripPlace4.setText(strPlace);
            }
            if(i==4){
                busnessTripPlace5.setVisibility(View.VISIBLE);
                txtbusnessTripPlace5.setText(strPlace);
            }
        }

        //图片
        for (BusinessTripDetailModel bean : LUPhoto) {
            if (bean.getAnnexName() != null) {
                LocalMedia localMedia = new LocalMedia();
                String url = String.format(GlobalVariableApplication.Edit_PHOTO_URL, bean.getAnnexPath());
                localMedia.setPath(url);
                selectList.add(localMedia);//添加图片
            }
        }
    }
    public String SaveInfo(String type) {
        try {
            if(txtbusnessTripPlace1.getText().toString().trim().equals("") && txtbusnessTripPlace2.getText().toString().trim().equals("") && txtbusnessTripPlace3.getText().toString().trim().equals("")
                    && txtbusnessTripPlace4.getText().toString().trim().equals("") && txtbusnessTripPlace5.getText().toString().trim().equals(""))
            {
                return "出差地点不能为空";
            }
            if(lblstartdate.getText().toString().trim().equals(""))
            {
                return "出发日期不能为空";
            }
            if(lblenddate.getText().toString().trim().equals(""))
            {
                return "返回日期不能为空";
            }
            if(txtduration.getText().toString().trim().equals(""))
            {
                return "出差天数不能为空";
            }
            if(GlobalMethodApplication.convertToDouble(txtduration.getText().toString(),0)<=0)
            {
                return "出差天数必须大于0";
            }
            if(GlobalMethodApplication.convertToDouble(txtduration.getText().toString(),0)>365)
            {
                return "出差天数不能大于365";
            }
            if(txtcause.getText().toString().trim().equals(""))
            {
                return "出差事由不能为空";
            }
            if(businessTripID==null)
            {
                businessTripID = "0";
            }
            if(processInstanceID==null)
            {
                processInstanceID = "0";
            }
            if(type.equals("0"))
            {
                if(pagetype.equals("4")){ //新增进入
                    pagetype="1";  //申请 原单还没有申请
                }
                else if(pagetype.equals("5")){ //待申请进入
                    pagetype="2";  //申请 原单还没有申请
                }
                else if(pagetype.equals("6")){ //修改已申请进入
                    pagetype="3";  //申请 原单还没有申请
                }
            }
            else
            {
                if(pagetype.equals("1")){ //新增进入
                    pagetype="4";  //申请 原单还没有申请
                }
                else if(pagetype.equals("2")){ //待申请进入
                    pagetype="5";  //申请 原单还没有申请
                }
                else if(pagetype.equals("3")){ //修改已申请进入
                    pagetype="6";  //申请 原单还没有申请
                }
            }
            String strPlace = ""; //出差地点{"json" : [    "B",    "W",    "H"  ]}
            if(!txtbusnessTripPlace1.getText().toString().trim().equals("")){
                strPlace="{\"json\" : [\""+txtbusnessTripPlace1.getText().toString().trim()+"\"";
            }
            if(!txtbusnessTripPlace2.getText().toString().trim().equals("")){
                strPlace=strPlace+"\""+","+txtbusnessTripPlace2.getText().toString().trim()+"\"";
            }
            if(!txtbusnessTripPlace3.getText().toString().trim().equals("")){
                strPlace=","+strPlace+"\""+txtbusnessTripPlace3.getText().toString().trim()+"\"";
            }
            if(!txtbusnessTripPlace4.getText().toString().trim().equals("")){
                strPlace=","+strPlace+"\""+txtbusnessTripPlace4.getText().toString().trim()+"\"";
            }
            if(!txtbusnessTripPlace5.getText().toString().trim().equals("")){
                strPlace=","+strPlace+"\""+txtbusnessTripPlace5.getText().toString().trim();
            }
            strPlace=strPlace+"\"]}";

            String methodName = "BusinessTripSave";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("userID", userID);
            soapObject.addProperty("processID", processInstanceID);
            soapObject.addProperty("businessTripID", businessTripID);
            soapObject.addProperty("empID", empID);
            soapObject.addProperty("groupID", groupid);
            soapObject.addProperty("starttime", startdatetag.getText().toString());
            soapObject.addProperty("endtime", endDateTag.getText().toString());
            soapObject.addProperty("businessTripNum", txtduration.getText().toString().trim());
            soapObject.addProperty("reson", txtcause.getText().toString().trim());
            soapObject.addProperty("operateType", pagetype);
            soapObject.addProperty("imageCount", selectList.size());
            soapObject.addProperty("strdetail", strPlace);
            soapObject.addProperty("iosid", iosid);
            HttpRequest http = new HttpRequest();
            String datastring = http.httpWebService_GetString(methodName, soapObject);
            if(datastring!="")
            {
                List<LeaveStatusModel> LU = new ArrayList<LeaveStatusModel>();
                //json转为实体
                Type type1 = new TypeToken<List<LeaveStatusModel>>() {
                }.getType();
                LU = new Gson().fromJson(datastring, type1);
                //json转为实体
                Type LeaveStatusModel = new TypeToken<List<LeaveStatusModel>>() {
                }.getType();

                if(!LU.get(0).getProcessID().equals("0"))
                {
                    int i=1;
                    String status="";
                    if (selectList.size()>0)
                    {
                        for (LocalMedia bean : selectList) {
                            if (bean.getCompressPath() != null) {
                                File file = new File(bean.getCompressPath());
                                ExecutorService pool = Executors.newFixedThreadPool(10);
                                String url = String.format(GlobalVariableApplication.UpdateIMAGE_URL, LU.get(0).getApplyCode(),i+".png");
                                Callable<String> c1 = new UploadImage("图片线程", url, file);
                                Future<String> f1 = pool.submit(c1);
                                status = f1.get();
                                pool.shutdown();
                                i++;
                            }
                        }
                    }
                    return "1";
                }
                else
                {
                    return  "0";
                }
            }
            else
            {
                return  "0";
            }

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "BusinessTripEdit", "GetInfo", e.toString(), "0");
        }
        return "";
    }
    public String GetInfo() {
        String datastring = "";
        try {
            //NSString *strURL = [NSString stringWithFormat:@"http://47.94.85.101:8095/AppWebService.asmx/VatcationSearchByID?userID=%@&VatcationID=%@&processid=%@&iosid=%@", userID,vatcationid,processid,iosid];
            String methodName = "BusinessTripSearchByID";
            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);
            soapObject.addProperty("userID", userID);
            soapObject.addProperty("businessTripID", businessTripID);
            soapObject.addProperty("iosid", iosid);
            HttpRequest http = new HttpRequest();
            datastring = http.httpWebService_GetString(methodName, soapObject);
            return datastring;
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误", "BusinessTripEdit", "GetInfo", e.toString(), "0");
        }
        return "";
    }
    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void initEvent() {//必须在onCreate方法内调用
        //findView(R.id.leavetype).setOnClickListener(this);
        findView(R.id.llStartDateTag).setOnClickListener(this);
        findView(R.id.llEndDateTag).setOnClickListener(this);
        findView(R.id.llduration).setOnClickListener(this);
        findView(R.id.btnpath).setOnClickListener(this);
    }
    //图片控件)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            try {
                boolean mode = true;// cb_mode.isChecked();
                if (mode) {
                    // 进入相册 以下是例子：不需要的api可以不写
                    PictureSelector.create(com.example.fv.judgement.app.activity.BusinessTrip.BusinessTripEdit.this)
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
                MyLog.writeLogtoFile("错误", "BusinessTripEdit", "onAddPicClick", e.toString(), "0");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0x002&&resultCode==0x001){
            typetag.setText(data.getStringExtra("name"));//当LoginActivity finish后，就会调用这里，data为值
            vtype=data.getStringExtra("code");
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
                        startdatetag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
                        selectedDate[1]=selectedDate[1]+1;
                        selectedStaraDate= selectedDate;
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
                        endDateTag.setText(selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
                        selectedDate[1]=selectedDate[1]+1;
                        selectedEndDate= selectedDate;
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        String message="";
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.llduration:
                break;
            case R.id.llStartDateTag:
                if(startdatetag.getText().toString()=="")
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            , TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKER, false);
                }
                else
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            ,selectedStaraDate), REQUEST_TO_DATE_PICKER, false);
                }
                break;
            case R.id.llEndDateTag:
                if(endDateTag.getText().toString()=="")
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            , TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKEREND, false);
                }
                else
                {
                    toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                            ,selectedEndDate), REQUEST_TO_DATE_PICKEREND, false);
                }
                break;
            case R.id.btnpath:
                break;
            case R.id.btnadd1:
                busnessTripPlace2.setVisibility(View.VISIBLE);
                break;
            case R.id.btndelete1:
                busnessTripPlace2.setVisibility(View.GONE);
                break;
            case R.id.btnadd2:
                busnessTripPlace3.setVisibility(View.VISIBLE);
                break;
            case R.id.btndelete2:
                busnessTripPlace3.setVisibility(View.GONE);
                break;
            case R.id.btnadd3:
                busnessTripPlace4.setVisibility(View.VISIBLE);
                break;
            case R.id.btndelete3:
                busnessTripPlace4.setVisibility(View.GONE);
                break;
            case R.id.btnadd4:
                busnessTripPlace5.setVisibility(View.VISIBLE);
                break;
            case R.id.btndelete4:
                busnessTripPlace5.setVisibility(View.GONE);
                break;
            case R.id.btnsave:
                message=SaveInfo("0");
                if(message=="1")
                {
                    showShortToast(GlobalVariableApplication.SaveMessage);
                    finish();
                }
                else if(message=="0")
                {
                    showShortToast(GlobalVariableApplication.SaveMessageN);
                }
                else
                {
                    showShortToast(message);
                }
                break;
            case R.id.btnsubmit:
                message=SaveInfo("1");
                if(message=="1")
                {
                    showShortToast(GlobalVariableApplication.UpdateMessage);
                    finish();
                }
                else if(message=="0")
                {
                    showShortToast(GlobalVariableApplication.UpdateMessageN);
                }
                else
                {
                    showShortToast(message);
                }
                break;
        }
    }


    @Override
    public boolean onLongClick(View v)
    {

        return false;
    }

}

//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


