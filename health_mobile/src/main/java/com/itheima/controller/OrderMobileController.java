package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.swing.*;
import java.util.Map;
import org.slf4j.Logger;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/13 18:39
 * @Version 1.0
 * 1.获取前端的参数 因为是JSON所以要用requestbody 然后这个是验证码和手机号所以需要一个map来进行接收， 一个对象，一个参数
 * 2.整个流程就是 1.先校验验证码 ，然后添加手机号，再把验证码和手机号拼接到一起放到一个key对象里面，做判断个Redis里面的验证码比对成功则放行失败则提示重新发送
 * 校验结束之后就把Redis里面的验证码移出防止重复提交，最后设置预约类型，orderType，然后在通过service查询里面放参数类型
 *
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    private static final Logger log = LoggerFactory.getLogger(OrderMobileController.class);
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo ){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //添加手机号码
        String telephone =orderInfo.get("telephone");
        // 获取验证码 拼接手机号
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        //redis中的验证码
        String yzRedis = jedis.get(key);

        if (StringUtil.isEmpty(yzRedis)) {
            //如果没值就重新发送
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 前端的验证码
        String validateCode = orderInfo.get("validateCode");
        if (!yzRedis.equals(validateCode)) {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //移出Redis中的验证码，防止重复提交
        jedis.del(key);
        //设置预约类型 设置商品的类型 并个提示
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        //预约成功页面展示时需要用到的ID 提交预约
        Order order = orderService.submitOrder(orderInfo);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 根据ID查询预约信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String,String> orderInfo = orderService.findDetaileById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }

}
