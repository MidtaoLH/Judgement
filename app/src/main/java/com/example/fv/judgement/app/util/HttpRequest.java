package com.example.fv.judgement.app.util;


import android.util.Log;

import com.example.fv.judgement.app.application.GlobalVariableApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lh on 2020/2/18.
 */

public class HttpRequest {
    // 调用远程webservice获取省份列表
    //参数1方法名 参数2 参数信息 返回值list<t>
    // SoapObject soapObject = new SoapObject(GlobalVariableApplication.SERVICE_NAMESPACE,methodName);
    // soapObject.addProperty("User", "zhaodan");
    public <T>  List<T> httpWebService(String methodName, SoapObject soapObject) {
        // 创建HttpTransportSE传输对象
        HttpTransportSE ht = new HttpTransportSE(GlobalVariableApplication.SERVICE_URL);
        try {
            ht.debug = true;
            // 使用SOAP1.1协议创建Envelop对象
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER10);
            // 实例化SoapObject对象
            envelope.bodyOut = soapObject;
            // 设置与.NET提供的webservice保持较好的兼容性
            envelope.dotNet = true;
            // 调用webservice
            ht.call(GlobalVariableApplication.SERVICE_NAMESPACE + methodName, envelope);
            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                // SoapObject result = (SoapObject) envelope.bodyIn;
                Object object = (Object)envelope.getResponse();
                String jsonString=object.toString();
                // String jsonString=new Gson().toJson(object);
                Log.e("test", jsonString);
                // String str =  (String) envelope.getResponse();
                List<T> LU=new ArrayList<T>();
                Type type1=new TypeToken<List<T>>(){}.getType();
                LU = new Gson().fromJson(jsonString, type1);
                Log.e("test", LU.size()+"");
                return LU;
            }
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","HttpRequest","httpWebService",e.toString(),"0");
        }
        return null;
    }

    public String httpWebService_GetString(String methodName,SoapObject soapObject) {
        // 创建HttpTransportSE传输对象
        HttpTransportSE ht = new HttpTransportSE(GlobalVariableApplication.SERVICE_URL);
        try {
            ht.debug = true;
            // 使用SOAP1.1协议创建Envelop对象
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER10);
            // 实例化SoapObject对象
            envelope.bodyOut = soapObject;
            // 设置与.NET提供的webservice保持较好的兼容性
            envelope.dotNet = true;
            // 调用webservice
            ht.call(GlobalVariableApplication.SERVICE_NAMESPACE + methodName, envelope);
            if (envelope.getResponse() != null) {
                // 获取服务器响应返回的SOAP消息
                // SoapObject result = (SoapObject) envelope.bodyIn;
                Object object = (Object)envelope.getResponse();
                String jsonString=object.toString();
                // String jsonString=new Gson().toJson(object);
                Log.e("test", jsonString);

                return jsonString;
            }
        } catch (Exception e) {
            MyLog.writeLogtoFile("错误","HttpRequest","httpWebService",e.toString(),"0");
        }
        return "";
    }


}
