package com.example.fv.judgement.app.application;

import android.content.ContentResolver;
import android.graphics.Color;
import android.provider.Settings;

/**
 * Created by lh on 2020/2/18.
 */

public class GlobalVariableApplication {
    public static final String SERVICE_NAMESPACE = "http://tempuri.org/";
    // 定义webservice提供服务的url
    public static final String SERVICE_URL = "http://47.94.85.101:8095/AppWebService.asmx";

    public static final String IMAGE_URL = "http://47.94.85.101:8095/APP/Image/";

    public static final String SERVICE_PHOTO_URL = "http://47.94.85.101:8095/APP/Image/%s.png";

    public static final String IMAGE_URL = "http://47.94.85.101:8095/APP/Image/";

    public static final int maxImageSelectNum=6;

    public static final int pageSize=5;

    public static final String editLeave="请假申请";

   public static final int cellStatusColor=Color.rgb(0, 204, 204);

}
