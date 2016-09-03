package com.app.xxnr.api;

import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.bean.BaseResult;
import com.app.xxnr.bean.CustomerDetailResult;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.bean.DailyReportResult;
import com.app.xxnr.bean.EveryWeekReportResult;
import com.app.xxnr.bean.OfflinePayTypeResult;
import com.app.xxnr.bean.OfflineStateListResult;
import com.app.xxnr.bean.OrderDetailResult;
import com.app.xxnr.bean.OrderListResult;
import com.app.xxnr.bean.PotentialDetailResult;
import com.app.xxnr.bean.PotentialListResult;
import com.app.xxnr.bean.RscInfoResult;
import com.app.xxnr.bean.SomeWeekReportResult;
import com.app.xxnr.bean.StatisticReportResult;
import com.app.xxnr.bean.WeekReportResult;
import com.app.xxnr.bean.dbbeans.LoginResult;
import com.app.xxnr.bean.PublicKeyResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 何鹏 on 2016/8/3.
 * api接口 定义请求类型 参数 path 和返回结果类型
 */
public interface ApiInterface {
    /**
     * 获取公钥
     */
    @GET("/api")
    Observable<PublicKeyResult> getPublicKey();


    /**
     * 登录
     */
    @Headers("Content-Type: application/json")
    @POST("/manager")
    Observable<LoginResult> login(@Body Map<String, Object> map);

    /**
     * 客户列表
     */
    @GET("/manager")
    Observable<CustomerListResult> getUsers(@QueryMap Map<String, Object> map);

    /**
     * Rsc信息
     */
    @GET("/manager/{id}")
    Observable<RscInfoResult> getRscInfo(@Path("id") String id, @Query("token") String token);

    /**
     * 客户详情
     */
    @GET("/manager/{id}")
    Observable<CustomerDetailResult> getCustomerDetail(@Path("id") String id, @Query("token") String token);


    /**
     * 认证客户
     */
    @Headers("Content-Type: application/json")
    @PUT("/manager")
    Observable<BaseResult> changeCustomer(@Body Map<String, Object> map);


    /**
     * 潜在客户列表
     */
    @GET("/manager")
    Observable<PotentialListResult> getPotentList(@QueryMap Map<String, Object> map);


    /**
     * 潜在客户详情
     */
    @GET("/manager{id}")
    Observable<PotentialDetailResult> getPotentDetail(@Path("id") String id, @Query("token") String token);


    /**
     * 订单列表
     */
    @GET("/manager")
    Observable<OrderListResult> getOrderList(@QueryMap Map<String, Object> map);

    /**
     * 订单详情
     */
    @GET("/manager/{id}")
    Observable<OrderDetailResult> getOrderDetail(@Path("id") String id, @Query("token") String token);


    /**
     * 线下支付方式
     */
    @GET("/manager")
    Observable<OfflinePayTypeResult> getOfflinePayType(@Query("token") String token);


    /**
     * 付款网点
     */
    @GET("/manager")
    Observable<OfflineStateListResult> getOfflineStateList(@QueryMap() Map<String, Object> map);


    /**
     * 审核付款
     */
    @Headers("Content-Type: application/json")
    @POST("/manager")
    Observable<BaseResult> confirmOfflinePay(@Body() Map<String, Object> map);


    /**
     * 发货到服务站
     */
    @Headers("Content-Type: application/json")
    @PUT("/manager")
    Observable<BaseResult> skuDelivery(@Body() Map<String, Object> map);

    /**
     * 日报(天)
     */
    @GET("/manager")
    Observable<DailyReportResult> getDailyReport(@Query("date") String date, @Query("token") String token);

    /**
     * 日报(累计)
     */
    @GET("/manager")
    Observable<StatisticReportResult> getStatistic(@Query("token") String token);

    /**
     * 周报(周)
     */
    @GET("/manager")
    Observable<EveryWeekReportResult> getWeeklyReport(@Query("date") String date, @Query("token") String token);

    /**
     * 经纪人排行(昨日)
     */
    @GET("/manager")
    Observable<AgentReportResult> queryAgentReportYesterday(@QueryMap() Map<String, Object> map);

    /**
     * 经纪人排行(汇总)
     */
    @GET("/manager")
    Observable<AgentReportTotalResult> queryAgentReports(@QueryMap() Map<String, Object> map);


    /**
     * 数据详情 一周数据
     */
    @GET("/manager")
    Observable<WeekReportResult> queryDailyReport(@Query("dateStart") String dateStart
            , @Query("dateEnd") String dateEnd, @Query("token") String token);


    /**
     * 数据详情 获得几周数据
     */
    @GET("/manager")
    Observable<SomeWeekReportResult> queryWeeklyReport(@Query("dateStart") String dateStart
            , @Query("dateEnd") String dateEnd, @Query("token") String token);
}
