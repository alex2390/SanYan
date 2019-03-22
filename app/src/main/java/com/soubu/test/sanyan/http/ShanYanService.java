package com.soubu.test.sanyan.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lipengfeii on 2018/2/3.
 * 闪验接口服务类
 */

public interface ShanYanService {

    /**
     * 移动一键登录
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-query-m")
    @FormUrlEncoded
    Call<ResponseBody> getMobile01(@FieldMap Map<String, String> params);

    /**
     * 联通一键登录
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-query-u")
    @FormUrlEncoded
    Call<ResponseBody> getMobile02(@FieldMap Map<String, String> params);



    /**
     * 电信一键登录
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-query-t")
    @FormUrlEncoded
    Call<ResponseBody> getMobile03(@FieldMap Map<String, String> params);


    /**
     * 移动本机校验
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-validate-m")
    @FormUrlEncoded
    Call<ResponseBody> phoneVerify01(@FieldMap Map<String, String> params);



    /**
     * 联通本机校验
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-validate-u")
    @FormUrlEncoded
    Call<ResponseBody> phoneVerify02(@FieldMap Map<String, String> params);



    /**
     * 电信本机校验
     * @param params
     * @return
     */
    @POST("/open/flashsdk/mobile-validate-t")
    @FormUrlEncoded
    Call<ResponseBody> phoneVerify03(@FieldMap Map<String, String> params);


}
