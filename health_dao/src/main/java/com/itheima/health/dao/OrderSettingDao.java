package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/10 19:53
 * @Version 1.0
 */
public interface OrderSettingDao {

    /*
    * 根据时间查询数据是否存在
    * */
    OrderSetting findOrderByData(Date orderDate);

    /*
     * 如果有就修改数据
     * */

    void upload(OrderSetting orderSetting);


    /**
     * 反之就添加
     * @param orderSetting
     */

    void add(OrderSetting orderSetting);


    /**
     * 通过月份展示预约信息
     * @param map
     * @return
     */
    List<OrderSetting> findByMouth(Map map);

    /**
     * //更新已预约人数
     * @param orderSetting
     * @return 受影响的记录数
     */

    int editReservationsByOrderDate(OrderSetting orderSetting);


}
