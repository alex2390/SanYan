package com.soubu.test.sanyan;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;


public class MyApplication extends Application {
    //填写您在闪验平台申请的appid和AppKey
    public static String appId = "jBHkJRt5";
    public static String appKey = "VDdpw55k";

    @Override
    public void onCreate() {
        super.onCreate();
        OneKeyLoginManager.getInstance().init(getApplicationContext(), appId, appKey, new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {
            }
        });
    }


}
