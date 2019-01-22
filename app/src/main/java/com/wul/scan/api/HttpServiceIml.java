package com.wul.scan.api;

import com.wul.scan.MyApplication;
import com.wul.scan.data.BindDianChiRequest;
import com.wul.scan.data.BindGuochengRequest;
import com.wul.scan.data.GuoChengBo;
import com.wul.scan.data.LoginBO;
import com.wul.scan.data.OrderBO;
import com.wul.scan.data.OrderResult;
import com.wul.scan.data.PageBO;
import com.wul.scan.data.UserBO;
import com.wul.scan.rx.RxResultHelper;

import java.util.List;

import rx.Observable;

/**
 * Created by wuliang on 2017/4/19.
 * <p>
 * 所有网络请求方法
 */

public class HttpServiceIml {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }


    public static Observable<List<OrderBO>> getOrderNum(PageBO pageBO) {
        pageBO.token = MyApplication.token;
        return getService().getOrderNum(pageBO).compose(RxResultHelper.httpRusult());
    }

    /**
     * 绑定过程编号
     */
    public static Observable<GuoChengBo> bindGuocheng(BindGuochengRequest request) {
        request.token = MyApplication.token;
        return getService().bindProcess(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 绑定电池标签
     */
    public static Observable<String> bindDianChi(BindDianChiRequest request) {
        request.token = MyApplication.token;
        return getService().bindBattery(request).compose(RxResultHelper.httpRusult());
    }


    public static Observable<UserBO> login(LoginBO loginBO) {
        loginBO.code = "001";
        return getService().login(loginBO).compose(RxResultHelper.httpRusult());
    }

    public static Observable<List<OrderBO>> selectOrderNum(OrderResult result) {
        result.setToken(MyApplication.token);
        return getService().selectByOrderNum(result).compose(RxResultHelper.httpRusult());
    }

}
