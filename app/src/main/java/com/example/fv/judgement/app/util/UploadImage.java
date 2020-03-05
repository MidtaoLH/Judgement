package com.example.fv.judgement.app.util;
import android.service.autofill.FillEventHistory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class UploadImage implements Callable<String> {
    private String str,actionUrl;
    private File files;
    private static String tag = "决裁系统" ;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    public UploadImage(String str, String actionUrl, File files) {
        this.str = str;
        this.actionUrl=actionUrl;
        this.files=files;
    }

    // 需要实现Callable的Call方法
    public String call() throws Exception {
        if("日志线程".equals(str)){
            Thread.sleep(1000);
        }
        String boundary = "*****";
        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        String status="0";
        Date dateMS = new Date(System.currentTimeMillis());
        try {
            // 统一资源
            URL url = new URL(actionUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            if (!files.isDirectory()) {
                if(files!=null){
                    String filename =files.getName();
                    if (filename.endsWith(".png")) {
                        FileInputStream fStream = new FileInputStream(files);
                        int length = -1;
                        while ((length = fStream.read(buffer)) != -1) {
                            ds.write(buffer, 0, length);
                        }
                        fStream.close();
                    }
                }
            }
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
                status="1";
                MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传成功", "0");
            }
            else
            {
                MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    MyLog.writeLogtoFile("记录", "UploadImage", "UploadImage","图片上传失败", "0");
                }
            }
            return status;
        }
    }
}