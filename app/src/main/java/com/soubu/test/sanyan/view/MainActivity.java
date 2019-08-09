package com.soubu.test.sanyan.view;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.utils.DES;
import com.soubu.test.sanyan.MyApplication;
import com.soubu.test.sanyan.R;
import com.soubu.test.sanyan.http.RequestExample;
import com.soubu.test.sanyan.utils.AbScreenUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private ImageView iv_flash;
    private LoadingDialog dialog;
    private RequestExample example = new RequestExample();

    public void onResume() {
        super.onResume();
       // MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
      //  MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        iv_flash = findViewById(R.id.iv_flash);
        int width = AbScreenUtils.getScreenWidth(this);
        int height = 1010 * width / 1242;
        AbScreenUtils.setViewWH(iv_flash, width, height);
        Glide.with(this).load(R.mipmap.gif).into(iv_flash);
    }


    public void doClick(View view) {
        dialog = LoadingDialog.showDialog(this);
        dialog.show();
        requestPermission(Permission.READ_PHONE_STATE);
    }

    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .permission(permissions)
                .onGranted(new Action() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onAction(List<String> permissions) {
                        //设置状态监听
                        OneKeyLoginManager.getInstance().setOneKeyLoginListener(10, new OneKeyLoginListener() {
                            @Override
                            public void getPhoneCode(int code, String result) {
                                dialog.dismiss();
                                dialog = null;
                                dataProcessing(code, result);
                            }
                        });
                       //开始拉取授权页
                        OneKeyLoginManager.getInstance().LoginStart();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                    }
                })
                .start();
    }

    private void dataProcessing(int code, String result) {
        if (code == 1000) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                //应用的appid
                String appId = jsonObject.optString("appId");
                //token
                String accessToken = jsonObject.optString("accessToken");
                //运营商
                String telecom = jsonObject.optString("telecom");
                //网络时间
                String timestamp = jsonObject.optString("timestamp");
                //随机数
                String randoms = jsonObject.optString("randoms");
                //版本号
                String version = jsonObject.optString("version");
                //签名
                String sign = jsonObject.optString("sign");
                //device
                String device = jsonObject.optString("device");
                getPhone(appId, accessToken, telecom, timestamp, randoms, sign, version, device);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //请参考（6.返回码说明） 进行处理
            AbScreenUtils.showToast(getApplicationContext(), result);
        }
    }

    /**
     * 开发者调用自己的后台接口
     *
     * @param appId
     * @param accessToken
     * @param telecom
     * @param timestamp
     * @param randoms
     * @param sign
     * @param version
     */
    private void getPhone(String appId, String accessToken, String telecom, String timestamp, String randoms, String sign, String version, String device) {
        Call<ResponseBody> call = example.getPhoneCode(appId, accessToken, telecom, timestamp, randoms, sign, version, device);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String response_data = new String(response.body().bytes());
                    JSONObject json = new JSONObject(response_data);
                    Log.d(TAG, "onResponse: "+json);
                    int code = json.optInt("code");
                    if (code == 200000) {
                        JSONObject data = json.getJSONObject("data");
                        String mobileName = data.optString("mobileName");
                        //获取到的手机号
                        String phone = DES.decryptDES(mobileName, MyApplication.appKey);
                        AbScreenUtils.showToast(getApplicationContext(), "登录成功，手机号为：" + phone);
                    } else {
                        String msg = json.optString("message");
                        AbScreenUtils.showToast(getApplicationContext(), msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AbScreenUtils.showToast(getApplicationContext(), "网络异常");
            }
        });
    }
}
