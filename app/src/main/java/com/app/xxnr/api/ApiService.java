package com.app.xxnr.api;



import com.app.xxnr.Constants;
import com.app.xxnr.UserManager;
import com.app.xxnr.bean.AgentReportResult;
import com.app.xxnr.bean.AgentReportTotalResult;
import com.app.xxnr.bean.CustomerDetailResult;
import com.app.xxnr.bean.CustomerListResult;
import com.app.xxnr.bean.BaseResult;
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
import com.app.xxnr.event.TokenErrorEvent;
import com.app.xxnr.utils.StringUtil;
import com.app.xxnr.utils.T;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 何鹏 on 2016/8/3.
 * Api服务类 用来处理参数 对返回的结果进行处理
 */
public class ApiService {

    private static ApiService mInstance;
    private ApiInterface mApiInterface;
    private UserManager userManager;


    private ApiService(ApiInterface apiInterface, UserManager userManager) {
        this.mApiInterface = apiInterface;
        this.userManager = userManager;
    }


    /**
     * 获取单例引用
     */
    public static ApiService getInstance(ApiInterface apiInterface, UserManager userManager) {
        if (mInstance == null) {
            synchronized (ApiService.class) {
                if (mInstance == null) {
                    mInstance = new ApiService(apiInterface, userManager);
                }
            }
        }
        return mInstance;
    }


