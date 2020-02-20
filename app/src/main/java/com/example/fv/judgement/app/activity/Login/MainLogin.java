package com.example.fv.judgement.app.activity.Login;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fv.judgement.R;
import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.example.fv.judgement.app.model.BaseBean;
import com.example.fv.judgement.app.model.LoginUserModel;
import com.example.fv.judgement.app.util.HttpRequest;
import com.example.fv.judgement.app.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zuo.biao.library.base.SpinnerEditText;

public class MainLogin extends AppCompatActivity {

    private Button loginbtn;
   // private EditText Username;
    private EditText Password;

    private  String adduserlistflag = "true";


    private SpinnerEditText<BaseBean> set_diy_att;

    private SharedPreferences preferences;
    private String userid;
    private String usercount = "0";
    private String adId;
    private int usercount_int = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //保证可以在主线程中执行调用ws
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //实例化控件
       // Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        loginbtn  = findViewById(R.id.login);

        //从硬盘获取数据
        preferences = getSharedPreferences("MainData",MainLogin.MODE_PRIVATE);
        userid = preferences.getString("username", "");
        usercount = preferences.getString("usercount", "0");
        //作为下拉框的计数器
        usercount_int = Integer.parseInt(usercount);


        //获取本机id
        adId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        if(userid.length() > 0)
        {
          //  Username.setText(userid);
            String LoginFlag = UseWebservice();
            if(LoginFlag.equals("1") )
            {
                //登录到首页

            }
        }

        initDIYAttSpinnerEditText();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //用户名或密码为空提示
                                if(set_diy_att.getValue().length() <= 0  || Password.getText().length() <= 0)
                                {
                                    new AlertDialog.Builder(MainLogin.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("")
                                            .setMessage("用户名密码不能为空")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                }
                                            }).create().show();

                                }
                                else
                                {
                                    userid = set_diy_att.getValue();

                                    String json = "";
                                    json = LoginWebService();

                                    List<LoginUserModel> LU=new ArrayList<LoginUserModel>();
                                    //json转为实体
                                    Type type1=new TypeToken<List<LoginUserModel>>(){}.getType();
                                    LU = new Gson().fromJson(json, type1);

                                    if(LU.size() > 0)
                                    {
                                        String Flag = LU.get(0).getFlag().toString();
                                        if(Flag.equals("1"))
                                        {
                                            //denglu chengg

                                            if(usercount_int > 0)
                                            {
                                                for(int i = 1; i<= usercount_int;i++)
                                                {


                                                    String username = preferences.getString("username"+i, "");

                                                    if(set_diy_att.getValue().equals(username))
                                                    {
                                                        adduserlistflag = "false";
                                                    }

                                                }
                                            }
                                            else
                                            {

                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("username1", set_diy_att.getValue());
                                                editor.commit();//写入

                                            }

                                            if(adduserlistflag.equals("true"))
                                            {
                                                usercount_int = usercount_int + 1;
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("usercount",  Integer.toString(usercount_int));

                                                editor.putString("username"+usercount_int, set_diy_att.getValue());
                                                editor.commit();//写入
                                            }


                                        }
                                    }
                                }

                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void initDIYAttSpinnerEditText(){
        set_diy_att= (SpinnerEditText<BaseBean>) findViewById(R.id.set_div_att);
        set_diy_att.setRightCompoundDrawable(R.drawable.vector_drawable_arrowdown);

        set_diy_att.setOnItemClickListener(new SpinnerEditText.OnItemClickListener<BaseBean>() {
            @Override
            public void onItemClick(BaseBean baseBean, SpinnerEditText<BaseBean> var1, View var2, int position, long var4, String selectContent) {
                new AlertDialog.Builder(MainLogin.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("test")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }).create().show();
            }
        });

        List<BaseBean> baseBeanList = new ArrayList<>();

        if(usercount_int == 0)
        {
            BaseBean baseBean = new BaseBean();
            baseBean.Name = "" ;
            baseBean.Id = 0;
            baseBeanList.add(baseBean);
        }
        else
        {
            for (int i = 1; i <= usercount_int; i++) {
                BaseBean baseBean = new BaseBean();
                baseBean.Name = preferences.getString("username" + i, "");
                baseBean.Id = i;
                baseBeanList.add(baseBean);
            }
        };
        set_diy_att.setNeedShowSpinner(true);
        set_diy_att.setList(baseBeanList);
        set_diy_att.setAlwaysShowAllItemList(false);
    }


    public  String  UseWebservice()
    {
        String datastring = "";
        try{


            String methodName = "GetLoginStatus";

            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);

            soapObject.addProperty("User", userid);

            soapObject.addProperty("macid", adId);

            HttpRequest http=new HttpRequest();

            datastring =http.httpWebService_GetString(methodName,soapObject);

            return datastring;

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","MainLogin","UseWebservice",e.toString(),"0");
        }

        return "";
    }

    public  String   LoginWebService()
    {
        String datastring = "";
        try{


            String methodName = "CheckUser";

            SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,
                    methodName);

            soapObject.addProperty("User", userid);
            soapObject.addProperty("Password", Password.getText().toString());
            soapObject.addProperty("macid", adId);

            HttpRequest http=new HttpRequest();

            datastring =http.httpWebService_GetString(methodName,soapObject);

            return datastring;

        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","MainLogin","UseWebservice",e.toString(),"0");
        }

        return datastring;
    }

}
