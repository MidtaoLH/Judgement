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

    public static final String Edit_PHOTO_URL = "http://47.94.85.101:8095/%s";

    public static final String UpdateIMAGE_URL = "http://47.94.85.101:8095//ImageHandler.ashx?name=%s&picname=%s";

    public static final String IMAGE_FUJIAN_URL = "http://47.94.85.101:8095/";

    public static final int maxImageSelectNum=6;

    public static final String UnLoginFlag="unlogin";

    public static final int pageSize=5;

    public static final String editLeave="请假申请";

   public static final int cellStatusColor=Color.rgb(0, 204, 204);

    public static String SaveMessage="保存成功";

    public static String SaveMessageN="保存失败";

    public static String UpdateMessage="修改成功";

    public static String UpdateMessageN="修改失败";
}
