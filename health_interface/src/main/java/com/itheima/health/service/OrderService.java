package com.itheima.health.service;


import com.itheima.health.pojo.Order;

import java.util.Map;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/15 17:04
 * @Version 1.0
 */

public interface OrderService {
    /**
     *提交预约
     * @param orderInfo
     * @return
     */
    Order submitOrder(Map<String, String> orderInfo);



    /**
     * 根据ID查询预约信息
     * @param id
     * @return
     */
    Map<String, String> findDetaileById(int id);
}
