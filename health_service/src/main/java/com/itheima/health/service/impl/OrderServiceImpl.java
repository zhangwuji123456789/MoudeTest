package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @Author Ma zhi lin
 * @Date 2021/1/15 17:10
 * @Version 1.0
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    /**
     * 提交预约
     *
     * @param paraMap
     * @return
     */
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    @Transactional
    public Order submitOrder(Map<String, String> orderInfo) {
        //根据体检日期查询预约设置
        String orderDateStr = orderInfo.get("orderDate");
        //查询 因为是日期所以要转成日期
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;

        try {
            //格式化
            orderDate = sim.parse(orderDateStr);
        } catch (ParseException e) {
           throw new MyException("日期格式不正确");
        }
        //通过预约日期查询预约设置
        OrderSetting osInDb = orderSettingDao.findOrderByData(orderDate);
        if (null != osInDb) {
            //判断是否有设置
            //判断是否预约已经满了。并选择其他日期
            //已预约人数
            int osInDbReservations = osInDb.getReservations();
            //最大预约数
            int osInDbNumber = osInDb.getNumber();
            //判断已预约人数是否大于最大预约数
            if (osInDbReservations >= osInDbNumber) {
                //预约已满
                throw new MyException("所选日期预约已满，请选择其他日期");
            }
        }else {

            //报错 所选日期不能预约，请选择其他日期
            throw  new MyException("所选的日期不能预约，请选其他日期");
        }
        //2.会员操作 获取手机号码
        String telephone = orderInfo.get("telephone");
        //手机号码是否存在
        Member member = memberDao.findByTelephone(telephone);
        Order order = new Order();
        //订单套餐ID和 预约时间
        String setmealId = orderInfo.get("setmealId");
        order.setSetmealId(Integer.valueOf(setmealId));
        order.setOrderDate(orderDate);
        //不存在  判断
        if (null == member) {
            //如果不存在就添加
            //添加会员  返回主键 会员信息由前端传过来
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setRemark("微信预约自动注册");
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setIdCard(orderInfo.get("idCard"));
            member.setPassword(telephone.substring(5));//默认密码
            memberDao.add(member);
            order.setMemberId(member.getId());

        }else {
            //存在的 判断是否重复预约  通过套餐ID 会员ID 预约日期 存在：就报错不能预约

            Integer memberId = member.getId();
            //动态查询 可以直接查询 这三个ID 是不同的三个表
            order.setMemberId(memberId);
            //动态查询的方法
            List<Order> orderList = orderDao.findByCondition(order);
            //存在 报错
            if (!CollectionUtils.isEmpty(orderList)) {
                throw new MyException("不能重复预约");
            }
        }
        //更新已经预约的人数，防止超卖 行锁，更新成功返回1，失败返回0
        int count = orderSettingDao.editReservationsByOrderDate(osInDb);
        if (count == 0) {
            throw new MyException("所选日期预约已满，请选择其他日期");
        }

        //3.订单表操作 添加预约
        //预约的类型
        order.setOrderType(orderInfo.get("orderType"));
        //预约的状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);
        return order;
    }


    /**
     * 根据ID查询预约信息
     * @param id
     * @return
     */
    @Override
    public Map<String, String> findDetaileById(int id) {
        return orderDao.findById4Detail(id);
    }


}
