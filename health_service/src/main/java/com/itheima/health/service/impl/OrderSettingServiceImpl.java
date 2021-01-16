package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/10 19:45
 * @Version 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void upload(List<OrderSetting> collect) {
        for (OrderSetting orderSetting : collect) {
            //先查询时间
           OrderSetting os = orderSettingDao.findOrderByData(orderSetting.getOrderDate());

            if (null != os) {
                //判断上传
                orderSettingDao.upload(orderSetting);
            }else{
                //添加
                orderSettingDao.add(orderSetting);
            }
        }


    }
    /**
     * 通过月份展示预约信息
     * @param mouth
     * @return
     */
    @Override
    public List<Map> findByMouth(String mouth) {
        //设置开始和结束的日期
        String monthStar =  mouth + "-1";//开始
        String monthEnd =  mouth +"-31"; //结束
        Map map = new HashMap<>(); //string 不用加泛型
        map.put("monthStar",monthStar);
        map.put("monthEnd",monthEnd);
        List<OrderSetting> orderSettings = orderSettingDao.findByMouth(map);
        List<Map> list = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map<String, Integer> map1 = new HashMap<>();
            map1.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            map1.put("number",orderSetting.getNumber());//可预约人数
            map1.put("reservations",orderSetting.getReservations());//已预约人数
            list.add(map1);
        }
        return list;
    }

    /**
     * 根据参数修改值
     * @param orderSetting
     */
    @Override
    public void update(OrderSetting orderSetting) {

        OrderSetting os = orderSettingDao.findOrderByData(orderSetting.getOrderDate());

        if (null != os) {
            //判断上传
            orderSettingDao.upload(orderSetting);
        }else{
            //添加
            orderSettingDao.add(orderSetting);
        }
    }


}
