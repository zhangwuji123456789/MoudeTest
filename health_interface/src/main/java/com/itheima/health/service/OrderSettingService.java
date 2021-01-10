package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/10 19:44
 * @Version 1.0
 */
public interface OrderSettingService {
    /*
    * 上传文件
    * */
    void upload(List<OrderSetting> collect);

    /**
     * 通过月份展示预约信息
     * @param mouth
     * @return
     */
    List<Map> findByMouth(String mouth);


    /**
     * 根据参数修改值
     */
    void update(OrderSetting orderSetting);
}
