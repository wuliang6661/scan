package com.wul.scan.api;

import com.wul.scan.data.BaseResult;
import com.wul.scan.data.BindDianChiRequest;
import com.wul.scan.data.BindGuochengRequest;
import com.wul.scan.data.GuoChengBo;
import com.wul.scan.data.LoginBO;
import com.wul.scan.data.OrderBO;
import com.wul.scan.data.OrderResult;
import com.wul.scan.data.PageBO;
import com.wul.scan.data.UserBO;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

//    String URL = "http://47.98.53.141:8099";   //测试服
//    String URL = "http://172.16.244.44:80";  //测试服2
    String URL = "http://192.168.1.200:80";  //测试服2
//    String URL = "http://mapi.open.yinghezhong.com/";  //正式环境2


    /**
     * 登录
     */
    @POST("/battery_webservice/dProcessLabel/login")
    Observable<BaseResult<UserBO>> login(@Body LoginBO loginBO);


    /**
     * 查询工单
     */
    @POST("/battery_webservice/dProcessLabel/selectOrderAndProduct")
    Observable<BaseResult<List<OrderBO>>> getOrderNum(@Body PageBO pageBO);


    /**
     * 绑定过程标签
     */
    @POST("/battery_webservice/dProcessLabel/insertProcessLabel")
    Observable<BaseResult<GuoChengBo>> bindProcess(@Body BindGuochengRequest request);

    /**
     * 绑定过程电芯关联
     */
    @POST("/battery_webservice/dProcessLabel/insertProcessBattery")
    Observable<BaseResult<String>> bindBattery(@Body BindDianChiRequest message);

    /**
     * 根据工单查询工单详情
     */
    @POST("/battery_webservice/dProcessLabel/selectOrderAndProduct")
    Observable<BaseResult<List<OrderBO>>> selectByOrderNum(@Body OrderResult result);


}
