package com.soubu.test.sanyan.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lipengfeii on 2018/2/3.
 * 网络请求实例
 */

public class RequestExample {


    public ShanYanService getService() {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HTTP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //创建网络请求接口实例
        ShanYanService service = retrofit.create(ShanYanService.class);
        return service;
    }


    /**
     * 获取手机号
     *
     * @param appId
     * @param accessToken
     * @param telecom
     * @param timestamp
     * @param randoms
     * @param sign
     * @return
     */
    public Call<ResponseBody> getPhoneCode(String appId, String accessToken, String telecom, String timestamp, String randoms, String sign, String version, String device) {
        Call<ResponseBody> service = null;
        Map<String, String> params = NetParams.getInstance().oneKeyLogin(appId, accessToken, telecom, timestamp, randoms, sign, version, device);
        if (telecom.equals("CMCC")) {//移动
            service = getService().getMobile01(params);
        } else if (telecom.equals("CUCC")) {//联通
            service = getService().getMobile02(params);
        } else if (telecom.equals("CTCC")) {//电信
            service = getService().getMobile03(params);
        }
        return service;
    }


    /**
     * 本机校验
     *
     * @param appId
     * @param accessCode
     * @param mobile
     * @param telecom
     * @param timestamp
     * @param randoms
     * @param tradeNo
     * @param sign
     * @return
     */
    public Call<ResponseBody> phoneCodeVerify(String appId, String accessCode, String mobile, String telecom,
                                              String timestamp, String randoms, String tradeNo, String version, String sign) {
        Call<ResponseBody> service = null;
        Map<String, String> params = NetParams.getInstance().mobileVerify(appId, accessCode, mobile, telecom, timestamp,
                randoms, tradeNo, version, sign);
        if (telecom.equals("CMCC")) {//移动
            service = getService().phoneVerify01(params);
        } else if (telecom.equals("CUCC")) {//联通
            service = getService().phoneVerify02(params);
        } else if (telecom.equals("CTCC")) {//电信
            service = getService().phoneVerify03(params);
        }
        return service;
    }
}
