package com.example.fv.judgement.app.activity.WayCheck;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fv.judgement.R;
import com.example.fv.judgement.Waydata;
import com.example.fv.judgement.app.activity.GoOut.GoOut;
import com.example.fv.judgement.app.activity.Home.PageHome;
import com.example.fv.judgement.app.activity.Login.MainLogin;
import com.example.fv.judgement.app.application.GlobalInformationApplication;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.model.WayCheckModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.ui.PlacePickerWindow;
import zuo.biao.library.util.PlaceUtil;
import zuo.biao.library.util.StringUtil;



///
/////
/** 使用方法：复制>粘贴>改名>改代码 */
/**fragmentActivity示例
 * @author Lemon
 * @use toActivity(DemoFragmentActivity.createIntent(...));
 */
public class WayCheckFragmentActivity extends BaseActivity implements OnBottomDragListener {
    //	private static final String TAG = "DemoFragmentActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<
    public static final String INTENT_USER_ID = "INTENT_USER_ID";

    private Button btnsave;
    //示例代码>>>>>>>>

    /**启动这个Activity的Intent
     * @param context
     * @param userId
     * @return
     */
    public static Intent createIntent(Context context, long userId) {
        return new Intent(context, WayCheckFragmentActivity.class).putExtra(INTENT_USER_ID, userId);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO demo_fragment_activity改为你所需要的layout文件；传this是为了全局滑动返回
        setContentView(R.layout.activity_way_check_fragment, this);

        btnsave = findViewById(R.id.btnsave);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                Gson g = new Gson();
                String jsonString = g.toJson(demoFragment.list);
                String datastring = InsertWebService(jsonString);
            }
        });

    }

    public String InsertWebService(String jsonString) {

            String datastring = "";
            try {
                String methodName = "InsertProcessChange_andr";

                SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                        methodName);
                //NSString *post = [NSString stringWithFormat:@"strjson=%@&userid=%@&processid=%@&iosid=%@",
                soapObject.addProperty("strjson", jsonString);
                soapObject.addProperty("userid", "94");
                soapObject.addProperty("processid", "271");
                soapObject.addProperty("iosid", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

                HttpRequest http = new HttpRequest();

                datastring = http.httpWebService_GetString(methodName, soapObject);

                return datastring;

            } catch (Exception e) {
                MyLog.writeLogtoFile("错误", "WayChose", "GetGroupWebService", e.toString(), "0");
            }

            return datastring;

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须在onCreate方法内调用
        autoSetTitle();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK) {
            return;
        }
        else
        {
            if(demoFragment.list.size() > 0 )
            {
                Waydata AppWaydata = (Waydata)getApplication();

                if(AppWaydata.getIndex().equals("0"))
                {

                }
                else
                {
                    WayCheckModel addWCM = new WayCheckModel();
                    addWCM.setEnglishname(AppWaydata.getEnglishname());
                    addWCM.setEditflag(AppWaydata.getEditflag());
                    addWCM.setName(AppWaydata.getName());
                    addWCM.setGroupname(AppWaydata.getGroupname());
                    addWCM.setLevelname(AppWaydata.getLevelname());
                    addWCM.setNameid(AppWaydata.getNameid());
                    addWCM.setLevel(AppWaydata.getLevel());
                    addWCM.setGroupid(AppWaydata.getGroupid());
                    addWCM.setIndex(AppWaydata.getIndex());
                    int index =Integer.parseInt(AppWaydata.getIndex());

                    demoFragment.list.add(index,addWCM);
                }

            };

            for(int i = 0; i < demoFragment.list.size(); i++)
            {
                demoFragment.list.get(i).setIndex( String.valueOf(i) );
            }


            demoFragment.setList(demoFragment.list);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

//        if(demoFragment.list.size() > 0 )
//        {
//            Waydata AppWaydata = (Waydata)getApplication();
//
//            if(AppWaydata.getIndex().equals("0"))
//            {
//
//            }
//            else
//            {
//                WayCheckModel addWCM = new WayCheckModel();
//                addWCM.setEnglishname(AppWaydata.getEnglishname());
//                addWCM.setEditflag(AppWaydata.getEditflag());
//                addWCM.setName(AppWaydata.getName());
//                addWCM.setGroupname(AppWaydata.getGroupname());
//                addWCM.setLevelname(AppWaydata.getLevelname());
//                int index =Integer.parseInt(AppWaydata.getIndex());
//
//                demoFragment.list.add(index,addWCM);
//            }
//
//        };
//
//        for(int i = 0; i < demoFragment.list.size(); i++)
//        {
//            demoFragment.list.get(i).setIndex( String.valueOf(i) );
//        }
//
//
//        demoFragment.setList(demoFragment.list);

    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<
    public WayCheckFragment demoFragment;
    //示例代码>>>>>>>>
    @Override
    public void initData() {//必须在onCreate方法内调用
        //示例代码<<<<<<<<
        demoFragment = WayCheckFragment.createInstance(getIntent().getLongExtra(INTENT_USER_ID, 0));

        fragmentManager
                .beginTransaction()
                .add(R.id.flDemoFragmentActivityContainer, demoFragment)
                .show(demoFragment)
                .commit();
        //示例代码>>>>>>>>
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreate方法内调用

    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }



    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}

