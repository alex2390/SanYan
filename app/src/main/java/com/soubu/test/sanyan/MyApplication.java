package com.soubu.test.sanyan;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

//import com.chuanglan.shanyan_sdk.OneKeyLoginManager;


public class MyApplication extends Application {
    //填写您在闪验平台申请的appid和AppKey
    public static String appId = "jBHkJRt5";
    public static String appKey = "VDdpw55k";

    @Override
    public void onCreate() {
        super.onCreate();
        //闪验sdk本地数据初始化set（）
        OneKeyLoginManager.getInstance().set(getApplicationContext(), appId, appKey);
		//设置授权页logo
        initAuthenticationLogo();
        //权限申请
        requestPermission(Permission.READ_PHONE_STATE);
    }


    //必须在init（）方法之前设置
    private void initAuthenticationLogo() {
         /*****************************************电信设置授权页logo方法************************************************/
        OneKeyLoginManager.getInstance().setCTCCImgLogo(R.drawable.oauth_logo);

        /*****************************************移动设置授权页logo方法************************************************/
        OneKeyLoginManager.getInstance().setCMCCImgLogo("oauth_logo");
        /*****************************************联通设置授权页logo方法************************************************/
        OneKeyLoginManager.getInstance().setCUCCImgLogo(R.drawable.oauth_logo);
        /*****************************************一键登录页面左上角帮助按钮 是否显示***********************************/
        OneKeyLoginManager.getInstance().setShowHelpBtn(true);
        /*****************************************一键登录页面其他方式登录按钮 是否显示*********************************/
        OneKeyLoginManager.getInstance().setShowOtherBtn(true);
    }

    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .permission(permissions)
                .onGranted(new Action() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onAction(List<String> permissions) {
                        //闪验SDK网络初始化
                        OneKeyLoginManager.getInstance().init();
                        //闪验SDK预取号（可选，能加速拉起授权页）
                        OneKeyLoginManager.getInstance().PreInitiaStart();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                    }
                })
                .start();
    }
}