    /**
     * 登录
     *
     * @param phone    用户名
     * @param password 密码
     */
    @SuppressWarnings("unchecked")
    public Observable<LoginResult> login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", phone);
        map.put("password", password);
        return (Observable<LoginResult>) initObservable(mApiInterface.login(map));
    }


    /**
     * 获取公钥
     */
    @SuppressWarnings("unchecked")
    public Observable<PublicKeyResult> getPublicKey() {
        return (Observable<PublicKeyResult>) initObservable(mApiInterface.getPublicKey());
    }


    /**
     * 客户列表
     */
    @SuppressWarnings("unchecked")
    public Observable<CustomerListResult> getUsers(int page, int query, String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        if (StringUtil.checkStr(search)) {
            map.put("search", search);
        } else {
            map.put("query", query);
        }
        map.put("max", 20);
        map.put("token", userManager.getToken());
        return (Observable<CustomerListResult>) initObservable(mApiInterface.getUsers(map));
    }


    /**
     * RscInfo
     */
    @SuppressWarnings("unchecked")
    public Observable<RscInfoResult> getRscInfo(String id) {
        return (Observable<RscInfoResult>) initObservable(mApiInterface.getRscInfo(id, userManager.getToken()));
    }

    /**
     * CustomerDetail
     */
    @SuppressWarnings("unchecked")
    public Observable<CustomerDetailResult> getCustomerDetail(String id) {
        return (Observable<CustomerDetailResult>) initObservable(mApiInterface.getCustomerDetail(id, userManager.getToken()));
    }

    /**
     * changeCustomer
     */
    @SuppressWarnings("unchecked")
    public Observable<BaseResult> changeCustomer(String customerId, List<String> list) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", customerId);
        map.put("token", userManager.getToken());
        map.put("typeVerified", list);
        return (Observable<BaseResult>) initObservable(mApiInterface.changeCustomer(map));
    }

    /**
     * 潜在客户列表
     */
    @SuppressWarnings("unchecked")
    public Observable<PotentialListResult> getPotentList(int page, String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("max", 20);
        map.put("token", userManager.getToken());
        map.put("page", page);
        if (StringUtil.checkStr(search)) {
            map.put("search", search);
        }
        return (Observable<PotentialListResult>) initObservable(mApiInterface.getPotentList(map));
    }

    /**
     * 潜在客户详情
     */
    @SuppressWarnings("unchecked")
    public Observable<PotentialDetailResult> getPotentDetail(String customerId) {
        return (Observable<PotentialDetailResult>) initObservable(mApiInterface.getPotentDetail(customerId, userManager.getToken()));
    }


    /**
     * 订单列表
     */
    @SuppressWarnings("unchecked")
    public Observable<OrderListResult> getOrderList(int page, int type, String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        if (StringUtil.checkStr(search)) {
            map.put("search", search);
        } else {
            map.put("type", type);
        }
        map.put("max", 20);
        map.put("token", userManager.getToken());
        return (Observable<OrderListResult>) initObservable(mApiInterface.getOrderList(map));
    }


    /**
     * 订单详情
     */
    @SuppressWarnings("unchecked")
    public Observable<OrderDetailResult> getOrderDetail(String orderId) {
        return (Observable<OrderDetailResult>) initObservable(mApiInterface.getOrderDetail(orderId, userManager.getToken()));
    }

    /**
     * 线下支付方式
     */
    @SuppressWarnings("unchecked")
    public Observable<OfflinePayTypeResult> getOfflinePayType() {
        return (Observable<OfflinePayTypeResult>) initObservable(mApiInterface.getOfflinePayType(userManager.getToken()));
    }

    /**
     * 线下付款网点
     */
    @SuppressWarnings("unchecked")
    public Observable<OfflineStateListResult> getOfflineStateList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", 1);
        map.put("max", 50);
        map.put("token", userManager.getToken());
        return (Observable<OfflineStateListResult>) initObservable(mApiInterface.getOfflineStateList(map));
    }


    /**
     * 审核付款
     */
    @SuppressWarnings("unchecked")
    public Observable<BaseResult> confirmOfflinePay(String paymentId, String offlinePayType, String RSCId) {
        Map<String, Object> map = new HashMap<>();
        map.put("paymentId", paymentId);
        map.put("offlinePayType", offlinePayType);
        map.put("RSCId", RSCId);
        map.put("token", userManager.getToken());
        return (Observable<BaseResult>) initObservable(mApiInterface.confirmOfflinePay(map));
    }


    /**
     * 发货到服务站
     */
    @SuppressWarnings("unchecked")
    public Observable<BaseResult> skuDelivery(List<Object> SKUs, String deliveringOrderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("SKUs", SKUs);
        map.put("id", deliveringOrderId);
        map.put("token", userManager.getToken());
        return (Observable<BaseResult>) initObservable(mApiInterface.skuDelivery(map));
    }


    /**
     * 日报（天）
     */
    @SuppressWarnings("unchecked")
    public Observable<DailyReportResult> getDailyReport(String date) {
        return (Observable<DailyReportResult>) initObservable(mApiInterface.getDailyReport(date, userManager.getToken()));
    }

    /**
     * 日报（累计）
     */
    @SuppressWarnings("unchecked")
    public Observable<StatisticReportResult> getStatistic() {
        return (Observable<StatisticReportResult>) initObservable(mApiInterface.getStatistic(userManager.getToken()));
    }

    /**
     * 周报（周）
     */
    @SuppressWarnings("unchecked")
    public Observable<EveryWeekReportResult> getWeeklyReport(String date) {
        return (Observable<EveryWeekReportResult>) initObservable(mApiInterface.getWeeklyReport(date, userManager.getToken()));
    }

    /**
     * 经纪人排行(昨日)
     */
    @SuppressWarnings("unchecked")
    public Observable<AgentReportResult> queryAgentReportYesterday(String sort, int sortOrder, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        map.put("sortOrder", sortOrder);
        map.put("page", page);
        map.put("max", 20);
        map.put("token", userManager.getToken());
        return (Observable<AgentReportResult>) initObservable(mApiInterface.queryAgentReportYesterday(map));
    }


    /**
     * 经纪人排行(汇总:业绩)
     */
    @SuppressWarnings("unchecked")
    public Observable<AgentReportTotalResult> queryAgentReports(String sort, int sortOrder, String search, int type, String dateStart, String dateEnd, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("max", 20);
        map.put("type", type);
        map.put("dateStart", dateStart);
        map.put("dateEnd", dateEnd);
        if (StringUtil.checkStr(search)) {
            map.put("search", search);
        }
        if (sortOrder != 0) {
            map.put("sortOrder", sortOrder);
            map.put("sort", sort);
        }
        map.put("token", userManager.getToken());
        return (Observable<AgentReportTotalResult>) initObservable(mApiInterface.queryAgentReports(map));
    }


    /**
     * 一周数据（详情）
     */
    @SuppressWarnings("unchecked")
    public Observable<WeekReportResult> queryDailyReport(String dateStart,String dateEnd) {
        return (Observable<WeekReportResult>) initObservable(mApiInterface.queryDailyReport(dateStart,dateEnd,userManager.getToken()));
    }

    /**
     * 几周周数据（详情）
     */
    @SuppressWarnings("unchecked")
    public Observable<SomeWeekReportResult> queryWeeklyReport(String dateStart, String dateEnd) {
        return (Observable<SomeWeekReportResult>) initObservable(mApiInterface.queryWeeklyReport(dateStart,dateEnd,userManager.getToken()));
    }
    /**
     * 集中处理Api事件
     */
    private Observable<? extends BaseResult> initObservable(Observable<? extends BaseResult> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new HttpFunction())
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    if (!throwable.getMessage().contains(Constants.API_ERROR)) {
                        T.showShort("请检查网络");
                    }
                    throw new RuntimeException(throwable);
                });
    }

    /**
     * 用来统一处理Http的resultCode
     */
    private class HttpFunction implements Func1<BaseResult, Observable<BaseResult>> {
        @Override
        public Observable<BaseResult> call(BaseResult baseResult) {
            if (baseResult.code.equals(Constants.SUCCESS_CODE)) {
                return Observable.just(baseResult);
            } else {
                T.showShort(baseResult.message);
                if (baseResult.code.equals(Constants.TOKEN_ERROR)) {
                    EventBus.getDefault().post(new TokenErrorEvent());
                }
                return Observable.error(new Throwable(Constants.API_ERROR));
            }
        }
    }

}
